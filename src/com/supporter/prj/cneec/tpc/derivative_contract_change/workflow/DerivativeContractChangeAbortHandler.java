package com.supporter.prj.cneec.tpc.derivative_contract_change.workflow;

import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeContractChange;
import com.supporter.prj.cneec.tpc.derivative_contract_change.service.DerivativeContractChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractChangeAbortHandler
 * @Description: 流程中止操作类
 */
public class DerivativeContractChangeAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		DerivativeContractChangeService contractChangeService = (DerivativeContractChangeService) SpringContextHolder.getBean(DerivativeContractChangeService.class);
		String changeId = (String) execContext.getProcVar("changeId");
		DerivativeContractChange contractChange = contractChangeService.get(changeId);
		contractChange.setSwfStatus(DerivativeContractChange.DRAFT);
		contractChangeService.update(contractChange);
		contractChangeService.WsfAbortAferExcuteByDerivativeContractChange(contractChange);;
		return null;
	}

}