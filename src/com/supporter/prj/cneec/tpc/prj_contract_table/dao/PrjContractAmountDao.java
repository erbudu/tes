package com.supporter.prj.cneec.tpc.prj_contract_table.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractAmount;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

@Repository
public class PrjContractAmountDao extends MainDaoSupport<PrjContractAmount, String> {

	/**
	 * 分页查询
	 */
	public List<PrjContractAmount> findPage(JqGrid jqGrid, String contractId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" contractId = ? ", CommonUtil.trim(contractId));
		jqGrid.addSortPropertyAsc("amountId");
		return this.retrievePage(jqGrid);
	}

	/**
	 * 根据contractId获取对象
	 * @param contractId
	 * @return
	 */
	public List<PrjContractAmount> getByContractId(String contractId) {
		String hql = " from " + PrjContractAmount.class.getName() + " where contractId = ?";
		List<PrjContractAmount> list = this.find(hql, contractId);
		return list;
	}

	/**
	 * 条件过滤获取合同收款/付款条款
	 * @param properName
	 * @param propValue
	 * @param likeSearch
	 * @param orderByName
	 * @param sort
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PrjContractAmount> queryBy(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(PrjContractAmount.class, properName, propValue, likeSearch, orderByName, sort);
		return (List<PrjContractAmount>) getHibernateTemplate().findByCriteria(dc);
	}

}
