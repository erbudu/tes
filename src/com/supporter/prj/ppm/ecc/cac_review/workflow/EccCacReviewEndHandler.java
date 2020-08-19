package com.supporter.prj.ppm.ecc.cac_review.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.ecc.cac_review.entity.EccCacReview;
import com.supporter.prj.ppm.ecc.cac_review.service.EccCacReviewService;

public class EccCacReviewEndHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		EccCacReviewService service = (EccCacReviewService)SpringContextHolder.getBean(EccCacReviewService.class);
		String eccCacId = (String)execContext.getProcVar("eccCacId");
		EccCacReview entity = service.get(eccCacId);
		entity.setDeptEccStatus(EccCacReview.COMPLETE);
		service.endEccCac(entity);
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
