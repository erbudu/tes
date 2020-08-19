package com.supporter.prj.cneec.tpc.prj_contract_modify.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.PrjContractCollectionTermsBM;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

@Repository
public class PrjContractCollectionTermsBMDao extends MainDaoSupport<PrjContractCollectionTermsBM, String> {

	/**
	 * 分页查询
	 */
	public List<PrjContractCollectionTermsBM> findPage(JqGrid jqGrid, String changeId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" changeId = ? ", CommonUtil.trim(changeId));
		jqGrid.addSortPropertyAsc("bmId");
		return this.retrievePage(jqGrid);
	}

	/**
	 * 根据contractId获取对象
	 * @param contractId
	 * @return
	 */
	public List<PrjContractCollectionTermsBM> getByContractId(String contractId) {
		String hql = " from " + PrjContractCollectionTermsBM.class.getName() + " where contractId = ?";
		List<PrjContractCollectionTermsBM> list = this.find(hql, contractId);
		return list;
	}

	/**
	 * 根据合同和当前年月获取付款条款集合
	 * @param contractId
	 * @param year
	 * @param month
	 * @return
	 */
	public List<PrjContractCollectionTermsBM> getPrjContractPaymentTermsList(String contractId, int year, int month) {
		String mth = null;
		if (String.valueOf(month).length() == 1) {
			mth = "0" + month;
		} else {
			mth = month + "";
		}
		String date = year + "-" + mth;
		String hql = " from " + PrjContractCollectionTermsBM.class.getName() + " where contractId = ? and receiveDate <= ? and receiveDate is not null";
		List<PrjContractCollectionTermsBM> list = this.find(hql, CommonUtil.trim(contractId), date);
		return list;
	}

}
