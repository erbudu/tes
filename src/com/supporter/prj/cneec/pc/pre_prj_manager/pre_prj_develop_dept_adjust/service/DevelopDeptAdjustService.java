package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_dept_adjust.service;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_dept_adjust.dao.DevelopDeptAdjustDao;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_dept_adjust.entity.DevelopDeptAdjust;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_filing.entity.PrePrjManagerFiling;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_filing.service.PrePrjManagerFilingService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: Service
 * @Description: 功能模块表
 * @author tiansen
 * 
 */
@Service
public class DevelopDeptAdjustService {
	@Autowired
	private  DevelopDeptAdjustDao developDeptAdjustDao;
	@Autowired
	private PrePrjManagerFilingService managerFilingService;
	
	/**
	 * 分页查询
	 * @param user
	 * @param jqGrid
	 * @param entity
	 * @return
	 */
	public List< DevelopDeptAdjust> getGrid(UserProfile user, JqGrid jqGrid,  DevelopDeptAdjust entity){
		return developDeptAdjustDao.findPage(user, jqGrid, entity);
	}
	
	/**
	 * 根据id获取对象
	 * @param  id
	 * @return
	 */
	public  DevelopDeptAdjust get(String  id){
		return developDeptAdjustDao.get( id);
	}
	
	/**
	 * 初始化备案信息
	 * @param  id
	 * @param user
	 * @return
	 */
	public  DevelopDeptAdjust initEditOrViewPage(String  id,String filingId,UserProfile user){
		 DevelopDeptAdjust entity =null;
		if(StringUtils.isBlank( id)){
			entity = newFiling(filingId,user);
		}else{
			entity =get( id);
		}
		return entity;
	}
	/**
	 * 新建初始化备案信息
	 * @param user
	 * @return
	 */
	public  DevelopDeptAdjust newFiling(String filingId,UserProfile user){
		 DevelopDeptAdjust entity = new  DevelopDeptAdjust();
		entity.setAdd(true);
		entity.setId(com.supporter.util.UUIDHex.newId());
		entity.setCreatedBy(user.getName());
		Date date = new Date();
		entity.setCreatedDate(date);
		Dept dept = user.getDept();
		if(dept!=null){
			entity.setDeptId(dept.getDeptId());
			entity.setDeptName(dept.getName());
		}
		entity.setModifiedBy(user.getName());
		entity.setModifiedById(user.getPersonId());
		entity.setModifiedDate(date);
		if(StringUtils.isNotBlank(filingId)) {
			PrePrjManagerFiling filing = managerFilingService.get(filingId);
			entity.setPrjNameEn(filing.getPrjNameEn());
			entity.setPrjNameZh(filing.getPrjNameZh());
			entity.setFilingId(filingId);
			entity.setOldDeptId(filing.getDeptId());
			entity.setOldDeptName(filing.getDeptName());
			//源协作部门
			
			//源跟踪人
			entity.setOldFollowingManId(filing.getFollowingManId());
			entity.setOldFollowingMan(filing.getFollowingMan());
		}
		return entity;
	}
	
	/**
	 * 保存或更细实体类
	 * @param entity
	 * @param user
	 * @return
	 */
	public  DevelopDeptAdjust saveOrUpdate( DevelopDeptAdjust entity,UserProfile user){
		if(entity.getAdd()){
			developDeptAdjustDao.save(entity);
		}else{
			developDeptAdjustDao.update(entity);
		}
		return entity;
	}
	
	/**
	 * 删除
	 * @param filingIds
	 * @param user
	 */
	public void delete (String ids,UserProfile user){
		if(StringUtils.isNotBlank(ids)){
			for (String  id : ids.split(",")) {
				developDeptAdjustDao.delete( id);
			}
		}
	}
}
