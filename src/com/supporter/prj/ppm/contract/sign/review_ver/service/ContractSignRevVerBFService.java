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

import com.supporter.prj.ppm.contract.sign.review_ver.dao.ContractSignRevVerBFDao;
import com.supporter.prj.ppm.contract.sign.review_ver.entity.ContractSignRevVerBF;

/**
 * @Title: Service
 * @Description: 验证标前评审资料清单单文件.
 * @author YAN
 * @date 2019-09-09 10:46:30
 * @version V1.0
 */
@Service
public class ContractSignRevVerBFService {

	@Autowired
	private ContractSignRevVerBFDao contractSignRevVerBFDao;

	/**
	 * 根据主键获取验证标前评审资料清单单文件.
	 * 
	 * @param recordId 主键
	 * @return ContractSignRevVerBF
	 */
	public ContractSignRevVerBF get(String recordId) {
		return contractSignRevVerBFDao.get(recordId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractSignRevVerBF > getGrid(UserProfile user, JqGrid jqGrid, ContractSignRevVerBF contractSignRevVerBF) {
		return contractSignRevVerBFDao.findPage(jqGrid, contractSignRevVerBF);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param recordId
	 * @return
	 */
	public ContractSignRevVerBF initEditOrViewPage(String recordId) {
		if (StringUtils.isBlank(recordId)) {// 新建
			ContractSignRevVerBF entity = new ContractSignRevVerBF();
			return entity;
		} else {// 编辑
			ContractSignRevVerBF entity = contractSignRevVerBFDao.get(recordId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractSignRevVerBF 实体类
	 * @return
	 */
	public ContractSignRevVerBF saveOrUpdate(UserProfile user, ContractSignRevVerBF contractSignRevVerBF) {
		if (StringUtils.isBlank(contractSignRevVerBF.getRecordId())) {// 新建
			return this.save(user, contractSignRevVerBF);
		} else {// 编辑
			return this.update(user, contractSignRevVerBF);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractSignRevVerBF 实体类
	 * @return
	 */
	private ContractSignRevVerBF save(UserProfile user, ContractSignRevVerBF contractSignRevVerBF) {
		this.contractSignRevVerBFDao.save(contractSignRevVerBF);
		return contractSignRevVerBF;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractSignRevVerBF 实体类
	 * @return
	 */
	private ContractSignRevVerBF update(UserProfile user, ContractSignRevVerBF contractSignRevVerBF) {
		ContractSignRevVerBF contractSignRevVerBFDb = this.contractSignRevVerBFDao.get(contractSignRevVerBF.getRecordId());
		if(contractSignRevVerBFDb == null) {
			return this.save(user, contractSignRevVerBF);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractSignRevVerBF, contractSignRevVerBFDb);
			this.contractSignRevVerBFDao.update(contractSignRevVerBFDb);
			return contractSignRevVerBFDb;
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
				ContractSignRevVerBF contractSignRevVerBFDb = this.contractSignRevVerBFDao.get(recordId);
				this.contractSignRevVerBFDao.delete(recordId);
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
		return this.contractSignRevVerBFDao.nameIsValid( recordId, recordName);
	}

}

