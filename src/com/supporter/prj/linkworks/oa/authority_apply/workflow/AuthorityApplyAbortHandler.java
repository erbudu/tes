package com.supporter.prj.linkworks.oa.authority_apply.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApply;
import com.supporter.prj.linkworks.oa.authority_apply.service.AuthorityApplyService;

public class AuthorityApplyAbortHandler extends AbstractExecHandler {

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
		
		AuthorityApplyService authorityApplyService = SpringContextHolder.getBean(AuthorityApplyService.class);		
		String applyId = (String) execContext.getProcVar("applyId");
//		System.out.println("(String) execContext.getProcVar(applyId)="+(String) execContext.getProcVar("applyId"));
//		System.out.println("(String) execContext.getProcVar(entityId)="+(String) execContext.getProcVar("entityId"));
		
		AuthorityApply authorityApply = authorityApplyService.get(applyId);
		authorityApply.setApplyStatus(Long.valueOf(AuthorityApply.DRAFT));
		authorityApply.setProcId(execContext.getProcId());
//			report.setApplyTime(new Date());
		authorityApplyService.update(authorityApply);		

		
		return null;
		
	} 

}
