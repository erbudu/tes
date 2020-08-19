package com.supporter.prj.pm.fund_appropriation.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.pm.fund_appropriation.dao.FundAppropriationSwfDao;
import com.supporter.prj.pm.fund_appropriation.entity.FundAppropriationSwf;

/**
 * 完成流程
 * @author Administrator
 *
 */
public class FuncApproHandlerComplete extends AbstractExecHandler {
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		FundAppropriationSwfDao dao = SpringContextHolder.getBean(FundAppropriationSwfDao.class);
		String fundId = (String) execContext.getProcVar("fundId");
		FundAppropriationSwf swf =  dao.get(fundId);
		swf.setOaExamEndDate(new java.util.Date());
		if (swf.getOaExamResult() == FundAppropriationSwf.OAExamResult.SUCCESS) {
			swf.setOaExamStatus(FundAppropriationSwf.OAExamStatus.COMPLETE);
		} else {
			swf.setOaExamStatus(FundAppropriationSwf.OAExamStatus.FAIL);
		}
		
		dao.update(swf);
		return null;
	} 
}
