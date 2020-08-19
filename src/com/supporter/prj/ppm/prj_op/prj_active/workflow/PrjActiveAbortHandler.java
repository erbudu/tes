package com.supporter.prj.ppm.prj_op.prj_active.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.prj_op.prj_active.entity.PrjActive;
import com.supporter.prj.ppm.prj_op.prj_active.service.PrjActiveService;

public class PrjActiveAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}
  
	public Object execute(ExecContext execContext) {
		PrjActiveService service = (PrjActiveService) SpringContextHolder.getBean(PrjActiveService.class);
		String id = (String) execContext.getProcVar("id");
		PrjActive entity = service.get(id);
		entity.setStatus(PrjActive.StatusCodeTable.DRAFT);
		service.update(entity);
		return null;
	}
  
	public void executeProcVars(ExecContext execContext) {
	}
}
