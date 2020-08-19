package com.supporter.prj.pm.fund_appropriation.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.pm.fund_appropriation.entity.base.BaseFundBudgetActual;

@Entity
@Table(name = "pm_fund_budget_actual", schema = "")
public class FundBudgetActual extends BaseFundBudgetActual{
	public FundBudgetActual() {
		super();
	}
}
