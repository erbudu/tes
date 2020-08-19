package com.supporter.prj.ppm.contract.sign.seal_bfd.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.sign.seal_bfd.dao.ContractSignSealBfdFDao;
import com.supporter.prj.ppm.contract.sign.seal_bfd.entity.ContractSignSealBfdF;

/**
 * @Title: Service
 * @Description: 资料清单文件.
 * @author YAN
 * @date 2019-09-10 14:57:16
 * @version V1.0
 */
@Service
public class ContractSignSealBfdFService {

	@Autowired
	private ContractSignSealBfdFDao contractSignSealBfdFDao;

	/**
	 * 根据主键获取资料清单文件.
	 * 
	 * @param recordId 主键
	 * @return ContractSignSealBfdF
	 */
	public ContractSignSealBfdF get(String recordId) {
		return contractSignSealBfdFDao.get(recordId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractSignSealBfdF > getGrid(UserProfile user, JqGrid jqGrid, ContractSignSealBfdF contractSignSealBfdF) {
		return contractSignSealBfdFDao.findPage(jqGrid, contractSignSealBfdF);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param recordId
	 * @return
	 */
	public ContractSignSealBfdF initEditOrViewPage(String recordId) {
		if (StringUtils.isBlank(recordId)) {// 新建
			ContractSignSealBfdF entity = new ContractSignSealBfdF();
			return entity;
		} else {// 编辑
			ContractSignSealBfdF entity = contractSignSealBfdFDao.get(recordId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractSignSealBfdF 实体类
	 * @return
	 */
	public ContractSignSealBfdF saveOrUpdate(UserProfile user, ContractSignSealBfdF contractSignSealBfdF) {
		if (StringUtils.isBlank(contractSignSealBfdF.getRecordId())) {// 新建
			return this.save(user, contractSignSealBfdF);
		} else {// 编辑
			return this.update(user, contractSignSealBfdF);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractSignSealBfdF 实体类
	 * @return
	 */
	private ContractSignSealBfdF save(UserProfile user, ContractSignSealBfdF contractSignSealBfdF) {
		this.contractSignSealBfdFDao.save(contractSignSealBfdF);
		return contractSignSealBfdF;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractSignSealBfdF 实体类
	 * @return
	 */
	private ContractSignSealBfdF update(UserProfile user, ContractSignSealBfdF contractSignSealBfdF) {
		ContractSignSealBfdF contractSignSealBfdFDb = this.contractSignSealBfdFDao.get(contractSignSealBfdF.getRecordId());
		if(contractSignSealBfdFDb == null) {
			return this.save(user, contractSignSealBfdF);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractSignSealBfdF, contractSignSealBfdFDb);
			this.contractSignSealBfdFDao.update(contractSignSealBfdFDb);
			return contractSignSealBfdFDb;
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
				ContractSignSealBfdF contractSignSealBfdFDb = this.contractSignSealBfdFDao.get(recordId);
				this.contractSignSealBfdFDao.delete(recordId);
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
		return this.contractSignSealBfdFDao.nameIsValid( recordId, recordName);
	}

	/**
	 * 根据类型id删除对应的文件信息
	 * @param bfdId
	 */
	public void deleteByBfdId(String bfdId) {
		this.contractSignSealBfdFDao.deleteByProperty("bfdId",bfdId);
	}

}

