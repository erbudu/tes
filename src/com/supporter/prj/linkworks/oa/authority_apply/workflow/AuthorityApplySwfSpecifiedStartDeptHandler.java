package com.supporter.prj.linkworks.oa.authority_apply.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApply;
import com.supporter.prj.linkworks.oa.authority_apply.service.AuthorityApplyService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class AuthorityApplySwfSpecifiedStartDeptHandler extends AbstractExecHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		
		return null;
	}
	
	//工作流组织树起始部门程序指定
	@Override
	public Object execute(ExecContext execContext) {
		
		AuthorityApplyService authorityApplyService = SpringContextHolder.getBean(AuthorityApplyService.class);
		String applyId = (String) execContext.getProcVar("applyId");
		AuthorityApply authorityApply = authorityApplyService.get(applyId);	
		return authorityApply.getApplyDeptId();
	} 

}
