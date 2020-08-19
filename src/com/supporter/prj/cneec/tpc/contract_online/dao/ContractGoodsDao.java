package com.supporter.prj.cneec.tpc.contract_online.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_online.entity.ContractGoods;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractGoodsDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-11-06
 * @version: V1.0
 */
@Repository
public class ContractGoodsDao extends MainDaoSupport<ContractGoods, String> {

	/**
	 * 分页查询
	 */
	public List<ContractGoods> findPage(JqGrid jqGrid, String contractId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" contractId = ? ", CommonUtil.trim(contractId));
		jqGrid.addSortPropertyAsc("goodsId");
		return this.retrievePage(jqGrid);
	}

}
