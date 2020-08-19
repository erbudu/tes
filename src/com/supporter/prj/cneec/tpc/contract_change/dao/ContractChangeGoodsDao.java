package com.supporter.prj.cneec.tpc.contract_change.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChangeGoods;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;


@Repository
public class ContractChangeGoodsDao extends MainDaoSupport<ContractChangeGoods, String> {

	/**
	 * 分页查询
	 */
	public List<ContractChangeGoods> findPage(JqGrid jqGrid, String chId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" chId = ? ", CommonUtil.trim(chId));
		jqGrid.addSortPropertyAsc("goodsId");
		return this.retrievePage(jqGrid);
	}

	public List<ContractChangeGoods> getGoodsGridSingle(JqGrid jqGrid, String goodsId) {
		jqGrid.addHqlFilter(" goodsId = ? ", CommonUtil.trim(goodsId));
		jqGrid.addSortPropertyAsc("goodsId");
		return this.retrievePage(jqGrid);
	}

}
