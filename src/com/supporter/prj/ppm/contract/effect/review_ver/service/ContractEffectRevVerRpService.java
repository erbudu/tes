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

import com.supporter.prj.ppm.contract.effect.review_ver.dao.ContractEffectRevVerRpDao;
import com.supporter.prj.ppm.contract.effect.review_ver.entity.ContractEffectRevVerRp;

/**
 * @Title: Service
 * @Description: 验证评审要点表.
 * @author YAN
 * @date 2019-09-09 10:46:31
 * @version V1.0
 */
@Service
public class ContractEffectRevVerRpService {

	@Autowired
	private ContractEffectRevVerRpDao contractEffectRevVerRpDao;

	/**
	 * 根据主键获取验证评审要点表.
	 * 
	 * @param rpId 主键
	 * @return ContractEffectRevVerRp
	 */
	public ContractEffectRevVerRp get(String rpId) {
		return contractEffectRevVerRpDao.get(rpId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractEffectRevVerRp > getGrid(UserProfile user, JqGrid jqGrid, ContractEffectRevVerRp contractEffectRevVerRp) {
		return contractEffectRevVerRpDao.findPage(jqGrid, contractEffectRevVerRp);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param rpId
	 * @return
	 */
	public ContractEffectRevVerRp initEditOrViewPage(String rpId) {
		if (StringUtils.isBlank(rpId)) {// 新建
			ContractEffectRevVerRp entity = new ContractEffectRevVerRp();
			return entity;
		} else {// 编辑
			ContractEffectRevVerRp entity = contractEffectRevVerRpDao.get(rpId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractEffectRevVerRp 实体类
	 * @return
	 */
	public ContractEffectRevVerRp saveOrUpdate(UserProfile user, ContractEffectRevVerRp contractEffectRevVerRp) {
		if (StringUtils.isBlank(contractEffectRevVerRp.getRpId())) {// 新建
			return this.save(user, contractEffectRevVerRp);
		} else {// 编辑
			return this.update(user, contractEffectRevVerRp);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractEffectRevVerRp 实体类
	 * @return
	 */
	private ContractEffectRevVerRp save(UserProfile user, ContractEffectRevVerRp contractEffectRevVerRp) {
		this.contractEffectRevVerRpDao.save(contractEffectRevVerRp);
		return contractEffectRevVerRp;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractEffectRevVerRp 实体类
	 * @return
	 */
	private ContractEffectRevVerRp update(UserProfile user, ContractEffectRevVerRp contractEffectRevVerRp) {
		ContractEffectRevVerRp contractEffectRevVerRpDb = this.contractEffectRevVerRpDao.get(contractEffectRevVerRp.getRpId());
		if(contractEffectRevVerRpDb == null) {
			return this.save(user, contractEffectRevVerRp);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectRevVerRp, contractEffectRevVerRpDb);
			this.contractEffectRevVerRpDao.update(contractEffectRevVerRpDb);
			return contractEffectRevVerRpDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param rpIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String rpIds) {
		if(StringUtils.isNotBlank(rpIds)) {
			for(String rpId : rpIds.split(",")) {
				ContractEffectRevVerRp contractEffectRevVerRpDb = this.contractEffectRevVerRpDao.get(rpId);
				this.contractEffectRevVerRpDao.delete(rpId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param rpId
	 * @param rpName
	 * @return
	 */
	public boolean nameIsValid(String rpId,String rpName) {
		return this.contractEffectRevVerRpDao.nameIsValid( rpId, rpName);
	}

}

