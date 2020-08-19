package com.supporter.prj.cneec.tpc.receive_credit_letter.workflow;



import com.supporter.prj.cneec.tpc.receive_credit_letter.entity.ReceiveCreditLetter;
import com.supporter.prj.cneec.tpc.receive_credit_letter.service.ReceiveCreditLetterService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class ReceiveCreditLetterEndHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		ReceiveCreditLetterService reportService = SpringContextHolder.getBean(ReceiveCreditLetterService.class);
		String receiveCreditLetterId = (String) execContext.getProcVar("receiveCreditLetterId");
		ReceiveCreditLetter acla_ReceiveCreditLetter =  reportService.get(receiveCreditLetterId);
		acla_ReceiveCreditLetter.setSwfStatus(ReceiveCreditLetter.ReceiveCreditLetterStatus.COMPLETE.getKey());// 审批完成
		reportService.update(acla_ReceiveCreditLetter);
        
		return null;
	} 
}
