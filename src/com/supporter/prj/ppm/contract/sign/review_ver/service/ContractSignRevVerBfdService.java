package com.supporter.prj.ppm.contract.sign.review_ver.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.sign.review_ver.dao.ContractSignRevVerBfdDao;
import com.supporter.prj.ppm.contract.sign.review_ver.entity.ContractSignRevVerBfd;

/**
 * @Title: Service
 * @Description: 验证标前评审资料清单.
 * @author YAN
 * @date 2019-09-09 10:46:29
 * @version V1.0
 */
@Service
public class ContractSignRevVerBfdService {

	@Autowired
	private ContractSignRevVerBfdDao contractSignRevVerBfdDao;

	/**
	 * 根据主键获取验证标前评审资料清单.
	 * 
	 * @param bfdId 主键
	 * @return ContractSignRevVerBfd
	 */
	public ContractSignRevVerBfd get(String bfdId) {
		return contractSignRevVerBfdDao.get(bfdId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractSignRevVerBfd > getGrid(UserProfile user, JqGrid jqGrid, ContractSignRevVerBfd contractSignRevVerBfd) {
		return contractSignRevVerBfdDao.findPage(jqGrid, contractSignRevVerBfd);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param bfdId
	 * @return
	 */
	public ContractSignRevVerBfd initEditOrViewPage(String bfdId) {
		if (StringUtils.isBlank(bfdId)) {// 新建
			ContractSignRevVerBfd entity = new ContractSignRevVerBfd();
			return entity;
		} else {// 编辑
			ContractSignRevVerBfd entity = contractSignRevVerBfdDao.get(bfdId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractSignRevVerBfd 实体类
	 * @return
	 */
	public ContractSignRevVerBfd saveOrUpdate(UserProfile user, ContractSignRevVerBfd contractSignRevVerBfd) {
		if (StringUtils.isBlank(contractSignRevVerBfd.getBfdId())) {// 新建
			return this.save(user, contractSignRevVerBfd);
		} else {// 编辑
			return this.update(user, contractSignRevVerBfd);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractSignRevVerBfd 实体类
	 * @return
	 */
	private ContractSignRevVerBfd save(UserProfile user, ContractSignRevVerBfd contractSignRevVerBfd) {
		this.contractSignRevVerBfdDao.save(contractSignRevVerBfd);
		return contractSignRevVerBfd;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractSignRevVerBfd 实体类
	 * @return
	 */
	private ContractSignRevVerBfd update(UserProfile user, ContractSignRevVerBfd contractSignRevVerBfd) {
		ContractSignRevVerBfd contractSignRevVerBfdDb = this.contractSignRevVerBfdDao.get(contractSignRevVerBfd.getBfdId());
		if(contractSignRevVerBfdDb == null) {
			return this.save(user, contractSignRevVerBfd);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractSignRevVerBfd, contractSignRevVerBfdDb);
			this.contractSignRevVerBfdDao.update(contractSignRevVerBfdDb);
			return contractSignRevVerBfdDb;
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
				ContractSignRevVerBfd contractSignRevVerBfdDb = this.contractSignRevVerBfdDao.get(bfdId);
				this.contractSignRevVerBfdDao.delete(bfdId);
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
		return this.contractSignRevVerBfdDao.nameIsValid( bfdId, bfdName);
	}

}

