package com.supporter.prj.cneec.tpc.order_change.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.order_change.entity.OrderChangeCollectionTerms;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: OrderChangeCollectionTermsDao
 * @Description: 收款条件DAO类
 * @author: yanweichao
 * @date: 2017-10-30
 * @version: V1.0
 */
@Repository
public class OrderChangeCollectionTermsDao extends MainDaoSupport<OrderChangeCollectionTerms, String> {

	/**
	 * 分页查询
	 */
	public List<OrderChangeCollectionTerms> findPage(JqGrid jqGrid, String chId) {
		// 客户明细须通过主表ID和客户表ID获取
		jqGrid.addHqlFilter(" chId = ? ", CommonUtil.trim(chId));
		jqGrid.addSortPropertyAsc("termsId");
		return this.retrievePage(jqGrid);
	}

}
