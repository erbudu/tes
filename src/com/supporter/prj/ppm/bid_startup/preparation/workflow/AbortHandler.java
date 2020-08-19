/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.bid_startup.preparation.constant.StartContant;
import com.supporter.prj.ppm.bid_startup.preparation.entity.StartEntity;
import com.supporter.prj.ppm.bid_startup.preparation.service.StartService;
import com.supporter.prj.ppm.service.PPMService;

/**
 *<p>Title: AbortHandler</p>
 *<p>Description:申报准备-流程终止服务类 </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月4日
 * 
 */
public class AbortHandler extends AbstractExecHandler{


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
		    	entity.setStatus(StartContant.DRAFT);//流程状态-草稿
		    	PPMService.getScheduleStatusService().saveScheduleStatus(entity.getPrjId(), "preparation03", null);
		    	startService.update(entity);
		    } else {
		      EIPService.getLogService().error("无效的leaveId:" + bidIngUpId);
		    }
		return null;
	}
}
