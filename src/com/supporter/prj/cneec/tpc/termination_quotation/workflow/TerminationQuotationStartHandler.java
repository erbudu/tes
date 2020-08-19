package com.supporter.prj.cneec.tpc.termination_quotation.workflow;


import com.supporter.prj.cneec.tpc.termination_quotation.entity.TerminationQuotation;
import com.supporter.prj.cneec.tpc.termination_quotation.service.TerminationQuotationService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * 新闻动态流程启动时的Handler.
 * @author Arnold
 *
 */
public class TerminationQuotationStartHandler extends AbstractExecHandler {

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
		if (terminationQuotation != null) {
			terminationQuotation.setSwfStatus(TerminationQuotation.PROCESSING);			
			terminationQuotation.setProcId(execContext.getProcId());
			terminationQuotationService.update(terminationQuotation);		
		} else {
			EIPService.getLogService().error("quotationId:" + quotationId);
		}
		
		return null;
	}


}
