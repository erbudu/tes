package com.supporter.prj.linkworks.oa.doc_out.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.news.service.NewsService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.doc_out.entity.OaDocOut;
import com.supporter.prj.linkworks.oa.doc_out.service.OaDocOutService;
import com.supporter.prj.meip_service.news.entity.News;
import com.supporter.prj.meip_service.news.entity.News.Status;
import java.util.Date;

public class DocOutStartHandler extends AbstractExecHandler {
	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		OaDocOutService docService = (OaDocOutService) SpringContextHolder
				.getBean(OaDocOutService.class);
		String docId = (String) execContext.getProcVar("docId");
		OaDocOut doc = docService.get(docId);
		doc.setDocStatus(OaDocOut.PROCESSING);
		doc.setProcId(execContext.getProcId());
		docService.update(doc);
		return null;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
