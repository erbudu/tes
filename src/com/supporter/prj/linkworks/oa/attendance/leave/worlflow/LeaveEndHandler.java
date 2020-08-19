package com.supporter.prj.linkworks.oa.attendance.leave.worlflow;


import java.util.Date;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.attendance.leave.entiyt.LeaveEntity;
import com.supporter.prj.linkworks.oa.attendance.leave.service.LeaveService;
import com.supporter.prj.linkworks.oa.attendance.reportBack.entity.ReportBackEntity;
import com.supporter.prj.linkworks.oa.attendance.reportBack.service.ReportBackService;
import com.supporter.util.CommonUtil;

public class LeaveEndHandler extends AbstractExecHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		LeaveService leaveService = (LeaveService)SpringContextHolder.getBean(LeaveService.class);
		
	    String leaveId = (String)execContext.getProcVar("leaveId");
	    LeaveEntity leaveEntity = leaveService.get(leaveId);
	    if (leaveEntity != null) {
	    	leaveEntity.setProcId(execContext.getProcId());
	    	leaveEntity.setStatus(2);
	    	leaveService.update(leaveEntity);
	    	
	    	saveRportBack(leaveEntity);
	    	sendNotifyToLeavePerson(leaveEntity);
	    } else {
	      EIPService.getLogService().error("无效的leaveId:" + leaveId);
	    }
	return null;
}

	/**
	 * 生成销假单
	 * @param leaveEntity
	 */
	private void saveRportBack(LeaveEntity leaveEntity) {
		ReportBackService reportBackService = (ReportBackService)SpringContextHolder.getBean(ReportBackService.class);
		ReportBackEntity reportBackEntity = new ReportBackEntity();
		reportBackEntity.setReportBackId(com.supporter.util.UUIDHex.newId());
    	reportBackEntity.setLeaveId(leaveEntity.getLeaveId());//请假单id
    	reportBackEntity.setCreateBy(leaveEntity.getLeavePersonName());//请假人名称
    	reportBackEntity.setCreateById(leaveEntity.getLeavePersonId());//请假人id
    	reportBackEntity.setDeptId(leaveEntity.getDeptId());//请假人部门id
    	reportBackEntity.setDeptName(leaveEntity.getDeptName());//请假人部门名称
    	reportBackEntity.setEmpLevel(leaveEntity.getEmpLevel());//请假人职位等级
    	reportBackService.save(reportBackEntity);
	}
	
	/**
	 * 发送知会给请假人
	 * @param entity
	 */
	private void sendNotifyToLeavePerson(LeaveEntity entity) {
		String title = "请假单（" + entity.getLeaveType() + "）审批完成，假期结束后请及时在此填写销假信息！";
		Message messageCreat =new Todo();
	    messageCreat.setPersonId(entity.getLeavePersonId());
		messageCreat.setEventTitle(title);
		messageCreat.setNotifyTime(new Date());
		messageCreat.setWebPageURL("/oa/attendance/report_back/report_back_edit.html?leaveId="
				+ CommonUtil.trim(entity.getLeaveId()));
		messageCreat.setMessageType(ITodo.MsgType.CC);
		messageCreat.setRelatedRecordTable(EIPService.getWebappService().getWebappName());
		EIPService.getBMSService().addMessage(messageCreat);
	}
	
	
}
