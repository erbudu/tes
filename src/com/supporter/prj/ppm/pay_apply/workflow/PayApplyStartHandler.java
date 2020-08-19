package com.supporter.prj.ppm.pay_apply.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.pay_apply.entity.PayApply;
import com.supporter.prj.ppm.pay_apply.service.PayApplyService;

public class PayApplyStartHandler extends AbstractExecHandler {
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
		if (payApply != null) {
			payApply.setStatus(PayApply.StatusCodeTable.EXAM);
			payApply.setProcId(execContext.getProcId());
			payApplyService.update(payApply);
		} else {
			EIPService.getLogService().error("无效的Id:" + Id);
		}
		return payApply;
		
	}
	@Override
	public void executeProcVars(ExecContext execContext) {
	}
}
