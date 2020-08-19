package com.supporter.prj.linkworks.oa.abroadPublicity.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadBack;
import com.supporter.prj.linkworks.oa.abroadPublicity.service.AbroadBackService;
public class AbroadBackStartHandler extends AbstractExecHandler
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public String getDesc(){
			 return null;
		}

		
  public Object execute(ExecContext execContext) {
	AbroadBackService abroadBackService = (AbroadBackService)SpringContextHolder.getBean(AbroadBackService.class);
    String backId = (String)execContext.getProcVar("backId");
    AbroadBack abroadBack = abroadBackService.get(backId);
    abroadBack.setSwfStatus(AbroadBack.PROCESSING);
    abroadBack.setProcId(execContext.getProcId());
    abroadBackService.update(abroadBack);
    return null;
  }

  
 
}