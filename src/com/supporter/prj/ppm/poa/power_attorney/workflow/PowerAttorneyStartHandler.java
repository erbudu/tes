package com.supporter.prj.ppm.poa.power_attorney.workflow;

import java.sql.SQLException;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.poa.power_attorney.entity.PowerAttorney;
import com.supporter.prj.ppm.poa.power_attorney.service.PowerAttorneyService;

/**
 * 流程启动时的Handler.
 */
public class PowerAttorneyStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}
	@Override
	public Object execute(ExecContext execContext) {
		PowerAttorneyService powerAttorneyService = SpringContextHolder.getBean(PowerAttorneyService.class);

		String laId = (String) execContext.getProcVar("laId");
		String laBusinessTypeId = (String) execContext.getProcVar("laBusinessTypeId");
		String prjId = (String) execContext.getProcVar("prjId");
		
		PowerAttorney  powerAttorney = powerAttorneyService.get(laId);
		if (powerAttorney != null) {
			powerAttorney.setProcStatus(PowerAttorney.PROCESSING);
			powerAttorney.setProcId(execContext.getProcId());
			try {
				int reviewTimeListSize = powerAttorneyService.getLaNumberByProIdAndLaBusinessTypeId(laBusinessTypeId, prjId);
				if(reviewTimeListSize == 0) {
					if(powerAttorney.getReviewTimes() == null) {
						powerAttorney.setReviewTimes(1);
					}
				}else {
					if(powerAttorney.getReviewTimes() == null) {
						powerAttorney.setReviewTimes(reviewTimeListSize + 1);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			powerAttorneyService.update(powerAttorney);		
		} else {
			EIPService.getLogService().error("无效的laId:" + laId);
		}
		return null;
	}
}
