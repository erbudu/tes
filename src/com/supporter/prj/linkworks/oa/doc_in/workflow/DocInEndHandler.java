package com.supporter.prj.linkworks.oa.doc_in.workflow;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.doc_in.entity.DocIn;
import com.supporter.prj.linkworks.oa.doc_in.service.DocInService;

public class DocInEndHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		DocInService docService = (DocInService) SpringContextHolder
				.getBean(DocInService.class);
		String docId = (String) execContext.getProcVar("docId");
		DocIn doc = docService.get(docId);
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (doc.getPublishDate() == null) {
			doc.setPublishDate(sdf.format(now));
		}
		doc.setDocStatus(DocIn.ARCHIVED);
		docService.update(doc);
		return null;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
