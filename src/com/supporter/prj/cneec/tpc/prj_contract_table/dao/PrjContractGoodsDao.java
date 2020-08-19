package com.supporter.prj.cneec.tpc.prj_contract_table.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractGoods;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

@Repository
public class PrjContractGoodsDao extends MainDaoSupport<PrjContractGoods, String> {

	/**
	 * 分页查询
	 */
	public List<PrjContractGoods> findPage(JqGrid jqGrid, String contractId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" contractId = ? ", CommonUtil.trim(contractId));
		jqGrid.addSortPropertyAsc("goodsId");
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 根据contractId获取对象
	 * @param contractId
	 * @return
	 */
	public List<PrjContractGoods> getByContractId(String contractId) {
		String hql = " from " + PrjContractGoods.class.getName() + " where contractId = ?";
		List<PrjContractGoods> list = this.find(hql, contractId);
		return list;
	}

}
