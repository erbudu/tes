package com.supporter.prj.ppm.prebid.review.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReview;
import com.supporter.prj.ppm.prebid.review.service.PpmPrebidReviewService;
import com.supporter.prj.ppm.service.PPMService;

/**
 * @Title: PpmPrebidReviewStartHandler
 * @Description: 流程开始操作类
 */
public class PpmPrebidReviewStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		PpmPrebidReviewService ppmPrebidReviewService = (PpmPrebidReviewService) SpringContextHolder.getBean(PpmPrebidReviewService.class);
		String prebidReviewId = (String) execContext.getProcVar("prebidReviewId");
		PpmPrebidReview ppmPrebidReview = ppmPrebidReviewService.get(prebidReviewId);
		if (ppmPrebidReview != null) {
			ppmPrebidReview.setPrebidReviewStutas(PpmPrebidReview.PROCESSING);			
			ppmPrebidReview.setProcId(execContext.getProcId());
			ppmPrebidReviewService.update(ppmPrebidReview);
			PPMService.getScheduleStatusService().saveScheduleStatus(ppmPrebidReview.getPrjId(), "prebid_review02", null);
		} else {
			EIPService.getLogService().error("无效的prebidReviewId:" + prebidReviewId);
		}
		return null;
	}

}