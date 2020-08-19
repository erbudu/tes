package com.supporter.prj.cneec.tpc.benefit_budget_change.service;

import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.BenefitContractCh;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BenefitBudgetChangePropRetriever extends AbstractPropRetriever {
	@Autowired
	private BenefitBudgetChangeService service;
	@Autowired
	private RegisterProjectService prjService;

	public String getId() {
		return BenefitContractCh.class.getName();
	}

	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		
		BenefitContractCh entity = this.service.initBenefitContract(entityId.toString());
		RegisterProject prj = prjService.get(entity.getProjectId());
		entity.setProjectDeptId(prj.getProjectDeptId());
		
		return entity;
	}
}