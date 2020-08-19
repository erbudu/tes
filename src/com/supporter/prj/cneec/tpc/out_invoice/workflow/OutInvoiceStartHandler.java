package com.supporter.prj.cneec.tpc.out_invoice.workflow;


import com.supporter.prj.cneec.tpc.out_invoice.entity.OutInvoice;
import com.supporter.prj.cneec.tpc.out_invoice.service.OutInvoiceService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * 报告流程启动时的Handler.
 * @author liyinfeng
 *
 */
public class OutInvoiceStartHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		OutInvoiceService reportService = SpringContextHolder.getBean(OutInvoiceService.class);
		
		String invoiceOutId = (String) execContext.getProcVar("invoiceOutId");
		OutInvoice report = reportService.get(invoiceOutId);
		if (report != null) {
			report.setInvoiceStatus(OutInvoice.Status.PROCESSING);
			report.setProcId(execContext.getProcId());
			reportService.update(report);		
		} else {
			EIPService.getLogService().error("无效的invoiceOutId:" + invoiceOutId);
		}
		
		return null;
	}


}
