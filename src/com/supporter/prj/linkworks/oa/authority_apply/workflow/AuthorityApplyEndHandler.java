package com.supporter.prj.linkworks.oa.authority_apply.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApply;
import com.supporter.prj.linkworks.oa.authority_apply.service.AuthorityApplyService;

public class AuthorityApplyEndHandler extends AbstractExecHandler {

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
		AuthorityApply  authorityApply = authorityApplyService.get(applyId);
			authorityApply.setApplyStatus(Long.valueOf(AuthorityApply.COMPLETE));
			authorityApply.setProcId(execContext.getProcId());
//			report.setApplyTime(new Date());
			authorityApplyService.update(authorityApply);
			String purpose = authorityApply.getPurpose();
			if ("4".equals(purpose) || "8".equals(purpose)) {//用途四、用途八给经营部合同管理员发送知会
				authorityApplyService.sendMessageToContractManager(authorityApply);
			}

		
		return null;
		
		
		
	} 
}
