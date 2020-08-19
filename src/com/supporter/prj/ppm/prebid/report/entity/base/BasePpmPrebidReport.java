package com.supporter.prj.ppm.prebid.report.entity.base;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author 王康
 *
 * PPM_PREBID_REPORT_报审表  entity
 */
@MappedSuperclass
public class BasePpmPrebidReport  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String prbidReportId;
	private String prjId;
	private double prjPlanAmount;
	private Date reportStratDate;
	private Date predictEndDate;
	private String linkmanName;
	private String linkmanTel;
	private String linkmanMob;
	private String linkmanFax;
	private Integer isNeedGuarantee;
	private Integer pretenderReportStatus;
	private Date createdDate;
	private String createdBy;
	private String createdId;
	private Date modifiedDate;
	private String modifiedBy;
	private String modifiedId;
	private String procId;
	

	@Id
	@Column(name = "PRBID_REPORT_ID", unique = true, nullable = false, length = 32)
	public String getPrbidReportId() {
		return prbidReportId;
	}
	public void setPrbidReportId(String prbidReportId) {
		this.prbidReportId = prbidReportId;
	}
	@Column(name = "PRJ_ID",  length = 32)
	public String getPrjId() {
		return prjId;
	}
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	@Column(name = "PRJ_PLAN_AMOUNT", nullable = true, precision = 18, scale = 5)
	//@Column(name = "PRJ_PLAN_AMOUNT",  precision = 18, scale = 5)
	public double getPrjPlanAmount() {
		return prjPlanAmount;
	}
	public void setPrjPlanAmount(double prjPlanAmount) {
		this.prjPlanAmount = prjPlanAmount;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "REPORT_STRAT_DATE",  length = 7)
	public Date getReportStratDate() {
		return reportStratDate;
	}
	public void setReportStratDate(Date reportStratDate) {
		this.reportStratDate = reportStratDate;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "PREDICT_END_DATE",  length = 7)
	public Date getPredictEndDate() {
		return predictEndDate;
	}
	public void setPredictEndDate(Date predictEndDate) {
		this.predictEndDate = predictEndDate;
	}
	@Column(name = "LINKMAN_NAME",  length = 128)
	public String getLinkmanName() {
		return linkmanName;
	}
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}
	@Column(name = "LINKMAN_TEL",  length = 128)
	public String getLinkmanTel() {
		return linkmanTel;
	}
	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel;
	}
	@Column(name = "LINKMAN_MOB",  length = 128)
	public String getLinkmanMob() {
		return linkmanMob;
	}
	public void setLinkmanMob(String linkmanMob) {
		this.linkmanMob = linkmanMob;
	}
	@Column(name = "LINKMAN_FAX",  length = 128)
	public String getLinkmanFax() {
		return linkmanFax;
	}
	public void setLinkmanFax(String linkmanFax) {
		this.linkmanFax = linkmanFax;
	}
	@Column(name = "IS_NEED_GUARANTEE",  length = 1)
	public Integer getIsNeedGuarantee() {
		return isNeedGuarantee;
	}
	public void setIsNeedGuarantee(Integer isNeedGuarantee) {
		this.isNeedGuarantee = isNeedGuarantee;
	}
	@Column(name = "PRETENDER_REPORT_STATUS",  length = 2)
	public Integer getPretenderReportStatus() {
		return pretenderReportStatus;
	}
	public void setPretenderReportStatus(Integer pretenderReportStatus) {
		this.pretenderReportStatus = pretenderReportStatus;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE",  length = 10)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name = "CREATED_BY",  length = 64)
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name = "CREATED_ID",  length = 32)
	public String getCreatedId() {
		return createdId;
	}
	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIED_DATE",  length = 10)
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Column(name = "MODIFIED_BY",  length = 64)
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@Column(name = "MODIFIED_ID",  length = 32)
	public String getModifiedId() {
		return modifiedId;
	}
	public void setModifiedId(String modifiedId) {
		this.modifiedId = modifiedId;
	}

	@Column(name = "PROC_ID",  length = 32)
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	public BasePpmPrebidReport(String prbidReportId, String prjId,
			Integer prjPlanAmount, Date reportStratDate, Date predictEndDate,
			String linkmanName, String linkmanTel, String linkmanMob,
			String linkmanFax, Integer isNeedGuarantee,
			Integer pretenderReportStatus, Date createdDate, String createdBy,
			String createdId, Date modifiedDate, String modifiedBy,
			String modifiedId, String procId) {
		super();
		this.prbidReportId = prbidReportId;
		this.prjId = prjId;
		this.prjPlanAmount = prjPlanAmount;
		this.reportStratDate = reportStratDate;
		this.predictEndDate = predictEndDate;
		this.linkmanName = linkmanName;
		this.linkmanTel = linkmanTel;
		this.linkmanMob = linkmanMob;
		this.linkmanFax = linkmanFax;
		this.isNeedGuarantee = isNeedGuarantee;
		this.pretenderReportStatus = pretenderReportStatus;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.createdId = createdId;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.modifiedId = modifiedId;
		this.procId = procId;
	}
	public BasePpmPrebidReport() {
		super();
	}
	@Override
	public String toString() {
		return "BasePpmPrebidReport [prbidReportId=" + prbidReportId
				+ ", prjId=" + prjId + ", prjPlanAmount=" + prjPlanAmount
				+ ", reportStratDate=" + reportStratDate + ", predictEndDate="
				+ predictEndDate + ", linkmanName=" + linkmanName
				+ ", linkmanTel=" + linkmanTel + ", linkmanMob=" + linkmanMob
				+ ", linkmanFax=" + linkmanFax + ", isNeedGuarantee="
				+ isNeedGuarantee + ", pretenderReportStatus="
				+ pretenderReportStatus + ", createdDate=" + createdDate
				+ ", createdBy=" + createdBy + ", createdId=" + createdId
				+ ", modifiedDate=" + modifiedDate + ", modifiedBy="
				+ modifiedBy + ", modifiedId=" + modifiedId + ", procId="
				+ procId + "]";
	}

	
	
}
