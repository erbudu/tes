package com.supporter.prj.ppm.prj_op.pact_close.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.prj_op.pact_close.entity.PactClose;
import com.supporter.prj.ppm.prj_op.pact_close.service.PactCloseService;

public class PactCloseAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}
  
	public Object execute(ExecContext execContext) {
		PactCloseService service = (PactCloseService) SpringContextHolder.getBean(PactCloseService.class);
		String id = (String) execContext.getProcVar("id");
		PactClose entity = service.get(id);
		entity.setStatus(PactClose.StatusCodeTable.DRAFT);
		service.update(entity);
		return null;
	}
  
	public void executeProcVars(ExecContext execContext) {
	}
}
