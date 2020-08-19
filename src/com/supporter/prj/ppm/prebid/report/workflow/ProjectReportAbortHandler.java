package com.supporter.prj.ppm.prebid.report.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReport;
import com.supporter.prj.ppm.prebid.report.service.ProjectReportService;

/**
 * @Title: ProjectReportAbortHandler
 * @Description: 流程中止操作类
 */
public class ProjectReportAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ProjectReportService projectReportService = (ProjectReportService) SpringContextHolder.getBean(ProjectReportService.class);
		String prbidReportId = (String) execContext.getProcVar("prbidReportId");
		int isPass = PpmPrebidReport.DRAFT;
		PpmPrebidReport ppmPrebidReport = projectReportService.get(prbidReportId);
		ppmPrebidReport.setPretenderReportStatus(isPass);
		projectReportService.update(ppmPrebidReport);
		//流程中止保存报审审核结果
		projectReportService.saveCon(prbidReportId,isPass);
		return null;
	}

}