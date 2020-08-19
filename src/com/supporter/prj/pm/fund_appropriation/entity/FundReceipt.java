package com.supporter.prj.pm.fund_appropriation.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.pm.fund_appropriation.entity.base.BaseFundReceipt;

@Entity
@Table(name = "pm_fund_receipt", schema = "")
public class FundReceipt extends BaseFundReceipt{
	public FundReceipt() {
		super();
	}
}
