package com.supporter.prj.ppm.poa.icc.workflow;


import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.poa.icc.entity.Coordination;
import com.supporter.prj.ppm.poa.icc.service.CoordinationService;

public class CoordinationStartHandler extends AbstractExecHandler
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public String getDesc(){
			 return null;
		}

		
  public Object execute(ExecContext execContext) {
	  CoordinationService coordinationService = (CoordinationService)SpringContextHolder.getBean(CoordinationService.class);
	    String iccId = (String)execContext.getProcVar("iccId");
	    Coordination coordination = coordinationService.get(iccId);
	    coordination.setStatus(1);
	    coordination.setProcId(execContext.getProcId());
	    coordinationService.update(coordination);
	    
	
    return null;
  }

  
 
}