package com.supporter.prj.linkworks.oa.integral.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.integral.entity.base.BasePersonIntegral;
@Entity
@Table(name = "OA_PERSON_INTEGRAL", schema = "")
public class PersonIntegral extends BasePersonIntegral{
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
