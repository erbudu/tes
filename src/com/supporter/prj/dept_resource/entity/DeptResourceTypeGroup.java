package com.supporter.prj.dept_resource.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.dept_resource.entity.base.BaseDeptResourceTypeGroup;

@Entity
@Table(name = "COM_DEPT_RESOURCE_TYPE_GROUP", schema = "")
public class DeptResourceTypeGroup extends BaseDeptResourceTypeGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String authDesc;

	@Transient
	public String getAuthDesc() {
		return authDesc;
	}

	public void setAuthDesc(String authDesc) {
		this.authDesc = authDesc;
	}
	
	private String roleNameG;
	@Transient
	public String getRoleNameG() {
		return roleNameG;
	}

	public void setRoleNameG(String roleNameG) {
		this.roleNameG = roleNameG;
	}
	

}
