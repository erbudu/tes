package com.supporter.prj.ppm.contract.sign.review.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.sign.review.dao.ContractSignReviewEmpDao;
import com.supporter.prj.ppm.contract.sign.review.entity.ContractSignReviewEmp;

/**
 * @Title: Service
 * @Description: 评审人员.
 * @author YAN
 * @date 2019-09-06 18:35:30
 * @version V1.0
 */
@Service
public class ContractSignReviewEmpService {

	@Autowired
	private ContractSignReviewEmpDao contractSignReviewEmpDao;

	/**
	 * 根据主键获取评审人员.
	 * 
	 * @param reviewEmpId 主键
	 * @return ContractSignReviewEmp
	 */
	public ContractSignReviewEmp get(String reviewEmpId) {
		return contractSignReviewEmpDao.get(reviewEmpId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractSignReviewEmp > getGrid(UserProfile user, JqGrid jqGrid, ContractSignReviewEmp contractSignReviewEmp) {
		return contractSignReviewEmpDao.findPage(jqGrid, contractSignReviewEmp);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param reviewEmpId
	 * @return
	 */
	public ContractSignReviewEmp initEditOrViewPage(String reviewEmpId) {
		if (StringUtils.isBlank(reviewEmpId)) {// 新建
			ContractSignReviewEmp entity = new ContractSignReviewEmp();
			return entity;
		} else {// 编辑
			ContractSignReviewEmp entity = contractSignReviewEmpDao.get(reviewEmpId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractSignReviewEmp 实体类
	 * @return
	 */
	public ContractSignReviewEmp saveOrUpdate(UserProfile user, ContractSignReviewEmp contractSignReviewEmp) {
		if (StringUtils.isBlank(contractSignReviewEmp.getReviewEmpId())) {// 新建
			return this.save(user, contractSignReviewEmp);
		} else {// 编辑
			return this.update(user, contractSignReviewEmp);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractSignReviewEmp 实体类
	 * @return
	 */
	private ContractSignReviewEmp save(UserProfile user, ContractSignReviewEmp contractSignReviewEmp) {
		this.contractSignReviewEmpDao.save(contractSignReviewEmp);
		return contractSignReviewEmp;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractSignReviewEmp 实体类
	 * @return
	 */
	private ContractSignReviewEmp update(UserProfile user, ContractSignReviewEmp contractSignReviewEmp) {
		ContractSignReviewEmp contractSignReviewEmpDb = this.contractSignReviewEmpDao.get(contractSignReviewEmp.getReviewEmpId());
		if(contractSignReviewEmpDb == null) {
			return this.save(user, contractSignReviewEmp);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractSignReviewEmp, contractSignReviewEmpDb);
			this.contractSignReviewEmpDao.update(contractSignReviewEmpDb);
			return contractSignReviewEmpDb;
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
				ContractSignReviewEmp contractSignReviewEmpDb = this.contractSignReviewEmpDao.get(reviewEmpId);
				this.contractSignReviewEmpDao.delete(reviewEmpId);
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
		return this.contractSignReviewEmpDao.nameIsValid( reviewEmpId, reviewEmpName);
	}

}

