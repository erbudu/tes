package com.supporter.prj.cneec.tpc.order_change.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.order_change.entity.base.BaseOrderChangeContractAmount;

/**
 * @Title: Entity
 * @Description: TPC_ORDER_CHANGE_CONT_AMOUNT.
 */
@Entity
@Table(name = "TPC_ORDER_CHANGE_CONT_AMOUNT", schema = "")
public class OrderChangeContractAmount extends BaseOrderChangeContractAmount {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public OrderChangeContractAmount() {
		super();
	}

	/**
	 * 构造函数.
	 * 
	 * @param amountId
	 */
	public OrderChangeContractAmount(String amountId) {
		super(amountId);
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
