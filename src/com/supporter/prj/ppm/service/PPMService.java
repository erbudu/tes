package com.supporter.prj.ppm.service;

import com.supporter.prj.core.spring.SpringContextHolder;

public final class PPMService{
	
	//进度状态service
	private static IScheduleStatusService scheduleStatusService = null;
	public static IScheduleStatusService getScheduleStatusService(){
	    if (scheduleStatusService != null) return scheduleStatusService;
	    scheduleStatusService = (IScheduleStatusService)SpringContextHolder.getBean(IScheduleStatusService.class);
	    return scheduleStatusService;
	}
  
	//模板service
	private static ITemplateRegisterService templateRegisterService = null;
	public static ITemplateRegisterService getTemplateRegisterService(){
	    if (templateRegisterService != null) return templateRegisterService;
	    templateRegisterService = (ITemplateRegisterService)SpringContextHolder.getBean(ITemplateRegisterService.class);
	    return templateRegisterService;
	}
}

