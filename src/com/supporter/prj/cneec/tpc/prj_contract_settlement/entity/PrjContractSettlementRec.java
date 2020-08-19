package com.supporter.prj.cneec.tpc.prj_contract_settlement.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.base.BasePrjContractSettlementRec;

@Entity
@Table(name = "TPC_PRJ_CONTRACT_SETTLEMENT_R", schema = "")
public class PrjContractSettlementRec extends BasePrjContractSettlementRec {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	private String yearMonth;

	public String getYearMonth() {
		yearMonth = getBudgetYear() + "-" + getBudgetMonth();
		return yearMonth;
	}

	/**
	 * 汇率
	 * @return
	 */
	@Transient
	public double getRate() {
		if (super.getSettlementAmountF() != 0 && super.getSettlementAmount() != 0) {
			return super.getSettlementAmount() / super.getSettlementAmountF();
		}
		return 0;
	}

	/**
	 * 付款明细已退金额(付款金额-实际付款金额)
	 * 
	 * @return
	 */
	@Transient
	public double getRefundAmount() {
		return this.getSettlementAmountF() - this.getRealSettlementAmountF();
	}

	/**
	 * 付款明细可退金额(实际付款金额-在途金额)
	 * 
	 * @return
	 */
	@Transient
	public double getRefundableAmount() {
		return this.getRealSettlementAmountF() - this.getOnWayAmount();
	}

	/**
	 * 是否可退款（可退金额大于0）
	 * 
	 * @return
	 */
	@Transient
	public boolean canRefund() {
		return this.getRefundableAmount() > 0;
	}

}
