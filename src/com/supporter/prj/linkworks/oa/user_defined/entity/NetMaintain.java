package com.supporter.prj.linkworks.oa.user_defined.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.user_defined.entity.base.BaseNetMaintain;
@Entity
@Table(name = "U_MAINTAIN", schema = "")
public class NetMaintain extends BaseNetMaintain{
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
