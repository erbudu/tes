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
import com.supporter.prj.linkworks.oa.bulletin.entity.VBulletin;
import com.supporter.prj.linkworks.oa.bulletin.service.BulletinService;
import com.supporter.prj.linkworks.oa.consignment.dao.ConsignmentDao;
import com.supporter.prj.linkworks.oa.consignment.entity.Consignment;

@Repository
public class VBulletinDao extends MainDaoSupport < VBulletin , String >{
	@Autowired
	private ConsignmentDao consignmentDao;
	@Autowired
	private DeptResourceService deptResourceService;
	@Autowired
	private AcccntAccessCountDao acccntAccessCountDao;
	/**
	 * 获取发布的滚动信息
	 * @param jqGrid
	 * @param attr
	 * @return
	 */
	public List<VBulletin> findPageV(JqGrid jqGrid,String attr,String dateFrom,String dateTo,String bulletinType,String authorizeeIds) {
		if(StringUtils.isNotBlank(attr)){
			jqGrid.addHqlFilter(
					"bulletinTitle like ? or bulletinType like ? or publisherName like ? ", 
					"%" + attr + "%","%" + attr + "%","%" + attr + "%");
		}
		
		if(StringUtils.isNotBlank(dateFrom)){
			jqGrid.addHqlFilter(" messageDate >= ? ", dateFrom);
			
		}
		
		if(StringUtils.isNotBlank(dateTo)){
			jqGrid.addHqlFilter("  messageDate <= ? ",dateTo);
			
		}
		
		if(StringUtils.isNotBlank(bulletinType)&& !bulletinType.equals("1")){
			jqGrid.addHqlFilter(" bulletinType like ? ","%" + bulletinType+ "%");
			
		}
		if(StringUtils.isNotBlank(authorizeeIds)){
			jqGrid.addHqlFilter(" authorizeeId in ("+authorizeeIds+")");
			jqGrid.addHqlFilter("canRead = ? or canWrite =? or canDelete=? or fullAccess= ? ", 
					"1","1","1","1");
		}
		
		List<VBulletin> list = this.retrievePage(jqGrid);
		
		List<VBulletin> retList = new ArrayList<VBulletin>();
		
		for (VBulletin bulletin : list) {
		if(bulletin.getPublishStatus() ==Bulletin.DRAFT){
			bulletin.setPublishName("草稿");
		}else{
			bulletin.setPublishName("已发布");
		}
		//添加授权书的title
		if(StringUtils.isNotBlank(bulletin.getRelatedIdVal())){
			Consignment consignment = consignmentDao.get(bulletin.getRelatedIdVal());
			StringBuffer stb = new StringBuffer();
			stb.append("授权书").append("(有效时间：").append(consignment.getDateFrom()).append("至")
			.append(consignment.getDateTo()).append(")");
			bulletin.setBulletinTitle(stb.toString());
		}
		
		//添加列表显示的公告板
		if(StringUtils.isNotBlank(bulletin.getDeptResourceId())){
			String fullDeptResourceName= deptResourceService.getDeptNameAndResourceName(bulletin.getDeptResourceId());
			bulletin.setFullDeptResourceName(fullDeptResourceName);
		}
		
		//添加阅读次数
		int readCount = 0;
		if(acccntAccessCountDao.getcount(bulletin.getBulletinId()) != null){
			readCount = acccntAccessCountDao.getcount(bulletin.getBulletinId()).getReadCount();
		};
		bulletin.setReadCount(readCount);
		
		retList.add(bulletin);
		}
		jqGrid.setRows(retList);
			
		return retList;
		
	}
	
	
	
	/**
	 * 首页获取发布的公告信息
	 * 
	 * @param jqGrid
	 * @param attr
	 * @return
	 */
	public List<VBulletin> findPageOnTopOne(JqGrid jqGrid, int includeConsignment,String authorizeeIds) {
		
		jqGrid.addHqlFilter( " publishStatus = ? ",VBulletin.PUBLISHED);
		if (includeConsignment == BulletinService.IncludeConsignment.ONLY_CONSIGNMENT){
			//授权书
			jqGrid.addHqlFilter(" (relatedIdVal is not null)");
		} else if (includeConsignment == BulletinService.IncludeConsignment.NO_CONSIGNMENT){
			jqGrid.addHqlFilter(" (relatedIdVal is null or relatedIdVal='')");
		}
		if(StringUtils.isNotBlank(authorizeeIds)){
			jqGrid.addHqlFilter(" authorizeeId in ("+authorizeeIds+")");
			jqGrid.addHqlFilter("canRead = ? or canWrite =? or canDelete=? or fullAccess= ? ", 
					"1","1","1","1");
		}
		
//		jqGrid.addHqlFilter(" alwaysOnTop = ? ","1");
//		jqGrid.addHqlFilter(" instr( ? , deptResourceId ) > 0 ",
//				"402805945de98e33015de996bf0d0002,402805915e360e47015e361321710004,402805915e360e47015e36139c580005,402805915e360e47015e3614a9730007,402805915e360e47015e361462a60006"
//		);
		jqGrid.addSortPropertyDesc("messageDate");
		jqGrid.addSortPropertyDesc("alwaysOnTop");
		jqGrid.addSortPropertyDesc("bulletinId");
		List<VBulletin> list = this.retrievePage(jqGrid);
		List<VBulletin> retList = new ArrayList<VBulletin>();
		
		for (VBulletin bulletin : list) {
			//添加授权书的title
			if(StringUtils.isNotBlank(bulletin.getRelatedIdVal())){
				if (includeConsignment == BulletinService.IncludeConsignment.NO_CONSIGNMENT){
					continue;
				}
				Consignment consignment = consignmentDao.get(bulletin.getRelatedIdVal());
				StringBuffer stb = new StringBuffer();
				stb.append("授权书").append("(有效时间：").append(consignment.getDateFrom()).append("至").append(consignment.getDateTo()).append(")");
				bulletin.setBulletinTitle(stb.toString());
			} else {
				if (includeConsignment == BulletinService.IncludeConsignment.ONLY_CONSIGNMENT){
					continue;
				}
			}

			retList.add(bulletin);
		}
		jqGrid.setRows(retList);
			
		return retList;
		
	}
	
	
	
	
	
	
	
}
