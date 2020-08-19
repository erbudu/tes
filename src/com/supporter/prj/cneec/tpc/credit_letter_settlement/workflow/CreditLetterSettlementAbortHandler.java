package com.supporter.prj.cneec.tpc.credit_letter_settlement.workflow;

import com.supporter.prj.cneec.tpc.credit_letter_settlement.entity.CreditLetterSettlement;
import com.supporter.prj.cneec.tpc.credit_letter_settlement.service.CreditLetterSettlementService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class CreditLetterSettlementAbortHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		
		CreditLetterSettlementService reportService = SpringContextHolder.getBean(CreditLetterSettlementService.class);
		String creditLetterSettlementId = (String) execContext.getProcVar("creditLetterSettlementId");
		CreditLetterSettlement report =  reportService.get(creditLetterSettlementId);
		report.setSettlementStatus(CreditLetterSettlement.CreditLetterSettlementStatus.DRAFT.getKey());
		reportService.update(report);		
		reportService.unLockOnwayAmount(report);
		return null;
	} 

}
