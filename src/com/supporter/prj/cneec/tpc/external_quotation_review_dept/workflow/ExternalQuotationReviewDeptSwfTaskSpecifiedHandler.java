package com.supporter.prj.cneec.tpc.external_quotation_review_dept.workflow;

import com.supporter.prj.cneec.tpc.external_quotation_review_dept.entity.ExternalQuotationReviewDept;
import com.supporter.prj.cneec.tpc.external_quotation_review_dept.service.ExternalQuotationReviewDeptService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ExternalQuotationReviewDeptSwfTaskSpecifiedHandler
 * @Description: 工作流普通任务人员程序指定类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
public class ExternalQuotationReviewDeptSwfTaskSpecifiedHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流普通任务人员程序指定
	@Override
	public Object execute(ExecContext execContext) {
		ExternalQuotationReviewDeptService externalQuotationReviewDeptService = SpringContextHolder.getBean(ExternalQuotationReviewDeptService.class);
		String externalId = (String) execContext.getProcVar("externalId");
		ExternalQuotationReviewDept externalQuotationReviewDept = externalQuotationReviewDeptService.get(externalId);		
		return externalQuotationReviewDept.getCreatedById();
	}

}
