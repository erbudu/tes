package com.supporter.prj.cneec.tpc.invoice.workflow;



import java.util.Date;

import com.supporter.prj.cneec.tpc.invoice.entity.Invoice;
import com.supporter.prj.cneec.tpc.invoice.service.InvoiceService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.util.CommonUtil;

public class InvoiceEndHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		InvoiceService reportService = SpringContextHolder.getBean(InvoiceService.class);
		String invoiceId = (String) execContext.getProcVar("invoiceId");
		Invoice acla_Invoice =  reportService.get(invoiceId);
		acla_Invoice.setInvoiceStatus(Invoice.Status.COMPLETE);// 审批完成
		acla_Invoice.setConfirmDate(CommonUtil.format(new Date(),"yyyy-MM-dd"));
		reportService.update(acla_Invoice);
		return null;
	} 
}
