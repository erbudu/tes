package com.supporter.prj.cneec.emp_mapping.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEmpMapping implements java.io.Serializable {
	// Fields

	private static final long serialVersionUID = 1L;
	private String eip6EmpId;
	private Long empId;

	// Constructors

	/** default constructor */
	public BaseEmpMapping() {
	}

	/** minimal constructor */
	public BaseEmpMapping(String creditLetterId) {
		this.eip6EmpId = eip6EmpId;
	}

	/** full constructor */
	public BaseEmpMapping(String eip6EmpId, Long empId) {
		this.eip6EmpId = eip6EmpId;
		this.empId = empId;
	}

	// Property accessors
	@Id
	@Column(name = "EIP6_EMP_ID", unique = true, nullable = false, length = 32)
	public String getEip6EmpId() {
		return this.eip6EmpId;
	}

	public void setEip6EmpId(String eip6EmpId) {
		this.eip6EmpId = eip6EmpId;
	}

	@Column(name = "EMP_ID", precision = 22, scale = 0)
	public Long getEmpId() {
		return this.empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}
}
