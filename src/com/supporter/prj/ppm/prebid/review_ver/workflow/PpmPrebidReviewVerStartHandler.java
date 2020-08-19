package com.supporter.prj.ppm.prebid.review_ver.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVer;
import com.supporter.prj.ppm.prebid.review_ver.service.PpmPrebidReviewVerService;
import com.supporter.prj.ppm.service.PPMService;

/**
 * @Title: PpmPrebidReviewVerStartHandler
 * @Description: 流程开始操作类
 */
public class PpmPrebidReviewVerStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		PpmPrebidReviewVerService ppmPrebidReviewVerService = (PpmPrebidReviewVerService) SpringContextHolder.getBean(PpmPrebidReviewVerService.class);
		String reviewVerId = (String) execContext.getProcVar("reviewVerId");
		PpmPrebidReviewVer ppmPrebidReviewVer = ppmPrebidReviewVerService.get(reviewVerId);
		if (ppmPrebidReviewVer != null) {
			ppmPrebidReviewVer.setStutas(PpmPrebidReviewVer.PROCESSING);
			ppmPrebidReviewVer.setProcId(execContext.getProcId());
			ppmPrebidReviewVerService.update(ppmPrebidReviewVer);
			PPMService.getScheduleStatusService().saveScheduleStatus(ppmPrebidReviewVer.getPrjId(), "prebid_reviewVer02", null);
		} else {
			EIPService.getLogService().error("无效的reviewVerId:" + reviewVerId);
		}
		return null;
	}

}