/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.attendance.leave.entiyt.LeaveEntity;
import com.supporter.prj.linkworks.oa.attendance.leave.service.LeaveService;
import com.supporter.prj.ppm.bid_startup.preparation.constant.StartContant;
import com.supporter.prj.ppm.bid_startup.preparation.entity.StartEntity;
import com.supporter.prj.ppm.bid_startup.preparation.service.StartService;
import com.supporter.prj.ppm.service.PPMService;

/**
 *<p>Title: StartHandler</p>
 *<p>Description: 申报准备-流程开始服务类</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月4日
 * 
 */
public class StartHandler extends AbstractExecHandler {


	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
			StartService startService = (StartService)SpringContextHolder.getBean(StartService.class);
		    String bidIngUpId = (String)execContext.getProcVar("bidIngUpId");
		    StartEntity entity = startService.get(bidIngUpId);
		    if (entity != null) {
		    	entity.setProcId(execContext.getProcId());//流程ID
		    	
		    	entity.setStatus(StartContant.PROCESSING);//流程状态-审批中
		    	startService.update(entity);
		    	PPMService.getScheduleStatusService().saveScheduleStatus(entity.getPrjId(), "preparation02", null);
		    } else {
		      EIPService.getLogService().error("无效的主键 :" + bidIngUpId);
		    }
		return null;
	}

}
