package com.supporter.prj.cneec.tpc.contract_change.workflow;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.cneec.tpc.contract_change.service.ContractChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractChangeEndHandler
 * @Description: 流程结束操作类
 * @author: yanweichao
 * @date: 2017-11-08
 * @version: V1.0
 */
public class ContractChangeEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractChangeService contractChangeService = (ContractChangeService) SpringContextHolder.getBean(ContractChangeService.class);
		String changeId = (String) execContext.getProcVar("changeId");
		ContractChange contractChange = contractChangeService.get(changeId);
		contractChange.setSwfStatus(ContractChange.COMPLETED);
		contractChange.setProcId(execContext.getProcId());
		contractChangeService.update(contractChange);
		return null;
	}

}