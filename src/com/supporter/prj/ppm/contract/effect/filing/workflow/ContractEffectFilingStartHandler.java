package com.supporter.prj.ppm.contract.effect.filing.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.effect.filing.entity.ContractEffectFiling;
import com.supporter.prj.ppm.contract.effect.filing.service.ContractEffectFilingService;

public class ContractEffectFilingStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractEffectFilingService service = (ContractEffectFilingService) SpringContextHolder.getBean(ContractEffectFilingService.class);
		String id = (String) execContext.getProcVar("filingId");
		ContractEffectFiling entity = service.get(id);
		entity.setProcId(execContext.getProcId());
		entity.setStatus(ContractEffectFiling.StatusCodeTable.EXAM);
		service.updateSimply(entity);
		return entity;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
