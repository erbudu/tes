package com.supporter.prj.linkworks.oa.seal_manage.engrave.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.seal_manage.engrave.entity.SealEngrave;
import com.supporter.prj.linkworks.oa.seal_manage.engrave.service.SealEngraveService;

public class SealEngraveSwfTaskSpecifiedHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;
	@Override
	public String getDesc() {
		
		return null;
	}
	
	//工作流普通任务人员程序指定
	@Override
	public Object execute(ExecContext execContext) {
		
		SealEngraveService sealEngraveService  = SpringContextHolder.getBean(SealEngraveService.class);
		String applyId = (String) execContext.getProcVar("applyId");
		SealEngrave sealEngrave =  sealEngraveService.get(applyId);		
		return sealEngrave.getCreatedBy();
	}
 
}
