package com.supporter.prj.linkworks.oa.attendance.leave.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.attendance.leave.entiyt.LeaveEntity;

@Service
public class LeaveViewerProcider implements IBusiEntityViewerProvider {

	public String getId() {
		return LeaveEntity.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/attendance/leave/leave_addOrEdit_view.html?leaveId=" + entityId;
	}

}
