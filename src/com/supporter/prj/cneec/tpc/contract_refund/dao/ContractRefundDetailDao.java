package com.supporter.prj.cneec.tpc.contract_refund.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_refund.entity.ContractRefundDetail;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: ContractRefundDetailDao
 * @Description: DAO类
 * @author: liuermeng
 * @date: 2017-11-17
 * @version: V1.0
 */
@Repository
public class ContractRefundDetailDao extends MainDaoSupport<ContractRefundDetail, String> {

	/**
	 * 分页查询
	 */
	public List<ContractRefundDetail> findPage(JqGrid jqGrid, String refundId) {
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
	 * 根据退款记录获取退款明细
	 * @param refundId
	 * @return
	 */
	public List<ContractRefundDetail> getListByRefundId(String refundId){
		String hql = " from "+ContractRefundDetail.class.getName()+" where refundId = ? ";
		return find(hql,refundId);
	}
}