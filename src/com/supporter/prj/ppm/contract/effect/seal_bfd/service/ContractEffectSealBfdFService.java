package com.supporter.prj.ppm.contract.effect.seal_bfd.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.effect.seal_bfd.dao.ContractEffectSealBfdFDao;
import com.supporter.prj.ppm.contract.effect.seal_bfd.entity.ContractEffectSealBfdF;

/**
 * @Title: Service
 * @Description: 资料清单文件.
 * @author YAN
 * @date 2019-09-10 14:57:16
 * @version V1.0
 */
@Service
public class ContractEffectSealBfdFService {

	@Autowired
	private ContractEffectSealBfdFDao contractEffectSealBfdFDao;

	/**
	 * 根据主键获取资料清单文件.
	 * 
	 * @param recordId 主键
	 * @return ContractEffectSealBfdF
	 */
	public ContractEffectSealBfdF get(String recordId) {
		return contractEffectSealBfdFDao.get(recordId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractEffectSealBfdF > getGrid(UserProfile user, JqGrid jqGrid, ContractEffectSealBfdF contractEffectSealBfdF) {
		return contractEffectSealBfdFDao.findPage(jqGrid, contractEffectSealBfdF);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param recordId
	 * @return
	 */
	public ContractEffectSealBfdF initEditOrViewPage(String recordId) {
		if (StringUtils.isBlank(recordId)) {// 新建
			ContractEffectSealBfdF entity = new ContractEffectSealBfdF();
			return entity;
		} else {// 编辑
			ContractEffectSealBfdF entity = contractEffectSealBfdFDao.get(recordId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractEffectSealBfdF 实体类
	 * @return
	 */
	public ContractEffectSealBfdF saveOrUpdate(UserProfile user, ContractEffectSealBfdF contractEffectSealBfdF) {
		if (StringUtils.isBlank(contractEffectSealBfdF.getRecordId())) {// 新建
			return this.save(user, contractEffectSealBfdF);
		} else {// 编辑
			return this.update(user, contractEffectSealBfdF);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractEffectSealBfdF 实体类
	 * @return
	 */
	private ContractEffectSealBfdF save(UserProfile user, ContractEffectSealBfdF contractEffectSealBfdF) {
		this.contractEffectSealBfdFDao.save(contractEffectSealBfdF);
		return contractEffectSealBfdF;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractEffectSealBfdF 实体类
	 * @return
	 */
	private ContractEffectSealBfdF update(UserProfile user, ContractEffectSealBfdF contractEffectSealBfdF) {
		ContractEffectSealBfdF contractEffectSealBfdFDb = this.contractEffectSealBfdFDao.get(contractEffectSealBfdF.getRecordId());
		if(contractEffectSealBfdFDb == null) {
			return this.save(user, contractEffectSealBfdF);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectSealBfdF, contractEffectSealBfdFDb);
			this.contractEffectSealBfdFDao.update(contractEffectSealBfdFDb);
			return contractEffectSealBfdFDb;
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
				ContractEffectSealBfdF contractEffectSealBfdFDb = this.contractEffectSealBfdFDao.get(recordId);
				this.contractEffectSealBfdFDao.delete(recordId);
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
		return this.contractEffectSealBfdFDao.nameIsValid( recordId, recordName);
	}

	/**
	 * 根据类型id删除对应的文件信息
	 * @param bfdId
	 */
	public void deleteByBfdId(String bfdId) {
		this.contractEffectSealBfdFDao.deleteByProperty("bfdId",bfdId);
	}

}

