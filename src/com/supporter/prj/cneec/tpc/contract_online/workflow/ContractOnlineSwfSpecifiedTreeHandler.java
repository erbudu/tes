package com.supporter.prj.cneec.tpc.contract_online.workflow;

import com.supporter.prj.cneec.tpc.contract_online.entity.ContractOnline;
import com.supporter.prj.cneec.tpc.contract_online.service.ContractOnlineService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractOnlineSwfTreeHandler
 * @Description: 工作流组织树程序指定类
 * @author: yanweichao
 * @date: 2017-11-06
 * @version: V1.0
 */
public class ContractOnlineSwfSpecifiedTreeHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树程序指定
	@Override
	public Object execute(ExecContext execContext) {
		ContractOnlineService contractOnlineService = SpringContextHolder.getBean(ContractOnlineService.class);
		String contractId = (String) execContext.getProcVar("contractId");
		ContractOnline contractOnline = contractOnlineService.get(contractId);
		return "ORG_TREE";
	}

}
