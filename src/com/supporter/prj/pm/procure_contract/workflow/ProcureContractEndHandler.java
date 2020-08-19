package com.supporter.prj.pm.procure_contract.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.pm.procure_contract.dao.ProcureContractSwfDao;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractSwf;

/**
 * @Title: ProcureContractEndHandler
 * @Description: 流程结束操作类
 * @author: liyinfeng
 * @date: 2018-6-14
 * @version: V1.0
 */
public class ProcureContractEndHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ProcureContractSwfDao dao = SpringContextHolder.getBean(ProcureContractSwfDao.class);
		String entityId = (String) execContext.getProcVar("contractId");
		ProcureContractSwf swf = dao.get(entityId);
		swf.setOaExamEndDate(new java.util.Date());
		swf.setOaExamOpinion("通过");
		swf.setOaExamResult(ProcureContractSwf.OAExamResult.SUCCESS);
		swf.setOaExamStatus(ProcureContractSwf.OAExamStatus.COMPLETE);
		dao.update(swf);
		return null;
	}

}