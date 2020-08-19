package com.supporter.prj.linkworks.oa.membership_dues.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.membership_dues.entity.base.BaseMembershipDues;
@Entity
@Table(name = "OA_MEMBERSHIP_DUES", schema = "SUPP_APP")
public class MembershipDues extends BaseMembershipDues {

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

}
