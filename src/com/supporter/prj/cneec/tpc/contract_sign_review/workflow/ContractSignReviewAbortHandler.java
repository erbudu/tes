package com.supporter.prj.cneec.tpc.contract_sign_review.workflow;

import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignReview;
import com.supporter.prj.cneec.tpc.contract_sign_review.service.ContractSignReviewService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractSignReviewAbortHandler
 * @Description: 流程中止操作类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
public class ContractSignReviewAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractSignReviewService contractSignReviewService = (ContractSignReviewService) SpringContextHolder.getBean(ContractSignReviewService.class);
		String signId = (String) execContext.getProcVar("signId");
		ContractSignReview contractSignReview = contractSignReviewService.get(signId);
		contractSignReview.setSwfStatus(ContractSignReview.DRAFT);
		contractSignReviewService.update(contractSignReview);
		return null;
	}

}