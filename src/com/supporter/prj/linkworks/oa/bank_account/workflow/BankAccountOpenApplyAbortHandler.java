package com.supporter.prj.linkworks.oa.bank_account.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApply;
import com.supporter.prj.linkworks.oa.bank_account.service.BankAccountOpenApplyService;

public class BankAccountOpenApplyAbortHandler extends AbstractExecHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		
		BankAccountOpenApplyService bankAccountOpenApplyService = SpringContextHolder.getBean(BankAccountOpenApplyService.class);		
		String applyId = (String) execContext.getProcVar("applyId");
		BankAccountOpenApply bankAccountOpenApply = bankAccountOpenApplyService.get(applyId);
			bankAccountOpenApply.setStatus(Long.valueOf(BankAccountOpenApply.DRAFT));
			bankAccountOpenApply.setProcId(execContext.getProcId());
			bankAccountOpenApplyService.update(bankAccountOpenApply);				
		return null;
		
	} 

}
