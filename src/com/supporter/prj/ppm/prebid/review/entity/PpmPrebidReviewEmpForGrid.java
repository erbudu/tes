package com.supporter.prj.ppm.prebid.review.entity;

public class PpmPrebidReviewEmpForGrid {

	private String reviewEmpId; // 评审人员主键Id
	private String rpId;//评审要点主键ID
	private String personId;// 评审人ID
	private String personName;// 评审人姓名
	private String reviewRoleId;// 评审角色ID
	private String reviewPointsId;// 评审要点ID
	private String reviewPoint; // 评审要点
	
	public String getReviewEmpId() {
		return reviewEmpId;
	}
	public void setReviewEmpId(String reviewEmpId) {
		this.reviewEmpId = reviewEmpId;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getReviewRoleId() {
		return reviewRoleId;
	}
	public void setReviewRoleId(String reviewRoleId) {
		this.reviewRoleId = reviewRoleId;
	}
	public String getReviewPointsId() {
		return reviewPointsId;
	}
	public void setReviewPointsId(String reviewPointsId) {
		this.reviewPointsId = reviewPointsId;
	}
	public String getReviewPoint() {
		return reviewPoint;
	}
	public void setReviewPoint(String reviewPoint) {
		this.reviewPoint = reviewPoint;
	}
	public String getRpId() {
		return rpId;
	}
	public void setRpId(String rpId) {
		this.rpId = rpId;
	}
	
	
}
