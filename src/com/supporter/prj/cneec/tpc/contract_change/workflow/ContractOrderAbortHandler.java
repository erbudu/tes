package com.supporter.prj.cneec.tpc.contract_change.workflow;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractOrder;
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
public class ContractOrderAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractChangeService contractChangeService = (ContractChangeService) SpringContextHolder.getBean(ContractChangeService.class);
		String chId = (String) execContext.getProcVar("chId");
		ContractOrder contractOrder = contractChangeService.getContractOrderByChId(chId);
		contractOrder.setSwfStatus(ContractOrder.DRAFT);
		contractChangeService.update(contractOrder);
		return null;
	}

}