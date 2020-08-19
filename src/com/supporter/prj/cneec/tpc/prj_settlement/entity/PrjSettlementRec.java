package com.supporter.prj.cneec.tpc.prj_settlement.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.prj_settlement.entity.base.BasePrjSettlementRec;

@Entity
@Table(name = "TPC_PRJ_SETTLEMENT_REC", schema = "")
public class PrjSettlementRec extends BasePrjSettlementRec{

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



}
