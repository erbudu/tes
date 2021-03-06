package com.supporter.prj.linkworks.oa.signed_report.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReport;
import com.supporter.prj.linkworks.oa.signed_report.service.SignedReportService;

public class SignedReportRejectHandler extends AbstractExecHandler {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc()
	  {
	    return null;
	  }

	  public Object execute(ExecContext execContext)
	  {
		SignedReportService signedReportService = (SignedReportService)SpringContextHolder.getBean(SignedReportService.class);
	    String signedReportId = (String)execContext.getProcVar("signedReportId");

	    SignedReport signedReport = signedReportService.get(signedReportId);
	    signedReport.setActresult(SignedReport.FAIL);
	    signedReportService.update(signedReport);
	    return null;
	  }
	
	  public void executeProcVars(ExecContext execContext)
	  {
	  }
}