package com.supporter.prj.cneec.tpc.contract_change.workflow;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.cneec.tpc.contract_change.service.ContractChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractChangeAbortHandler
 * @Description: 流程中止操作类
 * @author: yanweichao
 * @date: 2017-11-08
 * @version: V1.0
 */
public class ContractChangeAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractChangeService contractChangeService = (ContractChangeService) SpringContextHolder.getBean(ContractChangeService.class);
		String changeId = (String) execContext.getProcVar("changeId");
		ContractChange contractChange = contractChangeService.get(changeId);
		contractChange.setSwfStatus(ContractChange.DRAFT);
		contractChangeService.update(contractChange);
		contractChangeService.WsfAbortAferExcuteByContractChange(contractChange);
		return null;
	}

}