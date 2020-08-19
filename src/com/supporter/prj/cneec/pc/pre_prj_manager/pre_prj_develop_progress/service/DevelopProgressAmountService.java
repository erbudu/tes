package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.service;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.dao.DevelopProgressAmountDao;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.entity.DevelopProgressAmount;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: Service
 * @Description: 功能模块表
 * @author tiansen
 * 
 */
@Service
public class DevelopProgressAmountService {
	@Autowired
	private DevelopProgressAmountDao progressAmountDao;
	
	/**
	 * 分页查询
	 * @param user
	 * @param jqGrid
	 * @param filing
	 * @return
	 */
	public List<DevelopProgressAmount> getGrid(UserProfile user, JqGrid jqGrid, DevelopProgressAmount filing){
		List<DevelopProgressAmount> list=  progressAmountDao.findPage(user, jqGrid, filing);
		return list;
	}
	
	/**
	 * 根据id获取对象
	 * @param fundId
	 * @return
	 */
	public DevelopProgressAmount get(String fundId){
		return progressAmountDao.get(fundId);
	}
	
	/**
	 * 初始化备案信息
	 * @param fundId
	 * @param user
	 * @return
	 */
	public DevelopProgressAmount initEditOrViewPage(String recId,UserProfile user){
		DevelopProgressAmount filing =null;
		if(StringUtils.isBlank(recId)){
			filing = newFiling(user);
		}else{
			filing =get(recId);
		}
		return filing;
	}
	/**
	 * 新建初始化备案信息
	 * @param user
	 * @return
	 */
	public DevelopProgressAmount newFiling(UserProfile user){
		DevelopProgressAmount filing = new DevelopProgressAmount();
		return filing;
	}
	
	/**
	 * 保存或更细实体类
	 * @param filing
	 * @param user
	 * @return
	 */
	public DevelopProgressAmount saveOrUpdate(DevelopProgressAmount fund,UserProfile user){
		if(StringUtils.isBlank(fund.getRecId())){
			progressAmountDao.save(fund);
		}else{
			progressAmountDao.update(fund);
		}
		return fund;
	}
	
	/**
	 * 删除
	 * @param fundIds
	 * @param user
	 */
	public void delete (String recIds,UserProfile user){
		if(StringUtils.isNotBlank(recIds)){
			for (String recId : recIds.split(",")) {
				if(recId.length()!=32) {
					return;
				}
				DevelopProgressAmount fund =get(recId);
				if(fund!=null) {
					progressAmountDao.delete(fund);
				}
			}
		}
	}

	/**
	 * 新建保存
	 * @param prePrjInfoAdjustFund
	 */
	public void save(DevelopProgressAmount entity) {
		progressAmountDao.save(entity);
		
	}

	public void update(DevelopProgressAmount entity) {
		// TODO Auto-generated method stub
		progressAmountDao.update(entity);
	}

	/**
	 * @param developProgressAmount
	 */
	public void saveOrUpdate(DevelopProgressAmount developProgressAmount) {
		String recId = developProgressAmount.getRecId();
		if(StringUtils.isBlank(recId)||recId.length()!=32) {
			progressAmountDao.save(developProgressAmount);
		}else if(recId.length()==32){
			progressAmountDao.update(developProgressAmount);
		}
		
	}

	/**
	 * 根据主表删除从表
	 * @param progressId
	 * @param user
	 */
	public void deleteByProgress(String progressId, UserProfile user) {
		List<DevelopProgressAmount> list = progressAmountDao.findBy("progressId", progressId);
		if(list!=null&&list.size()>0) {
			progressAmountDao.delete(list);
		}
		
	}
}
