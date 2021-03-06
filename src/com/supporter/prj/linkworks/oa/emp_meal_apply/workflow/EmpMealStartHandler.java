package com.supporter.prj.linkworks.oa.emp_meal_apply.workflow;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.news.service.NewsService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.doc_out.entity.OaDocOut;
import com.supporter.prj.linkworks.oa.doc_out.service.OaDocOutService;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.EmpMealApply;
import com.supporter.prj.linkworks.oa.emp_meal_apply.service.EmpMealApplyService;
import com.supporter.prj.meip_service.news.entity.News;
import com.supporter.prj.meip_service.news.entity.News.Status;
import java.util.Date;

public class EmpMealStartHandler extends AbstractExecHandler {
	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		EmpMealApplyService service = (EmpMealApplyService) SpringContextHolder
				.getBean(EmpMealApplyService.class);
		String applyId = (String) execContext.getProcVar("applyId");
		EmpMealApply entity = service.get(applyId);
		entity.setStatus(EmpMealApply.PROCESSING);
		entity.setProcId(execContext.getProcId());
		service.update(entity);
		return null;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
