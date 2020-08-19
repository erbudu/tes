package com.supporter.prj.cneec.tpc.order_online.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.order_online.entity.OrderCollectionTerms;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: OrderCollectionTermsDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-10-30
 * @version: V1.0
 */
@Repository
public class OrderCollectionTermsDao extends MainDaoSupport<OrderCollectionTerms, String> {

	/**
	 * 分页查询
	 */
	public List<OrderCollectionTerms> findPage(JqGrid jqGrid, String orderId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" orderId = ? ", CommonUtil.trim(orderId));
		jqGrid.addSortPropertyAsc("termsId");
		return this.retrievePage(jqGrid);
	}

	/**
	 * 查询付款金额
	 * @param parameters
	 * @return
	 */
	public Map<String, Double> getPayPlanAmounts(String orderId) {
		Map<String, Double> payplanActAmounts = new HashMap<String, Double>();
		String hql = "select substr(t.receiveDate, 0, 7), sum(t.receiveRmbAmount) from " + OrderCollectionTerms.class.getName() + " t where t.orderId = ?"
				+ " group by substr(t.receiveDate, 0, 7)";
		List<Object[]> list = this.find(hql, orderId);
		for (Object[] obj : list) {
			payplanActAmounts.put(obj[0].toString(), Double.parseDouble(obj[1].toString()));
		}
		return payplanActAmounts;
	}

}
