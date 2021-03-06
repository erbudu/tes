package com.supporter.prj.cneec.tpc.order_online.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.order_online.entity.OrderGoods;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: OrderGoodsDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-10-30
 * @version: V1.0
 */
@Repository
public class OrderGoodsDao extends MainDaoSupport<OrderGoods, String> {

	/**
	 * 分页查询
	 */
	public List<OrderGoods> findPage(JqGrid jqGrid, String orderId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" orderId = ? ", CommonUtil.trim(orderId));
		jqGrid.addSortPropertyAsc("goodsId");
		return this.retrievePage(jqGrid);
	}

}
