package com.supporter.prj.ppm.contract.sign.review_ver.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.contract.sign.review_ver.entity.ContractSignRevVer;
import com.supporter.prj.ppm.contract.sign.review_ver.service.ContractSignRevVerService;

public class ContractSignRevVerAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}
  
	public Object execute(ExecContext execContext) {
		ContractSignRevVerService service = (ContractSignRevVerService) SpringContextHolder.getBean(ContractSignRevVerService.class);
		String id = (String) execContext.getProcVar("reviewVerId");
		ContractSignRevVer entity = service.get(id);
		entity.setStatus(ContractSignRevVer.StatusCodeTable.DRAFT);
		service.updateSimply(entity);
		return null;
	}
  
	public void executeProcVars(ExecContext execContext) {
	}
}
