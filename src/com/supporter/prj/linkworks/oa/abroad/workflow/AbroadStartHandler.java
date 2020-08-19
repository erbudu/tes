package com.supporter.prj.linkworks.oa.abroad.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;
import com.supporter.prj.linkworks.oa.abroad.service.AbroadService;
import com.supporter.prj.linkworks.oa.abroad.util.ConvertUtils;

public class AbroadStartHandler extends AbstractExecHandler
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public String getDesc(){
			 return null;
		}

		
  public Object execute(ExecContext execContext) {
    AbroadService abroadService = (AbroadService)SpringContextHolder.getBean(AbroadService.class);
    String recordId = (String)execContext.getProcVar("recordId");
    Abroad abroad = abroadService.get(recordId);
    abroad.setRecordStatus(Abroad.PROCESSING);
    abroad.setProcId(execContext.getProcId());
    abroad.setApplyDate(ConvertUtils.dateString());
    abroadService.update(abroad);
//    System.out.println();
//    System.out.println("recordId");
//    System.out.println();
    return null;
  }

  
 
}