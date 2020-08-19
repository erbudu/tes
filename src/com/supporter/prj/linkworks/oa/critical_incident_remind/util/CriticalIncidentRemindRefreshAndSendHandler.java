package com.supporter.prj.linkworks.oa.critical_incident_remind.util;


import org.quartz.JobExecutionContext;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.job_schedule.IJob;
import com.supporter.prj.linkworks.oa.critical_incident_remind.service.CriticalIncidentRemindService;

public class CriticalIncidentRemindRefreshAndSendHandler implements IJob {
	
	private CriticalIncidentRemindService getCriticalIncidentRemindService(){
		return SpringContextHolder.getBean(CriticalIncidentRemindService.class);
	}

	@Override
	public void execute(JobExecutionContext context) {
      //扫描不需要重复发送待办的方法
		getCriticalIncidentRemindService().RefreshAndSendAgent();
//		//扫描需要重复发送待办的方法
//		getCriticalIncidentRemindService().RefreshAndSendAgentOfRepeatRemind();
	}

	
}
