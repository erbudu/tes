package com.supporter.prj.cneec.tpc.contract_sign_review.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.tpc.contract_sign_review.entity.base.BaseContractSignAmount;

/**
 * @Title: Entity
 * @Description: TPC_CONTRACT_SIGN_AMOUNT.
 * @author Yanweichao
 * @date 2018-04-10
 * @version V1.0
 */
@Entity
@Table(name = "TPC_CONTRACT_SIGN_AMOUNT", schema = "")
public class ContractSignAmount extends BaseContractSignAmount implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignAmount() {
		super();
	}

	/**
	 * 构造函数.
	 * @param amountId
	 */
	public ContractSignAmount(String amountId) {
		super(amountId);
	}

	public ContractSignAmount(String inforId, String signId, int inforType, String currency, String currencyId) {
		super(inforId, signId, inforType, currency, currencyId);
	}

}
