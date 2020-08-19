package com.supporter.prj.cneec.tpc.external_quotation_review_dept.workflow;

import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ExternalQuotationReviewDeptSwfSpecifiedTreeHandler
 * @Description: 工作流组织树程序指定类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
public class ExternalQuotationReviewDeptSwfSpecifiedTreeHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树程序指定
	@Override
	public Object execute(ExecContext execContext) {
		return "ORG_TREE";
	}

}
