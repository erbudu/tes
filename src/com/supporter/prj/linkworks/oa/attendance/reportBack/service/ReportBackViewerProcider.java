package com.supporter.prj.linkworks.oa.attendance.reportBack.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.attendance.reportBack.entity.ReportBackEntity;

@Service
public class ReportBackViewerProcider implements IBusiEntityViewerProvider {

	public String getId() {
		return ReportBackEntity.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		ReportBackService service = (ReportBackService)SpringContextHolder.getBean(ReportBackService.class);
		ReportBackEntity reportBack = service.get(entityId.toString());
		return "/oa/attendance/report_back/report_back_view.html?leaveId=" + reportBack.getLeaveId() + "&reportBackId=" + entityId;
	}

}
