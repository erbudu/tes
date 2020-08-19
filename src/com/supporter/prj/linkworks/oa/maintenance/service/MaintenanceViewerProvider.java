package com.supporter.prj.linkworks.oa.maintenance.service;


import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.maintenance.entity.Maintenance;
@Service
public class MaintenanceViewerProvider implements IBusiEntityViewerProvider{
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return Maintenance.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/maintenance/maintenance_view.html?maintenanceId=" + entityId;
	}

}
