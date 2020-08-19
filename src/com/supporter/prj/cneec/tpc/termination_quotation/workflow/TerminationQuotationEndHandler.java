package com.supporter.prj.cneec.tpc.termination_quotation.workflow;

import com.supporter.prj.cneec.tpc.termination_quotation.entity.TerminationQuotation;
import com.supporter.prj.cneec.tpc.termination_quotation.service.TerminationQuotationService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class TerminationQuotationEndHandler extends AbstractExecHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
					
		TerminationQuotationService terminationQuotationService = SpringContextHolder.getBean(TerminationQuotationService.class);
		
		String quotationId = (String) execContext.getProcVar("quotationId");
		TerminationQuotation terminationQuotation = terminationQuotationService.get(quotationId);
			terminationQuotation.setSwfStatus(TerminationQuotation.COMPLETED);
			terminationQuotation.setProcId(execContext.getProcId());
			terminationQuotationService.update(terminationQuotation);		

		
		return null;
		
		
		
	} 
}
