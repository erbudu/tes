package com.supporter.prj.linkworks.oa.user_defined.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.news.service.NewsService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.doc_out.entity.OaDocOut;
import com.supporter.prj.linkworks.oa.doc_out.service.OaDocOutService;
import com.supporter.prj.linkworks.oa.user_defined.entity.UNetin;
import com.supporter.prj.linkworks.oa.user_defined.service.UNetinService;
import com.supporter.prj.meip_service.news.entity.News;

public class NetinSwfTaskSpecifiedHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7022088677322240901L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		UNetinService service = (UNetinService) SpringContextHolder
				.getBean(UNetinService.class);
		String id = (String) execContext.getProcVar("id");
		UNetin entity = service.get(id);
		return entity.getCreatedById();
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
