package com.supporter.prj.linkworks.oa.abroad.workflow;


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
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;
import com.supporter.prj.linkworks.oa.abroad.service.AbroadService;
import com.supporter.prj.linkworks.oa.abroad.util.ConvertUtils;

public class AbroadEndHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc()
	  {
	    return null;
	 }

  public Object execute(ExecContext execContext){
    AbroadService abroadService = (AbroadService)SpringContextHolder.getBean(AbroadService.class);
    String recordId = (String)execContext.getProcVar("recordId");
    Abroad abroad = abroadService.get(recordId);
    abroad.setRecordStatus(Abroad.COMPLETE);
    abroad.setPassDate(ConvertUtils.dateString());
    abroadService.update(abroad);
//    System.out.println();
//    System.out.println(recordId+"333");
//    System.out.println();
//    Message message = new Event();
//	message.setPersonId(abroad.getApplierId());
//	message.setEventTitle("填写回国报告：前往："+ abroad.getTgtCountries());
//	message.setTargetAccount("");
//	message.setNotifyTime(new Date());
//	message.setWebPageURL("/oa/abroadPublicity/abroadBack_addOrEdit.html?recordId="+abroad.getRecordId());
//	message.setMessageType(EventType.TODO);
//	message.setRelatedRecordTable(Abroad.class.getName());
//	EIPService.getBMSService().addMessage(message);
    //给创建人发知会
    if(abroad.getIsAgree() == 1){
    	Message messageCreat = new Todo();
    	messageCreat.setPersonId(abroad.getApplierId());
    	String title = "出国申请通过：前往" + abroad.getTgtCountries();
    	messageCreat.setEventTitle(title);
    	messageCreat.setNotifyTime(new Date());
    	messageCreat.setWebPageURL("/oa/abroad/abroad_audit_view_swf.html?isCcPage=true&recordId=" + recordId);
    	messageCreat.setMessageType(ITodo.MsgType.CC);	
    	messageCreat.setRelatedRecordTable("OAABROAD");
    	EIPService.getBMSService().addMessage(messageCreat);
    	
    	//给综合管理部经理发知会
    	List<Person> generalManager = ConvertUtils.getByRoleId("GeneralManager");
    	if(generalManager != null && generalManager.size() > 0){
    		for(Person person : generalManager){
    			Message message = new Todo();
    			message.setPersonId(person.getPersonId());
    			message.setEventTitle(title);
    			message.setNotifyTime(new Date());
    			message.setWebPageURL("/oa/abroad/abroad_audit_view_swf.html?isCcPage=true&recordId=" + recordId);
    			message.setMessageType(ITodo.MsgType.CC);	
    			message.setRelatedRecordTable("OAABROAD");
    			EIPService.getBMSService().addMessage(message);
    		}
    	}
    	
    	//给秘书处处长发知会
    	List<Person> chiefSecretariat = ConvertUtils.getByRoleId("ChiefSecretariat");
    	if(chiefSecretariat != null && chiefSecretariat.size() > 0){
    		for(Person person : chiefSecretariat){
    			Message message = new Todo();
    			message.setPersonId(person.getPersonId());
    			message.setEventTitle(title);
    			message.setNotifyTime(new Date());
    			message.setWebPageURL("/oa/abroad/abroad_audit_view_swf.html?isCcPage=true&recordId=" + recordId);
    			message.setMessageType(ITodo.MsgType.CC);	
    			message.setRelatedRecordTable("OAABROAD");
    			EIPService.getBMSService().addMessage(message);
    		}
    	}
    	
    	//申请人发送填写回国报告的待办
    	if(abroad.getIfPublicity() == Abroad.YESPUBLICITY){
    		Message message = new Todo();
    		message.setPersonId(abroad.getApplierId());
    		String titles = "填写回国报告：前往：" + abroad.getTgtCountries();
    		message.setEventTitle(titles);
    		message.setNotifyTime(new Date());
    		message.setWebPageURL("/oa/abroadPublicity/abroadBack_addOrEdit.html?recordId=" + recordId);
    		message.setMessageType(ITodo.MsgType.CC);	
    		message.setRelatedRecordTable("OAABROAD");
    		EIPService.getBMSService().addMessage(message);
    	}
    	
    	//申请人发送填写实际出国时间的待办
    	if(abroad.getIfPublicity() == Abroad.YESPUBLICITY){
    		Message message = new Todo();
    		message.setPersonId(abroad.getApplierId());
    		String titles = "填写实际出国时间：前往：" + abroad.getTgtCountries();
    		message.setEventTitle(titles);
    		message.setNotifyTime(new Date());
    		message.setWebPageURL("/oa/abroad/abroad_real_date_addOrEdit.html?recordId=" + recordId);
    		message.setMessageType(ITodo.MsgType.CC);	
    		message.setRelatedRecordTable("OAABROAD");
    		EIPService.getBMSService().addMessage(message);
    	}
    	
    }
	
    return null;
  }

  public void executeProcVars(ExecContext execContext)
  {
  }
}