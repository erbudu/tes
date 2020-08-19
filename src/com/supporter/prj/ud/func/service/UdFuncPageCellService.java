package com.supporter.prj.ud.func.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ud.control.InputControlType;
import com.supporter.prj.ud.func.dao.UdFuncPageCellDao;
import com.supporter.prj.ud.func.dao.UdFuncPageDao;
import com.supporter.prj.ud.func.entity.UdFuncPage;
import com.supporter.prj.ud.func.entity.UdFuncPageCell;

@Service
public class UdFuncPageCellService {
	@Autowired
	private UdFuncPageCellDao codeDao;
	@Autowired
	private UdFuncPageDao udfDao;

	/**
	 * 根据主键获取信息.
	 * 
	 * @param codeId
	 *            主键
	 * @return WmsUdFuncPageCell
	 */
	public UdFuncPageCell get(String codeId) {
		return codeDao.get(codeId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param codeId
	 * @return
	 */
	public List<UdFuncPageCell> initEditOrViewPage(String codeId,
			UserProfile user) {

		List<UdFuncPageCell> list = new ArrayList<UdFuncPageCell>();
		if (StringUtils.isNotBlank(codeId)) {
			UdFuncPage page = udfDao.get(codeId);
			Long col = page.getColCount();
			for (int i = 0; i < col; i++) {
				UdFuncPageCell udf = new UdFuncPageCell();
				udf.setPageId(codeId);
				list.add(udf);
			}
		}
		return list;
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user
	 *            用户信息
	 * @param jqGridReq
	 *            jqgrid请求对象
	 * @param codeIds
	 *            多个逗号分隔
	 * @return JqGrid
	 */
	public List<UdFuncPageCell> getGrid(UserProfile user, JqGrid jqGrid,
			UdFuncPageCell code) {
		return codeDao.findPage(jqGrid, code);
	}

	/**
	 * 删除
	 * 
	 * @param user
	 *            用户信息
	 * @param moduleIds
	 *            主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String docIds) {
		if (StringUtils.isNotBlank(docIds)) {
			for (String docId : docIds.split(",")) {
				UdFuncPageCell codeDb = this
						.getWmsUdFuncPageCellFromBuffer(docId);
				if (codeDb == null) {
					continue;
				}
				this.recursiveDelete(user, codeDb);
			}
			// 记录日志
			/*
			 * UdFuncPageCellUtils.saveUdFuncPageCellOperateLog(user, null,
			 * UdFuncPageCell.LogOper.UdFuncPageCell_DEL.getOperName(),
			 * "{delUdFuncPageCellIds : " + codeIds + "}");
			 */
		}
	}

	/**
	 * 保存或更新
	 * 
	 * @param user
	 *            用户信息
	 * @param materialCode
	 *            实体类
	 * @return
	 */
	public UdFuncPageCell saveOrUpdate(UserProfile user, UdFuncPage code,
			Map<String, Object> valueMap) {
		UdFuncPageCell ret = null;
		if (code != null) {
			UdFuncPage u = udfDao.get(code.getPageId());
			Long group = code.getFullAccessGroup();
			Long proc = code.getUseProc();
			if (group == null) {
				group = Long.valueOf("0");
			}
			if (proc == null) {
				proc = Long.valueOf("0");
			}
			u.setFullAccessGroup(group);
			u.setUseProc(proc);
			udfDao.update(u);

			List<UdFuncPageCell> list = code.getList();
			int i = 1;
			if (list != null) {
				for (UdFuncPageCell udFuncPageCell : list) {
					udFuncPageCell
							.setCellId(com.supporter.util.UUIDHex.newId());
					udFuncPageCell.setPageId(code.getPageId());
					udFuncPageCell.setListId(String.valueOf(i));
					i++;
					codeDao.save(udFuncPageCell);
				}
			}
			u.setList(list);
			final UdFuncPage page = u;
			udfDao.getHibernateTemplate().getSessionFactory().openSession()
					.doWork(new Work() {
						public void execute(Connection conn) {
							// 这里已经得到connection了，可以继续你的JDBC代码。
							// 注意不要close了这个connection。
							DatabaseMetaData metaDate;
							try {
								metaDate = conn.getMetaData();
								ResultSet rs = metaDate.getTables(null, null,
										"U_" + page.getPageId().substring(5),
										new String[] { "TABLE" });
								if (rs.next()) {
									System.out.println("" + "： 表存在! ");
								} else {
									String sql = " create table U_"
											+ page.getPageId().substring(5)
											+ " ("
											+ " record_id varchar2(32) , ";

									List<UdFuncPageCell> l = page.getList();
									String sql2 = "";
									if (l != null) {
										for (UdFuncPageCell u : l) {
											String modelName = u.getInputMode();
											sql += modelName
													+ u.getListId()
													+ " "
													+ InputControlType
															.getDataType(
																	modelName,
																	u
																			.getLabelName())
													+ ", ";
											if (modelName
													.equals(InputControlType.SELECT_EMP)
													|| modelName
															.equals(InputControlType.SELECT_ORG)) {
												sql2 += modelName
														+ u.getListId()
														+ "_id "
														+ InputControlType
																.getDataType(
																		modelName,
																		u
																				.getLabelName())
														+ ", ";
											}
										}
									}
									sql += sql2;
									sql += " related_files varchar2(255) , "
											+ " created_by_id varchar2(32) , "
											+ " created_by varchar2(64) ,"
											+ " created_date varchar2(27) , "
											+ " modified_by_id varchar2(32) ,"
											+ " modified_by varchar2(64) ,"
											+ " modified_date varchar2(27), "
											+ " primary key(record_id)) ";
									System.out.println(sql);
									conn.prepareStatement(sql).execute();
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});
		}
		return ret;

	}

	/**
	 * 递归删除
	 * 
	 * @param UdFuncPageCell
	 */
	private void recursiveDelete(UserProfile user, UdFuncPageCell code) {
		if (true) {// 可删除
			String codeId = code.getPageId();
			this.codeDao.delete(codeId);
		}

	}

	public UdFuncPageCell getWmsUdFuncPageCellFromBuffer(String codeIdOrName) {
		if (StringUtils.isBlank(codeIdOrName)) {
			return null;
		}
		UdFuncPageCell code = this.get(codeIdOrName);
		return code;
	}

}
