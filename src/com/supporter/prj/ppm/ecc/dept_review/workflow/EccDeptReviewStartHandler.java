package com.supporter.prj.ppm.ecc.dept_review.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptReview;
import com.supporter.prj.ppm.ecc.dept_review.service.EccDeptReviewService;

public class EccDeptReviewStartHandler extends AbstractExecHandler {
	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		EccDeptReviewService service = (EccDeptReviewService)SpringContextHolder.getBean(EccDeptReviewService.class);
		String deptEccId = (String)execContext.getProcVar("deptEccId");
		EccDeptReview entity = service.get(deptEccId);
		entity.setProcId(execContext.getProcId());
		entity.setDeptEccStatus(EccDeptReview.PROCESSING);
		service.startEccDept(entity);
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
