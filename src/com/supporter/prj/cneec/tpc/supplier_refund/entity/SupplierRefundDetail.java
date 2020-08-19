package com.supporter.prj.cneec.tpc.supplier_refund.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.supplier_refund.entity.base.BaseSupplierRefundDetail;

/**
 * @Title: SupplierRefund
 * @Description: 供应商退款实体类
 * @author: liuermeng
 * @date: 2018-09-20
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_SUPPLIER_REFUND_DETAIL", schema = "")
public class SupplierRefundDetail extends BaseSupplierRefundDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCSUPREFUND";

	// Constructors

	/** default constructor */
	public SupplierRefundDetail() {
		super();
	}

	/** minimal constructor */
	public SupplierRefundDetail(String refundId) {
		super(refundId);
	}

	private boolean add = false;// 是否新增

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

}
