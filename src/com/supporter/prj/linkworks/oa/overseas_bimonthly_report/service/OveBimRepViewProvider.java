/**
 * 
 */
package com.supporter.prj.linkworks.oa.overseas_bimonthly_report.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.entity.OverseasBimonthlyReportEntity;

/**
 *<p>Title: OveBimRepViewProvider</p>
 *<p>Description: 显示已办</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年12月31日
 * 
 */
@Service
public class OveBimRepViewProvider implements IBusiEntityViewerProvider{

	@Override
	public String getId() {
		return OverseasBimonthlyReportEntity.class.getName();
	}

	@Override
	public String getViewerUrl(int dbYer, Object entityId) {
		return "/oa/overseas_bimonthly_report/overseas_bimonthly_report_view.html?reportId="+entityId;
	}
	
	

}
