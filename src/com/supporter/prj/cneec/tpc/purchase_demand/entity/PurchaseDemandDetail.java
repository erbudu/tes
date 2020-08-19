package com.supporter.prj.cneec.tpc.purchase_demand.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.tpc.purchase_demand.entity.base.BasePurchaseDemandDetail;

/**
 * @Title: PurchaseDemandDetail
 * @Description: 客户需求单客户明细实体类
 * @author: yanweichao
 * @date: 2017-9-6
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_PURCHASE_DEMAND_DETAIL", schema = "")
public class PurchaseDemandDetail extends BasePurchaseDemandDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Constructors

	/** default constructor */
	public PurchaseDemandDetail() {
		super();
	}

	/** full constructor */
	public PurchaseDemandDetail(String detailId) {
		super(detailId);
	}

}
