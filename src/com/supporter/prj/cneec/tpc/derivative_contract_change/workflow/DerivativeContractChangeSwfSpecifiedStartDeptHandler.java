package com.supporter.prj.cneec.tpc.derivative_contract_change.workflow;

import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeContractChange;
import com.supporter.prj.cneec.tpc.derivative_contract_change.service.DerivativeContractChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: DerivativeContractChangeStartHandler
 * @Description: 工作流组织树起始部门程序指定类
 * @version: V1.0
 */
public class DerivativeContractChangeSwfSpecifiedStartDeptHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树起始部门程序指定
	@Override
	public Object execute(ExecContext execContext) {
		DerivativeContractChangeService contractChangeService = SpringContextHolder.getBean(DerivativeContractChangeService.class);
		String changeId = (String) execContext.getProcVar("changeId");
		DerivativeContractChange contractChange = contractChangeService.get(changeId);
		return contractChange.getDeptId();
	}

}
