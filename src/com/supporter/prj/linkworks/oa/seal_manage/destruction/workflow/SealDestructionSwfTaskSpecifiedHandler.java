package com.supporter.prj.linkworks.oa.seal_manage.destruction.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.seal_manage.destruction.entity.SealDestruction;
import com.supporter.prj.linkworks.oa.seal_manage.destruction.service.SealDestructionService;

public class SealDestructionSwfTaskSpecifiedHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}
	
	//工作流普通任务人员程序指定
	@Override
	public Object execute(ExecContext execContext) {
		
		SealDestructionService sealDestructionService  = SpringContextHolder.getBean(SealDestructionService.class);
		String applyId = (String) execContext.getProcVar("applyId");
		SealDestruction sealDestruction =  sealDestructionService.get(applyId);		
		return sealDestruction.getCreatedBy();
	}
 
}
