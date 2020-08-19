package com.supporter.prj.cneec.tpc.invoice.workflow;

import com.supporter.prj.cneec.tpc.invoice.entity.Invoice;
import com.supporter.prj.cneec.tpc.invoice.service.InvoiceService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class InvoiceAbortHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		
		InvoiceService reportService = SpringContextHolder.getBean(InvoiceService.class);
		String invoiceId = (String) execContext.getProcVar("invoiceId");
		Invoice report =  reportService.get(invoiceId);
		report.setInvoiceStatus(Invoice.Status.DRAFT);
		reportService.update(report);		
		return null;
	} 

}
