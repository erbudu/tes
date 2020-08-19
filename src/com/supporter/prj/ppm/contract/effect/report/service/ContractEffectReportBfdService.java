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

import com.supporter.prj.ppm.contract.effect.report.dao.ContractEffectReportBfdDao;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReportBfd;

/**
 * @Title: Service
 * @Description: 主合同签约评审资料单.
 * @author YAN
 * @date 2019-09-05 17:09:56
 * @version V1.0
 */
@Service
public class ContractEffectReportBfdService {

	@Autowired
	private ContractEffectReportBfdDao contractEffectReportBfdDao;

	/**
	 * 根据主键获取主合同签约评审资料单.
	 * 
	 * @param bfdId 主键
	 * @return ContractEffectReportBfd
	 */
	public ContractEffectReportBfd get(String bfdId) {
		return contractEffectReportBfdDao.get(bfdId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractEffectReportBfd > getGrid(UserProfile user, JqGrid jqGrid, ContractEffectReportBfd contractEffectReportBfd) {
		return contractEffectReportBfdDao.findPage(jqGrid, contractEffectReportBfd);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param bfdId
	 * @return
	 */
	public ContractEffectReportBfd initEditOrViewPage(String bfdId) {
		if (StringUtils.isBlank(bfdId)) {// 新建
			ContractEffectReportBfd entity = new ContractEffectReportBfd();
			return entity;
		} else {// 编辑
			ContractEffectReportBfd entity = contractEffectReportBfdDao.get(bfdId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractEffectReportBfd 实体类
	 * @return
	 */
	public ContractEffectReportBfd saveOrUpdate(UserProfile user, ContractEffectReportBfd contractEffectReportBfd) {
		if (StringUtils.isBlank(contractEffectReportBfd.getBfdId())) {// 新建
			return this.save(user, contractEffectReportBfd);
		} else {// 编辑
			return this.update(user, contractEffectReportBfd);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractEffectReportBfd 实体类
	 * @return
	 */
	private ContractEffectReportBfd save(UserProfile user, ContractEffectReportBfd contractEffectReportBfd) {
		this.contractEffectReportBfdDao.save(contractEffectReportBfd);
		return contractEffectReportBfd;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractEffectReportBfd 实体类
	 * @return
	 */
	private ContractEffectReportBfd update(UserProfile user, ContractEffectReportBfd contractEffectReportBfd) {
		ContractEffectReportBfd contractEffectReportBfdDb = this.contractEffectReportBfdDao.get(contractEffectReportBfd.getBfdId());
		if(contractEffectReportBfdDb == null) {
			return this.save(user, contractEffectReportBfd);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectReportBfd, contractEffectReportBfdDb);
			this.contractEffectReportBfdDao.update(contractEffectReportBfdDb);
			return contractEffectReportBfdDb;
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
				ContractEffectReportBfd contractEffectReportBfdDb = this.contractEffectReportBfdDao.get(bfdId);
				this.contractEffectReportBfdDao.delete(bfdId);
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
		return this.contractEffectReportBfdDao.nameIsValid( bfdId, bfdName);
	}

	/**
	 * 根据合同签约 报审id获取报审资料清单
	 * @param contractEffectId
	 * @return
	 */
	public List<ContractEffectReportBfd> getListBtEffectReportId(String contractEffectId) {
		return this.contractEffectReportBfdDao.findBy("contractEffectId",contractEffectId);
	}
}

