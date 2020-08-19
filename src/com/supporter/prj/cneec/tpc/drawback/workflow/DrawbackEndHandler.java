package com.supporter.prj.cneec.tpc.drawback.workflow;

import com.supporter.prj.cneec.tpc.drawback.entity.Drawback;
import com.supporter.prj.cneec.tpc.drawback.service.DrawbackService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * @Title: DrawbackEndHandler
 * @Description: 流程结束操作类
 * @author: yanweichao
 * @date: 2017-11-20
 * @version: V1.0
 */
public class DrawbackEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		DrawbackService drawbackService = SpringContextHolder.getBean(DrawbackService.class);
		String drawbackId = (String) execContext.getProcVar("drawbackId");
		Drawback drawback = drawbackService.get(drawbackId);
		drawback.setProcId(execContext.getProcId());
		drawbackService.completeExam(drawback);
		return null;
	}

}