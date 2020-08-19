package com.supporter.prj.ppm.ecc.wg_review.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.ecc.wg_review.entity.EccWgReview;
import com.supporter.prj.ppm.ecc.wg_review.service.EccWgReviewService;

public class EccWgReviewEndHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		EccWgReviewService service = (EccWgReviewService)SpringContextHolder.getBean(EccWgReviewService.class);
		String eccWgId = (String)execContext.getProcVar("eccWgId");
		EccWgReview entity = service.get(eccWgId);
		entity.setDeptEccStatus(EccWgReview.COMPLETE);
		service.endEccWg(entity);
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
