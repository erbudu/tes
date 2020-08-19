package com.supporter.prj.cneec.tpc.derivative_contract_change.workflow;

import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeConChange;
import com.supporter.prj.cneec.tpc.derivative_contract_change.service.DerivativeContractChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: ContractChangeStartHandler
 * @Description: 流程开始操作类
 */
public class DerivativeConChangeStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		DerivativeContractChangeService derivativeContractChangeService = (DerivativeContractChangeService) SpringContextHolder.getBean(DerivativeContractChangeService.class);
		String chId = (String) execContext.getProcVar("chId");
		DerivativeConChange derivativeConChange = derivativeContractChangeService.getContractOrderByChId(chId);
		if (derivativeConChange != null) {
			derivativeConChange.setSwfStatus(DerivativeConChange.PROCESSING);
			derivativeConChange.setProcId(execContext.getProcId());
			derivativeContractChangeService.update(derivativeConChange);
		} else {
			EIPService.getLogService().error("无效的chId:" + chId);
		}
		return null;
	}

}