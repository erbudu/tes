package com.supporter.prj.linkworks.oa.abroadPublicity.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;

import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.abroadPublicity.service.AbroadBackService;
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadBack;

public class AbroadBackSwfStartDeptHandler extends AbstractExecHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		
		return null;
	}
	
	//工作流组织树起始部门程序指定
	@Override
	public Object execute(ExecContext execContext) {
		
		AbroadBackService abroadBackService = SpringContextHolder.getBean(AbroadBackService.class);
		String backId = (String) execContext.getProcVar("backId");
		AbroadBack abroadBack =  abroadBackService.get(backId);		
		return abroadBack.getDeptId();
	} 

}
