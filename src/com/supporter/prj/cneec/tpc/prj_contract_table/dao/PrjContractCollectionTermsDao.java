package com.supporter.prj.cneec.tpc.prj_contract_table.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractCollectionTerms;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

@Repository
public class PrjContractCollectionTermsDao extends MainDaoSupport<PrjContractCollectionTerms, String> {

	/**
	 * 分页查询
	 */
	public List<PrjContractCollectionTerms> findPage(JqGrid jqGrid, String contractId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" contractId = ? ", CommonUtil.trim(contractId));
		jqGrid.addSortPropertyAsc("termsId");
		return this.retrievePage(jqGrid);
	}

	/**
	 * 根据合同和当前年月获取付款条款集合
	 * @param contractId
	 * @param year
	 * @param month
	 * @return
	 */
	public List<PrjContractCollectionTerms> getPrjContractPaymentTermsList(String contractId, int year, int month) {
		String mth = null;
		if (String.valueOf(month).length() == 1) {
			mth = "0" + month;
		} else {
			mth = month + "";
		}
		String date = year + "-" + mth;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(CommonUtil.parseDate(date+"-01"));
		// 当月最后一天（即本月及之前）
		date += "-" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		String hql = " from " + PrjContractCollectionTerms.class.getName() + " t where t.contractId = ? and t.receiveDate <= ? and t.receiveDate is not null"
					+ " and (t.receiveAmount - t.onwayAmount - t.realReceiveAmount) > 0";
		List<PrjContractCollectionTerms> list = this.find(hql, CommonUtil.trim(contractId), date);
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
	public List<PrjContractCollectionTerms> queryBy(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		DetachedCriteria dc = TpcCommonUtil.getQueryDetachedCriteria(PrjContractCollectionTerms.class, properName, propValue, likeSearch, orderByName, sort);
		return (List<PrjContractCollectionTerms>) getHibernateTemplate().findByCriteria(dc);
	}
	/**
	 * 根据contractId获取对象
	 * @param contractId
	 * @return
	 */
	public List<PrjContractCollectionTerms> getByContractId(String contractId) {
		String hql = " from " + PrjContractCollectionTerms.class.getName() + " where contractId = ?";
		List<PrjContractCollectionTerms> list = this.find(hql, contractId);
		return list;
	}

}
