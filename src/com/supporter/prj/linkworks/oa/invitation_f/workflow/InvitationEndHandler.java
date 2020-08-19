package com.supporter.prj.linkworks.oa.invitation_f.workflow;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.doc_out.entity.OaDocOut;
import com.supporter.prj.linkworks.oa.doc_out.service.OaDocOutService;
import com.supporter.prj.linkworks.oa.invitation_f.entity.InvitationForeignerApply;
import com.supporter.prj.linkworks.oa.invitation_f.service.InvitationForeignerApplyService;

public class InvitationEndHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		InvitationForeignerApplyService service = (InvitationForeignerApplyService) SpringContextHolder
				.getBean(InvitationForeignerApplyService.class);
		String invitationId = (String) execContext.getProcVar("invitationId");
		InvitationForeignerApply entity = service.get(invitationId);
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		entity.setApplyId(entity.getCreatedById());
		entity.setApplyName(entity.getCreatedBy());
		entity.setInvitationStatus(InvitationForeignerApply.COMPLETE);
		service.update(entity);
		return null;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
