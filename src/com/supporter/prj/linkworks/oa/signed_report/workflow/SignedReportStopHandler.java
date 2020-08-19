package com.supporter.prj.linkworks.oa.signed_report.workflow;

import org.apache.commons.lang3.StringUtils;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReport;
import com.supporter.prj.linkworks.oa.signed_report.service.SignedReportService;

import bsh.StringUtil;

public class SignedReportStopHandler extends AbstractExecHandler {
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
	    signedReport.setSwfStatus(SignedReport.DRAFT);
	    //中止流程如果清除佣金代理协议编号
	    if (StringUtils.isNotBlank(signedReport.getAgreementNo())) {
	    	signedReport.setAgreementNo("");
	    }
	    signedReportService.update(signedReport);
	    return null;
	  }
	
	  public void executeProcVars(ExecContext execContext)
	  {
	  }
}