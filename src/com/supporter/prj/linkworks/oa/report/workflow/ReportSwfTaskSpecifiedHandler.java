package com.supporter.prj.linkworks.oa.report.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.linkworks.oa.report.service.ReportService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.report.entity.Report;

public class ReportSwfTaskSpecifiedHandler extends AbstractExecHandler {

	@Override
	public String getDesc() {
		
		return null;
	}
	
	//工作流普通任务人员程序指定
	@Override
	public Object execute(ExecContext execContext) {
		
		ReportService reportService = SpringContextHolder.getBean(ReportService.class);
		String reportId = (String) execContext.getProcVar("reportId");
		Report report =  reportService.get(reportId);		
		return report.getCreatedBy();
	}
 
}
