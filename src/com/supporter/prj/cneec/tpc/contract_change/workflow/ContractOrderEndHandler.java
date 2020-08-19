package com.supporter.prj.cneec.tpc.contract_change.workflow;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractOrder;
import com.supporter.prj.cneec.tpc.contract_change.service.ContractChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class ContractOrderEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractChangeService contractChangeService = (ContractChangeService) SpringContextHolder.getBean(ContractChangeService.class);
		String chId = (String) execContext.getProcVar("chId");
		ContractOrder contractOrder = contractChangeService.getContractOrderByChId(chId);
		contractOrder.setSwfStatus(ContractOrder.COMPLETED);
		contractOrder.setProcId(execContext.getProcId());
		contractChangeService.update(contractOrder);
        contractChangeService.adjustPrjBenefitBudget(contractOrder);
		contractChangeService.change(null,contractOrder);
		//设置变更单状态
		String changeId = contractOrder.getChangeId();
		ContractChange contractChange = contractChangeService.get(changeId);
		contractChange.setSwfStatus(ContractChange.CONTRACTOK);
		return null;
	}

}