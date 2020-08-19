package com.supporter.prj.linkworks.oa.abroadPublicity.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.base.BaseAbroadPublicity;

@Entity
@Table(name="OA_ABROAD_PUBLICITY",schema="")
public class AbroadPublicity extends BaseAbroadPublicity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Transient
	private Abroad abroad;

	//是否提交出国报告
	@Transient
	private String isReport = "否";

	@Transient
	private String prjName;

	public String getIsReport() {
		return isReport;
	}


	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}


	public Abroad getAbroad() {
		return abroad;
	}


	public void setAbroad(Abroad abroad) {
		this.abroad = abroad;
	}


	public String getPrjName() {
		return prjName;
	}


	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}
	
	
}
