package com.supporter.prj.cneec.tpc.credit_letter_collecting.workflow;


import com.supporter.prj.cneec.tpc.credit_letter_collecting.entity.CreditLetterCollecting;
import com.supporter.prj.cneec.tpc.credit_letter_collecting.service.CreditLetterCollectingService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * 报告流程启动时的Handler.
 * @author liyinfeng
 *
 */
public class CreditLetterCollectingStartHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		CreditLetterCollectingService reportService = SpringContextHolder.getBean(CreditLetterCollectingService.class);
		
		String creditLetterCollectingId = (String) execContext.getProcVar("creditLetterCollectingId");
		CreditLetterCollecting report = reportService.get(creditLetterCollectingId);
		if (report != null) {
			report.setSwfStatus(CreditLetterCollecting.CreditLetterSwfStatus.PROCESSING.getKey());
			report.setProcId(execContext.getProcId());
			reportService.update(report);		
		} else {
			EIPService.getLogService().error("无效的creditLetterCollectingId:" + creditLetterCollectingId);
		}
		
		return null;
	}


}
