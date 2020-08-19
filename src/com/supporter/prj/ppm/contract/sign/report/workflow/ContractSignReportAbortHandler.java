package com.supporter.prj.ppm.contract.sign.report.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReport;
import com.supporter.prj.ppm.contract.sign.report.service.ContractSignReportService;

public class ContractSignReportAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}
  
	public Object execute(ExecContext execContext) {
		ContractSignReportService service = (ContractSignReportService) SpringContextHolder.getBean(ContractSignReportService.class);
		String id = (String) execContext.getProcVar("contractSignId");
		ContractSignReport entity = service.get(id);
		entity.setStatus(ContractSignReport.StatusCodeTable.DRAFT);
		service.updateSimply(entity);
		return null;
	}
  
	public void executeProcVars(ExecContext execContext) {
	}
}
