package com.supporter.prj.dept_resource.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.dept_resource.entity.base.BaseDeptResourceAuth;

@Entity
@Table(name = "COM_DEPT_RESOURCE_AUTH", schema = "")
public class DeptResourceAuth extends BaseDeptResourceAuth {

	private static final long serialVersionUID = 1L;
    public static final String PERSON = "PERSON";
    public static final String POST = "POST";
    public static final String GROUP = "GROUP";
    public static final String DEPT = "DEPT";
    
    public static final String DEFINETION = "DEFINETION";
    public static final String RESOURCE_CONTENT = "RESOURCE_CONTENT";
	private boolean add;

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	private String authorizeeIdDesc = "";

	@Transient
	public String getAuthorizeeIdDesc() {
		return authorizeeIdDesc;
	}

	public void setAuthorizeeIdDesc(String authorizeeIdDesc) {
		this.authorizeeIdDesc = authorizeeIdDesc;
	}

	private String authDesc = "";

	@Transient
	public String getAuthDesc() {
		return authDesc;
	}

	public void setAuthDesc(String authDesc) {
		this.authDesc = authDesc;
	}

	private DeptResource deptResource;

	@Transient
	public DeptResource getDeptResource() {
		return deptResource;
	}

	public void setDeptResource(DeptResource deptResource) {
		this.deptResource = deptResource;
	}
    //用于重新授权给部门时部门名称的回显
	private String deptName;

	@Transient
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	//用于重新授权给个人时人员姓名的回显
	private String empName;
	@Transient
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	//用于重新授权给个人时岗位的回显
	private String postName;
	
	//用于重新授权给个人时角色的回显
	private String roleName;
	@Transient
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}
	@Transient
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
