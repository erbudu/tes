package com.supporter.prj.ppm.contract.pact.review.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReview;
import com.supporter.prj.ppm.contract.pact.review.service.ContractPactReviewService;

public class ContractPactReviewAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}
  
	public Object execute(ExecContext execContext) {
		ContractPactReviewService service = (ContractPactReviewService) SpringContextHolder.getBean(ContractPactReviewService.class);
		String id = (String) execContext.getProcVar("reviewId");
		ContractPactReview entity = service.get(id);
		entity.setStatus(ContractPactReview.StatusCodeTable.DRAFT);
		service.update(entity);
		return null;
	}
  
	public void executeProcVars(ExecContext execContext) {
	}
}
