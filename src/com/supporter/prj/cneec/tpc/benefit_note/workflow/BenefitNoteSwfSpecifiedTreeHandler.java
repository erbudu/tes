package com.supporter.prj.cneec.tpc.benefit_note.workflow;

import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: BenefitNoteSwfTreeHandler
 * @Description: 工作流组织树程序指定类
 * @author: yanweichao
 * @date: 2018-07-17
 * @version: V1.0
 */
public class BenefitNoteSwfSpecifiedTreeHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树程序指定
	@Override
	public Object execute(ExecContext execContext) {
		return "ORG_TREE";
	}

}
