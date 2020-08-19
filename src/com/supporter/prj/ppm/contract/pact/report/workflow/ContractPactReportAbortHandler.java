package com.supporter.prj.ppm.contract.pact.report.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.pact.report.entity.ContractPactReport;
import com.supporter.prj.ppm.contract.pact.report.service.ContractPactReportService;

public class ContractPactReportAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}
  
	public Object execute(ExecContext execContext) {
		ContractPactReportService service = (ContractPactReportService) SpringContextHolder.getBean(ContractPactReportService.class);
		String id = (String) execContext.getProcVar("reportId");
		ContractPactReport entity = service.get(id);
		entity.setStatus(ContractPactReport.StatusCodeTable.DRAFT);
		service.update(entity);
		return null;
	}
  
	public void executeProcVars(ExecContext execContext) {
	}
}
