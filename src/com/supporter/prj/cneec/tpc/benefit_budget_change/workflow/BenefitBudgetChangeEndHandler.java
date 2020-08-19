package com.supporter.prj.cneec.tpc.benefit_budget_change.workflow;

import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.BenefitContractCh;
import com.supporter.prj.cneec.tpc.benefit_budget_change.service.BenefitBudgetChangeService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class BenefitBudgetChangeEndHandler extends AbstractExecHandler {
	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		BenefitBudgetChangeService service = (BenefitBudgetChangeService) SpringContextHolder
				.getBean(BenefitBudgetChangeService.class);
		String changeId = (String) execContext.getProcVar("changeId");
		BenefitContractCh benefitContractCh = service.getBenefitContractChByChangeId(changeId);
		benefitContractCh.setSwfStatus(Integer.valueOf(20));
		benefitContractCh.setProcId(execContext.getProcId());
		service.update(benefitContractCh);
		//必须在completeExam()方法前调用。
		service.completeExamAdjustPrjBudget(benefitContractCh);
		//流程审批完成处理（更新合同预算表及项目预算表）
		service.completeExam(null, benefitContractCh);
		//更新项目预算表
//		service.saveOrUpdateProportionAndSpecialBudget(benefitContractCh);
		return null;
	}
}