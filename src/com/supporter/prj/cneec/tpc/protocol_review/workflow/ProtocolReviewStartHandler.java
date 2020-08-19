package com.supporter.prj.cneec.tpc.protocol_review.workflow;


import com.supporter.prj.cneec.tpc.protocol_review.entity.ProtocolReview;
import com.supporter.prj.cneec.tpc.protocol_review.service.ProtocolReviewService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * 报告流程启动时的Handler.
 * @author liyinfeng
 *
 */
public class ProtocolReviewStartHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		ProtocolReviewService protocolReviewService = SpringContextHolder.getBean(ProtocolReviewService.class);
		
		String reviewId = (String) execContext.getProcVar("reviewId");
		ProtocolReview protocolReview = protocolReviewService.get(reviewId);
		//锁在途金额
		if (protocolReview != null) {
			protocolReview.setSwfStatus(ProtocolReview.PROCESSING);
			protocolReview.setProcId(execContext.getProcId());
			protocolReviewService.update(protocolReview);		
		} else {
			EIPService.getLogService().error("无效的reviewId:" + reviewId);
		}
		
		return null;
	}


}
