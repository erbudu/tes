package com.supporter.prj.cneec.tpc.order_change.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.order_change.entity.OrderChangeContractAmount;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: OrderChangeContractAmountDao
 * @Description: 合同额DAO
 */
@Repository
public class OrderChangeContractAmountDao extends MainDaoSupport<OrderChangeContractAmount, String> {

	/**
	 * 分页查询
	 */
	public List<OrderChangeContractAmount> findPage(JqGrid jqGrid, String chId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" chId = ? ", CommonUtil.trim(chId));
		jqGrid.addSortPropertyAsc("amountId");
		return this.retrievePage(jqGrid);
	}

}
