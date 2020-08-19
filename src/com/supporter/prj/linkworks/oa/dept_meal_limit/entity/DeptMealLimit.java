package com.supporter.prj.linkworks.oa.dept_meal_limit.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.dept_meal_limit.entity.base.BaseDeptMealLimit;
@Entity
@Table(name = "OA_DEPT_MEAL_LIMIT", schema = "")
public class DeptMealLimit extends BaseDeptMealLimit{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean add = false;
	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}
	
	

}
