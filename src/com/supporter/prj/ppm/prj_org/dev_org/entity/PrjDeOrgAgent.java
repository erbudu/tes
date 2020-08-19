package com.supporter.prj.ppm.prj_org.dev_org.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.ppm.prj_org.dev_org.entity.base.BasePrjDeOrgAgent;



@Entity
@Table(name = "PPM_PRJ_DE_ORG_AGENT", schema = "")
public class PrjDeOrgAgent extends BasePrjDeOrgAgent {

	private static final long serialVersionUID = 1L;

	public PrjDeOrgAgent() {
		super();
	}
	
	
	private String inOutDesc;
	@Transient
	public String getInOutDesc() {
		if(StringUtils.isNotBlank(this.getInOut())) {
			if(this.getInOut().equals("out")){
				inOutDesc = "国外";
			}else{
				inOutDesc = "国内";
			}
		}
		return inOutDesc;
	}
	
}
