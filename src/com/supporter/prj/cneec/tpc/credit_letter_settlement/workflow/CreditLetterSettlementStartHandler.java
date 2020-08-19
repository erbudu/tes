package com.supporter.prj.cneec.tpc.credit_letter_settlement.workflow;


import com.supporter.prj.cneec.tpc.credit_letter_settlement.entity.CreditLetterSettlement;
import com.supporter.prj.cneec.tpc.credit_letter_settlement.service.CreditLetterSettlementService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * 报告流程启动时的Handler.
 * @author liyinfeng
 *
 */
public class CreditLetterSettlementStartHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		CreditLetterSettlementService reportService = SpringContextHolder.getBean(CreditLetterSettlementService.class);
		
		String creditLetterSettlementId = (String) execContext.getProcVar("creditLetterSettlementId");
		CreditLetterSettlement report = reportService.get(creditLetterSettlementId);
		//锁在途金额
		reportService.lockOnwayAmount(report);
		if (report != null) {
			report.setSettlementStatus(CreditLetterSettlement.CreditLetterSettlementStatus.PROCESSING.getKey());
			report.setProcId(execContext.getProcId());
			reportService.update(report);		
		} else {
			EIPService.getLogService().error("无效的creditLetterSettlementId:" + creditLetterSettlementId);
		}
		
		return null;
	}


}
