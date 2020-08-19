package com.supporter.prj.linkworks.oa.authority_apply.workflow;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApply;
import com.supporter.prj.linkworks.oa.authority_apply.service.AuthorityApplyService;

public class AuthorityApplySwfTaskSpecifiedHandler extends AbstractExecHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		
		return null;
	}
	
	//工作流普通任务人员程序指定
	@Override
	public Object execute(ExecContext execContext) {
		
		AuthorityApplyService applyService = SpringContextHolder.getBean(AuthorityApplyService.class);
		String applyId = (String) execContext.getProcVar("applyId");
		AuthorityApply apply =  applyService.get(applyId);		
		return apply.getCreatedBy();
	}
 
}
