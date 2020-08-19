package com.supporter.prj.cneec.tpc.record_filing_manager.workflow;

import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.record_filing_manager.service.RecordFilingManagerService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class RecordFilingManagerSwfTaskSpecifiedHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流普通任务人员程序指定
	@Override
	public Object execute(ExecContext execContext) {
		RecordFilingManagerService filingManagerService = (RecordFilingManagerService) SpringContextHolder.getBean(RecordFilingManagerService.class);
		String recordFilingId = (String) execContext.getProcVar("recordFilingId");
		RecordFilingManager recordFiling = filingManagerService.get(recordFilingId);
		return recordFiling.getCreatedBy();
	}

}
