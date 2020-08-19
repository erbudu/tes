package com.supporter.prj.cneec.tpc.order_change.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.order_change.entity.OrderChangeGoods;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: OrderChangeGoodsDao
 * @Description: 货物及服务明细DAO
 * @author: yanweichao
 * @date: 2017-11-3
 * @version: V1.0
 */
@Repository
public class OrderChangeGoodsDao extends MainDaoSupport<OrderChangeGoods, String> {

	/**
	 * 分页查询
	 */
	public List<OrderChangeGoods> findPage(JqGrid jqGrid, String chId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" chId = ? ", CommonUtil.trim(chId));
		jqGrid.addSortPropertyAsc("goodsId");
		return this.retrievePage(jqGrid);
	}

	public List<OrderChangeGoods> getGoodsGridSingle(JqGrid jqGrid, String goodsId) {
		jqGrid.addHqlFilter(" goodsId = ? ", CommonUtil.trim(goodsId));
		jqGrid.addSortPropertyAsc("goodsId");
		return this.retrievePage(jqGrid);
	}

}
