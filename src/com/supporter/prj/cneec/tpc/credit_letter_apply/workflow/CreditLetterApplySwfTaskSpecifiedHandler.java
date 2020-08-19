package com.supporter.prj.cneec.tpc.credit_letter_apply.workflow;

import com.supporter.prj.cneec.tpc.credit_letter_apply.entity.CreditLetterApply;
import com.supporter.prj.cneec.tpc.credit_letter_apply.service.CreditLetterApplyService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: CreditLetterApplySwfTaskSpecifiedHandler
 * @Description: 工作流普通任务人员程序指定类
 */
public class CreditLetterApplySwfTaskSpecifiedHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流普通任务人员程序指定
	@Override
	public Object execute(ExecContext execContext) {
		CreditLetterApplyService service = SpringContextHolder.getBean(CreditLetterApplyService.class);
		String creditLetterId = (String) execContext.getProcVar("creditLetterId");
		CreditLetterApply entity = service.get(creditLetterId);
		return entity.getCreatedById();
	}

}
