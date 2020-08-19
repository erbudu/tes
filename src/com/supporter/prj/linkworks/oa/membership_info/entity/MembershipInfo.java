package com.supporter.prj.linkworks.oa.membership_info.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.membership_info.entity.base.BaseMembershipInfo;
@Entity
@Table(name = "OA_MEMBERSHIP_INFO", schema = "")
public class MembershipInfo extends BaseMembershipInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean add;
	private String deptName;
	@Transient
	public boolean getAdd() {
		return add;
	}
	public void setAdd(boolean add) {
		this.add = add;
	}
	@Transient
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
