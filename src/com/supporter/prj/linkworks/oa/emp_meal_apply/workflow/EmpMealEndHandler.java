package com.supporter.prj.linkworks.oa.emp_meal_apply.workflow;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.linkworks.oa.doc_out.entity.OaDocOut;
import com.supporter.prj.linkworks.oa.doc_out.service.OaDocOutService;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.EmpMealApply;
import com.supporter.prj.linkworks.oa.emp_meal_apply.service.EmpMealApplyService;

public class EmpMealEndHandler extends AbstractExecHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getDesc() {
		return null;
	}

	public Object execute(ExecContext execContext) {
		EmpMealApplyService service = (EmpMealApplyService) SpringContextHolder
				.getBean(EmpMealApplyService.class);
		String applyId = (String) execContext.getProcVar("applyId");
		EmpMealApply entity = service.get(applyId);
		entity.setStatus(EmpMealApply.COMPLETE);
		service.update(entity);
		return null;
	}

	public void executeProcVars(ExecContext execContext) {
	}
}
