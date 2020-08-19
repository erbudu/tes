/**
 * 
 */
package com.supporter.prj.linkworks.oa.overseas_bimonthly_report.workflow;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.constant.OverseasBimonthlyReportConstant;
import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.entity.OverseasBimonthlyReportEntity;
import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.service.OverseasBimonthlyReportService;

/**
 *<p>Title: OveBimRepEndHandler</p>
 *<p>Description: 流程结束服务类</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年12月25日
 * 
 */
public class OveBimRepEndHandler extends AbstractExecHandler{

	private static final long serialVersionUID = 1L;

	@Override
	public Object execute(ExecContext execContext) {
		OverseasBimonthlyReportService service = SpringContextHolder.getBean(OverseasBimonthlyReportService.class);
		String reportId = (String) execContext.getProcVar("reportId");
		OverseasBimonthlyReportEntity entity = service.get(reportId);
		if(entity != null) {
			entity.setStatus(OverseasBimonthlyReportConstant.COMPLETE);
			service.update(entity);
			String personIds = entity.getNotifyPersonId();
			if(personIds != null && personIds != "") {
				service.sendNotice(personIds,reportId);
			}
		}else {
			EIPService.getLogService().error("无效的reportId:" + reportId);
		}
		return null;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}

}
