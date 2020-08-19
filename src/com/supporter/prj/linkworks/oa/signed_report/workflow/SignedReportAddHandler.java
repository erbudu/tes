package com.supporter.prj.linkworks.oa.signed_report.workflow;


import java.util.List;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReport;
import com.supporter.prj.linkworks.oa.signed_report.service.SignedReportService;

public class SignedReportAddHandler extends AbstractExecHandler {
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
    IFileUploadService fileUploadService = EIPService.getFileUploadService();
	List<IFile> list  = fileUploadService.getFileList("OASIGNREPORT", "ProFiles", signedReportId);
	if(list !=null && list.size()>0){
		
	}
    signedReportService.update(signedReport);
	System.out.println();
	System.out.println(signedReport.getActresult());
	System.out.println();
    return null;
  }

  public void executeProcVars(ExecContext execContext)
  {
  }
}