package com.supporter.prj.linkworks.oa.report.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.report.entity.Report;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 * @author Arnold
 *
 */
@Service
public class ReportViewerProvider implements IBusiEntityViewerProvider {
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return Report.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/report/report_view.html?reportId=" + entityId;
	}
	   
}
