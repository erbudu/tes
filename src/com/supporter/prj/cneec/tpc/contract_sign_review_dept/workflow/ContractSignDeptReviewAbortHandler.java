package com.supporter.prj.cneec.tpc.contract_sign_review_dept.workflow;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptReview;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.service.ContractSignDeptReviewService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractSignDeptReviewAbortHandler
 * @Description: 流程中止操作类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
public class ContractSignDeptReviewAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractSignDeptReviewService contractSignDeptReviewService = (ContractSignDeptReviewService) SpringContextHolder.getBean(ContractSignDeptReviewService.class);
		String signId = (String) execContext.getProcVar("signId");
		ContractSignDeptReview contractSignDeptReview = contractSignDeptReviewService.get(signId);
		contractSignDeptReview.setSwfStatus(ContractSignDeptReview.DRAFT);
		contractSignDeptReviewService.update(contractSignDeptReview);
		return null;
	}

}