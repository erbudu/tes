package com.supporter.prj.cneec.tpc.derivative_contract_online.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.base.BaseDerivativeContractContractAmount;

/**   
 * @Title: Entity
 * @Description: 合同额
 * @author Yanweichao
 * @date 2017-11-06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_DERIVATIVE_CONTRACT_AMOUNT", schema = "")
public class DerivativeContractContractAmount extends BaseDerivativeContractContractAmount {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public DerivativeContractContractAmount() {
		super();
	}

	/**
	 * 构造函数.
	 * @param amountId
	 */
	public DerivativeContractContractAmount(String amountId) {
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
