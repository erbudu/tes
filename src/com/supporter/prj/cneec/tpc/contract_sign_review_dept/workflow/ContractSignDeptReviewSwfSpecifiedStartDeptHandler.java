package com.supporter.prj.cneec.tpc.contract_sign_review_dept.workflow;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptReview;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.service.ContractSignDeptReviewService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractSignDeptReviewSwfSpecifiedStartDeptHandler
 * @Description: 工作流组织树起始部门程序指定类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
public class ContractSignDeptReviewSwfSpecifiedStartDeptHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树起始部门程序指定
	@Override
	public Object execute(ExecContext execContext) {
		ContractSignDeptReviewService contractSignDeptReviewService = SpringContextHolder.getBean(ContractSignDeptReviewService.class);
		String signId = (String) execContext.getProcVar("signId");
		ContractSignDeptReview contractSignDeptReview = contractSignDeptReviewService.get(signId);
		return contractSignDeptReview.getDeptId();
	}

}
