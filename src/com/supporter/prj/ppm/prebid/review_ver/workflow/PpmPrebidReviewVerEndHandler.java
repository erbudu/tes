package com.supporter.prj.ppm.prebid.review_ver.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVer;
import com.supporter.prj.ppm.prebid.review_ver.service.PpmPrebidReviewVerService;
import com.supporter.prj.ppm.service.PPMService;

import net.sf.json.JSONObject;

/**
 * @Title: PpmPrebidReviewVerEndHandler
 * @Description: 流程结束操作类
 * @version: V1.0
 */
public class PpmPrebidReviewVerEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		PpmPrebidReviewVerService ppmPrebidReviewVerService = (PpmPrebidReviewVerService) SpringContextHolder.getBean(PpmPrebidReviewVerService.class);
		String reviewVerId = (String) execContext.getProcVar("reviewVerId");
		PpmPrebidReviewVer ppmPrebidReviewVer = ppmPrebidReviewVerService.get(reviewVerId);
		System.out.println(JSONObject.fromObject(ppmPrebidReviewVer));
		ppmPrebidReviewVer.setStutas(PpmPrebidReviewVer.COMPLETED);
		ppmPrebidReviewVer.setResult(PpmPrebidReviewVer.PASS);
		ppmPrebidReviewVerService.update(ppmPrebidReviewVer);
		PPMService.getScheduleStatusService().saveScheduleStatus(ppmPrebidReviewVer.getPrjId(), "prebid_reviewVer03", null);
		return null;
	}

}