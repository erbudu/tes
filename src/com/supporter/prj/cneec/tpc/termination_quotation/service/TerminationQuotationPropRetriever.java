package com.supporter.prj.cneec.tpc.termination_quotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.termination_quotation.entity.TerminationQuotation;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;

@Service
public class TerminationQuotationPropRetriever extends AbstractPropRetriever {

	@Autowired
	private TerminationQuotationService service;
	@Autowired
	private RegisterProjectService prjService;

	@Override
	public String getId() {
		return TerminationQuotation.class.getName();
	}

	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		
		TerminationQuotation entity = this.service.get(entityId.toString());
		RegisterProject prj = prjService.get(entity.getProjectId());
		entity.setPrjDeptId(prj.getProjectDeptId());
		return entity;
	}

}
