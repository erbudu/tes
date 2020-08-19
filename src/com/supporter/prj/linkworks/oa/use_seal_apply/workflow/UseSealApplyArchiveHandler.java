package com.supporter.prj.linkworks.oa.use_seal_apply.workflow;


import java.util.Date;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.UseSealApply;
import com.supporter.prj.linkworks.oa.use_seal_apply.service.UseSealApplyService;
import com.supporter.util.CommonUtil;

public class UseSealApplyArchiveHandler extends AbstractExecHandler {
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
	UseSealApplyService useSealApplyService = (UseSealApplyService)SpringContextHolder.getBean(UseSealApplyService.class);
    String applyId = (String)execContext.getProcVar("applyId");
    UseSealApply useSealApply = useSealApplyService.get(applyId);
    useSealApply.setArchiveDate(CommonUtil.format(new Date(), "yyyy-MM-dd"));
    useSealApply.setConfirmDate(CommonUtil.format(new Date(), "yyyy-MM-dd"));
    useSealApply.setActresult(1L);
    useSealApply.setActresultname("同意");
    useSealApplyService.update(useSealApply);
    return null;
  }

  public void executeProcVars(ExecContext execContext)
  {
  }
}