package com.supporter.prj.cneec.tpc.contract_online.workflow;

import com.supporter.prj.cneec.tpc.contract_online.entity.ContractOnline;
import com.supporter.prj.cneec.tpc.contract_online.service.ContractOnlineService;
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
public class ContractOnlineAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractOnlineService contractOnlineService = (ContractOnlineService) SpringContextHolder.getBean(ContractOnlineService.class);
		String contractId = (String) execContext.getProcVar("contractId");
		ContractOnline contractOnline = contractOnlineService.get(contractId);
		contractOnline.setSwfStatus(ContractOnline.DRAFT);
		contractOnlineService.update(contractOnline);
		return null;
	}

}