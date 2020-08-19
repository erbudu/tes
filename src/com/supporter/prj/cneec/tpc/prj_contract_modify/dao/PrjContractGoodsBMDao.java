package com.supporter.prj.cneec.tpc.prj_contract_modify.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.PrjContractGoodsBM;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

@Repository
public class PrjContractGoodsBMDao extends MainDaoSupport<PrjContractGoodsBM, String> {

	/**
	 * 分页查询
	 */
	public List<PrjContractGoodsBM> findPage(JqGrid jqGrid, String changeId) {
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
	public List<PrjContractGoodsBM> getByContractId(String contractId) {
		String hql = " from " + PrjContractGoodsBM.class.getName() + " where contractId = ?";
		List<PrjContractGoodsBM> list = this.find(hql, contractId);
		return list;
	}

}
