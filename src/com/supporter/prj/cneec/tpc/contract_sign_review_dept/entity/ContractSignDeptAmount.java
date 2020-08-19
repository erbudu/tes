package com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.base.BaseContractSignDeptAmount;

/**
 * @Title: Entity
 * @Description: TPC_CONTRACT_SIGN_DEPT_AMOUNT.
 * @author Yanweichao
 * @date 2018-04-10
 * @version V1.0
 */
@Entity
@Table(name = "TPC_CONTRACT_SIGN_DEPT_AMOUNT", schema = "")
public class ContractSignDeptAmount extends BaseContractSignDeptAmount implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignDeptAmount() {
		super();
	}

	/**
	 * 构造函数.
	 * @param amountId
	 */
	public ContractSignDeptAmount(String amountId) {
		super(amountId);
	}

	public ContractSignDeptAmount(String inforId, String signId, int inforType, String currency, String currencyId) {
		super(inforId, signId, inforType, currency, currencyId);
	}

	@Transient
	public double getExecuteRate() {
		if (super.getExecuteRate() != 0) {
			return super.getExecuteRate();
		}
		return super.getRate();
	}

}
