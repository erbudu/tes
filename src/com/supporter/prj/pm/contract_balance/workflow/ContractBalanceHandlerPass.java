package com.supporter.prj.pm.contract_balance.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.pm.contract_balance.dao.ContractBalanceConstSwfDao;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConstSwf;

/**
 * 结束流程
 * @author Administrator
 *
 */
public class ContractBalanceHandlerPass extends AbstractExecHandler {
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
		entity.setOaExamResult(ContractBalanceConstSwf.OAExamResult.SUCCESS);
		entity.setOaExamOpinion(ContractBalanceConstSwf.OAExamResult.getMap().get(entity.getOaExamResult()));
		dao.update(entity);
		return null;
	} 
}
