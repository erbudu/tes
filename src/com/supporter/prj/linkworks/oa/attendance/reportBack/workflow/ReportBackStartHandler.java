/**
 * 
 */
package com.supporter.prj.linkworks.oa.attendance.reportBack.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.attendance.reportBack.entity.ReportBackEntity;
import com.supporter.prj.linkworks.oa.attendance.reportBack.service.ReportBackService;

/**
 * @title 销假单流程开始服务类
 * @author YUEYUN
 * @date 2019年7月21日
 * 
 */
public class ReportBackStartHandler extends AbstractExecHandler{

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
		ReportBackService reportBackService = (ReportBackService)SpringContextHolder.getBean(ReportBackService.class);
		    String reportBackId= (String)execContext.getProcVar("reportBackId");

		    ReportBackEntity reportBackEntity = reportBackService.get(reportBackId);
		    if (reportBackEntity != null) {
		    	reportBackEntity.setProcId(execContext.getProcId());
		    	reportBackEntity.setStatus(1);
		    	reportBackService.update(reportBackEntity);
		    } else {
		      EIPService.getLogService().error("无效的leaveId:" + reportBackId);
		    }
		return null;
	}
}
