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

import com.supporter.prj.ppm.contract.effect.review.dao.ContractEffectRevEmpRpDao;
import com.supporter.prj.ppm.contract.effect.review.entity.ContractEffectRevEmpRp;

/**
 * @Title: Service
 * @Description: 评审人员的评审要点.
 * @author YAN
 * @date 2019-09-06 18:35:31
 * @version V1.0
 */
@Service
public class ContractEffectRevEmpRpService {

	@Autowired
	private ContractEffectRevEmpRpDao contractEffectRevEmpRpDao;

	/**
	 * 根据主键获取评审人员的评审要点.
	 * 
	 * @param rpId 主键
	 * @return ContractEffectRevEmpRp
	 */
	public ContractEffectRevEmpRp get(String rpId) {
		return contractEffectRevEmpRpDao.get(rpId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractEffectRevEmpRp > getGrid(UserProfile user, JqGrid jqGrid, ContractEffectRevEmpRp contractEffectRevEmpRp) {
		return contractEffectRevEmpRpDao.findPage(jqGrid, contractEffectRevEmpRp);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param rpId
	 * @return
	 */
	public ContractEffectRevEmpRp initEditOrViewPage(String rpId) {
		if (StringUtils.isBlank(rpId)) {// 新建
			ContractEffectRevEmpRp entity = new ContractEffectRevEmpRp();
			return entity;
		} else {// 编辑
			ContractEffectRevEmpRp entity = contractEffectRevEmpRpDao.get(rpId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractEffectRevEmpRp 实体类
	 * @return
	 */
	public ContractEffectRevEmpRp saveOrUpdate(UserProfile user, ContractEffectRevEmpRp contractEffectRevEmpRp) {
		if (StringUtils.isBlank(contractEffectRevEmpRp.getRpId())) {// 新建
			return this.save(user, contractEffectRevEmpRp);
		} else {// 编辑
			return this.update(user, contractEffectRevEmpRp);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractEffectRevEmpRp 实体类
	 * @return
	 */
	private ContractEffectRevEmpRp save(UserProfile user, ContractEffectRevEmpRp contractEffectRevEmpRp) {
		this.contractEffectRevEmpRpDao.save(contractEffectRevEmpRp);
		return contractEffectRevEmpRp;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractEffectRevEmpRp 实体类
	 * @return
	 */
	private ContractEffectRevEmpRp update(UserProfile user, ContractEffectRevEmpRp contractEffectRevEmpRp) {
		ContractEffectRevEmpRp contractEffectRevEmpRpDb = this.contractEffectRevEmpRpDao.get(contractEffectRevEmpRp.getRpId());
		if(contractEffectRevEmpRpDb == null) {
			return this.save(user, contractEffectRevEmpRp);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectRevEmpRp, contractEffectRevEmpRpDb);
			this.contractEffectRevEmpRpDao.update(contractEffectRevEmpRpDb);
			return contractEffectRevEmpRpDb;
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
				ContractEffectRevEmpRp contractEffectRevEmpRpDb = this.contractEffectRevEmpRpDao.get(rpId);
				this.contractEffectRevEmpRpDao.delete(rpId);
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
		return this.contractEffectRevEmpRpDao.nameIsValid( rpId, rpName);
	}

}

