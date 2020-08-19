package com.supporter.prj.linkworks.oa.authority_apply.workflow;


import java.util.Date;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApply;
import com.supporter.prj.linkworks.oa.authority_apply.service.AuthorityApplyService;
import com.supporter.util.CommonUtil;

public class AuthorityApplyArchiveHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc()
	  {
	    return null;
	 }

  public Object execute(ExecContext execContext)
  {
	AuthorityApplyService authorityApplyService = (AuthorityApplyService)SpringContextHolder.getBean(AuthorityApplyService.class);
    String applyId = (String)execContext.getProcVar("applyId");
    AuthorityApply authorityApply = authorityApplyService.get(applyId);
    authorityApply.setArchiveDate(CommonUtil.format(new Date(), "yyyy-MM-dd"));
    authorityApply.setConfirmDate(CommonUtil.format(new Date(), "yyyy-MM-dd"));
    authorityApply.setActresult(1L);
    authorityApply.setActresultname("同意");
    authorityApplyService.update(authorityApply);
    return null;
  }

  public void executeProcVars(ExecContext execContext)
  {
  }
}