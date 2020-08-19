package com.supporter.prj.ppm.ecc.group_review.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.ecc.group_review.entity.EccGroupReview;
import com.supporter.prj.ppm.ecc.group_review.service.EccGroupReviewService;

public class EccGroupReviewEndHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		EccGroupReviewService service = (EccGroupReviewService)SpringContextHolder.getBean(EccGroupReviewService.class);
		String eccGroupId = (String)execContext.getProcVar("eccGroupId");
		EccGroupReview entity = service.get(eccGroupId);
		entity.setGroupEccStatus(EccGroupReview.COMPLETE);
		service.endEccGroup(entity);
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
