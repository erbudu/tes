package com.supporter.prj.dept_resource.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.dept_resource.entity.base.BaseDeptResource;

@Entity
@Table(name = "COM_DEPT_RESOURCE", schema = "")
public class DeptResource extends BaseDeptResource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean add;

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	private String deptName;

	@Transient
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	private String deptResourceTypeName;

	@Transient
	public String getDeptResourceTypeName() {
		return deptResourceTypeName;
	}

	public void setDeptResourceTypeName(String deptResourceTypeName) {
		this.deptResourceTypeName = deptResourceTypeName;
	}

	private String deptNameAndResourceName;

	@Transient
	public String getDeptNameAndResourceName() {
		return deptNameAndResourceName;
	}

	public void setDeptNameAndResourceName(String deptNameAndResourceName) {
		this.deptNameAndResourceName = deptNameAndResourceName;
	}

	// 用于列表资源内容授权格式化
	private String resourceContentDesc;

	@Transient
	public String getResourceContentDesc() {
		return resourceContentDesc;
	}

	public void setResourceContentDesc(String resourceContentDesc) {
		this.resourceContentDesc = resourceContentDesc;
	}

	// 用于列表资源设置授权格式化
	private String finetionDesc;

	@Transient
	public String getFinetionDesc() {
		return finetionDesc;
	}

	public void setFinetionDesc(String finetionDesc) {
		this.finetionDesc = finetionDesc;
	}

	private DeptResourceAuth deptResourceAuth;

	@Transient
	public DeptResourceAuth getDeptResourceAuth() {
		return deptResourceAuth;
	}

	public void setDeptResourceAuth(DeptResourceAuth deptResourceAuth) {
		this.deptResourceAuth = deptResourceAuth;
	}

}
