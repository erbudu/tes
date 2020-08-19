package com.supporter.prj.cneec.tpc.prj_contract_modify.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.base.BasePrjContractAmountBM;

/**
 * @Title: Entity
 * @Description: TPC_PRJ_CONTRACT_AMOUNT_BM.
 * @author Yanweichao
 * @date 2018-03-15
 * @version V1.0
 */
@Entity
@Table(name = "TPC_PRJ_CONTRACT_AMOUNT_BM", schema = "")
public class PrjContractAmountBM extends BasePrjContractAmountBM implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public PrjContractAmountBM() {
		super();
	}

	/**
	 * 构造函数.
	 * @param amountId
	 */
	public PrjContractAmountBM(String bmId) {
		super(bmId);
	}

}
