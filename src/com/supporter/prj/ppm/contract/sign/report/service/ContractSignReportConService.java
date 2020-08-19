package com.supporter.prj.ppm.contract.sign.report.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.sign.report.dao.ContractSignReportConDao;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReportCon;

/**
 * @Title: Service
 * @Description: 报审审核结果.
 * @author YAN
 * @date 2019-09-05 17:09:59
 * @version V1.0
 */
@Service
public class ContractSignReportConService {

	@Autowired
	private ContractSignReportConDao contractSignReportConDao;

	/**
	 * 根据主键获取报审审核结果.
	 * 
	 * @param contractSignRvConId 主键
	 * @return ContractSignReportCon
	 */
	public ContractSignReportCon get(String contractSignRvConId) {
		return contractSignReportConDao.get(contractSignRvConId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractSignReportCon > getGrid(UserProfile user, JqGrid jqGrid, ContractSignReportCon contractSignReportCon) {
		return contractSignReportConDao.findPage(jqGrid, contractSignReportCon);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param contractSignRvConId
	 * @return
	 */
	public ContractSignReportCon initEditOrViewPage(String contractSignRvConId) {
		if (StringUtils.isBlank(contractSignRvConId)) {// 新建
			ContractSignReportCon entity = new ContractSignReportCon();
			return entity;
		} else {// 编辑
			ContractSignReportCon entity = contractSignReportConDao.get(contractSignRvConId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractSignReportCon 实体类
	 * @return
	 */
	public ContractSignReportCon saveOrUpdate(UserProfile user, ContractSignReportCon contractSignReportCon) {
		if (StringUtils.isBlank(contractSignReportCon.getContractSignRvConId())) {// 新建
			return this.save(user, contractSignReportCon);
		} else {// 编辑
			return this.update(user, contractSignReportCon);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractSignReportCon 实体类
	 * @return
	 */
	private ContractSignReportCon save(UserProfile user, ContractSignReportCon contractSignReportCon) {
		this.contractSignReportConDao.save(contractSignReportCon);
		return contractSignReportCon;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractSignReportCon 实体类
	 * @return
	 */
	private ContractSignReportCon update(UserProfile user, ContractSignReportCon contractSignReportCon) {
		ContractSignReportCon contractSignReportConDb = this.contractSignReportConDao.get(contractSignReportCon.getContractSignRvConId());
		if(contractSignReportConDb == null) {
			return this.save(user, contractSignReportCon);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractSignReportCon, contractSignReportConDb);
			this.contractSignReportConDao.update(contractSignReportConDb);
			return contractSignReportConDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param contractSignRvConIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String contractSignRvConIds) {
		if(StringUtils.isNotBlank(contractSignRvConIds)) {
			for(String contractSignRvConId : contractSignRvConIds.split(",")) {
				ContractSignReportCon contractSignReportConDb = this.contractSignReportConDao.get(contractSignRvConId);
				this.contractSignReportConDao.delete(contractSignRvConId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractSignRvConId
	 * @param contractSignRvConName
	 * @return
	 */
	public boolean nameIsValid(String contractSignRvConId,String contractSignRvConName) {
		return this.contractSignReportConDao.nameIsValid( contractSignRvConId, contractSignRvConName);
	}

}

