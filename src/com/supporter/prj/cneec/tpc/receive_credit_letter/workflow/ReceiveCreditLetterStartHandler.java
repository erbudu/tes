package com.supporter.prj.cneec.tpc.receive_credit_letter.workflow;


import com.supporter.prj.cneec.tpc.receive_credit_letter.entity.ReceiveCreditLetter;
import com.supporter.prj.cneec.tpc.receive_credit_letter.service.ReceiveCreditLetterService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * 报告流程启动时的Handler.
 * @author liyinfeng
 *
 */
public class ReceiveCreditLetterStartHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		ReceiveCreditLetterService reportService = SpringContextHolder.getBean(ReceiveCreditLetterService.class);
		
		String receiveCreditLetterId = (String) execContext.getProcVar("receiveCreditLetterId");
		ReceiveCreditLetter report = reportService.get(receiveCreditLetterId);
		if (report != null) {
			report.setSwfStatus(ReceiveCreditLetter.ReceiveCreditLetterStatus.PROCESSING.getKey());
			report.setProcId(execContext.getProcId());
			reportService.update(report);		
		} else {
			EIPService.getLogService().error("无效的receiveCreditLetterId:" + receiveCreditLetterId);
		}
		
		return null;
	}


}
