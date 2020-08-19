package com.supporter.prj.cneec.tpc.use_seal.workflow;

import com.supporter.prj.cneec.tpc.use_seal.entity.UseSeal;
import com.supporter.prj.cneec.tpc.use_seal.service.UseSealService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;

public class UseSealStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		UseSealService useSealService = (UseSealService) SpringContextHolder.getBean(UseSealService.class);
		String sealId = (String) execContext.getProcVar("sealId");
		UseSeal useSeal = useSealService.get(sealId);
		if (useSeal != null) {
			useSeal.setSwfStatus(UseSeal.PROCESSING);
			useSeal.setProcId(execContext.getProcId());
			useSealService.update(useSeal);
		} else {
			EIPService.getLogService().error("无效的sealId:" + sealId);
		}
		return null;
	}

}