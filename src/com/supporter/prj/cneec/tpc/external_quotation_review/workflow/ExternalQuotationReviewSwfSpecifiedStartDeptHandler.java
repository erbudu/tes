package com.supporter.prj.cneec.tpc.external_quotation_review.workflow;

import com.supporter.prj.cneec.tpc.external_quotation_review.entity.ExternalQuotationReview;
import com.supporter.prj.cneec.tpc.external_quotation_review.service.ExternalQuotationReviewService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ExternalQuotationReviewSwfSpecifiedStartDeptHandler
 * @Description: 工作流组织树起始部门程序指定类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
public class ExternalQuotationReviewSwfSpecifiedStartDeptHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树起始部门程序指定
	@Override
	public Object execute(ExecContext execContext) {
		ExternalQuotationReviewService externalQuotationReviewService = SpringContextHolder.getBean(ExternalQuotationReviewService.class);
		String externalId = (String) execContext.getProcVar("externalId");
		ExternalQuotationReview externalQuotationReview = externalQuotationReviewService.get(externalId);
		return externalQuotationReview.getDeptId();
	}

}
