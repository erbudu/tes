package com.supporter.prj.cneec.tpc.contract_change.workflow;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractOrder;
import com.supporter.prj.cneec.tpc.contract_change.service.ContractChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractChangeStartHandler
 * @Description: 流程开始操作类
 */
public class ContractOrderStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractChangeService contractChangeService = (ContractChangeService) SpringContextHolder.getBean(ContractChangeService.class);
		String chId = (String) execContext.getProcVar("chId");
		ContractOrder contractOrder = contractChangeService.getContractOrderByChId(chId);
		if (contractOrder != null) {
			contractOrder.setSwfStatus(ContractOrder.PROCESSING);
			contractOrder.setProcId(execContext.getProcId());
			contractChangeService.update(contractOrder);
		} else {
			EIPService.getLogService().error("无效的changeId:" + chId);
		}
		return null;
	}

}