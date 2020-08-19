package com.supporter.prj.pm.design_change.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.pm.design_change.entity.DesignChange;
import com.supporter.prj.pm.design_change.service.DesignChangeService;

public class DesignChangeHandlerStart extends AbstractExecHandler {

	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		DesignChangeService designChangeService = SpringContextHolder.getBean(DesignChangeService.class);
		String applyId = (String) execContext.getProcVar("applyId");
		DesignChange designChange = designChangeService.getDesignChangeById(applyId);
		if (designChange != null) {
			designChange.setStatus(DesignChange.StatusCodeTable.EXAMING);
			designChange.setProcId(execContext.getProcId());
			designChangeService.saveOrUpdate(null, designChange);		
		} else {
			EIPService.getLogService().error("无效的applyId:" + applyId);
		}
		return null;
	}

}
