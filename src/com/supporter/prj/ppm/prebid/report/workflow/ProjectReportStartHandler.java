package com.supporter.prj.ppm.prebid.report.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReport;
import com.supporter.prj.ppm.prebid.report.service.ProjectReportService;

/**
 * @Title: ProjectReportStartHandler
 * @Description: 流程开始操作类
 */
public class ProjectReportStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ProjectReportService projectReportService = (ProjectReportService) SpringContextHolder.getBean(ProjectReportService.class);
		String prbidReportId = (String) execContext.getProcVar("prbidReportId");
		PpmPrebidReport ppmPrebidReport = projectReportService.get(prbidReportId);
		if (ppmPrebidReport != null) {
			ppmPrebidReport.setPretenderReportStatus(PpmPrebidReport.PROCESSING);
			ppmPrebidReport.setProcId(execContext.getProcId());
			projectReportService.update(ppmPrebidReport);
		} else {
			EIPService.getLogService().error("无效的prbidReportId:" + prbidReportId);
		}
		return null;
	}

}