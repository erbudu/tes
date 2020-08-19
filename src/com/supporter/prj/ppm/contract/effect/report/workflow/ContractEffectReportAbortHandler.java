package com.supporter.prj.ppm.contract.effect.report.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReport;
import com.supporter.prj.ppm.contract.effect.report.service.ContractEffectReportService;

public class ContractEffectReportAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}
  
	public Object execute(ExecContext execContext) {
		ContractEffectReportService service = (ContractEffectReportService) SpringContextHolder.getBean(ContractEffectReportService.class);
		String id = (String) execContext.getProcVar("contractEffectId");
		ContractEffectReport entity = service.get(id);
		entity.setStatus(ContractEffectReport.StatusCodeTable.DRAFT);
		service.updateSimply(entity);
		return null;
	}
  
	public void executeProcVars(ExecContext execContext) {
	}
}
