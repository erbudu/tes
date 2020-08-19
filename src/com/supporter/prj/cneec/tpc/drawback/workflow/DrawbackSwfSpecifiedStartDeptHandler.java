package com.supporter.prj.cneec.tpc.drawback.workflow;

import com.supporter.prj.cneec.tpc.drawback.entity.Drawback;
import com.supporter.prj.cneec.tpc.drawback.service.DrawbackService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: DrawbackSwfSpecifiedStartDeptHandler
 * @Description: 工作流组织树起始部门程序指定类
 * @author: yanweichao
 * @date: 2017-11-20
 * @version: V1.0
 */
public class DrawbackSwfSpecifiedStartDeptHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树起始部门程序指定
	@Override
	public Object execute(ExecContext execContext) {
		DrawbackService drawbackService = SpringContextHolder.getBean(DrawbackService.class);
		String drawbackId = (String) execContext.getProcVar("drawbackId");
		Drawback drawback = drawbackService.get(drawbackId);
		return drawback.getDeptId();
	}

}
