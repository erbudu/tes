package com.supporter.prj.cneec.tpc.contract_sign_review.workflow;

import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignReview;
import com.supporter.prj.cneec.tpc.contract_sign_review.service.ContractSignReviewService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractSignReviewEndHandler
 * @Description: 流程结束操作类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
public class ContractSignReviewEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractSignReviewService contractSignReviewService = (ContractSignReviewService) SpringContextHolder.getBean(ContractSignReviewService.class);
		String signId = (String) execContext.getProcVar("signId");
		ContractSignReview contractSignReview = contractSignReviewService.get(signId);
		contractSignReview.setSwfStatus(ContractSignReview.CONFIRM);
		contractSignReview.setProcId(execContext.getProcId());
		contractSignReviewService.update(contractSignReview);
		contractSignReviewService.completeExam(contractSignReview);
		return null;
	}

}