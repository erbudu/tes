package com.supporter.prj.ppm.ecc.group_review.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptReview;
import com.supporter.prj.ppm.ecc.dept_review.service.EccDeptReviewService;
import com.supporter.prj.ppm.ecc.group_review.entity.EccGroupReview;
import com.supporter.prj.ppm.ecc.group_review.service.EccGroupReviewService;

public class EccGroupReviewAbortHandler
  extends AbstractExecHandler
{
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
    EccGroupReviewService service = (EccGroupReviewService)SpringContextHolder.getBean(EccGroupReviewService.class);
    String eccGroupId = (String)execContext.getProcVar("eccGroupId");
    EccGroupReview entity = service.get(eccGroupId);
    entity.setGroupEccStatus(EccGroupReview.DRAFT);
    service.abortEccGroup(entity);
    return null;
  }
  
  public void executeProcVars(ExecContext execContext) {}
}
