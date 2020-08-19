package com.supporter.prj.linkworks.oa.bank_account.workflow;


import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountChangeApply;
import com.supporter.prj.linkworks.oa.bank_account.service.BankAccountChangeApplyService;

/**
 * 新闻动态流程启动时的Handler.
 * @author Arnold
 *
 */
public class BankAccountChangeApplyStartHandler extends AbstractExecHandler {

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
		if (bankAccountChangeApply != null) {
			bankAccountChangeApply.setStatus(Long.valueOf(BankAccountChangeApply.PROCESSING));			
			bankAccountChangeApply.setProcId(execContext.getProcId());
			bankAccountChangeApplyService.update(bankAccountChangeApply);		
		} else {
			EIPService.getLogService().error("无效的changeApplyId:" + changeApplyId);
		}
		
		return null;
	}


}
