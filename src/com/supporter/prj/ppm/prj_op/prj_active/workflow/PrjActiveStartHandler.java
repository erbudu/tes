package com.supporter.prj.ppm.prj_op.prj_active.workflow;

import java.util.Date;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.prj_op.prj_active.entity.PrjActive;
import com.supporter.prj.ppm.prj_op.prj_active.service.PrjActiveService;

public class PrjActiveStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		PrjActiveService service = (PrjActiveService) SpringContextHolder.getBean(PrjActiveService.class);
		String id = (String) execContext.getProcVar("id");
		PrjActive entity = service.get(id);
		entity.setProcId(execContext.getProcId());
		entity.setStatus(PrjActive.StatusCodeTable.EXAM);
		entity.setSubmitDate(new Date());
		service.update(entity);
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
