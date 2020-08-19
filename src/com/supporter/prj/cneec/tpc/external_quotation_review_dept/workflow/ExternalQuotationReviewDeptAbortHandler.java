package com.supporter.prj.cneec.tpc.external_quotation_review_dept.workflow;

import com.supporter.prj.cneec.tpc.external_quotation_review_dept.entity.ExternalQuotationReviewDept;
import com.supporter.prj.cneec.tpc.external_quotation_review_dept.service.ExternalQuotationReviewDeptService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ExternalQuotationReviewDeptAbortHandler
 * @Description: 流程中止操作类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
public class ExternalQuotationReviewDeptAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ExternalQuotationReviewDeptService externalQuotationReviewDeptService = (ExternalQuotationReviewDeptService) SpringContextHolder.getBean(ExternalQuotationReviewDeptService.class);
		String externalId = (String) execContext.getProcVar("externalId");
		ExternalQuotationReviewDept externalQuotationReviewDept = externalQuotationReviewDeptService.get(externalId);
		externalQuotationReviewDept.setSwfStatus(ExternalQuotationReviewDept.DRAFT);
		externalQuotationReviewDeptService.update(externalQuotationReviewDept);
		return null;
	}

}