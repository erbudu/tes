package com.supporter.prj.cneec.tpc.record_filing_manager.workflow;

import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class RecordFilingManagerSwfSpecifiedTreeHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树程序指定
	@Override
	public Object execute(ExecContext execContext) {
		return "ORG_TREE";
	}

}
