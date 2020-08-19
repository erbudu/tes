package com.supporter.prj.linkworks.oa.maintenance.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.linkworks.oa.maintenance.entity.Maintenance;
import com.supporter.prj.linkworks.oa.maintenance.service.MaintenanceService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class MaintenanceEndHandler extends AbstractExecHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
					
		MaintenanceService maintenanceService = SpringContextHolder.getBean(MaintenanceService.class);
		
		String maintenanceId = (String) execContext.getProcVar("maintenanceId");
		Maintenance maintenance = maintenanceService.get(maintenanceId);
			maintenance.setSwfStatus(Long.valueOf(Maintenance.COMPLETED));
			maintenance.setProcId(execContext.getProcId());
//			report.setApplyTime(new Date());
			maintenanceService.update(maintenance);		

		
		return null;
		
		
		
	} 
}
