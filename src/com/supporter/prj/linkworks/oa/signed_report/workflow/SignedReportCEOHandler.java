package com.supporter.prj.linkworks.oa.signed_report.workflow;


import java.util.Date;
import java.util.List;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReport;
import com.supporter.prj.linkworks.oa.signed_report.service.SignedReportService;
import com.supporter.prj.linkworks.oa.abroad.util.ConvertUtils;

public class SignedReportCEOHandler extends AbstractExecHandler {
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
    //流程到总裁处，给秘书处处长发知会
	List<Person> generalManager = ConvertUtils.getByRoleId("ChiefSecretariat");
	if(generalManager != null && generalManager.size() > 0){
		for(Person person : generalManager){
			Message message = new Todo();
			message.setPersonId(person.getPersonId());
			message.setEventTitle("知会（签报流程到总裁秘书）：" + signedReport.getReason() +"(" + signedReport.getDeptName() +")");
			message.setNotifyTime(new Date());
			message.setWebPageURL("/oa/signed_report/signed_report_view.html?isCcPage=true&signedReportId="+signedReportId);
			message.setMessageType(ITodo.MsgType.CC);	
			message.setRelatedRecordTable("OASIGNREPORT");
			EIPService.getBMSService().addMessage(message);
		}
	}
	
	//流程到总裁处，给部门经理发送知会
	Message message = new Todo();
	message.setPersonId(signedReport.getDeptLeaderId());
	message.setEventTitle("知会（签报流程到总裁秘书）：" + signedReport.getReason() +"(" + signedReport.getDeptName() +")");
	message.setNotifyTime(new Date());
	message.setWebPageURL("/oa/signed_report/signed_report_view.html?isCcPage=true&signedReportId="+signedReportId);
	message.setMessageType(ITodo.MsgType.CC);	
	message.setRelatedRecordTable("OASIGNREPORT");
	EIPService.getBMSService().addMessage(message);
    return null;
  }

  public void executeProcVars(ExecContext execContext)
  {
  }
}