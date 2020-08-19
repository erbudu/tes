package com.supporter.prj.cneec.tpc.contract_sign_review.workflow;

import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignReview;
import com.supporter.prj.cneec.tpc.contract_sign_review.service.ContractSignReviewService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractSignReviewSwfTaskSpecifiedHandler
 * @Description: 工作流普通任务人员程序指定类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
public class ContractSignReviewSwfTaskSpecifiedHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流普通任务人员程序指定
	@Override
	public Object execute(ExecContext execContext) {
		ContractSignReviewService contractSignReviewService = SpringContextHolder.getBean(ContractSignReviewService.class);
		String signId = (String) execContext.getProcVar("signId");
		ContractSignReview contractSignReview = contractSignReviewService.get(signId);		
		return contractSignReview.getCreatedById();
	}

}
