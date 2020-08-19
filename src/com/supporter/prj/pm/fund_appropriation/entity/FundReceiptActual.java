package com.supporter.prj.pm.fund_appropriation.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.pm.fund_appropriation.entity.base.BaseFundReceiptActual;

@Entity
@Table(name = "pm_fund_receipt_actual", schema = "")
public class FundReceiptActual extends BaseFundReceiptActual{
	public FundReceiptActual() {
		super();
	}
}
