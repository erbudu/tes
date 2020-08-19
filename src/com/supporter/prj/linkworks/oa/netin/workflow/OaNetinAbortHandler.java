package com.supporter.prj.linkworks.oa.netin.workflow;


import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.netin.entity.OaNetin;
import com.supporter.prj.linkworks.oa.netin.service.OaNetinService;

public class OaNetinAbortHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 1L;
	
	@Override
	public Object execute(ExecContext execContext) {
		// TODO Auto-generated method stub
		OaNetinService netinService = (OaNetinService)SpringContextHolder.getBean(OaNetinService.class);
	    String netinId = (String)execContext.getProcVar("netinId");
	    OaNetin netin = netinService.get(netinId);
	    netin.setStatus(OaNetin.DRAFT);
	    netinService.update(netin);
		return null;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}

}
