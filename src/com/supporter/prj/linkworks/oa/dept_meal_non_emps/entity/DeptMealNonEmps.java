package com.supporter.prj.linkworks.oa.dept_meal_non_emps.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.dept_meal_non_emps.entity.base.BaseDeptMealNonEmps;
@Entity
@Table(name = "OA_DEPT_MEAL_NON_EMPS", schema = "")
public class DeptMealNonEmps extends BaseDeptMealNonEmps {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean add;
	private String delIds;
	private List<DeptMealNonEmpsRec> list;
	@Transient
	public List<DeptMealNonEmpsRec> getList() {
		return list;
	}

	public void setList(List<DeptMealNonEmpsRec> list) {
		this.list = list;
	}

	@Transient
	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}
	

}
