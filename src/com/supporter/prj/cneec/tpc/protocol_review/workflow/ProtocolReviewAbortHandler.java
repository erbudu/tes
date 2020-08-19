package com.supporter.prj.cneec.tpc.protocol_review.workflow;

import com.supporter.prj.cneec.tpc.protocol_review.entity.ProtocolReview;
import com.supporter.prj.cneec.tpc.protocol_review.service.ProtocolReviewService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class ProtocolReviewAbortHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		
		ProtocolReviewService protocolReviewService = SpringContextHolder.getBean(ProtocolReviewService.class);
		String reviewId = (String) execContext.getProcVar("reviewId");
		ProtocolReview protocolReview =  protocolReviewService.get(reviewId);
		protocolReview.setSwfStatus(ProtocolReview.DRAFT);
		protocolReviewService.update(protocolReview);		
		return null;
	} 

}
