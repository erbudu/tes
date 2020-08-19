package com.supporter.prj.cneec.tpc.benefit_note.workflow;

import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNote;
import com.supporter.prj.cneec.tpc.benefit_note.service.BenefitNoteService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: BenefitNoteStartHandler
 * @Description: 工作流组织树起始部门程序指定类
 * @author: yanweichao
 * @date: 2018-07-17
 * @version: V1.0
 */
public class BenefitNoteSwfSpecifiedStartDeptHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树起始部门程序指定
	@Override
	public Object execute(ExecContext execContext) {
		BenefitNoteService service = SpringContextHolder.getBean(BenefitNoteService.class);
		String entityId = (String) execContext.getProcVar("noteId");
		BenefitNote entity = service.get(entityId);
		return entity.getDeptId();
	}

}
