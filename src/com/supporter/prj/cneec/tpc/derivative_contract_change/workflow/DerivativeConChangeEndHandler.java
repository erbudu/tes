package com.supporter.prj.cneec.tpc.derivative_contract_change.workflow;

import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeConChange;
import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeContractChange;
import com.supporter.prj.cneec.tpc.derivative_contract_change.service.DerivativeContractChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class DerivativeConChangeEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		DerivativeContractChangeService derivativeContractChangeService = (DerivativeContractChangeService) SpringContextHolder.getBean(DerivativeContractChangeService.class);
		String chId = (String) execContext.getProcVar("chId");
		DerivativeConChange derivativeConChange = derivativeContractChangeService.getContractOrderByChId(chId);
		derivativeConChange.setSwfStatus(DerivativeConChange.COMPLETED);
		derivativeConChange.setProcId(execContext.getProcId());
		derivativeContractChangeService.update(derivativeConChange);
		//调整项目预算表（注意：一定要在更新合同数据前执行）
		derivativeContractChangeService.adjustPrjBenefitBudget(derivativeConChange);
		derivativeContractChangeService.change(null,derivativeConChange);
	
		//设置变更单状态
		String changeId = derivativeConChange.getChangeId();
		DerivativeContractChange derivativeChange = derivativeContractChangeService.get(changeId);
		derivativeChange.setSwfStatus(DerivativeContractChange.CONTRACTOK);
		return null;
	}

}