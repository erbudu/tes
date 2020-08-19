package com.supporter.prj.cneec.tpc.benefit_budget_change.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.BenefitContractCh;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;

@Service
public class BenefitBudgetChangeViewerProvider implements IBusiEntityViewerProvider {
	public String getId() {
		return BenefitContractCh.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/tpc/benefit_budget_change/benefit_process_contract_view.html?changeId=" + entityId;
	}
}