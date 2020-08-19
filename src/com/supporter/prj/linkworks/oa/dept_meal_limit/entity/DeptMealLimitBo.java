package com.supporter.prj.linkworks.oa.dept_meal_limit.entity;

import java.util.List;

public class DeptMealLimitBo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<DeptMealLimit> limits;
	public List<DeptMealLimit> getLimits() {
		return limits;
	}
	public void setLimits(List<DeptMealLimit> limits) {
		this.limits = limits;
	}
}
