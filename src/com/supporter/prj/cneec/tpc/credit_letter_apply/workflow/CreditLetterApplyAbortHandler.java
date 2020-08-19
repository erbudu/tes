package com.supporter.prj.cneec.tpc.credit_letter_apply.workflow;

import com.supporter.prj.cneec.tpc.credit_letter_apply.entity.CreditLetterApply;
import com.supporter.prj.cneec.tpc.credit_letter_apply.service.CreditLetterApplyService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: CreditLetterApplyAbortHandler
 * @Description: 流程中止操作类
 */
public class CreditLetterApplyAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		CreditLetterApplyService service = (CreditLetterApplyService) SpringContextHolder.getBean(CreditLetterApplyService.class);
		String creditLetterId = (String) execContext.getProcVar("creditLetterId");
		CreditLetterApply entity = service.get(creditLetterId);
		entity.setSwfStatus(CreditLetterApply.DRAFT);
		service.update(entity);
		return null;
	}

}