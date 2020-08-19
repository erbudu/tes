/**
 * 
 */
package com.supporter.prj.linkworks.oa.attendance.reportBack.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.attendance.leave.entiyt.LeaveEntity;
import com.supporter.prj.linkworks.oa.attendance.leave.service.LeaveService;
import com.supporter.prj.linkworks.oa.attendance.reportBack.entity.ReportBackEntity;
import com.supporter.prj.linkworks.oa.attendance.reportBack.service.ReportBackService;

/**
 * @title 销假单流程结束服务类
 * @author YUEYUN
 * @date 2019年7月21日
 * 
 */
public class ReportBackEndHandler extends AbstractExecHandler{

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
		ReportBackService reportBackService = (ReportBackService)SpringContextHolder.getBean(ReportBackService.class);
		    String reportBackId= (String)execContext.getProcVar("reportBackId");

		    ReportBackEntity reportBackEntity = reportBackService.get(reportBackId);
		    if (reportBackEntity != null) {
		    	reportBackEntity.setProcId(execContext.getProcId());
		    	reportBackEntity.setStatus(2);
		    	reportBackService.update(reportBackEntity);
		    	//销假后将请假单的销假状态改为已销假
		    	String leaveId = reportBackEntity.getLeaveId();
		    	LeaveEntity leaveEntity = leaveService.get(leaveId);
		    	leaveEntity.setIsSellingOff(1);
		    	leaveService.update(leaveEntity);
		    } else {
		      EIPService.getLogService().error("无效的leaveId:" + reportBackId);
		    }
		return null;
	}
	
	
}
