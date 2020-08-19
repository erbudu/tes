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

import com.supporter.prj.ppm.contract.effect.review_ver.dao.ContractEffectRevVerBfdDao;
import com.supporter.prj.ppm.contract.effect.review_ver.entity.ContractEffectRevVerBfd;

/**
 * @Title: Service
 * @Description: 验证标前评审资料清单.
 * @author YAN
 * @date 2019-09-09 10:46:29
 * @version V1.0
 */
@Service
public class ContractEffectRevVerBfdService {

	@Autowired
	private ContractEffectRevVerBfdDao contractEffectRevVerBfdDao;

	/**
	 * 根据主键获取验证标前评审资料清单.
	 * 
	 * @param bfdId 主键
	 * @return ContractEffectRevVerBfd
	 */
	public ContractEffectRevVerBfd get(String bfdId) {
		return contractEffectRevVerBfdDao.get(bfdId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractEffectRevVerBfd > getGrid(UserProfile user, JqGrid jqGrid, ContractEffectRevVerBfd contractEffectRevVerBfd) {
		return contractEffectRevVerBfdDao.findPage(jqGrid, contractEffectRevVerBfd);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param bfdId
	 * @return
	 */
	public ContractEffectRevVerBfd initEditOrViewPage(String bfdId) {
		if (StringUtils.isBlank(bfdId)) {// 新建
			ContractEffectRevVerBfd entity = new ContractEffectRevVerBfd();
			return entity;
		} else {// 编辑
			ContractEffectRevVerBfd entity = contractEffectRevVerBfdDao.get(bfdId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractEffectRevVerBfd 实体类
	 * @return
	 */
	public ContractEffectRevVerBfd saveOrUpdate(UserProfile user, ContractEffectRevVerBfd contractEffectRevVerBfd) {
		if (StringUtils.isBlank(contractEffectRevVerBfd.getBfdId())) {// 新建
			return this.save(user, contractEffectRevVerBfd);
		} else {// 编辑
			return this.update(user, contractEffectRevVerBfd);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractEffectRevVerBfd 实体类
	 * @return
	 */
	private ContractEffectRevVerBfd save(UserProfile user, ContractEffectRevVerBfd contractEffectRevVerBfd) {
		this.contractEffectRevVerBfdDao.save(contractEffectRevVerBfd);
		return contractEffectRevVerBfd;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractEffectRevVerBfd 实体类
	 * @return
	 */
	private ContractEffectRevVerBfd update(UserProfile user, ContractEffectRevVerBfd contractEffectRevVerBfd) {
		ContractEffectRevVerBfd contractEffectRevVerBfdDb = this.contractEffectRevVerBfdDao.get(contractEffectRevVerBfd.getBfdId());
		if(contractEffectRevVerBfdDb == null) {
			return this.save(user, contractEffectRevVerBfd);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectRevVerBfd, contractEffectRevVerBfdDb);
			this.contractEffectRevVerBfdDao.update(contractEffectRevVerBfdDb);
			return contractEffectRevVerBfdDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param bfdIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String bfdIds) {
		if(StringUtils.isNotBlank(bfdIds)) {
			for(String bfdId : bfdIds.split(",")) {
				ContractEffectRevVerBfd contractEffectRevVerBfdDb = this.contractEffectRevVerBfdDao.get(bfdId);
				this.contractEffectRevVerBfdDao.delete(bfdId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param bfdId
	 * @param bfdName
	 * @return
	 */
	public boolean nameIsValid(String bfdId,String bfdName) {
		return this.contractEffectRevVerBfdDao.nameIsValid( bfdId, bfdName);
	}

}

