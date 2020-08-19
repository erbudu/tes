package com.supporter.prj.linkworks.oa.user_defined.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.user_defined.entity.UNetin;
import com.supporter.prj.linkworks.oa.user_defined.service.UNetinService;

public class NetinEndHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		 UNetinService service = (UNetinService)SpringContextHolder.getBean(UNetinService.class);
		    String id = (String)execContext.getProcVar("id");
		    UNetin entity = service.get(id);
		    entity.setStatus(UNetin.COMPLETE);
		    service.update(entity);
		return null;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
