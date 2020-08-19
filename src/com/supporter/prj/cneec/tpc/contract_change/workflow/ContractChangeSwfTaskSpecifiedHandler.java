package com.supporter.prj.cneec.tpc.contract_change.workflow;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.cneec.tpc.contract_change.service.ContractChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractChangeSwfTaskSpecifiedHandler
 * @Description: 工作流普通任务人员程序指定类
 * @author: yanweichao
 * @date: 2017-11-08
 * @version: V1.0
 */
public class ContractChangeSwfTaskSpecifiedHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流普通任务人员程序指定
	@Override
	public Object execute(ExecContext execContext) {
		ContractChangeService contractChangeService = SpringContextHolder.getBean(ContractChangeService.class);
		String changeId = (String) execContext.getProcVar("changeId");
		ContractChange contractChange = contractChangeService.get(changeId);		
		return contractChange.getCreatedById();
	}

}
