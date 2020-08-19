package com.supporter.prj.cneec.tpc.credit_letter_settlement.workflow;



import com.supporter.prj.cneec.tpc.credit_letter_settlement.entity.CreditLetterSettlement;
import com.supporter.prj.cneec.tpc.credit_letter_settlement.service.CreditLetterSettlementService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.util.CommonUtil;

public class CreditLetterSettlementEndHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		CreditLetterSettlementService reportService = SpringContextHolder.getBean(CreditLetterSettlementService.class);
		String creditLetterSettlementId = (String) execContext.getProcVar("creditLetterSettlementId");
		CreditLetterSettlement acla_CreditLetterSettlement =  reportService.get(creditLetterSettlementId);
		acla_CreditLetterSettlement.setSettlementStatus(CreditLetterSettlement.CreditLetterSettlementStatus.COMPLETE.getKey());// 审批完成
		acla_CreditLetterSettlement.setPaymentStatus(CreditLetterSettlement.PaymentStatus.UNPAID.getKey());//待支付
		reportService.update(acla_CreditLetterSettlement);
		//加入待支付记录列表中.
		reportService.addPayableRecords(acla_CreditLetterSettlement); 
		//设置信用证付款序列号(本年度内顺序编号)  
        int li_Index = reportService.generateNewIndex();
        acla_CreditLetterSettlement.setCreditSettlementOrderNo(CommonUtil.format(li_Index,"00000"));
        reportService.update(acla_CreditLetterSettlement);
        
		return null;
	} 
}
