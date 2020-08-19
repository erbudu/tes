package com.supporter.prj.linkworks.oa.authority_apply.workflow;


import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApply;
import com.supporter.prj.linkworks.oa.authority_apply.service.AuthorityApplyService;

/**
 * 新闻动态流程启动时的Handler.
 * @author Arnold
 *
 */
public class AuthorityApplyStartHandler extends AbstractExecHandler {

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
		AuthorityApply authorityApply = authorityApplyService.get(applyId);
		if (authorityApply != null) {
			authorityApply.setApplyStatus(Long.valueOf((AuthorityApply.PROCESSING)));
			
			authorityApply.setProcId(execContext.getProcId());
//			report.setApplyTime(new Date());
			authorityApplyService.update(authorityApply);		
		} else {
			EIPService.getLogService().error("无效的applyId:" + applyId);
		}
		
		return null;
	}


}
