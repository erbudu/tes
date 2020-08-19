package com.supporter.prj.cneec.tpc.derivative_contract_change.workflow;

import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeContractChange;
import com.supporter.prj.cneec.tpc.derivative_contract_change.service.DerivativeContractChangeService;
import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.DerivativeContractOnline;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: DerivativeContractOnlineStartHandler
 * @Description: 流程开始操作类
 */
public class DerivativeContractChangeStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		DerivativeContractChangeService contractChangeService = (DerivativeContractChangeService) SpringContextHolder.getBean(DerivativeContractChangeService.class);
		String changeId = (String) execContext.getProcVar("changeId");
		DerivativeContractChange derivativeContractChange = contractChangeService.get(changeId);
		if (derivativeContractChange != null) {
			derivativeContractChange.setSwfStatus(DerivativeContractOnline.PROCESSING);
			derivativeContractChange.setProcId(execContext.getProcId());
			contractChangeService.update(derivativeContractChange);
			contractChangeService.WsfSubmitAferExcuteByDerivativeContractChange(null, derivativeContractChange);
		} else {
			EIPService.getLogService().error("无效的changeId:" + changeId);
		}
		return null;
	}

}