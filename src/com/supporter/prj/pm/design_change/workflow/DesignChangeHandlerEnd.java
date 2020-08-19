package com.supporter.prj.pm.design_change.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.pm.design_change.entity.DesignChange;
import com.supporter.prj.pm.design_change.service.DesignChangeService;

public class DesignChangeHandlerEnd extends AbstractExecHandler {

	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		DesignChangeService designChangeService = SpringContextHolder.getBean(DesignChangeService.class);
		String applyId = (String) execContext.getProcVar("applyId");
		DesignChange designChange =  designChangeService.getDesignChangeById(applyId);
//		designChange.setStatus(DesignChange.StatusCodeTable.INVALID);
		designChangeService.valid(null, designChange);
		return null;
	} 
}
