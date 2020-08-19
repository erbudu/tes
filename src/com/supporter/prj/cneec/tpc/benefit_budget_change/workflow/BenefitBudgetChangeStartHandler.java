package com.supporter.prj.cneec.tpc.benefit_budget_change.workflow;

import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.BenefitContractCh;
import com.supporter.prj.cneec.tpc.benefit_budget_change.service.BenefitBudgetChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.log.service.ILogService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class BenefitBudgetChangeStartHandler extends AbstractExecHandler {
	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		BenefitBudgetChangeService service = (BenefitBudgetChangeService) SpringContextHolder
				.getBean(BenefitBudgetChangeService.class);
		String changeId = (String) execContext.getProcVar("changeId");
		BenefitContractCh benefitContractCh = service.getBenefitContractChByChangeId(changeId);
		if (benefitContractCh != null) {
			benefitContractCh.setSwfStatus(Integer.valueOf(10));
			benefitContractCh.setProcId(execContext.getProcId());
			service.update(benefitContractCh);
		} else {
			EIPService.getLogService().error("无效的changeId:" + changeId);
		}
		return null;
	}
}