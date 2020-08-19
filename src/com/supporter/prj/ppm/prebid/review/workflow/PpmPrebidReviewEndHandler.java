package com.supporter.prj.ppm.prebid.review.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReview;
import com.supporter.prj.ppm.prebid.review.service.PpmPrebidReviewService;

/**
 * @Title: PpmPrebidReviewEndHandler
 * @Description: 流程结束操作类
 * @version: V1.0
 */
public class PpmPrebidReviewEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		PpmPrebidReviewService ppmPrebidReviewService = (PpmPrebidReviewService) SpringContextHolder.getBean(PpmPrebidReviewService.class);
		String prebidReviewId = (String) execContext.getProcVar("prebidReviewId");
		int isPass = PpmPrebidReview.COMPLETED;
		PpmPrebidReview ppmPrebidReview = ppmPrebidReviewService.get(prebidReviewId);
		ppmPrebidReview.setPrebidReviewStutas(isPass);
		ppmPrebidReviewService.update(ppmPrebidReview);
		//流程结束保存报审审核结果
		ppmPrebidReviewService.saveCon(prebidReviewId,isPass,ppmPrebidReview.getProcId());
		//PPMService.getScheduleStatusService().saveScheduleStatus(ppmPrebidReview.getPrjId(), "prebid_review03", null);
		return null;
	}

}