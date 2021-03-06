package com.supporter.prj.pm.fund_appropriation.workflow;

import org.apache.commons.lang3.StringUtils;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.swf.dao.MyWfExamDao;
import com.supporter.prj.eip.swf.runtime.entity.WfExam;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.pm.fund_appropriation.dao.FundAppropriationSwfDao;
import com.supporter.prj.pm.fund_appropriation.entity.FundAppropriationSwf;

/**
 * 完成流程
 * @author Administrator
 *
 */
public class FuncApproHandlerFail extends AbstractExecHandler {
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
		swf.setOaExamResult(FundAppropriationSwf.OAExamResult.FAILED);
		
		MyWfExamDao examDao = SpringContextHolder.getBean(MyWfExamDao.class);
		String procId = execContext.getProcId();
		String nodeName = execContext.getCurrNodeName();
		WfExam exam = examDao.getLatestExamByNodeName(procId, nodeName);
		String empName = "";
		if (exam != null) {
			swf.setOaExamOpinion(exam.getOpinionDesc());
			empName = exam.getEmpName();
		}
		if (StringUtils.isBlank(swf.getOaExamOpinion())) {
			swf.setOaExamOpinion(FundAppropriationSwf.OAExamResult.getMap().get(swf.getOaExamResult()));
		}
		swf.setOaExamOpinion(nodeName + "驳回（" + empName + "）：" + swf.getOaExamOpinion());
		
		dao.update(swf);
		return null;
	} 
}
