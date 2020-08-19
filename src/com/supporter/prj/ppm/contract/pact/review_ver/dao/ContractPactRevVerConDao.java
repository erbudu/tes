package com.supporter.prj.ppm.contract.pact.review_ver.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVerCon;

@Repository
public class ContractPactRevVerConDao extends MainDaoSupport<ContractPactRevVerCon, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractPactRevVerCon> findPage(JqGrid jqGrid, ContractPactRevVerCon contractPactRevVerCon) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param revConId
	 * @param revConName
	 * @return
	 */
	public boolean nameIsValid(String revConId,String revConName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(revConId)) {//新建时
			hql = "from " + ContractPactRevVerCon.class.getName() + " where revConName = ?";
			retList = this.retrieve(hql, revConName);
		} else {//编辑时
			hql = "from " + ContractPactRevVerCon.class.getName() + " where revConId != ? and revConName = ?";
			retList = this.retrieve(hql, revConId, revConName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取所有验证结果为通过的评审验证ID集合
	 * @return 评审验证ID集合
	 */
	public List<String> getPassRevVerId() {
		String hql = "select revVerId from " + ContractPactRevVerCon.class.getName() + " where revConStatus="
				+ ContractPactRevVerCon.ConStatusCodeTable.PASS;
		return this.find(hql);
	}

}

