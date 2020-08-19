package com.supporter.prj.linkworks.oa.abroadPublicity.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadBack;
import com.supporter.prj.linkworks.oa.abroadPublicity.service.AbroadBackService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class AbroadBackSwfTaskSpecifiedHandler extends AbstractExecHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		
		return null;
	}
	
	//工作流普通任务人员程序指定
	@Override
	public Object execute(ExecContext execContext) {
		
		AbroadBackService abroadService = SpringContextHolder.getBean(AbroadBackService.class);
		String backId = (String) execContext.getProcVar("backId");
		AbroadBack abroadBack =  abroadService.get(backId);		
		return abroadBack.getApplier();
	}
 
}
