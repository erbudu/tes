package com.supporter.prj.cneec.tpc.benefit_budget_change.workflow;

import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class BenefitBudgetChangeSwfSpecifiedTreeHandler extends AbstractExecHandler {
	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		return "ORG_TREE";
	}
}