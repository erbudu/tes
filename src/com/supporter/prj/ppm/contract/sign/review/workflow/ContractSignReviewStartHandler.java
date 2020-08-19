package com.supporter.prj.ppm.contract.sign.review.workflow;

import java.util.Date;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.sign.review.entity.PpmContractSignReview;
import com.supporter.prj.ppm.contract.sign.review.service.PpmContractSignReviewService;

public class ContractSignReviewStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		PpmContractSignReviewService service = (PpmContractSignReviewService) SpringContextHolder.getBean(PpmContractSignReviewService.class);
		String id = (String) execContext.getProcVar("contractSignReviewId");
		PpmContractSignReview entity = service.get(id);
		entity.setProcId(execContext.getProcId());
		entity.setStatus(PpmContractSignReview.StatusCodeTable.EXAM);
		entity.setReviewSubmitDate(new Date());
		service.updateSimply(entity);
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
