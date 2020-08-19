package com.supporter.prj.linkworks.oa.doc_in.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.doc_in.entity.DocIn;
import com.supporter.prj.linkworks.oa.doc_in.service.DocInService;

public class DocInStartHandler extends AbstractExecHandler {
	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		DocInService docService = (DocInService) SpringContextHolder
				.getBean(DocInService.class);
		String docId = (String) execContext.getProcVar("docId");
		DocIn doc = docService.get(docId);
		doc.setDocStatus(DocIn.PROCESSING);
		doc.setProcId(execContext.getProcId());
		docService.update(doc);
		return null;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
