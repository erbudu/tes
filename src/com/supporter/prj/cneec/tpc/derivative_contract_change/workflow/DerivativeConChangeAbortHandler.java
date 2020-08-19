package com.supporter.prj.cneec.tpc.derivative_contract_change.workflow;

import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeConChange;
import com.supporter.prj.cneec.tpc.derivative_contract_change.service.DerivativeContractChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractChangeAbortHandler
 * @Description: 流程中止操作类
 */
public class DerivativeConChangeAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		DerivativeContractChangeService derivativeContractChangeService = (DerivativeContractChangeService) SpringContextHolder.getBean(DerivativeContractChangeService.class);
		String chId = (String) execContext.getProcVar("chId");
		DerivativeConChange derivativeConChange = derivativeContractChangeService.getContractOrderByChId(chId);
		derivativeConChange.setSwfStatus(DerivativeConChange.DRAFT);
		derivativeContractChangeService.update(derivativeConChange);
		return null;
	}

}