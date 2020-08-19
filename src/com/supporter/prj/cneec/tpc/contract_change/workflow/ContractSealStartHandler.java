package com.supporter.prj.cneec.tpc.contract_change.workflow;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractSeal;
import com.supporter.prj.cneec.tpc.contract_change.service.ContractChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class ContractSealStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractChangeService contractChangeService = (ContractChangeService) SpringContextHolder.getBean(ContractChangeService.class);
		String sealId = (String) execContext.getProcVar("sealId");
		ContractSeal contractSeal = contractChangeService.getContractSealBySealId(sealId);
		if (contractSeal != null) {
			contractSeal.setSwfStatus(ContractSeal.PROCESSING);
			contractSeal.setProcId(execContext.getProcId());
			contractChangeService.update(contractSeal);
		} else {
			EIPService.getLogService().error("无效的sealId:" + sealId);
		}
		return null;
	}

}