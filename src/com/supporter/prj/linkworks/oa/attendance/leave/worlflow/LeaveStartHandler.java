package com.supporter.prj.linkworks.oa.attendance.leave.worlflow;


import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.attendance.leave.entiyt.LeaveEntity;
import com.supporter.prj.linkworks.oa.attendance.leave.service.LeaveService;

public class LeaveStartHandler extends AbstractExecHandler{

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
		    	leaveEntity.setStatus(1);
		    	leaveService.update(leaveEntity);
		    } else {
		      EIPService.getLogService().error("无效的leaveId:" + leaveId);
		    }
		return null;
	}

	
}
