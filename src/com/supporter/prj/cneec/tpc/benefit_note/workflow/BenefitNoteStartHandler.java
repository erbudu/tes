package com.supporter.prj.cneec.tpc.benefit_note.workflow;

import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNote;
import com.supporter.prj.cneec.tpc.benefit_note.service.BenefitNoteService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: BenefitNoteStartHandler
 * @Description: 流程开始操作类
 * @author: yanweichao
 * @date: 2018-07-17
 * @version: V1.0
 */
public class BenefitNoteStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		BenefitNoteService service = SpringContextHolder.getBean(BenefitNoteService.class);
		String entityId = (String) execContext.getProcVar("noteId");
		BenefitNote entity = service.get(entityId);
		if (entity != null) {
			entity.setSwfStatus(BenefitNote.CONFIRMING);
			entity.setProcId(execContext.getProcId());
			service.update(entity);
		} else {
			EIPService.getLogService().error("无效的noteId:" + entityId);
		}
		return null;
	}

}