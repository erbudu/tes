package com.supporter.prj.cneec.emp_mapping.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.emp_mapping.entity.base.BaseEmpMapping;

@Entity
@Table(name = "S_SFSYS_EMP_MAPPING", schema = "SUPP_APP")
public class EmpMapping extends BaseEmpMapping implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	// Constructors

	/** default constructor */
	public EmpMapping() {
	}

	/** minimal constructor */
	public EmpMapping(String eip6EmpId) {
		super(eip6EmpId);
	}
}
