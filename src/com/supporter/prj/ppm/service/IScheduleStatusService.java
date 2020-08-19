package com.supporter.prj.ppm.service;

import com.supporter.prj.eip_service.security.entity.UserProfile;

public abstract interface IScheduleStatusService{
  
	public abstract void saveScheduleStatus(String moudleId, String textDisplay, UserProfile user);
}

