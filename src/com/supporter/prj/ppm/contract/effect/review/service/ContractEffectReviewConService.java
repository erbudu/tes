package com.supporter.prj.ppm.contract.effect.review.service;

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

import com.supporter.prj.ppm.contract.effect.review.dao.ContractEffectReviewConDao;
import com.supporter.prj.ppm.contract.effect.review.entity.ContractEffectReviewCon;

/**
 * @Title: Service
 * @Description: 评审结果.
 * @author YAN
 * @date 2019-09-06 18:35:32
 * @version V1.0
 */
@Service
public class ContractEffectReviewConService {

	@Autowired
	private ContractEffectReviewConDao contractEffectReviewConDao;

	/**
	 * 根据主键获取评审结果.
	 * 
	 * @param contractEffectRevConId 主键
	 * @return ContractEffectReviewCon
	 */
	public ContractEffectReviewCon get(String contractEffectRevConId) {
		return contractEffectReviewConDao.get(contractEffectRevConId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractEffectReviewCon > getGrid(UserProfile user, JqGrid jqGrid, ContractEffectReviewCon contractEffectReviewCon) {
		return contractEffectReviewConDao.findPage(jqGrid, contractEffectReviewCon);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param contractEffectRevConId
	 * @return
	 */
	public ContractEffectReviewCon initEditOrViewPage(String contractEffectRevConId) {
		if (StringUtils.isBlank(contractEffectRevConId)) {// 新建
			ContractEffectReviewCon entity = new ContractEffectReviewCon();
			return entity;
		} else {// 编辑
			ContractEffectReviewCon entity = contractEffectReviewConDao.get(contractEffectRevConId);
			return entity;
		}
	}

	public ContractEffectReviewCon initPageByReviewId(String contractEffectReviewId) {
		ContractEffectReviewCon entity = new ContractEffectReviewCon();
		if (StringUtils.isNotBlank(contractEffectReviewId)) {
			List<ContractEffectReviewCon> cons = this.contractEffectReviewConDao.findBy("contractEffectReviewId", contractEffectReviewId);
			if (!CommonUtil.isNullOrEmpty(cons)) {
				return cons.get(0);
			}
			entity.setContractEffectReviewId(contractEffectReviewId);
		}
			return entity;
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractEffectReviewCon 实体类
	 * @return
	 */
	public ContractEffectReviewCon saveOrUpdate(UserProfile user, ContractEffectReviewCon contractEffectReviewCon) {
		if (StringUtils.isBlank(contractEffectReviewCon.getContractEffectRevConId())) {// 新建
			return this.save(user, contractEffectReviewCon);
		} else {// 编辑
			return this.update(user, contractEffectReviewCon);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractEffectReviewCon 实体类
	 * @return
	 */
	private ContractEffectReviewCon save(UserProfile user, ContractEffectReviewCon contractEffectReviewCon) {
		this.contractEffectReviewConDao.save(contractEffectReviewCon);
		return contractEffectReviewCon;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractEffectReviewCon 实体类
	 * @return
	 */
	private ContractEffectReviewCon update(UserProfile user, ContractEffectReviewCon contractEffectReviewCon) {
		ContractEffectReviewCon contractEffectReviewConDb = this.contractEffectReviewConDao.get(contractEffectReviewCon.getContractEffectRevConId());
		if(contractEffectReviewConDb == null) {
			return this.save(user, contractEffectReviewCon);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectReviewCon, contractEffectReviewConDb);
			this.contractEffectReviewConDao.update(contractEffectReviewConDb);
			return contractEffectReviewConDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param contractEffectRevConIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String contractEffectRevConIds) {
		if(StringUtils.isNotBlank(contractEffectRevConIds)) {
			for(String contractEffectRevConId : contractEffectRevConIds.split(",")) {
				ContractEffectReviewCon contractEffectReviewConDb = this.contractEffectReviewConDao.get(contractEffectRevConId);
				this.contractEffectReviewConDao.delete(contractEffectRevConId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractEffectRevConId
	 * @param contractEffectRevConName
	 * @return
	 */
	public boolean nameIsValid(String contractEffectRevConId,String contractEffectRevConName) {
		return this.contractEffectReviewConDao.nameIsValid( contractEffectRevConId, contractEffectRevConName);
	}

	public ContractEffectReviewCon getByReviwId(String contractEffectReviewId) {
		List<ContractEffectReviewCon> list = this.contractEffectReviewConDao.findBy("contractEffectReviewId",contractEffectReviewId);
		if (list!=null&&list.size()>0){
			return  list.get(0);
		}
		return null;
	}


}

