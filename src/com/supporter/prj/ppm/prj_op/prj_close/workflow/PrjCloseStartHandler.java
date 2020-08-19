package com.supporter.prj.ppm.prj_op.prj_close.workflow;

import java.util.Date;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.prj_op.prj_close.entity.PrjClose;
import com.supporter.prj.ppm.prj_op.prj_close.service.PrjCloseService;

public class PrjCloseStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		PrjCloseService service = (PrjCloseService) SpringContextHolder.getBean(PrjCloseService.class);
		String id = (String) execContext.getProcVar("id");
		PrjClose entity = service.get(id);
		entity.setProcId(execContext.getProcId());
		entity.setStatus(PrjClose.StatusCodeTable.EXAM);
		entity.setSubmitDate(new Date());
		service.update(entity);
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
