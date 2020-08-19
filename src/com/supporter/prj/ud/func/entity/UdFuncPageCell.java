package com.supporter.prj.ud.func.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ud.func.entity.base.BaseUdFuncPageCell;

@Entity
@Table(name = "UD_FUNC_PAGE_CELL", schema = "")
public class UdFuncPageCell extends BaseUdFuncPageCell{

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
