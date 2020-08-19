package com.supporter.prj.ppm.contract.sign.review.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.sign.review.dao.ContractSignReviewConDao;
import com.supporter.prj.ppm.contract.sign.review.entity.ContractSignReviewCon;

/**
 * @Title: Service
 * @Description: 评审结果.
 * @author YAN
 * @date 2019-09-06 18:35:32
 * @version V1.0
 */
@Service
public class ContractSignReviewConService {

	@Autowired
	private ContractSignReviewConDao contentDao;

	/**
	 * 根据主键获取评审结果.
	 * 
	 * @param contractSignRevConId 主键
	 * @return ContractSignReviewCon
	 */
	public ContractSignReviewCon get(String contractSignRevConId) {
		return contentDao.get(contractSignRevConId);
	}

	/**
	 * 根据主键获取审批结果
	 * @param contractSignReviewId-主键
	 * @return
	 */
	public ContractSignReviewCon getByReviewId(String contractSignReviewId) {
		List<ContractSignReviewCon> list = contentDao.findBy("contractSignReviewId",
				contractSignReviewId);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractSignReviewCon > getGrid(UserProfile user, JqGrid jqGrid, ContractSignReviewCon contractSignReviewCon) {
		return contentDao.findPage(jqGrid, contractSignReviewCon);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param contractSignRevConId
	 * @return
	 */
	public ContractSignReviewCon initEditOrViewPage(String contractSignRevConId) {
		if (StringUtils.isBlank(contractSignRevConId)) {// 新建
			ContractSignReviewCon entity = new ContractSignReviewCon();
			return entity;
		} else {// 编辑
			ContractSignReviewCon entity = contentDao.get(contractSignRevConId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractSignReviewCon 实体类
	 * @return
	 */
	public void saveOrUpdate(UserProfile user, ContractSignReviewCon entity) {
		List<ContractSignReviewCon> list = contentDao.findBy("contractSignReviewId", entity.getContractSignReviewId());
		if (CollectionUtils.isEmpty(list)) {
			entity.setContractSignRevConId(com.supporter.util.UUIDHex.newId());
			contentDao.save(entity);
		} else {
			contentDao.update(entity);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractSignReviewCon 实体类
	 * @return
	 */
	private ContractSignReviewCon save(UserProfile user, ContractSignReviewCon contractSignReviewCon) {
		contractSignReviewCon.setContractSignRevConId(com.supporter.util.UUIDHex.newId());
		this.contentDao.save(contractSignReviewCon);
		return contractSignReviewCon;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractSignReviewCon 实体类
	 * @return
	 */
	private ContractSignReviewCon update(UserProfile user, ContractSignReviewCon contractSignReviewCon) {
		ContractSignReviewCon contractSignReviewConDb = this.contentDao
				.get(contractSignReviewCon.getContractSignRevConId());
		if(contractSignReviewConDb == null) {
			return this.save(user, contractSignReviewCon);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractSignReviewCon, contractSignReviewConDb);
			this.contentDao.update(contractSignReviewConDb);
			return contractSignReviewConDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param contractSignRevConIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String contractSignRevConIds) {
		if(StringUtils.isNotBlank(contractSignRevConIds)) {
			for(String contractSignRevConId : contractSignRevConIds.split(",")) {
				ContractSignReviewCon contractSignReviewConDb = this.contentDao.get(contractSignRevConId);
				this.contentDao.delete(contractSignRevConId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractSignRevConId
	 * @param contractSignRevConName
	 * @return
	 */
	public boolean nameIsValid(String contractSignRevConId,String contractSignRevConName) {
		return this.contentDao.nameIsValid(contractSignRevConId, contractSignRevConName);
	}

    public List<ContractSignReviewCon> getListByRevId(String contractSignReviewId) {
		return this.contentDao.findBy("contractSignReviewId", contractSignReviewId);
    }

}

