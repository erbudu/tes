package com.supporter.prj.linkworks.oa.emp_meal_apply.service;


import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.EmpMealApply;
@Service
public class EmpMealApplyViewerProvider implements IBusiEntityViewerProvider{
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return EmpMealApply.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/emp_meal/mealApply_view.html?applyId=" + entityId;
	}

}
