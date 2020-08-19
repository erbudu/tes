package com.supporter.prj.linkworks.oa.abroad.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.abroad.entity.AbroadRealDate;
import com.supporter.prj.linkworks.oa.abroad.service.AbroadRealDateService;
import com.supporter.prj.linkworks.oa.signed_report.util.ConvertUtils;

public class AbroadRealDateAbortHandler extends AbstractExecHandler {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc()
	  {
	    return null;
	  }

	public Object execute(ExecContext execContext){
		AbroadRealDateService abroadService = (AbroadRealDateService)SpringContextHolder.getBean(AbroadRealDateService.class);
		String realId = (String)execContext.getProcVar("realId");
		AbroadRealDate abroadRealDate = abroadService.get(realId);
		abroadRealDate.setConfirmDate(ConvertUtils.dateDetailString());
		abroadRealDate.setRealDateConfirm(ConvertUtils.dateDetailString());
		abroadRealDate.setSwfStatus(AbroadRealDate.ARCHIVED);
		abroadRealDate.setProcId(execContext.getProcId());	  
		abroadService.update(abroadRealDate);
		return null;
	}
	
	  public void executeProcVars(ExecContext execContext)
	  {
	  }
}