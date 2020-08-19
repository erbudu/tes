package com.supporter.prj.pm.procure_contract.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.pm.procure_contract.dao.ProcureContractSwfDao;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractSwf;

/**
 * @Title: ProcureContractStartHandler
 * @Description: 流程开始操作类
 * @author: liyinfeng
 * @date: 2017-11-06
 * @version: V1.0
 */
public class ProcureContractStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		ProcureContractSwfDao dao = SpringContextHolder.getBean(ProcureContractSwfDao.class);
		String entityId = (String) execContext.getProcVar("contractId");
		ProcureContractSwf swf = dao.get(entityId);
		if (swf != null) {
			swf.setOaProcId(execContext.getProcId());
			swf.setOaExamStatus(ProcureContractSwf.OAExamStatus.EXAM);
			dao.update(swf);		
		} else {
			EIPService.getLogService().error(ProcureContractStartHandler.class.getName() + "无效的contractId:" + entityId);
		}
		return null;
	}

}