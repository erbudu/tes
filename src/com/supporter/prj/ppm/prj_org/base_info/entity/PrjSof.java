package com.supporter.prj.ppm.prj_org.base_info.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.prj_org.base_info.entity.base.BasePrjSof;

@Entity
@Table(name = "PPM_PRJ_SOF", schema = "")
public class PrjSof extends BasePrjSof{

	private static final long serialVersionUID = 1L;

	public PrjSof() {
		super();
	}
	
}
