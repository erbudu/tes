package com.supporter.prj.cneec.tpc.derivative_contract_online.workflow;

import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.DerivativeContractOnline;
import com.supporter.prj.cneec.tpc.derivative_contract_online.service.DerivativeContractOnlineService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractOnlineAbortHandler
 * @Description: 流程中止操作类
 * @author: yanweichao
 * @date: 2017-11-06
 * @version: V1.0
 */
public class DerivativeContractOnlineAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		DerivativeContractOnlineService contractOnlineService = (DerivativeContractOnlineService) SpringContextHolder.getBean(DerivativeContractOnlineService.class);
		String contractId = (String) execContext.getProcVar("contractId");
		DerivativeContractOnline contractOnline = contractOnlineService.get(contractId);
		contractOnline.setSwfStatus(DerivativeContractOnline.DRAFT);
		contractOnlineService.update(contractOnline);
		return null;
	}

}