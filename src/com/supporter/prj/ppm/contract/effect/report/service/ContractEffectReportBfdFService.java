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

import com.supporter.prj.ppm.contract.effect.report.dao.ContractEffectReportBfdFDao;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReportBfdF;

import javax.persistence.Transient;

/**
 * @Title: Service
 * @Description: 主合同签约评审资料清单单文件.
 * @author YAN
 * @date 2019-09-05 17:09:58
 * @version V1.0
 */
@Service
public class ContractEffectReportBfdFService {

	@Autowired
	private ContractEffectReportBfdFDao contractEffectReportBfdFDao;

	/**
	 * 根据主键获取主合同签约评审资料清单单文件.
	 * 
	 * @param recordId 主键
	 * @return ContractEffectReportBfdF
	 */
	public ContractEffectReportBfdF get(String recordId) {
		return contractEffectReportBfdFDao.get(recordId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractEffectReportBfdF > getGrid(UserProfile user, JqGrid jqGrid, ContractEffectReportBfdF contractEffectReportBfdF) {
		return contractEffectReportBfdFDao.findPage(jqGrid, contractEffectReportBfdF);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param recordId
	 * @return
	 */
	public ContractEffectReportBfdF initEditOrViewPage(String recordId) {
		if (StringUtils.isBlank(recordId)) {// 新建
			ContractEffectReportBfdF entity = new ContractEffectReportBfdF();
			return entity;
		} else {// 编辑
			ContractEffectReportBfdF entity = contractEffectReportBfdFDao.get(recordId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractEffectReportBfdF 实体类
	 * @return
	 */
	@Transient
	public ContractEffectReportBfdF saveOrUpdate(UserProfile user, ContractEffectReportBfdF contractEffectReportBfdF) {
		if (StringUtils.isBlank(contractEffectReportBfdF.getRecordId())) {// 新建
			return this.save(user, contractEffectReportBfdF);
		} else {// 编辑
			return this.update(user, contractEffectReportBfdF);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractEffectReportBfdF 实体类
	 * @return
	 */
	private ContractEffectReportBfdF save(UserProfile user, ContractEffectReportBfdF contractEffectReportBfdF) {
		this.contractEffectReportBfdFDao.save(contractEffectReportBfdF);
		return contractEffectReportBfdF;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractEffectReportBfdF 实体类
	 * @return
	 */
	private ContractEffectReportBfdF update(UserProfile user, ContractEffectReportBfdF contractEffectReportBfdF) {
		ContractEffectReportBfdF contractEffectReportBfdFDb = this.contractEffectReportBfdFDao.get(contractEffectReportBfdF.getRecordId());
		if(contractEffectReportBfdFDb == null) {
			return this.save(user, contractEffectReportBfdF);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectReportBfdF, contractEffectReportBfdFDb);
			this.contractEffectReportBfdFDao.update(contractEffectReportBfdFDb);
			return contractEffectReportBfdFDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param recordIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String recordIds) {
		if(StringUtils.isNotBlank(recordIds)) {
			for(String recordId : recordIds.split(",")) {
				ContractEffectReportBfdF contractEffectReportBfdFDb = this.contractEffectReportBfdFDao.get(recordId);
				this.contractEffectReportBfdFDao.delete(recordId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param recordId
	 * @param recordName
	 * @return
	 */
	public boolean nameIsValid(String recordId,String recordName) {
		return this.contractEffectReportBfdFDao.nameIsValid( recordId, recordName);
	}

	/**
	 * 根据签约 报审id 删除对应的附件记录
	 * @param contractEffectId
	 */
	public void deleteByContractEffectId(String contractEffectId) {
		this.contractEffectReportBfdFDao.deleteByProperty("contractEffectId",contractEffectId);
	}
}

