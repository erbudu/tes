package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.entity.base.PcPreDevelopProgress;
@Entity
@Table(name = "PC_PRE_DEVELOP_PROGRESS", schema = "")
public class DevelopProgress extends PcPreDevelopProgress{

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
	
	private List<DevelopProgressAmount> recList;
	
	private String delRec;
	@Transient
	public List<DevelopProgressAmount> getRecList() {
		return recList;
	}

	public void setRecList(List<DevelopProgressAmount> recList) {
		this.recList = recList;
	}
	@Transient
	public String getDelRec() {
		return delRec;
	}

	public void setDelRec(String delRec) {
		this.delRec = delRec;
	}

}
