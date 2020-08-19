package com.supporter.prj.cneec.tpc.credit_letter_collecting.workflow;

import com.supporter.prj.cneec.tpc.credit_letter_collecting.entity.CreditLetterCollecting;
import com.supporter.prj.cneec.tpc.credit_letter_collecting.service.CreditLetterCollectingService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.util.CommonUtil;

public class CreditLetterCollectingEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		CreditLetterCollectingService reportService = SpringContextHolder.getBean(CreditLetterCollectingService.class);
		String creditLetterCollectingId = (String) execContext.getProcVar("creditLetterCollectingId");
		CreditLetterCollecting acla_CreditLetterCollecting =  reportService.get(creditLetterCollectingId);
		reportService.completeExam(acla_CreditLetterCollecting);
		return null;
	} 
}
