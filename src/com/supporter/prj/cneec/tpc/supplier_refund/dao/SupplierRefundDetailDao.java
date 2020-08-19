package com.supporter.prj.cneec.tpc.supplier_refund.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.collection_confirmation.entity.CollectionDetail;
import com.supporter.prj.cneec.tpc.contract_refund.entity.ContractRefundDetail;
import com.supporter.prj.cneec.tpc.supplier_refund.entity.SupplierRefund;
import com.supporter.prj.cneec.tpc.supplier_refund.entity.SupplierRefundDetail;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: SupplierRefundDetailDao
 * @Description: DAO类
 * @author: liuermeng
 * @date: 2017-11-17
 * @version: V1.0
 */
@Repository
public class SupplierRefundDetailDao extends MainDaoSupport<SupplierRefundDetail, String> {

	/**
	 * 分页查询
	 */
	public List<SupplierRefundDetail> findPage(JqGrid jqGrid, String refundId) {
		// 明细须通过主表ID获取
		if (StringUtils.isNotBlank(refundId)) {
			jqGrid.addHqlFilter(" refundId = ? ", refundId);
		} else {
			jqGrid.addHqlFilter(" refundId = ? ", "");
		}
		jqGrid.addSortPropertyAsc("detailId");
		return this.retrievePage(jqGrid);
	}
	/**
	 * 根据主表ID获取list
	 * @param refundId
	 * @return
	 */
	public List<SupplierRefundDetail> getListByRefundId(String refundId){
		String hql = " from "+SupplierRefundDetail.class.getName()+" where refundId = ? ";
		return find(hql,refundId);
	}

}