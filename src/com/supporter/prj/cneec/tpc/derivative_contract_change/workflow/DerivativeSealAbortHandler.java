package com.supporter.prj.cneec.tpc.derivative_contract_change.workflow;

import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeSeal;
import com.supporter.prj.cneec.tpc.derivative_contract_change.service.DerivativeContractChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class DerivativeSealAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		DerivativeContractChangeService derivativeContractChangeService = (DerivativeContractChangeService) SpringContextHolder.getBean(DerivativeContractChangeService.class);
		String sealId = (String) execContext.getProcVar("sealId");
		DerivativeSeal derivativeSeal = derivativeContractChangeService.getContractSealBySealId(sealId);
		derivativeSeal.setSwfStatus(DerivativeSeal.DRAFT);
		derivativeContractChangeService.update(derivativeSeal);
		return null;
	}

}