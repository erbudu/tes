package com.supporter.prj.pm.contract_balance.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.pm.contract_balance.dao.ContractBalanceConstSwfDao;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConstSwf;

/**
 * 启动流程
 * @author Administrator
 *
 */
public class ContractBalanceHandlerStart extends AbstractExecHandler {
	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	@Override
	public Object execute(ExecContext execContext) {
		ContractBalanceConstSwfDao dao = SpringContextHolder.getBean(ContractBalanceConstSwfDao.class);
		String balanceId = (String) execContext.getProcVar("balanceId");
		ContractBalanceConstSwf entity = dao.get(balanceId);
		if (entity != null) {
			entity.setOaExamResult(ContractBalanceConstSwf.OAExamResult.SUCCESS);
			entity.setOaExamOpinion(ContractBalanceConstSwf.OAExamResult.getMap().get(entity.getOaExamResult()));
			entity.setOaExamStatus(ContractBalanceConstSwf.OAExamStatus.EXAM);
			entity.setOaProcId(execContext.getProcId());
			dao.update(entity);		
		} else {
			EIPService.getLogService().error("无效的balanceId:" + balanceId);
		}
		return null;
	}

}
