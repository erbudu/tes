package com.supporter.prj.cneec.tpc.contract_change.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChangeGoodsChild;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

@Repository
public class ContractChangeGoodsChildDao extends MainDaoSupport<ContractChangeGoodsChild, String> {

	/**
	 * 分页查询
	 */
	public List<ContractChangeGoodsChild> findPage(JqGrid jqGrid, String goodsId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" goodsId = '"+CommonUtil.trim(goodsId)+"'");
		jqGrid.addSortPropertyAsc("goodsId");
		return this.retrievePage(jqGrid);
	}

}
