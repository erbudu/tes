package com.supporter.prj.linkworks.oa.bank_account.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountChangeApply;
import com.supporter.prj.linkworks.oa.bank_account.service.BankAccountChangeApplyService;

public class BankAccountChangeApplyAbortHandler extends AbstractExecHandler {

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
		
		BankAccountChangeApplyService bankAccountChangeApplyService = SpringContextHolder.getBean(BankAccountChangeApplyService.class);		
		String changeApplyId = (String) execContext.getProcVar("changeApplyId");
		BankAccountChangeApply bankAccountChangeApply = bankAccountChangeApplyService.get(changeApplyId);
			bankAccountChangeApply.setStatus(Long.valueOf(BankAccountChangeApply.DRAFT));
			bankAccountChangeApply.setProcId(execContext.getProcId());
			bankAccountChangeApplyService.update(bankAccountChangeApply);				
		return null;
		
	} 

}
