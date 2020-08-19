package com.supporter.prj.ud.udf.service;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ud.control.InputControlFactory;
import com.supporter.prj.ud.control.InputControlType;
import com.supporter.prj.ud.func.dao.UdFuncPageCellDao;
import com.supporter.prj.ud.func.entity.UdFuncPageCell;
import com.supporter.prj.ud.func.service.UdFuncPageCellService;
import com.supporter.prj.ud.udf.dao.UdfDao;
import com.supporter.prj.ud.udf.entity.CommonType;
import com.supporter.prj.ud.udf.entity.Lable;
import com.supporter.util.CommonUtil;

@Service
public class UdfService {
	@Autowired
	private UdFuncPageCellService cellService;
	@Autowired
	private UdFuncPageCellDao cellDao;
	@Autowired
	private UdfDao udfDao;

	public void getGrid(UserProfile user, JqGrid jqGrid, String pageId,String keyWord) {
		List<UdFuncPageCell> cells = cellDao.findByKeyword(pageId);
		udfDao.findPage(jqGrid, pageId,keyWord,cells);
	}

	public Map<String, Object> initEditOrViewPage(String pageId, String recId,
			UserProfile user) {
		Map<String, Object> map = new LinkedHashMap<String, Object>(1);
		List<UdFuncPageCell> cells = cellDao.findByKeyword(pageId);
		String sql = "select  ";
		for (UdFuncPageCell udFuncPageCell : cells) {
			sql += udFuncPageCell.getInputMode() + udFuncPageCell.getListId()
					+ " , ";
		}
		sql += " CREATED_BY , MODIFIED_BY_ID , CREATED_DATE from U_"
				+ pageId.substring(5) + " where RECORD_ID = '" + recId + "'";
		List<Map<String, Object>> list = udfDao.getHibernateTemplate()
				.getSessionFactory().openSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (list != null && list.size() > 0) {
			/*
			 * query.setResultTransformer(new ResultTransformer(){ private
			 * static final long serialVersionUID = 1L; @Override public List
			 * transformList(List arg0) { // TODO Auto-generated method stub
			 * return arg0; } @Override public Object transformTuple(Object[]
			 * values, String[] columns) { int i = 0; System.out.println(i);
			 * for(String column : columns){
			 * 
			 * map.put(column, values[i++]); } map.put("add",false); return map; }
			 * 
			 * });
			 */
			map = list.get(0);
			map.put("recId", recId);
			map.put("add", false);
			map.put("modifiedBy", user.getName());
			map.put("modifiedById", user.getPersonId());
			map.put("modifiedByDate", new Date());
			return map;
		}
		// 新建的时候赋值问题//根据新建页面的下拉框取值动态赋值
		for (UdFuncPageCell udFuncPageCell : cells) {
			String setValue = udFuncPageCell.getDefaultValueSetting();
			String mode = udFuncPageCell.getInputMode();
			String listId = udFuncPageCell.getListId();
			if (mode.equals(InputControlType.SELECT_DATE)) {
				if (setValue.equals("当前时间")) {
					map.put(mode + listId, new Date());
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					map.put(mode + listId, sdf.format(new Date()));
				}
			} else if (mode.equals(InputControlType.SELECT_EMP)) {
				if (setValue.equals("当前用户")) {
					map.put(mode + listId, user.getName());
					map.put(mode + listId + "_id", user.getPersonId());
				}
			} else if (mode.equals(InputControlType.SELECT_ORG)) {
				if (setValue.equals("当前部门")) {
					Dept dept = user.getDept();
					if (dept != null) {
						map.put(mode + listId, dept.getName());
						map.put(mode + listId + "_id", user.getDeptId());
					}
				}
			} else {
				if (StringUtils.isNotBlank(setValue)) {
					map.put(mode + listId, setValue);
				}
			}
		}
		map.put("recId", com.supporter.util.UUIDHex.newId());
		map.put("createdBy", user.getName());
		map.put("createdById", user.getPersonId());
		map.put("createdDate", new Date());
		map.put("add", true);
		return map;
	}

	public List<Lable> getLable(String pageId, UserProfile user) {
		List<Lable> ls = new ArrayList<Lable>();
		// TODO Auto-generated method stub
		if (StringUtils.isNotBlank(pageId)) {
			List<UdFuncPageCell> cells = cellDao.findByKeyword(pageId);

			Lable l = new Lable();
			l.setLabel("主键");
			l.setKey(true);
			l.setName("RECORD_ID");
			l.setHidden(true);
			ls.add(l);
			for (UdFuncPageCell udFuncPageCell : cells) {
				l = new Lable();
				String lable = InputControlType.getCodeTable().getDisplay(
						udFuncPageCell.getLabelName());
				String model = udFuncPageCell.getInputMode()
						+ udFuncPageCell.getListId();
				l.setName(model);
				l.setLabel(lable);
				l.setHidden(false);
				l.setKey(false);
				ls.add(l);
			}
		}

		return ls;
	}

	public String createPage(String pageId) {
		String page = "";
		List<UdFuncPageCell> cells = cellDao.findByKeyword(pageId);
		if (cells != null) {
			for (UdFuncPageCell udFuncPageCell : cells) {
				String type = udFuncPageCell.getInputMode();
				if (StringUtils.isNotBlank(type)) {
					page += InputControlFactory.createInputControl(type)
							.getSuppTagCode(udFuncPageCell);
				}
			}
		}
		return page;
	}

	public void saveOrUpdate(HttpServletRequest request, UserProfile user,
			CommonType coo) {
		if (coo != null) {
			String bo = CommonUtil.trim(request.getParameter("add"));
			String pageId = CommonUtil.trim(request.getParameter("pageId"));
			List<Lable> ls = new ArrayList<Lable>();
			List<UdFuncPageCell> cells = cellDao.findByKeyword(pageId);
			String sql = "";
			if (bo.equals("true")) {
				// 执行新建操作
				sql = "insert into U_" + pageId.substring(5) + " ( ";
				for (UdFuncPageCell udFuncPageCell : cells) {
					Lable l = new Lable();
					String modelName = udFuncPageCell.getInputMode()
							+ udFuncPageCell.getListId();
					l.setName(modelName);
					sql += modelName + " , ";
					if (udFuncPageCell.getInputMode().equals(
							InputControlType.SELECT_EMP)
							|| modelName.equals(InputControlType.SELECT_ORG)) {
						sql += modelName + "_id , ";
						l.setLabel("id");
					}
					if (udFuncPageCell.getInputMode().equals(
							InputControlType.CHECKBOX)) {
						l.setKey(true);
					}
					ls.add(l);
				}
				sql += " record_id , created_by_id , created_by, created_date, modified_by_id,modified_by,modified_date) values( '";
				for (Lable lable : ls) {
					String name = lable.getName();
					String label = lable.getLabel();
					// 判断是否是复选框
					if (lable.getKey() != null && lable.getKey()) {
						String[] checkbox = request.getParameterValues(name);// 根据名字获得checkbox的值，注意是getParameters，后面要加上s，因为是一个数组
						String typeString = "";// 定义一个数组接收checkbox
						for (int i = 0; i < checkbox.length; i++) {// 对checkbox进行遍历
							typeString += checkbox[i] + ",";
						}
						typeString = typeString.substring(0, typeString
								.length() - 1); // 截取掉最后一个逗号
						sql += typeString + "' , '";
					} else {
						sql += CommonUtil.trim(request.getParameter(name))
								+ "' , '";
					}

					if (label != null && label.equals("id")) {
						sql += CommonUtil.trim(request.getParameter(name
								+ "_id"))
								+ "' , '";
					}
				}
				sql += coo.getRecId() + "' , '" + coo.getCreatedById()
						+ "' , '" + coo.getCreatedBy() + "' , '"
						+ coo.getCreatedDate() + "' , '"
						+ coo.getModifiedById() + "' , '" + coo.getModifiedBy()
						+ "' ,' " + coo.getModifiedByDate() + "' )";
				final String sql2 = sql;
				udfDao.getHibernateTemplate().getSessionFactory().openSession()
						.doWork(new Work() {
							@Override
							public void execute(Connection conn) {
								try {
									boolean a = conn.prepareStatement(sql2)
											.execute();
								} catch (Exception e) {
									System.out.println(e.toString());
								}

							}

						});

			} else {
				sql = "update U_" + pageId.substring(5) + " set ";
				for (UdFuncPageCell udFuncPageCell : cells) {

					String modelName = udFuncPageCell.getInputMode()
							+ udFuncPageCell.getListId();
					// 如果目标是复选框
					if (udFuncPageCell.getInputMode().equals(
							InputControlType.CHECKBOX)) {
						String[] checkbox = request
								.getParameterValues(modelName);// 根据名字获得checkbox的值，注意是getParameters，后面要加上s，因为是一个数组
						String typeString = "";// 定义一个数组接收checkbox
						for (int i = 0; i < checkbox.length; i++) {// 对checkbox进行遍历
							typeString += checkbox[i] + ",";
						}
						typeString = typeString.substring(0, typeString
								.length() - 1); // 截取掉最后一个逗号
						sql += modelName + " = '" + typeString + "' ,";
					} else {
						sql += modelName
								+ " = '"
								+ CommonUtil.trim(request
										.getParameter(modelName)) + "' ,";
						if (udFuncPageCell.getInputMode().equals(
								InputControlType.SELECT_EMP)
								|| modelName
										.equals(InputControlType.SELECT_ORG)) {
							sql += modelName
									+ "_id ='"
									+ CommonUtil.trim(request
											.getParameter(modelName + "_id"))
									+ "', ";
						}
					}

				}
				sql += "modified_by_id = '" + coo.getModifiedById()
						+ "' , modified_by = '" + coo.getModifiedBy()
						+ "' , modified_date = '" + coo.getModifiedByDate()
						+ "' where record_id = '" + coo.getRecId() + "'";
				final String sql2 = sql;
				udfDao.getHibernateTemplate().getSessionFactory().openSession()
						.doWork(new Work() {
							@Override
							public void execute(Connection conn) {
								try {
									boolean a = conn.prepareStatement(sql2)
											.execute();
								} catch (Exception e) {
									System.out.println(e.toString());
								}

							}

						});

			}
		}

	}

	// 获取查看页面表单
	public List<UdFuncPageCell> createViewPage(String pageId, UserProfile user) {
		if (StringUtils.isNotBlank(pageId)) {
			List<UdFuncPageCell> list = cellDao.findByKeyword(pageId);
			return list;
		}
		return null;
	}

	public void delete(UserProfile user, String recIds, String pageId) {
		if (StringUtils.isNotBlank(recIds)) {
			for (String recId : recIds.split(",")) {
				String sql = "delete from U_" + pageId.substring(5)
						+ " where RECORD_ID = '" + recId + "'";
				final String sql2 = sql;
				udfDao.getHibernateTemplate().getSessionFactory().openSession()
						.doWork(new Work() {
							@Override
							public void execute(Connection conn) {
								try {
									boolean a = conn.prepareStatement(sql2)
											.execute();
								} catch (Exception e) {
									System.out.println(e.toString());
								}

							}

						});
			}
			// 记录日志
			/*
			 * UdFuncPageCellUtils.saveUdFuncPageCellOperateLog(user, null,
			 * UdFuncPageCell.LogOper.UdFuncPageCell_DEL.getOperName(),
			 * "{delUdFuncPageCellIds : " + codeIds + "}");
			 */
		}
	}
}
