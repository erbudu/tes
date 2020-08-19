package com.supporter.prj.linkworks.oa.user_defined.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.user_defined.entity.BusinessRequirement;
import com.supporter.prj.linkworks.oa.user_defined.service.BusinessRequirementService;

public class BusinessRequirementStartHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		BusinessRequirementService service = (BusinessRequirementService)SpringContextHolder.getBean(BusinessRequirementService.class);
		    String id = (String)execContext.getProcVar("id");
		    BusinessRequirement entity = service.get(id);
		    entity.setStatus(BusinessRequirement.PROCESSING);
		    entity.setProcId(execContext.getProcId());
		    service.update(entity);
		return null;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
