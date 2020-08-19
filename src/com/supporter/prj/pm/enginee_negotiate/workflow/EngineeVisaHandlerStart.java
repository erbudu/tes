package com.supporter.prj.pm.enginee_negotiate.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.pm.enginee_negotiate.dao.EngineeVisaSwfDao;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaSwf;

/**
 * 启动流程
 * @author Administrator
 *
 */
public class EngineeVisaHandlerStart extends AbstractExecHandler {
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		EngineeVisaSwfDao dao = SpringContextHolder.getBean(EngineeVisaSwfDao.class);
		String visaId = (String) execContext.getProcVar("visaId");
		EngineeVisaSwf swf = dao.get(visaId);
		if (swf != null) {
			swf.setOaExamResult(EngineeVisaSwf.OAExamResult.SUCCESS);
			swf.setOaExamOpinion(EngineeVisaSwf.OAExamResult.getMap().get(swf.getOaExamResult()));
			swf.setOaExamStatus(EngineeVisaSwf.OAExamStatus.EXAM);
			swf.setOaProcId(execContext.getProcId());
			dao.update(swf);
		} else {
			EIPService.getLogService().error("无效的visaId:" + visaId);
		}
		return null;
	}

}
