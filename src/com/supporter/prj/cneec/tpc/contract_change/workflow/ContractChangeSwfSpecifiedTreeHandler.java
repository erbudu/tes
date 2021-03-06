package com.supporter.prj.cneec.tpc.contract_change.workflow;

import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractChangeSwfSpecifiedTreeHandler
 * @Description: 工作流组织树程序指定类
 * @author: yanweichao
 * @date: 2017-11-08
 * @version: V1.0
 */
public class ContractChangeSwfSpecifiedTreeHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树程序指定
	@Override
	public Object execute(ExecContext execContext) {
		return "ORG_TREE";
	}

}
