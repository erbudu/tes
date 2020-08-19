package com.supporter.prj.ppm.prj_org.dev_org.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.prj_org.dev_org.entity.base.BasePrjDeOrgDept;


@Entity
@Table(name = "PPM_PRJ_DE_ORG_DEPT", schema = "")
public class PrjDeOrgDept extends BasePrjDeOrgDept{

	private static final long serialVersionUID = 1L;

	public PrjDeOrgDept() {
		super();
	}
	
}
