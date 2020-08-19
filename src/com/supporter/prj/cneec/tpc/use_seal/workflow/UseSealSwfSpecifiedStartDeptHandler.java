package com.supporter.prj.cneec.tpc.use_seal.workflow;

import com.supporter.prj.cneec.tpc.use_seal.entity.UseSeal;
import com.supporter.prj.cneec.tpc.use_seal.service.UseSealService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class UseSealSwfSpecifiedStartDeptHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public String getDesc() {
		return null;
	}

	// 工作流组织树起始部门程序指定
	@Override
	public Object execute(ExecContext execContext) {
		UseSealService useSealService = (UseSealService) SpringContextHolder.getBean(UseSealService.class);
		String sealId = (String) execContext.getProcVar("sealId");
		UseSeal useSeal = useSealService.get(sealId);
		return useSeal.getDeptId();
	}

}
