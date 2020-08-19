package com.supporter.prj.cneec.tpc.supplier_refund.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.supplier_refund.entity.SupplierRefund;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: SupplierRefundDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-11-22
 * @version: V1.0
 */
@Repository
public class SupplierRefundDao extends MainDaoSupport<SupplierRefund, String> {

	/**
	 * 分页查询
	 */
	public List<SupplierRefund> findPage(JqGrid jqGrid, SupplierRefund supplierRefund, String authFilter) {
		if (supplierRefund != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = supplierRefund.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or contractNo like ? or contractName like ? or returnSlip like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (supplierRefund.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus = ? ", supplierRefund.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

}
