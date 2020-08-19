package com.supporter.prj.ppm.contract.effect.report.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.effect.report.dao.ContractEffectReportConDao;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReportCon;

/**
 * @Title: Service
 * @Description: 报审审核结果.
 * @author YAN
 * @date 2019-09-05 17:09:59
 * @version V1.0
 */
@Service
public class ContractEffectReportConService {

	@Autowired
	private ContractEffectReportConDao contractEffectReportConDao;

	/**
	 * 根据主键获取报审审核结果.
	 * 
	 * @param contractEffectRvConId 主键
	 * @return ContractEffectReportCon
	 */
	public ContractEffectReportCon get(String contractEffectRvConId) {
		return contractEffectReportConDao.get(contractEffectRvConId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractEffectReportCon > getGrid(UserProfile user, JqGrid jqGrid, ContractEffectReportCon contractEffectReportCon) {
		return contractEffectReportConDao.findPage(jqGrid, contractEffectReportCon);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param contractEffectRvConId
	 * @return
	 */
	public ContractEffectReportCon initEditOrViewPage(String contractEffectRvConId) {
		if (StringUtils.isBlank(contractEffectRvConId)) {// 新建
			ContractEffectReportCon entity = new ContractEffectReportCon();
			return entity;
		} else {// 编辑
			ContractEffectReportCon entity = contractEffectReportConDao.get(contractEffectRvConId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractEffectReportCon 实体类
	 * @return
	 */
	public ContractEffectReportCon saveOrUpdate(UserProfile user, ContractEffectReportCon contractEffectReportCon) {
		if (StringUtils.isBlank(contractEffectReportCon.getContractEffectRvConId())) {// 新建
			return this.save(user, contractEffectReportCon);
		} else {// 编辑
			return this.update(user, contractEffectReportCon);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractEffectReportCon 实体类
	 * @return
	 */
	private ContractEffectReportCon save(UserProfile user, ContractEffectReportCon contractEffectReportCon) {
		this.contractEffectReportConDao.save(contractEffectReportCon);
		return contractEffectReportCon;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractEffectReportCon 实体类
	 * @return
	 */
	private ContractEffectReportCon update(UserProfile user, ContractEffectReportCon contractEffectReportCon) {
		ContractEffectReportCon contractEffectReportConDb = this.contractEffectReportConDao.get(contractEffectReportCon.getContractEffectRvConId());
		if(contractEffectReportConDb == null) {
			return this.save(user, contractEffectReportCon);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectReportCon, contractEffectReportConDb);
			this.contractEffectReportConDao.update(contractEffectReportConDb);
			return contractEffectReportConDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param contractEffectRvConIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String contractEffectRvConIds) {
		if(StringUtils.isNotBlank(contractEffectRvConIds)) {
			for(String contractEffectRvConId : contractEffectRvConIds.split(",")) {
				ContractEffectReportCon contractEffectReportConDb = this.contractEffectReportConDao.get(contractEffectRvConId);
				this.contractEffectReportConDao.delete(contractEffectRvConId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractEffectRvConId
	 * @param contractEffectRvConName
	 * @return
	 */
	public boolean nameIsValid(String contractEffectRvConId,String contractEffectRvConName) {
		return this.contractEffectReportConDao.nameIsValid( contractEffectRvConId, contractEffectRvConName);
	}

}

