package com.supporter.prj.ppm.contract.pact.review_ver.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVer;
import com.supporter.prj.ppm.contract.pact.review_ver.service.ContractPactRevVerService;

public class ContractPactRevVerAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}
  
	public Object execute(ExecContext execContext) {
		ContractPactRevVerService service = (ContractPactRevVerService) SpringContextHolder.getBean(ContractPactRevVerService.class);
		String id = (String) execContext.getProcVar("revVerId");
		ContractPactRevVer entity = service.get(id);
		entity.setStatus(ContractPactRevVer.StatusCodeTable.DRAFT);
		service.update(entity);
		return null;
	}
  
	public void executeProcVars(ExecContext execContext) {
	}
}
