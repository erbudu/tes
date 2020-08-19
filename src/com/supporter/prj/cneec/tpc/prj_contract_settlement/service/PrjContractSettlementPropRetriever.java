package com.supporter.prj.cneec.tpc.prj_contract_settlement.service;

import com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.PrjContractSettlement;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrjContractSettlementPropRetriever extends AbstractPropRetriever {

	@Autowired
	private PrjContractSettlementService service;
	@Autowired
	private RegisterProjectService prjService;

	public String getId() {
		return PrjContractSettlement.class.getName();
	}

	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}

		PrjContractSettlement entity = this.service.get(entityId.toString());
		RegisterProject prj = prjService.get(entity.getPrjId());
		entity.setPrjDeptId(prj.getProjectDeptId());
		return entity;
	}
}