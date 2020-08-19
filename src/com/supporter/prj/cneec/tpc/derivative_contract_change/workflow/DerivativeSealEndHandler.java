package com.supporter.prj.cneec.tpc.derivative_contract_change.workflow;

import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeContractChange;
import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeSeal;
import com.supporter.prj.cneec.tpc.derivative_contract_change.service.DerivativeContractChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class DerivativeSealEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		DerivativeContractChangeService derivativeContractChangeService = (DerivativeContractChangeService) SpringContextHolder.getBean(DerivativeContractChangeService.class);
		String sealId = (String) execContext.getProcVar("sealId");
		DerivativeSeal derivativeSeal = derivativeContractChangeService.getContractSealBySealId(sealId);
		derivativeSeal.setSwfStatus(DerivativeSeal.COMPLETED);
		derivativeSeal.setProcId(execContext.getProcId());
		derivativeContractChangeService.update(derivativeSeal);
		
		//设置变更单状态
		String changeId = derivativeSeal.getChangeId();
		DerivativeContractChange derivativeChange = derivativeContractChangeService.get(changeId);
		derivativeChange.setSwfStatus(DerivativeContractChange.SEALOK);
		//用印审批完成给变更单赋值协议编号
		derivativeChange.setBusinessNo(derivativeSeal.getBusinessNo());
		derivativeContractChangeService.update(derivativeChange);
		return null;
	}

}