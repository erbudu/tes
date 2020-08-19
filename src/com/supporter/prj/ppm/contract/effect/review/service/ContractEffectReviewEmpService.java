package com.supporter.prj.ppm.contract.effect.review.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.effect.review.dao.ContractEffectReviewEmpDao;
import com.supporter.prj.ppm.contract.effect.review.entity.ContractEffectReviewEmp;

/**
 * @Title: Service
 * @Description: 评审人员.
 * @author YAN
 * @date 2019-09-06 18:35:30
 * @version V1.0
 */
@Service
public class ContractEffectReviewEmpService {

	@Autowired
	private ContractEffectReviewEmpDao contractEffectReviewEmpDao;

	/**
	 * 根据主键获取评审人员.
	 * 
	 * @param reviewEmpId 主键
	 * @return ContractEffectReviewEmp
	 */
	public ContractEffectReviewEmp get(String reviewEmpId) {
		return contractEffectReviewEmpDao.get(reviewEmpId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractEffectReviewEmp > getGrid(UserProfile user, JqGrid jqGrid, ContractEffectReviewEmp contractEffectReviewEmp) {
		return contractEffectReviewEmpDao.findPage(jqGrid, contractEffectReviewEmp);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param reviewEmpId
	 * @return
	 */
	public ContractEffectReviewEmp initEditOrViewPage(String reviewEmpId) {
		if (StringUtils.isBlank(reviewEmpId)) {// 新建
			ContractEffectReviewEmp entity = new ContractEffectReviewEmp();
			return entity;
		} else {// 编辑
			ContractEffectReviewEmp entity = contractEffectReviewEmpDao.get(reviewEmpId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractEffectReviewEmp 实体类
	 * @return
	 */
	public ContractEffectReviewEmp saveOrUpdate(UserProfile user, ContractEffectReviewEmp contractEffectReviewEmp) {
		if (StringUtils.isBlank(contractEffectReviewEmp.getReviewEmpId())) {// 新建
			return this.save(user, contractEffectReviewEmp);
		} else {// 编辑
			return this.update(user, contractEffectReviewEmp);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractEffectReviewEmp 实体类
	 * @return
	 */
	private ContractEffectReviewEmp save(UserProfile user, ContractEffectReviewEmp contractEffectReviewEmp) {
		this.contractEffectReviewEmpDao.save(contractEffectReviewEmp);
		return contractEffectReviewEmp;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractEffectReviewEmp 实体类
	 * @return
	 */
	private ContractEffectReviewEmp update(UserProfile user, ContractEffectReviewEmp contractEffectReviewEmp) {
		ContractEffectReviewEmp contractEffectReviewEmpDb = this.contractEffectReviewEmpDao.get(contractEffectReviewEmp.getReviewEmpId());
		if(contractEffectReviewEmpDb == null) {
			return this.save(user, contractEffectReviewEmp);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectReviewEmp, contractEffectReviewEmpDb);
			this.contractEffectReviewEmpDao.update(contractEffectReviewEmpDb);
			return contractEffectReviewEmpDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param reviewEmpIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String reviewEmpIds) {
		if(StringUtils.isNotBlank(reviewEmpIds)) {
			for(String reviewEmpId : reviewEmpIds.split(",")) {
				ContractEffectReviewEmp contractEffectReviewEmpDb = this.contractEffectReviewEmpDao.get(reviewEmpId);
				this.contractEffectReviewEmpDao.delete(reviewEmpId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param reviewEmpId
	 * @param reviewEmpName
	 * @return
	 */
	public boolean nameIsValid(String reviewEmpId,String reviewEmpName) {
		return this.contractEffectReviewEmpDao.nameIsValid( reviewEmpId, reviewEmpName);
	}

}

