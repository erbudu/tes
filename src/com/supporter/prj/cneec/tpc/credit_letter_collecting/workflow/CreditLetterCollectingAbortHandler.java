package com.supporter.prj.cneec.tpc.credit_letter_collecting.workflow;

import com.supporter.prj.cneec.tpc.credit_letter_collecting.entity.CreditLetterCollecting;
import com.supporter.prj.cneec.tpc.credit_letter_collecting.service.CreditLetterCollectingService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class CreditLetterCollectingAbortHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		
		CreditLetterCollectingService reportService = SpringContextHolder.getBean(CreditLetterCollectingService.class);
		String creditLetterCollectingId = (String) execContext.getProcVar("creditLetterCollectingId");
		CreditLetterCollecting report =  reportService.get(creditLetterCollectingId);
		report.setSwfStatus(CreditLetterCollecting.CreditLetterSwfStatus.DRAFT.getKey());
		reportService.update(report);		
		return null;
	} 

}
