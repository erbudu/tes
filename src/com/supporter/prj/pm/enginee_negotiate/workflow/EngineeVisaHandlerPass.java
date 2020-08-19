package com.supporter.prj.pm.enginee_negotiate.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.pm.enginee_negotiate.dao.EngineeVisaSwfDao;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaSwf;

/**
 * 完成流程
 * @author Administrator
 *
 */
public class EngineeVisaHandlerPass extends AbstractExecHandler {
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		EngineeVisaSwfDao dao = SpringContextHolder.getBean(EngineeVisaSwfDao.class);
		String visaId = (String) execContext.getProcVar("visaId");
		EngineeVisaSwf swf =  dao.get(visaId);
		swf.setOaExamResult(EngineeVisaSwf.OAExamResult.SUCCESS);
		swf.setOaExamOpinion(EngineeVisaSwf.OAExamResult.getMap().get(swf.getOaExamResult()));
		dao.update(swf);
		return null;
	} 
}
