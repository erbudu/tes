package com.supporter.prj.linkworks.oa.maintenance.workflow;


import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.linkworks.oa.maintenance.entity.Maintenance;
import com.supporter.prj.linkworks.oa.maintenance.service.MaintenanceService;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

/**
 * 新闻动态流程启动时的Handler.
 * @author Arnold
 *
 */
public class MaintenanceStartHandler extends AbstractExecHandler {

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
		if (maintenance != null) {
			maintenance.setSwfStatus(Long.valueOf(Maintenance.PROCESSING));
			
			maintenance.setProcId(execContext.getProcId());
			
//			report.setApplyTime(new Date());
			maintenanceService.update(maintenance);		
		} else {
			EIPService.getLogService().error("无效的maintenanceId:" + maintenanceId);
		}
		
		return null;
	}


}
