package com.supporter.prj.cneec.tpc.contract_refund.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.contract_refund.entity.base.BaseContractRefundDetail;

/**
 * @Title: ContractRefund
 * @Description: 销售合同退款实体类
 * @author: yanweichao
 * @date: 2017-11-24
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_CONTRACT_REFUNDDETAIL", schema = "")
public class ContractRefundDetail extends BaseContractRefundDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCCONREFUND";

	// Constructors

	/** default constructor */
	public ContractRefundDetail() {
		super();
	}

	/** minimal constructor */
	public ContractRefundDetail(String refundId) {
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
