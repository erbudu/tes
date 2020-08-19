package com.supporter.prj.cneec.tpc.out_invoice.workflow;

import com.supporter.prj.cneec.tpc.out_invoice.entity.OutInvoice;
import com.supporter.prj.cneec.tpc.out_invoice.service.OutInvoiceService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class OutInvoiceAbortHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		
		OutInvoiceService reportService = SpringContextHolder.getBean(OutInvoiceService.class);
		String invoiceOutId = (String) execContext.getProcVar("invoiceOutId");
		OutInvoice report =  reportService.get(invoiceOutId);
		report.setInvoiceStatus(OutInvoice.Status.DRAFT);
		reportService.update(report);		
		return null;
	} 

}
