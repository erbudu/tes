package com.supporter.prj.linkworks.oa.invitation_f.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.news.service.NewsService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.doc_out.entity.OaDocOut;
import com.supporter.prj.linkworks.oa.doc_out.service.OaDocOutService;
import com.supporter.prj.linkworks.oa.invitation_f.entity.InvitationForeignerApply;
import com.supporter.prj.linkworks.oa.invitation_f.service.InvitationForeignerApplyService;
import com.supporter.prj.meip_service.news.entity.News;

public class InvitationSwfTaskSpecifiedHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7022088677322240901L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		InvitationForeignerApplyService service = (InvitationForeignerApplyService) SpringContextHolder
				.getBean(InvitationForeignerApplyService.class);
		String invitationId = (String) execContext.getProcVar("invitationId");
		InvitationForeignerApply entity = service.get(invitationId);
		return entity.getCreatedById();
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
