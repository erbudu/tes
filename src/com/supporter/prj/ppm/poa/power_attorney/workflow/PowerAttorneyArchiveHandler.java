package com.supporter.prj.ppm.poa.power_attorney.workflow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.poa.power_attorney.entity.PowerAttorney;
import com.supporter.prj.ppm.poa.power_attorney.service.PowerAttorneyService;

/**
 * 流程中归档时的Handler.
 */
public class PowerAttorneyArchiveHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}
	@Override
	public Object execute(ExecContext execContext) {
		PowerAttorneyService powerAttorneyService = SpringContextHolder.getBean(PowerAttorneyService.class);

		String laId = (String) execContext.getProcVar("laId");
		PowerAttorney  powerAttorney = powerAttorneyService.get(laId);
		if (powerAttorney != null) {
			/*
			List<PowerAttorney> PowerAttorneyAll = powerAttorneyService.selectPowerAttorneyAll();
			Calendar date = Calendar.getInstance();
			int sysYear = Integer.parseInt(String.valueOf(date.get(Calendar.YEAR)));
			List<Integer> number = new ArrayList<Integer>();
			String strNum = ""; 
			if(PowerAttorneyAll.size() > 0) {
				for (int i = 0; i < PowerAttorneyAll.size(); i++) {
					String laNo = PowerAttorneyAll.get(i).getLaNo();
					int str_year = Integer.parseInt(laNo.substring(7, 11));
					int str_num = Integer.parseInt(laNo.substring(13, 17));
					if(str_year == sysYear) {
						number.add(str_num);
					}
				}
				int num = Collections.max(number) + 1;
				strNum = String.format("%04d", num);
			} else {
				strNum = "0001";
			}
			powerAttorney.setLaNo("中电项目授字【"+sysYear+"】第"+strNum+"号");
			*/
			powerAttorneyService.update(powerAttorney);
		}else {
			EIPService.getLogService().error("无效的业务单主键laId："+laId);
		}
		return null;
	} 
}
