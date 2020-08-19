package com.supporter.prj.pm.external_drawings.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.pm.external_drawings.entity.ExternalDrawings;
import com.supporter.prj.pm.external_drawings.service.ExternalDrawingsService;


public class ExternalHandlerStart extends AbstractExecHandler {

	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		ExternalDrawingsService externalDrawingsService = SpringContextHolder.getBean(ExternalDrawingsService.class);
		String externalId = (String) execContext.getProcVar("externalId");
		ExternalDrawings entity =  externalDrawingsService.getExternalDrawingsById(externalId);
		entity.setStatus(ExternalDrawings.StatusCodeTable.APPROVAL);
		entity.setProcId(execContext.getProcId());
		externalDrawingsService.update(entity);
		// //审批完成后改变图纸状态
		// externalDrawingsService.updateDrawingStatus(externalId,
		// DrawingLibraryVersion.AUDIT_OUT);
		return null;
	}

}
