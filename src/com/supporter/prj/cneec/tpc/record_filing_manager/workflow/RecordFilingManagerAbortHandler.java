package com.supporter.prj.cneec.tpc.record_filing_manager.workflow;

import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.record_filing_manager.service.RecordFilingManagerService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class RecordFilingManagerAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		RecordFilingManagerService filingManagerService = (RecordFilingManagerService) SpringContextHolder.getBean(RecordFilingManagerService.class);
		String recordFilingId = (String) execContext.getProcVar("recordFilingId");
		RecordFilingManager recordFiling = filingManagerService.get(recordFilingId);
		recordFiling.setSwfStatus(RecordFilingManager.DRAFT);
		filingManagerService.update(recordFiling);
		return null;
	}

}