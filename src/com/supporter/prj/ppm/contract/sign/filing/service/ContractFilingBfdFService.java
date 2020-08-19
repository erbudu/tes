package com.supporter.prj.ppm.contract.sign.filing.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.sign.filing.dao.ContractFilingBfdFDao;
import com.supporter.prj.ppm.contract.sign.filing.entity.ContractFilingBfdF;

/**
 * @Title: Service
 * @Description: PPM_CONTRACT_FILING_BFD_F.
 * @author YAN
 * @date 2019-09-17 11:37:26
 * @version V1.0
 */
@Service
public class ContractFilingBfdFService {

	@Autowired
	private ContractFilingBfdFDao contractFilingBfdFDao;

	/**
	 * 根据主键获取PPM_CONTRACT_FILING_BFD_F.
	 * 
	 * @param recordId 主键
	 * @return ContractFilingBfdF
	 */
	public ContractFilingBfdF get(String recordId) {
		return contractFilingBfdFDao.get(recordId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractFilingBfdF > getGrid(UserProfile user, JqGrid jqGrid, ContractFilingBfdF contractFilingBfdF) {
		return contractFilingBfdFDao.findPage(jqGrid, contractFilingBfdF);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param recordId
	 * @return
	 */
	public ContractFilingBfdF initEditOrViewPage(String recordId) {
		if (StringUtils.isBlank(recordId)) {// 新建
			ContractFilingBfdF entity = new ContractFilingBfdF();
			return entity;
		} else {// 编辑
			ContractFilingBfdF entity = contractFilingBfdFDao.get(recordId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractFilingBfdF 实体类
	 * @return
	 */
	public ContractFilingBfdF saveOrUpdate(UserProfile user, ContractFilingBfdF contractFilingBfdF) {
		if (StringUtils.isBlank(contractFilingBfdF.getRecordId())) {// 新建
			return this.save(user, contractFilingBfdF);
		} else {// 编辑
			return this.update(user, contractFilingBfdF);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractFilingBfdF 实体类
	 * @return
	 */
	private ContractFilingBfdF save(UserProfile user, ContractFilingBfdF contractFilingBfdF) {
		this.contractFilingBfdFDao.save(contractFilingBfdF);
		return contractFilingBfdF;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractFilingBfdF 实体类
	 * @return
	 */
	private ContractFilingBfdF update(UserProfile user, ContractFilingBfdF contractFilingBfdF) {
		ContractFilingBfdF contractFilingBfdFDb = this.contractFilingBfdFDao.get(contractFilingBfdF.getRecordId());
		if(contractFilingBfdFDb == null) {
			return this.save(user, contractFilingBfdF);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractFilingBfdF, contractFilingBfdFDb);
			this.contractFilingBfdFDao.update(contractFilingBfdFDb);
			return contractFilingBfdFDb;
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
				ContractFilingBfdF contractFilingBfdFDb = this.contractFilingBfdFDao.get(recordId);
				this.contractFilingBfdFDao.delete(recordId);
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
		return this.contractFilingBfdFDao.nameIsValid( recordId, recordName);
	}

	public void deleteByBfdId(String bfdId) {
		this.contractFilingBfdFDao.deleteByProperty("bfdId",bfdId);
	}

	public void delteByFilingId(String filingId) {
		this.contractFilingBfdFDao.deleteByProperty("filingId",filingId);
	}
}

