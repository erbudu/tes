package com.supporter.prj.ppm.prebid.review_ver.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author 王康
 *
 * PPM_EVALUATION_REPORTING  entity
 */
@MappedSuperclass
public class BasePpmPrebidReviewVer  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

    private String reviewVerId;
    private String prebidReviewId;
    private String prebidRevConId;
    private Date reviewSubmitDate;
    private Integer result;
    private Integer stutas;
    private String reviewVerificatContent;
    private String modifyEvalPoint;
	private Date createdDate;
	private String createdBy;
	private String createdId;
	private Date modifiedDate;
	private String modifiedBy;
	private String modifiedId;
	private String prjId;
	private String procId;

	@Id
	@Column(name = "REVIEW_VER_ID", unique = true, nullable = false, length = 32)
	public String getReviewVerId() {
		return reviewVerId;
	}
	public void setReviewVerId(String reviewVerId) {
		this.reviewVerId = reviewVerId;
	}
	@Column(name = "PREBID_REVIEW_ID", length = 32)
	public String getPrebidReviewId() {
		return prebidReviewId;
	}
	public void setPrebidReviewId(String prebidReviewId) {
		this.prebidReviewId = prebidReviewId;
	}
	@Column(name = "PREBID_REV_CON_ID", length = 32)
	public String getPrebidRevConId() {
		return prebidRevConId;
	}
	public void setPrebidRevConId(String prebidRevConId) {
		this.prebidRevConId = prebidRevConId;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REVIEW_SUBMIT_DATE", length = 7)
	public Date getReviewSubmitDate() {
		return reviewSubmitDate;
	}
	public void setReviewSubmitDate(Date reviewSubmitDate) {
		this.reviewSubmitDate = reviewSubmitDate;
	}
	@Column(name = "RESULT", length = 2)
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	@Column(name = "STUTAS", length = 2)
	public Integer getStutas() {
		return stutas;
	}
	public void setStutas(Integer stutas) {
		this.stutas = stutas;
	}
	@Column(name = "REVIEW_VERIFICAT_CONTENT", length = 512)
	public String getReviewVerificatContent() {
		return reviewVerificatContent;
	}
	public void setReviewVerificatContent(String reviewVerificatContent) {
		this.reviewVerificatContent = reviewVerificatContent;
	}
	@Column(name = "MODIFY_EVAL_POINT", length = 512)
	public String getModifyEvalPoint() {
		return modifyEvalPoint;
	}
	public void setModifyEvalPoint(String modifyEvalPoint) {
		this.modifyEvalPoint = modifyEvalPoint;
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
	@Column(name = "PRJ_ID", length = 32)
	public String getPrjId() {
		return prjId;
	}
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}
	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	
	
}
