package com.supporter.prj.cneec.tpc.contract_sign_review_dept.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptReview;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;

@Service
public class ContractSignDeptReviewPropRetriever extends AbstractPropRetriever {

	@Autowired
	private ContractSignDeptReviewService service;
	@Autowired
	private RegisterProjectService prjService;

	@Override
	public String getId() {
		return ContractSignDeptReview.class.getName();
	}

	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		
		ContractSignDeptReview entity = this.service.get(entityId.toString());
		RegisterProject prj = prjService.get(entity.getProjectId());
		entity.setPrjDeptId(prj.getProjectDeptId());
		return entity;
	}

}
