package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_dept_adjust.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_dept_adjust.entity.base.PcPreDevelopDeptAdjust;
@Entity
@Table(name = "PC_PRE_DEVELOP_DEPT_ADJUST", schema = "")
public class DevelopDeptAdjust extends PcPreDevelopDeptAdjust{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean add;
	private String keyWords;
	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}
	@Transient
	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

}
