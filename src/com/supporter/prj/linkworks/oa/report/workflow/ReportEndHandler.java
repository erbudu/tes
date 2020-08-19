package com.supporter.prj.linkworks.oa.report.workflow;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.linkworks.oa.report.service.ReportService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.report.entity.Report;

public class ReportEndHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		
		ReportService reportService = SpringContextHolder.getBean(ReportService.class);
		String reportId = (String) execContext.getProcVar("reportId");
		Report report =  reportService.get(reportId);
		Date now = new Date();
//		report.setPublisherId(report.getCreaterId());// 发布人
//		report.setPublisherName(report.getCreaterName());
//		if(report.getPublishTime() == null){
//			report.setPublishTime(now);// 发布时间
//		}
		report.setReportStatus(Report.Status.COMPLETED.getKey());// 状态置已发布
		//设置失效时间
//		reportService.setExpiryTime(report);
		
		reportService.update(report);
		return null;
	} 
}
