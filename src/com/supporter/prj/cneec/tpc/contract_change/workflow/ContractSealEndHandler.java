package com.supporter.prj.cneec.tpc.contract_change.workflow;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractSeal;
import com.supporter.prj.cneec.tpc.contract_change.service.ContractChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class ContractSealEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractChangeService contractChangeService = (ContractChangeService) SpringContextHolder.getBean(ContractChangeService.class);
		String sealId = (String) execContext.getProcVar("sealId");
		ContractSeal contractSeal = contractChangeService.getContractSealBySealId(sealId);
		contractSeal.setSwfStatus(ContractSeal.COMPLETED);
		contractSeal.setProcId(execContext.getProcId());
		contractChangeService.update(contractSeal);
		

		//设置变更单状态
		String changeId = contractSeal.getChangeId();
		ContractChange contractChange = contractChangeService.get(changeId);
		contractChange.setSwfStatus(ContractChange.SEALOK);
		//用印审批完成给变更单赋值协议编号
		contractChange.setBusinessNo(contractSeal.getBusinessNo());
		contractChangeService.update(contractChange);
		return null;
	}

}