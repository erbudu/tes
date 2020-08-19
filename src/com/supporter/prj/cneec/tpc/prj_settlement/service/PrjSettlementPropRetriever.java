package com.supporter.prj.cneec.tpc.prj_settlement.service;

import com.supporter.prj.cneec.tpc.prj_settlement.entity.PrjSettlement;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrjSettlementPropRetriever extends AbstractPropRetriever {

	@Autowired
	private PrjSettlementService service;
	@Autowired
	private RegisterProjectService prjService;

	public String getId() {
		return PrjSettlement.class.getName();
	}

	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		
		PrjSettlement entity = this.service.get(entityId.toString());
		RegisterProject prj = prjService.get(entity.getPrjId());
		entity.setPrjDeptId(prj.getProjectDeptId());
		return entity;
	}

}