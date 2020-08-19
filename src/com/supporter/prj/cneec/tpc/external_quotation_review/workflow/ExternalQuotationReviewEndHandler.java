package com.supporter.prj.cneec.tpc.external_quotation_review.workflow;

import com.supporter.prj.cneec.tpc.external_quotation_review.entity.ExternalQuotationReview;
import com.supporter.prj.cneec.tpc.external_quotation_review.service.ExternalQuotationReviewService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ExternalQuotationReviewEndHandler
 * @Description: 流程结束操作类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
public class ExternalQuotationReviewEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ExternalQuotationReviewService externalQuotationReviewService = (ExternalQuotationReviewService) SpringContextHolder.getBean(ExternalQuotationReviewService.class);
		String externalId = (String) execContext.getProcVar("externalId");
		ExternalQuotationReview externalQuotationReview = externalQuotationReviewService.get(externalId);
		externalQuotationReview.setSwfStatus(ExternalQuotationReview.COMPLETED);
		externalQuotationReview.setProcId(execContext.getProcId());
		externalQuotationReviewService.update(externalQuotationReview);
		externalQuotationReviewService.completeExam(externalQuotationReview);
		return null;
	}

}