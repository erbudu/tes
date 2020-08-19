package com.supporter.prj.pm.contract_balance.workflow;

import org.apache.commons.lang3.StringUtils;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.swf.dao.MyWfExamDao;
import com.supporter.prj.eip.swf.runtime.entity.WfExam;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.pm.contract_balance.dao.ContractBalanceConstSwfDao;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConstSwf;

/**
 * 结束流程
 * @author Administrator
 *
 */
public class ContractBalanceHandlerFail extends AbstractExecHandler {
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		ContractBalanceConstSwfDao dao = SpringContextHolder.getBean(ContractBalanceConstSwfDao.class);
		String balanceId = (String) execContext.getProcVar("balanceId");
		ContractBalanceConstSwf entity =  dao.get(balanceId);
		entity.setOaExamResult(ContractBalanceConstSwf.OAExamResult.FAILED);
		
		MyWfExamDao examDao = SpringContextHolder.getBean(MyWfExamDao.class);
		String procId = execContext.getProcId();
		String nodeName = execContext.getCurrNodeName();
		WfExam exam = examDao.getLatestExamByNodeName(procId, nodeName);
		String empName = "";
		if (exam != null) { 
			entity.setOaExamOpinion(exam.getOpinionDesc());
			empName = exam.getEmpName();
		}
		if (StringUtils.isBlank(entity.getOaExamOpinion())) {
			entity.setOaExamOpinion(ContractBalanceConstSwf.OAExamResult.getMap().get(entity.getOaExamResult()));
		}
		entity.setOaExamOpinion(nodeName + "驳回（" + empName + "）：" + entity.getOaExamOpinion());
		
		dao.update(entity);
		return null;
	} 
}
