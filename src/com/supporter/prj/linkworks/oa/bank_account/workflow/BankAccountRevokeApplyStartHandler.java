package com.supporter.prj.linkworks.oa.bank_account.workflow;


import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountRevokeApply;
import com.supporter.prj.linkworks.oa.bank_account.service.BankAccountRevokeApplyService;

/**
 * 新闻动态流程启动时的Handler.
 * @author Arnold
 *
 */
public class BankAccountRevokeApplyStartHandler extends AbstractExecHandler {

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
		if (bankAccountRevokeApply != null) {
			bankAccountRevokeApply.setStatus(Long.valueOf(BankAccountRevokeApply.PROCESSING));			
			bankAccountRevokeApply.setProcId(execContext.getProcId());
			bankAccountRevokeApplyService.update(bankAccountRevokeApply);		
		} else {
			EIPService.getLogService().error("无效的revokeId:" + revokeId);
		}
		
		return null;
	}


}
