package com.supporter.prj.ppm.prj_org.base_info.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.prj_org.base_info.entity.base.BasePrjPfi;



@Entity
@Table(name = "PPM_PRJ_PFI", schema = "")
public class PrjPfi extends BasePrjPfi {

	private static final long serialVersionUID = 1L;

	public PrjPfi() {
		super();
	}
	
	
}
