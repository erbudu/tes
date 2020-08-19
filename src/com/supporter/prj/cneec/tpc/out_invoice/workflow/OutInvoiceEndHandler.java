package com.supporter.prj.cneec.tpc.out_invoice.workflow;



import java.util.Date;

import com.supporter.prj.cneec.tpc.out_invoice.entity.OutInvoice;
import com.supporter.prj.cneec.tpc.out_invoice.service.OutInvoiceService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.util.CommonUtil;

public class OutInvoiceEndHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		OutInvoiceService reportService = SpringContextHolder.getBean(OutInvoiceService.class);
		String invoiceOutId = (String) execContext.getProcVar("invoiceOutId");
		OutInvoice acla_OutInvoice =  reportService.get(invoiceOutId);
		acla_OutInvoice.setInvoiceOutDate(CommonUtil.format(new Date(), "yyyy-MM-dd"));
		acla_OutInvoice.setInvoiceStatus(OutInvoice.Status.COMPLETE);// 审批完成
        reportService.update(acla_OutInvoice);
        
		return null;
	} 
}
