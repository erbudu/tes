package com.supporter.prj.linkworks.oa.bank_account.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountRevokeApply;
import com.supporter.prj.linkworks.oa.bank_account.service.BankAccountRevokeApplyService;

public class BankAccountRevokeApplyAbortHandler extends AbstractExecHandler {

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
		
		BankAccountRevokeApplyService bankAccountRevokeApplyService = SpringContextHolder.getBean(BankAccountRevokeApplyService.class);		
		String revokeId = (String) execContext.getProcVar("revokeId");
		BankAccountRevokeApply bankAccountRevokeApply = bankAccountRevokeApplyService.get(revokeId);
			bankAccountRevokeApply.setStatus(Long.valueOf(BankAccountRevokeApply.DRAFT));
			bankAccountRevokeApply.setProcId(execContext.getProcId());
			bankAccountRevokeApplyService.update(bankAccountRevokeApply);				
		return null;
		
	} 

}
