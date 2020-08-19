package com.supporter.prj.ppm.contract.pact.review.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseContractPactReviewEmp implements Serializable {

	private static final long serialVersionUID = 1L;

	private String reviewEmpId; // 主键id
	private String reviewId; // 评审id
	private String prjId; // 拟评估项目ID
	private String reviewRoleId; // 评审角色ID
	private String reviewRole; // 评审角色
	private String personId; // 评审人员ID
	private String personName; // 评审人员

	/**
	 * 无参构造
	 */
	public BaseContractPactReviewEmp() {
	}

	/**
	 * 构造函数
	 */
	public BaseContractPactReviewEmp(String reviewEmpId) {
		this.reviewEmpId = reviewEmpId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactReviewEmp(String reviewEmpId, String reviewId, String prjId, String reviewRoleId, String reviewRole, String personId,
			String personName) {
		this.reviewEmpId = reviewEmpId;
		this.reviewId = reviewId;
		this.prjId = prjId;
		this.reviewRoleId = reviewRoleId;
		this.reviewRole = reviewRole;
		this.personId = personId;
		this.personName = personName;
	}

	@Id
	@Column(name = "rev_emp_id", nullable = false, length = 32)
	public String getReviewEmpId() {
		return reviewEmpId;
	}

	public void setReviewEmpId(String reviewEmpId) {
		this.reviewEmpId = reviewEmpId;
	}

	@Column(name = "rev_id", nullable = true, length = 32)
	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	@Column(name = "prj_id", nullable = true, length = 32)
	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	@Column(name = "rev_role_id", nullable = true, length = 32)
	public String getReviewRoleId() {
		return reviewRoleId;
	}

	public void setReviewRoleId(String reviewRoleId) {
		this.reviewRoleId = reviewRoleId;
	}

	@Column(name = "rev_role", nullable = true, length = 128)
	public String getReviewRole() {
		return reviewRole;
	}

	public void setReviewRole(String reviewRole) {
		this.reviewRole = reviewRole;
	}

	@Column(name = "person_id", nullable = true, length = 32)
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Column(name = "person_name", nullable = true, length = 128)
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

}
