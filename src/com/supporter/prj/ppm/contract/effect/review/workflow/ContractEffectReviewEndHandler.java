package com.supporter.prj.ppm.contract.effect.review.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.effect.review.entity.PpmContractEffectReview;
import com.supporter.prj.ppm.contract.effect.review.service.PpmContractEffectReviewService;

public class ContractEffectReviewEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		PpmContractEffectReviewService service = (PpmContractEffectReviewService) SpringContextHolder.getBean(PpmContractEffectReviewService.class);
		String id = (String) execContext.getProcVar("contractEffectReviewId");
		PpmContractEffectReview entity = service.get(id);
		entity.setStatus(PpmContractEffectReview.StatusCodeTable.COMPLETE);
		service.updateSimply(entity);
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
