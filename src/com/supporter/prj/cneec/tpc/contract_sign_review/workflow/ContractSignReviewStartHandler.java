package com.supporter.prj.cneec.tpc.contract_sign_review.workflow;

import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignReview;
import com.supporter.prj.cneec.tpc.contract_sign_review.service.ContractSignReviewService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractSignReviewStartHandler
 * @Description: 流程开始操作类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
public class ContractSignReviewStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractSignReviewService contractSignReviewService = (ContractSignReviewService) SpringContextHolder.getBean(ContractSignReviewService.class);
		String signId = (String) execContext.getProcVar("signId");
		ContractSignReview contractSignReview = contractSignReviewService.get(signId);
		if (contractSignReview != null) {
			contractSignReview.setSwfStatus(ContractSignReview.PROCESSING);
			contractSignReview.setProcId(execContext.getProcId());
			contractSignReviewService.update(contractSignReview);
		} else {
			EIPService.getLogService().error("无效的signId:" + signId);
		}
		return null;
	}

}