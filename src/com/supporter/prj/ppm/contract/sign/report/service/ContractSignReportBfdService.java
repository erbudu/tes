package com.supporter.prj.ppm.contract.sign.report.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.sign.report.dao.ContractSignReportBfdDao;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReportBfd;

/**
 * @Title: Service
 * @Description: 主合同签约评审资料单.
 * @author YAN
 * @date 2019-09-05 17:09:56
 * @version V1.0
 */
@Service
public class ContractSignReportBfdService {

	@Autowired
	private ContractSignReportBfdDao contractSignReportBfdDao;

	/**
	 * 根据主键获取主合同签约评审资料单.
	 * 
	 * @param bfdId 主键
	 * @return ContractSignReportBfd
	 */
	public ContractSignReportBfd get(String bfdId) {
		return contractSignReportBfdDao.get(bfdId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractSignReportBfd > getGrid(UserProfile user, JqGrid jqGrid, ContractSignReportBfd contractSignReportBfd) {
		return contractSignReportBfdDao.findPage(jqGrid, contractSignReportBfd);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param bfdId
	 * @return
	 */
	public ContractSignReportBfd initEditOrViewPage(String bfdId) {
		if (StringUtils.isBlank(bfdId)) {// 新建
			ContractSignReportBfd entity = new ContractSignReportBfd();
			return entity;
		} else {// 编辑
			ContractSignReportBfd entity = contractSignReportBfdDao.get(bfdId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractSignReportBfd 实体类
	 * @return
	 */
	public ContractSignReportBfd saveOrUpdate(UserProfile user, ContractSignReportBfd contractSignReportBfd) {
		if (StringUtils.isBlank(contractSignReportBfd.getBfdId())) {// 新建
			return this.save(user, contractSignReportBfd);
		} else {// 编辑
			return this.update(user, contractSignReportBfd);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractSignReportBfd 实体类
	 * @return
	 */
	private ContractSignReportBfd save(UserProfile user, ContractSignReportBfd contractSignReportBfd) {
		this.contractSignReportBfdDao.save(contractSignReportBfd);
		return contractSignReportBfd;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractSignReportBfd 实体类
	 * @return
	 */
	private ContractSignReportBfd update(UserProfile user, ContractSignReportBfd contractSignReportBfd) {
		ContractSignReportBfd contractSignReportBfdDb = this.contractSignReportBfdDao.get(contractSignReportBfd.getBfdId());
		if(contractSignReportBfdDb == null) {
			return this.save(user, contractSignReportBfd);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractSignReportBfd, contractSignReportBfdDb);
			this.contractSignReportBfdDao.update(contractSignReportBfdDb);
			return contractSignReportBfdDb;
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
				ContractSignReportBfd contractSignReportBfdDb = this.contractSignReportBfdDao.get(bfdId);
				this.contractSignReportBfdDao.delete(bfdId);
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
		return this.contractSignReportBfdDao.nameIsValid( bfdId, bfdName);
	}

	/**
	 * 根据合同签约 报审id获取报审资料清单
	 * @param contractSignId
	 * @return
	 */
	public List<ContractSignReportBfd> getListBySignReportId(String contractSignId) {
		return this.contractSignReportBfdDao.findBy("contractSignId",contractSignId);
	}

}

