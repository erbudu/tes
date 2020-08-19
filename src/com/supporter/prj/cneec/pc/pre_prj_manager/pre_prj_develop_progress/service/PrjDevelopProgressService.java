package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.service;


import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.dao.PrjDevelopProgressDao;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.entity.DevelopProgress;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.entity.DevelopProgressAmount;
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
public class PrjDevelopProgressService {
	@Autowired
	private PrjDevelopProgressDao progressDao;
	@Autowired
	private PrePrjManagerFilingService managerFilingService;
	@Autowired
	private DevelopProgressAmountService progressAmountService;
	
	/**
	 * 分页查询
	 * @param user
	 * @param jqGrid
	 * @param progress
	 * @return
	 */
	public List<DevelopProgress> getGrid(UserProfile user, JqGrid jqGrid, DevelopProgress progress){
		return progressDao.findPage(user, jqGrid, progress);
	}
	
	/**
	 * 根据id获取对象
	 * @param progressId
	 * @return
	 */
	public DevelopProgress get(String progressId){
		return progressDao.get(progressId);
	}
	
	/**
	 * 初始化备案信息
	 * @param progressId
	 * @param user
	 * @return
	 */
	public DevelopProgress initEditOrViewPage(String progressId,String filingId,UserProfile user){
		DevelopProgress progress =null;
		if(StringUtils.isBlank(progressId)){
			progress = newFiling(filingId,user);
		}else{
			progress =get(progressId);
		}
		return progress;
	}
	/**
	 * 新建初始化备案信息
	 * @param user
	 * @return
	 */
	public DevelopProgress newFiling(String filingId,UserProfile user){
		DevelopProgress progress = new DevelopProgress();
		progress.setAdd(true);
		progress.setProgressId(com.supporter.util.UUIDHex.newId());
		progress.setCreatedBy(user.getName());
		Date date = new Date();
		progress.setCreatedDate(date);
		Dept dept = user.getDept();
		if(dept!=null){
			progress.setDeptId(dept.getDeptId());
			progress.setDeptName(dept.getName());
		}
		progress.setModifiedBy(user.getName());
		progress.setModifiedById(user.getPersonId());
		progress.setModifiedDate(date);
		if(StringUtils.isNotBlank(filingId)) {
			PrePrjManagerFiling filing = managerFilingService.get(filingId);
			progress.setPrjNameEn(filing.getPrjNameEn());
			progress.setPrjNameZh(filing.getPrjNameZh());
			progress.setFilingId(filingId);
			progress.setPrjOverview(filing.getPrjOverview());
			progress.setForeignAgents(filing.getForeignAgents());
		}
		return progress;
	}
	
	/**
	 * 保存或更细实体类
	 * @param progress
	 * @param user
	 * @return
	 */
	public DevelopProgress saveOrUpdate(DevelopProgress progress,UserProfile user){
		if(progress.getAdd()){
			progressDao.save(progress);
		}else{
			progressDao.update(progress);
		}
		//保存或更新从表
		List<DevelopProgressAmount> list = progress.getRecList();
		if(list!=null&&list.size()>0) {
			for (DevelopProgressAmount developProgressAmount : list) {
				developProgressAmount.setProgressId(progress.getProgressId());
				progressAmountService.saveOrUpdate(developProgressAmount);
			}
		}
		String delRec = progress.getDelRec();
		if(StringUtils.isNotBlank(delRec)) {
			progressAmountService.delete(delRec, user);
		}
		return progress;
	}
	
	/**
	 * 删除
	 * @param filingIds
	 * @param user
	 */
	public void delete (String filingIds,UserProfile user){
		if(StringUtils.isNotBlank(filingIds)){
			for (String progressId : filingIds.split(",")) {
				progressDao.delete(progressId);
				//删除从表
				progressAmountService.deleteByProgress(progressId, user);
			}
		}
	}
}
