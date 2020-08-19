package com.supporter.prj.linkworks.oa.bulletin.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.dept_resource.service.DeptResourceService;
import com.supporter.prj.linkworks.oa.bulletin.entity.Bulletin;
import com.supporter.prj.linkworks.oa.bulletin.entity.BulletinContent;
import com.supporter.prj.linkworks.oa.bulletin.service.BulletinService;
import com.supporter.prj.linkworks.oa.consignment.dao.ConsignmentDao;
import com.supporter.prj.linkworks.oa.consignment.entity.Consignment;

/**
 * @Title: Entity
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0
 * 
 */
@Repository
public class BulletinDao extends MainDaoSupport<Bulletin, String> {

	@Autowired
	private ConsignmentDao consignmentDao;
	@Autowired
	private DeptResourceService deptResourceService;
	@Autowired
	private AcccntAccessCountDao acccntAccessCountDao;
	@Autowired
	private BulletinContentDao bulletinContentDAO;

	/**
	 * 获取发布的滚动信息
	 * 
	 * @param jqGrid
	 * @param attr
	 * @return
	 */
	public List<Bulletin> findPage(JqGrid jqGrid,String attr,String dateFrom,String dateTo,String bulletinType,String resourcehaveAuth,String bull_type) {
		if(StringUtils.isNotBlank(resourcehaveAuth)){
			jqGrid.addHqlFilter("deptResourceId in ("+resourcehaveAuth+")"); 
		}
		if(StringUtils.isNotBlank(bull_type)){			
			if(bull_type.equals("only_consignment")){//仅是授权公告
				// 授权书
				jqGrid.addHqlFilter(" (relatedIdVal is not null)");
			}else if(bull_type.equals("on_consignment")){//不包括授权公告
				jqGrid.addHqlFilter(" (relatedIdVal is null or relatedIdVal='')");
				
			}
		}
		if(StringUtils.isNotBlank(attr)){
			jqGrid.addHqlFilter(
					"bulletinTitle like ? or bulletinType like ? or publisherName like ? ", 
					"%" + attr + "%","%" + attr + "%","%" + attr + "%");
		}
		if (StringUtils.isNotBlank(dateFrom)) {
			jqGrid.addHqlFilter(" messageDate >= ? ", dateFrom);

		}

		if (StringUtils.isNotBlank(dateTo)) {
			jqGrid.addHqlFilter("  messageDate <= ? ", dateTo);

		}

		if (StringUtils.isNotBlank(bulletinType) && !bulletinType.equals("1")) {
			jqGrid.addHqlFilter(" bulletinType like ? ", "%" + bulletinType
					+ "%");

		}
		
		
		jqGrid.addSortPropertyDesc("createdDate");
		List<Bulletin> list = this.retrievePage(jqGrid);
		
		List<Bulletin> retList = new ArrayList<Bulletin>();

		for (Bulletin bulletin : list) {
			if (bulletin.getPublishStatus() == Bulletin.DRAFT) {
				bulletin.setPublishName("草稿");
			} else {
				bulletin.setPublishName("已发布");
			}

			// 添加授权书的title
			if (StringUtils.isNotBlank(bulletin.getRelatedIdVal())) {
				String bId = bulletin.getBulletinId();
				// 如果是老系统的历史数据，则从content中取数据
				if (bId.length() < 32) {
					BulletinContent bulletinContent = bulletinContentDAO
							.get(bId);

					if (bulletinContent != null) {
						String stra = bulletinContent.getBulletinContent();
						if (stra != null) {
							String strs[] = stra.split("授权有效期自");
							if (strs != null && strs.length > 1) {
								String dateTo_ = strs[1];
								String dateTos[] = dateTo_.split("。");
								if (dateTo_ != null && dateTos.length > 1) {
									String a = dateTos[0].replaceAll("年", "-")
											.replaceAll("月", "-").replaceAll(
													"日", "-").replace("-至",
													" 至 ");
									a = a.substring(0, a.length() - 1);
									//System.out.println("sss截取的日期：" + a);
									bulletin.setBulletinTitle("授权书(有效时间：" + a
											+ ")");
								}
							}
						}

					}

				} else {
					Consignment consignment = consignmentDao.get(bulletin
							.getRelatedIdVal());
					StringBuffer stb = new StringBuffer();
					if (consignment != null) {
						stb.append("授权书").append("(有效时间：").append(
								consignment.getDateFrom()).append("至").append(
								consignment.getDateTo()).append(")");
						bulletin.setBulletinTitle(stb.toString());
					} else {
						bulletin.setBulletinTitle("授权书");
					}
				}
			}

			// 添加列表显示的公告板
			if (StringUtils.isNotBlank(bulletin.getDeptResourceId())) {
				String fullDeptResourceName = deptResourceService
						.getDeptNameAndResourceName(bulletin
								.getDeptResourceId());
				bulletin.setFullDeptResourceName(fullDeptResourceName);
			}

			// 添加阅读次数
			int readCount = 0;
			if (acccntAccessCountDao.getcount(bulletin.getBulletinId()) != null) {
				readCount = acccntAccessCountDao.getcount(
						bulletin.getBulletinId()).getReadCount();
			}
			;
			bulletin.setReadCount(readCount);

			retList.add(bulletin);
		}
		jqGrid.setRows(retList);

		return retList;
	}	
	
	/**
	 * 获取业务授权的公告
	 * 
	 * @return
	 */
	public Bulletin getByRelatedIdVal(String consignmentId) {
		String hql = "from " + Bulletin.class.getName()
				+ " where relatedIdVal = '" + consignmentId
				+ "' and relatedTable = '" + Consignment.class.getName() + "'";
		List<Bulletin> list = this.find(hql);
		if (list.size() > 0) {
			if (list.get(0) != null) {
				return list.get(0);
			}
		}
		return null;
	}
	/**
	 * 首页获取发布的公告信息
	 * 
	 * @param jqGrid
	 * @param attr
	 * @return
	 */
	public List<Bulletin> findPageOnTopOne(JqGrid jqGrid, int includeConsignment,String resourcehaveAuth ) {

		jqGrid.addHqlFilter(" publishStatus = ? ", Bulletin.PUBLISHED);
		if (includeConsignment == BulletinService.IncludeConsignment.ONLY_CONSIGNMENT) {
			// 授权书
			jqGrid.addHqlFilter(" (relatedIdVal is not null)");
		} else if (includeConsignment == BulletinService.IncludeConsignment.NO_CONSIGNMENT) {
			jqGrid.addHqlFilter(" (relatedIdVal is null or relatedIdVal='')");
		}
		
		if(StringUtils.isNotBlank(resourcehaveAuth)){
			jqGrid.addHqlFilter("deptResourceId in ("+resourcehaveAuth+")"); 
		}
		// jqGrid.addHqlFilter(" alwaysOnTop = ? ","1");
		// jqGrid.addHqlFilter(" instr( ? , deptResourceId ) > 0 ",
		// "402805945de98e33015de996bf0d0002,402805915e360e47015e361321710004,402805915e360e47015e36139c580005,402805915e360e47015e3614a9730007,402805915e360e47015e361462a60006"
		// );
		jqGrid.addSortPropertyDesc("messageDate");
		jqGrid.addSortPropertyDesc("alwaysOnTop");
		jqGrid.addSortPropertyDesc("bulletinId");
		List<Bulletin> list = this.retrievePage(jqGrid);
		List<Bulletin> retList = new ArrayList<Bulletin>();

		for (Bulletin bulletin : list) {
			if (bulletin == null)
				continue;

			// 添加授权书的title
			if (StringUtils.isNotBlank(bulletin.getRelatedIdVal())) {
				if (includeConsignment == BulletinService.IncludeConsignment.NO_CONSIGNMENT) {
					continue;
				}
				String bId = bulletin.getBulletinId();
				// 如果是老系统的历史数据，则从content中取数据
				if (bId.length() < 32) {
					BulletinContent bulletinContent = bulletinContentDAO
							.get(bId);

					if (bulletinContent != null) {
						String stra = bulletinContent.getBulletinContent();
						if (stra != null) {
							String strs[] = stra.split("授权有效期自");
							if (strs != null && strs.length > 1) {
								String dateTo_ = strs[1];
								String dateTos[] = dateTo_.split("。");
								if (dateTo_ != null && dateTos.length > 1) {
									String a = dateTos[0].replaceAll("年", "-")
											.replaceAll("月", "-").replaceAll(
													"日", "-").replace("-至",
													" 至 ");
									a = a.substring(0, a.length() - 1);
									//System.out.println("bbb截取的日期：" + a);
									bulletin.setBulletinTitle("授权书(有效时间：" + a
											+ ")");
								}
							}
						}
					}

				} else {
					Consignment consignment = consignmentDao.get(bulletin
							.getRelatedIdVal());
					StringBuffer stb = new StringBuffer();
					if (consignment != null) {
						stb.append("授权书").append("(有效时间：").append(
								consignment.getDateFrom()).append("至").append(
								consignment.getDateTo()).append(")");
					}

					bulletin.setBulletinTitle(stb.toString());
				}
			} else {
				if (includeConsignment == BulletinService.IncludeConsignment.ONLY_CONSIGNMENT) {
					continue;
				}
			}

			retList.add(bulletin);
		}
		jqGrid.setRows(retList);

		return retList;

	}

	/**
	 * 手机端获取列表
	 * 
	 * @param jqGrid
	 * @return
	 */
	public List<Bulletin> iphonePage(JqGrid jqGrid) {
		jqGrid.addHqlFilter(" publishStatus = ? ", Bulletin.PUBLISHED);
		jqGrid.addSortPropertyDesc("messageDate");
		List<Bulletin> list = this.retrievePage(jqGrid);
		List<Bulletin> retList = new ArrayList<Bulletin>();

		for (Bulletin bulletin : list) {
			// 添加授权书的title
			if (StringUtils.isNotBlank(bulletin.getRelatedIdVal())) {
				String bId = bulletin.getBulletinId();
				//如果是老系统的历史数据，则从content中取数据
				if (bId.length() < 32) {
					BulletinContent bulletinContent = bulletinContentDAO
							.get(bId);
						
					if (bulletinContent != null) {
						String stra = bulletinContent.getBulletinContent();
						if(stra!=null){
							String strs[] = stra.split("授权有效期自");
							if (strs != null && strs.length > 1) {
								String dateTo_ = strs[1];
								String dateTos[] = dateTo_.split("。");
								if (dateTo_ != null && dateTos.length > 1) {
									String a = dateTos[0].replaceAll("年", "-")
											.replaceAll("月", "-").replaceAll("日",
													"-").replace("-至", " 至 ");
									a= a.substring(0,a.length()-1);
									//System.out.println("sss截取的日期：" + a);
									bulletin.setBulletinTitle("授权书(有效时间："+a+")");
								}
							}
						}
						
					}

				} else {
					Consignment consignment = consignmentDao.get(bulletin
							.getRelatedIdVal());
					StringBuffer stb = new StringBuffer();
					stb.append("授权书").append("(有效时间：").append(
							consignment.getDateFrom()).append("至").append(
							consignment.getDateTo()).append(")");
					bulletin.setBulletinTitle(stb.toString());
				}
			}
			// 添加列表显示的公告板
			if (StringUtils.isNotBlank(bulletin.getDeptResourceId())) {
				String fullDeptResourceName = deptResourceService
						.getDeptNameAndResourceName(bulletin
								.getDeptResourceId());
				bulletin.setFullDeptResourceName(fullDeptResourceName);
			}

			// 添加阅读次数
			int readCount = 0;
			if (acccntAccessCountDao.getcount(bulletin.getBulletinId()) != null) {
				readCount = acccntAccessCountDao.getcount(
						bulletin.getBulletinId()).getReadCount();
			}
			;
			bulletin.setReadCount(readCount);

			String rowOne = bulletin.getFullDeptResourceName();
			rowOne = rowOne + "<br/>";
			rowOne = rowOne
					+ "<a class=\"bulHref\" href=\"javaScript:javascript:uf_View('"
					+ bulletin.getBulletinId() + "');\"  >"
					+ bulletin.getBulletinTitle() + "</a>";
			String rowTwo = bulletin.getCreatedBy() + "<br/>"
					+ bulletin.getCreatedBy();
			String rowThree = bulletin.getMessageDate() + "<br/>"
					+ bulletin.getReadCount() + "次阅读";

			bulletin.setRowOne(rowOne);
			bulletin.setRowTwo(rowTwo);
			bulletin.setRowThree(rowThree);

			retList.add(bulletin);
		}
		jqGrid.setRows(retList);

		return retList;
	}

	public List<Bulletin> getBulletinList() {
		StringBuffer hql = new StringBuffer("from " + Bulletin.class.getName()
				+ " where 1=1 ");
		List<Bulletin> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
	/**
	 * 根据状态获取公告列表.
	 * @param bulletinStatus
	 * @return
	 */
	public List<Bulletin> getBulletinList(int bulletinStatus) {
		StringBuffer hql = new StringBuffer("from " + Bulletin.class.getName()
				+ " where  publishStatus = ? order by messageDate desc");
		List<Bulletin> list = this.find(hql.toString(),bulletinStatus);
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
}
