package com.supporter.prj.ud.func.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ud.func.entity.base.BaseUdFuncPage;
@Entity
@Table(name = "UD_FUNC_PAGE", schema = "")
public class UdFuncPage extends BaseUdFuncPage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean add;
	private List<UdFuncPageCell> list;
	@Transient
	public List<UdFuncPageCell> getList() {
		return list;
	}
	public void setList(List<UdFuncPageCell> list) {
		this.list = list;
	}
	@Transient
	public boolean getAdd() {
		return add;
	}
	public void setAdd(boolean add) {
		this.add = add;
	}
	
}
