package com.supporter.prj.ppm.contract.pact.seal_bfd.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.pact.seal_bfd.entity.ContractPactPublish;
import com.supporter.prj.ppm.contract.pact.seal_bfd.service.ContractPactPublishService;

public class ContractPactPubAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}
  
	public Object execute(ExecContext execContext) {
		ContractPactPublishService service = (ContractPactPublishService) SpringContextHolder.getBean(ContractPactPublishService.class);
		String id = (String) execContext.getProcVar("publishId");
		ContractPactPublish entity = service.get(id);
		entity.setStatus(ContractPactPublish.StatusCodeTable.DRAFT);
		service.update(entity);
		return null;
	}
  
	public void executeProcVars(ExecContext execContext) {
	}
}
