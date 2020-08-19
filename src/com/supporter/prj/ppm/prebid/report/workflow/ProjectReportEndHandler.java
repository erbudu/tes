package com.supporter.prj.ppm.prebid.report.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReport;
import com.supporter.prj.ppm.prebid.report.service.ProjectReportService;
import com.supporter.prj.ppm.service.PPMService;

/**
 * @Title: ProjectReportEndHandler
 * @Description: 流程结束操作类
 * @version: V1.0
 */
public class ProjectReportEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ProjectReportService projectReportService = (ProjectReportService) SpringContextHolder.getBean(ProjectReportService.class);
		String prbidReportId = (String) execContext.getProcVar("prbidReportId");
		int isPass = PpmPrebidReport.COMPLETED;
		PpmPrebidReport ppmPrebidReport = projectReportService.get(prbidReportId);
		ppmPrebidReport.setPretenderReportStatus(isPass);
		projectReportService.update(ppmPrebidReport);
		PPMService.getScheduleStatusService().saveScheduleStatus(ppmPrebidReport.getPrjId(), "report03",null);
		//流程结束保存报审审核结果
		projectReportService.saveCon(prbidReportId,isPass);
		return null;
	}

}