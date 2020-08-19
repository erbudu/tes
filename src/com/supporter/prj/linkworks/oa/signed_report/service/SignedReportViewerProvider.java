package com.supporter.prj.linkworks.oa.signed_report.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReport;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 * @author Arnold
 *
 */
@Service
public class SignedReportViewerProvider implements IBusiEntityViewerProvider {
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return SignedReport.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/signed_report/signed_report_view.html?isView=true&signedReportId=" + entityId;
	}
	   
}
