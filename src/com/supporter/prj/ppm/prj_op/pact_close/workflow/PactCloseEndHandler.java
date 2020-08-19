package com.supporter.prj.ppm.prj_op.pact_close.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.pact.report.service.ContractPactReportService;
import com.supporter.prj.ppm.prj_op.pact_close.entity.PactClose;
import com.supporter.prj.ppm.prj_op.pact_close.service.PactCloseService;

public class PactCloseEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		PactCloseService service = (PactCloseService) SpringContextHolder.getBean(PactCloseService.class);
		String id = (String) execContext.getProcVar("id");
		PactClose entity = service.get(id);
		entity.setStatus(PactClose.StatusCodeTable.COMPLETE);
		service.update(entity);
		// 设置合同及协议报审状态为失效
		ContractPactReportService pactReportService = (ContractPactReportService) SpringContextHolder
				.getBean(ContractPactReportService.class);
		pactReportService.invalidReport(entity.getReportId());
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
