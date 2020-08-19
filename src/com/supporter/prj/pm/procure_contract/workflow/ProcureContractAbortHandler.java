package com.supporter.prj.pm.procure_contract.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.pm.procure_contract.dao.ProcureContractSwfDao;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractSwf;

/**
 * @Title: ProcureContractAbortHandler
 * @Description: 流程中止操作类
 * @author: liyinfeng
 * @date: 2018-6-14
 * @version: V1.0
 */
public class ProcureContractAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	/**
	 * 流程中止执行动作.
	 */
	@Override
	public Object execute(ExecContext execContext) {
		ProcureContractSwfDao dao = SpringContextHolder.getBean(ProcureContractSwfDao.class);
		String entityId = (String) execContext.getProcVar("contractId");
		ProcureContractSwf swf = dao.get(entityId);
		swf.setOaExamStatus(ProcureContractSwf.OAExamStatus.DRAFT);
		dao.update(swf);
		return null;
	}

}