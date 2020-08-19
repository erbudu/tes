package com.supporter.prj.ppm.prebid.review.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author 王康
 *
 * PPM_EVALUATION_REPORTING  entity
 */
@MappedSuperclass
public class BasePpmPrebidReviewEmp  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
    private String reviewEmpId;
    private String prebidReviewId;
    private String prjId;
    private String reviewRoleId;
    private String reviewRole;
    private String personId;
    private String personName;

	@Id
	@Column(name = "REVIEW_EMP_ID", unique = true, nullable = false, length = 32)
	public String getReviewEmpId() {
		return reviewEmpId;
	}
	public void setReviewEmpId(String reviewEmpId) {
		this.reviewEmpId = reviewEmpId;
	}
	@Column(name = "PREBID_REVIEW_ID", length = 32)
	public String getPrebidReviewId() {
		return prebidReviewId;
	}
	public void setPrebidReviewId(String prebidReviewId) {
		this.prebidReviewId = prebidReviewId;
	}
	@Column(name = "PRJ_ID", length = 32)
	public String getPrjId() {
		return prjId;
	}
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}
	@Column(name = "REVIEW_ROLE_ID", length = 32)
	public String getReviewRoleId() {
		return reviewRoleId;
	}
	public void setReviewRoleId(String reviewRoleId) {
		this.reviewRoleId = reviewRoleId;
	}
	@Column(name = "REVIEW_ROLE", length = 128)
	public String getReviewRole() {
		return reviewRole;
	}
	public void setReviewRole(String reviewRole) {
		this.reviewRole = reviewRole;
	}
	@Column(name = "PERSON_ID", length = 32)
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	@Column(name = "PERSON_NAME", length = 128)
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	@Override
	public String toString() {
		return "BasePpmPrebidReviewEmp [reviewEmpId=" + reviewEmpId
				+ ", prebidReviewId=" + prebidReviewId + ", prjId=" + prjId
				+ ", reviewRoleId=" + reviewRoleId + ", reviewRole="
				+ reviewRole + ", personId=" + personId + ", personName="
				+ personName + "]";
	}

    
}
