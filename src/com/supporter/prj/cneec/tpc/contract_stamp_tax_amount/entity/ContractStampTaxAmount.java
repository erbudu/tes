package com.supporter.prj.cneec.tpc.contract_stamp_tax_amount.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.tpc.contract_stamp_tax_amount.entity.base.BaseContractStampTaxAmount;

/**
 * @Title: Entity
 * @Description: 贸易印花税金额表.
 * @author: LEGO
 * @date: 2020-02-03
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_CONTRACT_STAMP_TAX_AMOUNT", schema = "")
public class ContractStampTaxAmount extends BaseContractStampTaxAmount implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractStampTaxAmount() {
		super();
	}

	/**
	 * 构造函数.
	 * @param stampAmountId
	 */
	public ContractStampTaxAmount(String stampAmountId) {
		super(stampAmountId);
	}

}
