package com.supporter.prj.ppm.ecc.base_info.fund_plan.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.base_info.fund_plan.dao.EccFundPlanDao;
import com.supporter.prj.ppm.ecc.base_info.fund_plan.entity.EccFundPlan;

/**
 * @Title: Service
 * @Description: 出口管制资金安排.
 * @author YAN
 * @date 2019-08-16 18:42:25
 * @version V1.0
 */
@Service
public class EccFundPlanService {

	@Autowired
	private EccFundPlanDao eccFundPlanDao;

	/**
	 * 根据主键获取出口管制资金安排.
	 * 
	 * @param fpId 主键
	 * @return EccFundPlan
	 */
	public EccFundPlan get(String fpId) {
		return eccFundPlanDao.get(fpId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccFundPlan > getGrid(UserProfile user, JqGrid jqGrid, EccFundPlan eccFundPlan) {
		return eccFundPlanDao.findPage(jqGrid, eccFundPlan);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param fpId
	 * @return
	 */
	public EccFundPlan initEditOrViewPage(String fpId) {
		if (StringUtils.isBlank(fpId)) {// 新建
			EccFundPlan entity = new EccFundPlan();
			entity.setFpId(com.supporter.util.UUIDHex.newId());
			return entity;
		} else {// 编辑
			EccFundPlan entity = eccFundPlanDao.get(fpId);
			entity = initEditOrViewPage(fpId,entity);
			return entity;
		}
	}
	public EccFundPlan initEditOrViewPage(String fpId,EccFundPlan entity) {
		if (entity==null){
			entity = new EccFundPlan();
			entity.setFpId(fpId);
		}
		return entity;
	}
	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccFundPlan 实体类
	 * @return
	 */
	public EccFundPlan saveOrUpdate(UserProfile user, EccFundPlan eccFundPlan) {
		if (StringUtils.isBlank(eccFundPlan.getFpId())) {// 新建
			return this.save(user, eccFundPlan);
		} else {// 编辑
			return this.update(user, eccFundPlan);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccFundPlan 实体类
	 * @return
	 */
	private EccFundPlan save(UserProfile user, EccFundPlan eccFundPlan) {
		this.eccFundPlanDao.save(eccFundPlan);
		return eccFundPlan;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccFundPlan 实体类
	 * @return
	 */
	private EccFundPlan update(UserProfile user, EccFundPlan eccFundPlan) {
		EccFundPlan eccFundPlanDb = this.eccFundPlanDao.get(eccFundPlan.getFpId());
		if(eccFundPlanDb == null) {
			return this.save(user, eccFundPlan);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccFundPlan, eccFundPlanDb);
			this.eccFundPlanDao.update(eccFundPlanDb);
			return eccFundPlanDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param fpIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String fpIds) {
		if(StringUtils.isNotBlank(fpIds)) {
			for(String fpId : fpIds.split(",")) {
				EccFundPlan eccFundPlanDb = this.eccFundPlanDao.get(fpId);
				this.eccFundPlanDao.delete(fpId);
			}
		}
	}
	/**
	 * 全部删除
	 * @param user
	 * @param eccId
	 */
	public void batchDelAll(UserProfile user, String eccId) {
		this.eccFundPlanDao.deleteByProperty("eccId",eccId);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param fpId
	 * @param fpName
	 * @return
	 */
	public boolean nameIsValid(String fpId,String fpName) {
		return this.eccFundPlanDao.nameIsValid( fpId, fpName);
	}

}

