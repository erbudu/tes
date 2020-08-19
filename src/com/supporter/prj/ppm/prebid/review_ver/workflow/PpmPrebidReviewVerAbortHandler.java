package com.supporter.prj.ppm.prebid.review_ver.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVer;
import com.supporter.prj.ppm.prebid.review_ver.service.PpmPrebidReviewVerService;

/**
 * @Title: PpmPrebidReviewVerAbortHandler
 * @Description: 流程中止操作类
 */
public class PpmPrebidReviewVerAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		PpmPrebidReviewVerService ppmPrebidReviewVerService = (PpmPrebidReviewVerService) SpringContextHolder.getBean(PpmPrebidReviewVerService.class);
		String reviewVerId = (String) execContext.getProcVar("reviewVerId");
		PpmPrebidReviewVer ppmPrebidReviewVer = ppmPrebidReviewVerService.get(reviewVerId);
		ppmPrebidReviewVer.setStutas(PpmPrebidReviewVer.DRAFT);
		ppmPrebidReviewVer.setResult(PpmPrebidReviewVer.NOPASS);
		ppmPrebidReviewVerService.update(ppmPrebidReviewVer);
		return null;
	}

}