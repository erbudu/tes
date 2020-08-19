package com.supporter.prj.pm.external_drawings.workflow;

import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class ExternalHandlerAbort extends AbstractExecHandler {

	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
//		ExternalDrawingsService externalDrawingsService = SpringContextHolder.getBean(ExternalDrawingsService.class);
//		String externalId = (String) execContext.getProcVar("externalId");
//	    ExternalDrawings entity =  externalDrawingsService.getExternalDrawingsById(externalId);
//		entity.setStatus(ExternalDrawings.StatusCodeTable.DRAFT);
//		externalDrawingsService.update(entity);
//		//审批完成后改变图纸状态
//		externalDrawingsService.updateDrawingStatus(externalId, DrawingLibraryVersion.ABORT);
		return null;
	}

}
