package com.supporter.prj.linkworks.oa.netin.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.netin.entity.OaNetin;
import com.supporter.prj.linkworks.oa.netin.service.OaNetinService;

public class OaNetinSwfTaskSpecifiedHandler extends AbstractExecHandler {

	private static final long serialVersionUID = 7022088677322240901L;
	
	@Override
	public Object execute(ExecContext execContext) {
		// TODO Auto-generated method stub
		OaNetinService service = (OaNetinService) SpringContextHolder.getBean(OaNetinService.class);
		String netinId = (String) execContext.getProcVar("netinId");
		OaNetin entity = service.get(netinId);
		return entity.getCreatedById();
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}

}
