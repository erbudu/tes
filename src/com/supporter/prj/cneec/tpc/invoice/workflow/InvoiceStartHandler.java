package com.supporter.prj.cneec.tpc.invoice.workflow;


import com.supporter.prj.cneec.tpc.invoice.entity.Invoice;
import com.supporter.prj.cneec.tpc.invoice.service.InvoiceService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * 报告流程启动时的Handler.
 * @author liyinfeng
 *
 */
public class InvoiceStartHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		InvoiceService reportService = SpringContextHolder.getBean(InvoiceService.class);
		
		String invoiceId = (String) execContext.getProcVar("invoiceId");
		Invoice report = reportService.get(invoiceId);
		//锁在途金额
		if (report != null) {
			report.setInvoiceStatus(Invoice.Status.PROCESSING);
			report.setProcId(execContext.getProcId());
			reportService.update(report);		
		} else {
			EIPService.getLogService().error("无效的invoiceId:" + invoiceId);
		}
		
		return null;
	}


}
