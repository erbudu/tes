package com.supporter.prj.linkworks.oa.user_defined.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.user_defined.entity.UCard;
import com.supporter.prj.linkworks.oa.user_defined.service.UCardService;

public class UCardStartHandler extends AbstractExecHandler {
	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		UCardService service = (UCardService)SpringContextHolder.getBean(UCardService.class);
		    String id = (String)execContext.getProcVar("id");
		    UCard entity = service.get(id);
		    entity.setStatus(UCard.PROCESSING);
		    entity.setProcId(execContext.getProcId());
		    service.update(entity);
		return null;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
