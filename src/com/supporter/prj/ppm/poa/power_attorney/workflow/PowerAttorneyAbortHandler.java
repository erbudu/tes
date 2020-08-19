package com.supporter.prj.ppm.poa.power_attorney.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.poa.power_attorney.entity.PowerAttorney;
import com.supporter.prj.ppm.poa.power_attorney.service.PowerAttorneyService;

/**
 * 流程中止时的Handler.
 */
public class PowerAttorneyAbortHandler extends AbstractExecHandler {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}
	@Override
	public Object execute(ExecContext execContext) {
		
		PowerAttorneyService powerAttorneyService = SpringContextHolder.getBean(PowerAttorneyService.class);		
		String laId = (String) execContext.getProcVar("laId");
		
		PowerAttorney powerAttorney = powerAttorneyService.get(laId);
		if (powerAttorney != null) {
			powerAttorney.setProcStatus(PowerAttorney.REJECTED);
			powerAttorney.setProcId(execContext.getProcId());
			powerAttorneyService.update(powerAttorney);		
		}else {
			EIPService.getLogService().error("无效的业务单主键laId："+laId);
		}
		return null;
	} 
}
