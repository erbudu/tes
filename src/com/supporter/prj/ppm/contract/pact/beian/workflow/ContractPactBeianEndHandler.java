package com.supporter.prj.ppm.contract.pact.beian.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.pact.beian.entity.ContractPactBeian;
import com.supporter.prj.ppm.contract.pact.beian.service.ContractPactBeianService;

public class ContractPactBeianEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractPactBeianService service = (ContractPactBeianService) SpringContextHolder.getBean(ContractPactBeianService.class);
		String id = (String) execContext.getProcVar("recordId");
		ContractPactBeian entity = service.get(id);
		entity.setStatus(ContractPactBeian.StatusCodeTable.COMPLETE);
		service.update(entity);
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
