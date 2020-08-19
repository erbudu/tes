package com.supporter.prj.linkworks.oa.user_defined.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.user_defined.entity.Empchange;
import com.supporter.prj.linkworks.oa.user_defined.service.EmpchangeService;

public class EmpChangeStartHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		 EmpchangeService service = (EmpchangeService)SpringContextHolder.getBean(EmpchangeService.class);
		    String id = (String)execContext.getProcVar("id");
		    Empchange entity = service.get(id);
		    entity.setStatus(Empchange.PROCESSING);
		    entity.setProcId(execContext.getProcId());
		    service.update(entity);
		return null;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
