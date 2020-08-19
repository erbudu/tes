package com.supporter.prj.ppm.contract.effect.review_ver.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.effect.review_ver.dao.ContractEffectRevVerBFDao;
import com.supporter.prj.ppm.contract.effect.review_ver.entity.ContractEffectRevVerBF;

/**
 * @Title: Service
 * @Description: 验证标前评审资料清单单文件.
 * @author YAN
 * @date 2019-09-09 10:46:30
 * @version V1.0
 */
@Service
public class ContractEffectRevVerBFService {

	@Autowired
	private ContractEffectRevVerBFDao contractEffectRevVerBFDao;

	/**
	 * 根据主键获取验证标前评审资料清单单文件.
	 * 
	 * @param recordId 主键
	 * @return ContractEffectRevVerBF
	 */
	public ContractEffectRevVerBF get(String recordId) {
		return contractEffectRevVerBFDao.get(recordId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractEffectRevVerBF > getGrid(UserProfile user, JqGrid jqGrid, ContractEffectRevVerBF contractEffectRevVerBF) {
		return contractEffectRevVerBFDao.findPage(jqGrid, contractEffectRevVerBF);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param recordId
	 * @return
	 */
	public ContractEffectRevVerBF initEditOrViewPage(String recordId) {
		if (StringUtils.isBlank(recordId)) {// 新建
			ContractEffectRevVerBF entity = new ContractEffectRevVerBF();
			return entity;
		} else {// 编辑
			ContractEffectRevVerBF entity = contractEffectRevVerBFDao.get(recordId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractEffectRevVerBF 实体类
	 * @return
	 */
	public ContractEffectRevVerBF saveOrUpdate(UserProfile user, ContractEffectRevVerBF contractEffectRevVerBF) {
		if (StringUtils.isBlank(contractEffectRevVerBF.getRecordId())) {// 新建
			return this.save(user, contractEffectRevVerBF);
		} else {// 编辑
			return this.update(user, contractEffectRevVerBF);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractEffectRevVerBF 实体类
	 * @return
	 */
	private ContractEffectRevVerBF save(UserProfile user, ContractEffectRevVerBF contractEffectRevVerBF) {
		this.contractEffectRevVerBFDao.save(contractEffectRevVerBF);
		return contractEffectRevVerBF;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractEffectRevVerBF 实体类
	 * @return
	 */
	private ContractEffectRevVerBF update(UserProfile user, ContractEffectRevVerBF contractEffectRevVerBF) {
		ContractEffectRevVerBF contractEffectRevVerBFDb = this.contractEffectRevVerBFDao.get(contractEffectRevVerBF.getRecordId());
		if(contractEffectRevVerBFDb == null) {
			return this.save(user, contractEffectRevVerBF);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectRevVerBF, contractEffectRevVerBFDb);
			this.contractEffectRevVerBFDao.update(contractEffectRevVerBFDb);
			return contractEffectRevVerBFDb;
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
				ContractEffectRevVerBF contractEffectRevVerBFDb = this.contractEffectRevVerBFDao.get(recordId);
				this.contractEffectRevVerBFDao.delete(recordId);
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
		return this.contractEffectRevVerBFDao.nameIsValid( recordId, recordName);
	}

}

