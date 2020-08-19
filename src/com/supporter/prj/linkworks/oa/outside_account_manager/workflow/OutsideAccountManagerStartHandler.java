package com.supporter.prj.linkworks.oa.outside_account_manager.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.outside_account_manager.entity.OutsideAccountManager;
import com.supporter.prj.linkworks.oa.outside_account_manager.service.OutsideAccountManagerService;

public class OutsideAccountManagerStartHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;
	
	@Override
	public Object execute(ExecContext execContext) {
		// TODO Auto-generated method stub
		OutsideAccountManagerService service = (OutsideAccountManagerService)SpringContextHolder.getBean(OutsideAccountManagerService.class);
	    String managerId = (String)execContext.getProcVar("managerId");
	    OutsideAccountManager entity = service.get(managerId);
	    entity.setStatus(OutsideAccountManager.PROCESSING);
	    entity.setProcId(execContext.getProcId());
	    service.update(entity);
		return null;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}

}
