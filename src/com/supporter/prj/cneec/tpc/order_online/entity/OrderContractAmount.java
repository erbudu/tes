package com.supporter.prj.cneec.tpc.order_online.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.order_online.entity.base.BaseOrderContractAmount;

/**   
 * @Title: Entity
 * @Description: 合同额
 * @author Yanweichao
 * @date 2017-10-30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_ORDER_CONTRACT_AMOUNT", schema = "")
public class OrderContractAmount extends BaseOrderContractAmount {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public OrderContractAmount() {
		super();
	}

	/**
	 * 构造函数.
	 * @param amountId
	 */
	public OrderContractAmount(String amountId) {
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
