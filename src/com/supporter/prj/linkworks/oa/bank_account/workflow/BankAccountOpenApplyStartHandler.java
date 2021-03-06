package com.supporter.prj.linkworks.oa.bank_account.workflow;


import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApply;
import com.supporter.prj.linkworks.oa.bank_account.service.BankAccountOpenApplyService;

/**
 * 新闻动态流程启动时的Handler.
 * @author Arnold
 *
 */
public class BankAccountOpenApplyStartHandler extends AbstractExecHandler {

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
		if (bankAccountOpenApply != null) {
			bankAccountOpenApply.setStatus(Long.valueOf(BankAccountOpenApply.PROCESSING));			
			bankAccountOpenApply.setProcId(execContext.getProcId());
			bankAccountOpenApplyService.update(bankAccountOpenApply);		
		} else {
			EIPService.getLogService().error("无效的applyId:" + applyId);
		}
		
		return null;
	}


}
