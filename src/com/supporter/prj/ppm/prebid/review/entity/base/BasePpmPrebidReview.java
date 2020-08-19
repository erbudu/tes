package com.supporter.prj.ppm.prebid.review.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author 王康
 *
 * PPM_PREBID_REVIEW  entity
 */
@MappedSuperclass
public class BasePpmPrebidReview  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
    private String prebidReviewId;
    private String prjId;
    private String prjName;
    private String reviewTypeId;
    private String reviewType;
    private String reviewNo;
    private Integer reviewNumber;
    private String cwPersonId;
    private String cwPerson;
    private Date reviewSubmitDate;
    private Integer prebidReviewStutas;
	private Date createdDate;
	private String createdBy;
	private String createdId;
	private Date modifiedDate;
	private String modifiedBy;
	private String modifiedId;
	
	//流程ID
	private String procId;

	@Id
	@Column(name = "PREBID_REVIEW_ID", unique = true, nullable = false, length = 32)
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
	@Column(name = "PRJ_NAME", length = 128)
	public String getPrjName() {
		return prjName;
	}
	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}
	@Column(name = "REVIEW_TYPE_ID", length = 32)
	public String getReviewTypeId() {
		return reviewTypeId;
	}
	public void setReviewTypeId(String reviewTypeId) {
		this.reviewTypeId = reviewTypeId;
	}
	@Column(name = "REVIEW_TYPE", length = 128)
	public String getReviewType() {
		return reviewType;
	}
	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}
	@Column(name = "REVIEW_NO", length = 128)
	public String getReviewNo() {
		return reviewNo;
	}
	public void setReviewNo(String reviewNo) {
		this.reviewNo = reviewNo;
	}
	@Column(name = "REVIEW_NUMBER", length = 2)
	public Integer getReviewNumber() {
		return reviewNumber;
	}
	public void setReviewNumber(Integer reviewNumber) {
		this.reviewNumber = reviewNumber;
	}
	@Column(name = "CW_PERSON_ID", length = 32)
	public String getCwPersonId() {
		return cwPersonId;
	}
	public void setCwPersonId(String cwPersonId) {
		this.cwPersonId = cwPersonId;
	}
	@Column(name = "CW_PERSON", length = 128)
	public String getCwPerson() {
		return cwPerson;
	}
	public void setCwPerson(String cwPerson) {
		this.cwPerson = cwPerson;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REVIEW_SUBMIT_DATE", length = 7)
	public Date getReviewSubmitDate() {
		return reviewSubmitDate;
	}
	public void setReviewSubmitDate(Date reviewSubmitDate) {
		this.reviewSubmitDate = reviewSubmitDate;
	}
	@Column(name = "PREBID_REVIEW_STUTAS", length = 2)
	public Integer getPrebidReviewStutas() {
		return prebidReviewStutas;
	}
	public void setPrebidReviewStutas(Integer prebidReviewStutas) {
		this.prebidReviewStutas = prebidReviewStutas;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", length = 7)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name = "CREATED_BY", length = 64)
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name = "CREATED_ID", length = 32)
	public String getCreatedId() {
		return createdId;
	}
	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIED_DATE", length = 7)
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Column(name = "MODIFIED_BY", length = 64)
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@Column(name = "MODIFIED_ID", length = 32)
	public String getModifiedId() {
		return modifiedId;
	}
	public void setModifiedId(String modifiedId) {
		this.modifiedId = modifiedId;
	}
	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	@Override
	public String toString() {
		return "BasePpmPrebidReview [prebidReviewId=" + prebidReviewId
				+ ", prjId=" + prjId + ", prjName=" + prjName
				+ ", reviewTypeId=" + reviewTypeId + ", reviewType="
				+ reviewType + ", reviewNo=" + reviewNo + ", reviewNumber="
				+ reviewNumber + ", cwPersonId=" + cwPersonId + ", cwPerson="
				+ cwPerson + ", reviewSubmitDate=" + reviewSubmitDate
				+ ", prebidReviewStutas=" + prebidReviewStutas
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy
				+ ", createdId=" + createdId + ", modifiedDate=" + modifiedDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedId=" + modifiedId 
				+ ", procId=" + procId + "]";
	}


	
}
