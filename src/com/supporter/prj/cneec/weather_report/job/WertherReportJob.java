package com.supporter.prj.cneec.weather_report.job;


import org.quartz.JobExecutionContext;

import com.supporter.prj.cneec.weather_report.service.WertherReportService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.job_schedule.IJob;
import com.supporter.util.CommonUtil;

/**
 * 获取天气预报定时执行类.
 * @author linda
 *
 */
public class WertherReportJob implements IJob{
	
	private WertherReportService getWertherReportService(){
		return SpringContextHolder.getBean(WertherReportService.class);
	}
	
	@Override
	public void execute(JobExecutionContext context) {
		WertherReportService wertherReportService = getWertherReportService();
		String remoteWertherReport = CommonUtil.trim(wertherReportService.getRemoteWertherReport());
		if(remoteWertherReport.length() > 0){
			wertherReportService.saveOrUpdate(remoteWertherReport);
		}
	}
}
