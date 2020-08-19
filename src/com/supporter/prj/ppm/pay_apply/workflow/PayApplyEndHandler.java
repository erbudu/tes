package com.supporter.prj.ppm.pay_apply.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.pay_apply.entity.PayApply;
import com.supporter.prj.ppm.pay_apply.service.PayApplyService;

public class PayApplyEndHandler extends AbstractExecHandler {
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		PayApplyService payApplyService = SpringContextHolder.getBean(PayApplyService.class);
		String Id = (String) execContext.getProcVar("id");
		PayApply payApply = payApplyService.get(Id);
		payApply.setStatus(PayApply.StatusCodeTable.COMPLETE);
		payApplyService.update(payApply);
		return payApply;
		
	}
	@Override
	public void executeProcVars(ExecContext execContext) {
	}
}
