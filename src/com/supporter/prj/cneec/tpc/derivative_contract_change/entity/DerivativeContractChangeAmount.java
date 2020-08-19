package com.supporter.prj.cneec.tpc.derivative_contract_change.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.base.BaseDerivativeContractChangeAmount;

/**   
 * @Title: Entity
 * @Description: 合同额
 */
@Entity
@Table(name = "TPC_DERIVATIVE_CHANGE_AMOUNT", schema = "")
public class DerivativeContractChangeAmount extends BaseDerivativeContractChangeAmount {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public DerivativeContractChangeAmount() {
		super();
	}

	/**
	 * 构造函数.
	 * @param amountId
	 */
	public DerivativeContractChangeAmount(String amountId) {
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

//	public void setChId(String chId) {
//		// TODO Auto-generated method stub
//		
//	}

}
