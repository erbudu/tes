package com.supporter.prj.linkworks.oa.signed_report.workflow;


import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReport;
import com.supporter.prj.linkworks.oa.signed_report.service.SignedReportService;
import com.supporter.prj.linkworks.oa.signed_report.util.ConvertUtils;

public class SignedReportFileHandler extends AbstractExecHandler {
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
    signedReport.setSwfStatus(SignedReport.COMPLETE);
    signedReport.setArchiveDate(ConvertUtils.dateString());
    if(signedReport.getActresult() ==SignedReport.PASS){
    	signedReport.setactresultname("同意");
    }
    if(signedReport.getActresult()==SignedReport.FAIL){
    	signedReport.setactresultname("不同意，返回修改");
    }
    if(signedReport.getActresult() ==SignedReport.FAIL_ABORT_PROC){
    	signedReport.setactresultname("不同意");
    }
    signedReportService.update(signedReport);
    return null;
  }

  public void executeProcVars(ExecContext execContext)
  {
  }
}