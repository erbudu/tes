package com.supporter.prj.cneec.tpc.contract_change.workflow;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.cneec.tpc.contract_change.service.ContractChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractChangeStartHandler
 * @Description: 流程开始操作类
 */
public class ContractChangeStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ContractChangeService contractChangeService = (ContractChangeService) SpringContextHolder.getBean(ContractChangeService.class);
		String changeId = (String) execContext.getProcVar("changeId");
		ContractChange contractChange = contractChangeService.get(changeId);
		if (contractChange != null) {
			contractChange.setSwfStatus(ContractChange.PROCESSING);
			contractChange.setProcId(execContext.getProcId());
			contractChangeService.update(contractChange);
			contractChangeService.WsfSubmitAferExcuteByContractChange(null, contractChange);
		} else {
			EIPService.getLogService().error("无效的changeId:" + changeId);
		}
		return null;
	}

}