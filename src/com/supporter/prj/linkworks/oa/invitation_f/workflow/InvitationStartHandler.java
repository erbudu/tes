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
import com.supporter.prj.meip_service.news.entity.News.Status;
import java.util.Date;

public class InvitationStartHandler extends AbstractExecHandler {
	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		InvitationForeignerApplyService service = (InvitationForeignerApplyService) SpringContextHolder
				.getBean(InvitationForeignerApplyService.class);
		String invitationId = (String) execContext.getProcVar("invitationId");
		InvitationForeignerApply entity = service.get(invitationId);
		entity.setInvitationStatus(InvitationForeignerApply.PROCESSING);
		entity.setProcId(execContext.getProcId());
		service.update(entity);
		return null;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
