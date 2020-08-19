package com.supporter.prj.ppm.contract.sign.filing.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.sign.filing.entity.ContractFiling;
import com.supporter.prj.ppm.contract.sign.filing.service.ContractFilingService;

public class ContractSignFilingAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}
  
	public Object execute(ExecContext execContext) {
		ContractFilingService service = (ContractFilingService) SpringContextHolder.getBean(ContractFilingService.class);
		String id = (String) execContext.getProcVar("filingId");
		ContractFiling entity = service.get(id);
		entity.setStatus(ContractFiling.StatusCodeTable.DRAFT);
		service.updateSimply(entity);
		return null;
	}
  
	public void executeProcVars(ExecContext execContext) {
	}
}
