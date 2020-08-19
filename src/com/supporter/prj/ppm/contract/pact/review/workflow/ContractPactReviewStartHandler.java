package com.supporter.prj.ppm.contract.pact.review.workflow;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReview;
import com.supporter.prj.ppm.contract.pact.review.service.ContractPactReviewService;

public class ContractPactReviewStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractPactReviewService service = (ContractPactReviewService) SpringContextHolder.getBean(ContractPactReviewService.class);
		String id = (String) execContext.getProcVar("reviewId");
		ContractPactReview entity = service.get(id);
		entity.setProcId(execContext.getProcId());
		entity.setStatus(ContractPactReview.StatusCodeTable.EXAM);
		// 判断是否已存在评审编号，没有编号则生成
		String reviewNo = entity.getReviewNo();
		if (StringUtils.isBlank(reviewNo)) {
			entity.setReviewNo(service.generateReviewNo());
		}
		service.update(entity);
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
