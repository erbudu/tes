package com.supporter.prj.linkworks.oa.seal_manage.destruction.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.seal_manage.destruction.entity.SealDestruction;
import com.supporter.prj.linkworks.oa.seal_manage.destruction.service.SealDestructionService;

public class SealDestructionStartHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		SealDestructionService sealDestructionService = SpringContextHolder.getBean(SealDestructionService.class);
		String applyId = (String) execContext.getProcVar("applyId");
		System.out.println(applyId);
		SealDestruction sealDestruction = sealDestructionService.get(applyId);
		if (sealDestruction != null) {
			sealDestruction.setStatus(SealDestruction.PROCESSING);
			sealDestruction.setProcId(execContext.getProcId());
			sealDestructionService.update(sealDestruction);		
		} else {
			EIPService.getLogService().error("无效的applyId:" + applyId);
		}
		
		return null;
	}


}
