package com.supporter.prj.linkworks.oa.report.workflow;

import java.util.Date;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.linkworks.oa.report.service.ReportService;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.report.entity.Report;

/**
 * 报告流程启动时的Handler.
 * @author liyinfeng
 *
 */
public class ReportStartHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		ReportService reportService = SpringContextHolder.getBean(ReportService.class);
		
		String reportId = (String) execContext.getProcVar("reportId");
		Report report = reportService.get(reportId);
		if (report != null) {
			report.setReportStatus(Report.Status.auditing.getKey());
			report.setProcId(execContext.getProcId());
//			report.setApplyTime(new Date());
			reportService.update(report);		
		} else {
			EIPService.getLogService().error("无效的reportId:" + reportId);
		}
		
		return null;
	}


}
