package com.supporter.prj.linkworks.oa.signed_report.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * OaSignedReportContent entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseSignedReportContent implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String signedReportId;
	private String signedReportContent;

	public BaseSignedReportContent() {
	}

	public BaseSignedReportContent(String signedReportContent) {
		this.signedReportContent = signedReportContent;
	}

	@Id
	@Column(name = "SIGNED_REPORT_ID", unique = true, nullable = false, length = 32)
	public String getSignedReportId() {
		return this.signedReportId;
	}

	public void setSignedReportId(String signedReportId) {
		this.signedReportId = signedReportId;
	}

	@Column(name = "SIGNED_REPORT_CONTENT")
	public String getSignedReportContent() {
		return this.signedReportContent;
	}

	public void setSignedReportContent(String signedReportContent) {
		this.signedReportContent = signedReportContent;
	}
}