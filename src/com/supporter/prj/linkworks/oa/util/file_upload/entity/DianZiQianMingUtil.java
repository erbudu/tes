package com.supporter.prj.linkworks.oa.util.file_upload.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FupUpload entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SFSYS_EMP_SIGNATURE", schema = "")
public class DianZiQianMingUtil implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long signtureId;
	private Long empId;
	private String fielType;
	private String flag;

	// Constructors

	/** default constructor */
	public DianZiQianMingUtil() {
	}
	public DianZiQianMingUtil(Long signtureId, Long empId, String fielType,
			String flag) {
		super();
		this.signtureId = signtureId;
		this.empId = empId;
		this.fielType = fielType;
		this.flag = flag;
	}
	@Id
	@Column(name = "SIGNATURE_ID", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getSigntureId() {
		return signtureId;
	}
	public void setSigntureId(Long signtureId) {
		this.signtureId = signtureId;
	}
	@Column(name = "EMP_ID", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	@Column(name = "FILE_TYPE", length = 128)
	public String getFielType() {
		return fielType;
	}
	public void setFielType(String fielType) {
		this.fielType = fielType;
	}
	@Column(name = "FLAG", length = 128)
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}