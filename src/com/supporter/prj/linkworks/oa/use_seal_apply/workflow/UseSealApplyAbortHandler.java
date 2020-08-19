package com.supporter.prj.linkworks.oa.use_seal_apply.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.UseSealApply;
import com.supporter.prj.linkworks.oa.use_seal_apply.service.UseSealApplyService;

public class UseSealApplyAbortHandler extends AbstractExecHandler {

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
		
		UseSealApplyService useSealApplyService = SpringContextHolder.getBean(UseSealApplyService.class);		
		String applyId = (String) execContext.getProcVar("applyId");
//		System.out.println("(String) execContext.getProcVar(applyId)="+(String) execContext.getProcVar("applyId"));
//		System.out.println("(String) execContext.getProcVar(entityId)="+(String) execContext.getProcVar("entityId"));
		
		UseSealApply useSealApply = useSealApplyService.get(applyId);
			useSealApply.setApplyStatus(Long.valueOf(UseSealApply.DRAFT));
			useSealApply.setProcId(execContext.getProcId());
//			report.setApplyTime(new Date());
			useSealApplyService.update(useSealApply);		

		
		return null;
		
	} 

}
