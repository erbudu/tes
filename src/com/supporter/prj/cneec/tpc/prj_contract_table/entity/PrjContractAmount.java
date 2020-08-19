package com.supporter.prj.cneec.tpc.prj_contract_table.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.tpc.prj_contract_table.entity.base.BasePrjContractAmount;

/**
 * @Title: Entity
 * @Description: TPC_PRJ_CONTRACT_AMOUNT.
 * @author Yanweichao
 * @date 2018-03-15
 * @version V1.0
 */
@Entity
@Table(name = "TPC_PRJ_CONTRACT_AMOUNT", schema = "")
public class PrjContractAmount extends BasePrjContractAmount implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public PrjContractAmount() {
		super();
	}

	/**
	 * 构造函数.
	 * @param amountId
	 */
	public PrjContractAmount(String amountId) {
		super(amountId);
	}

}
