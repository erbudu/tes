package com.supporter.prj.pm.drawing_library.entity.base;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseDrawingLibraryVersion implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	// primary key
	private java.lang.String versionId;//主键
	private java.lang.String libraryId;
	private String versionRule;
	private String versionNo;
	private String drawingNo;
	private String drawingName;
	private java.lang.Integer checkStatus;
	private java.util.Date checkDate;
	private java.lang.String createdId;
	private java.lang.String createdName;
	private java.util.Date createdDate;
	private java.util.Date submitDate;
	private java.util.Date noticeDate;
	private java.lang.String filePath;
	private java.lang.String scanId;
	private java.lang.String scanName;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "version_id", unique = true, nullable = false, length = 32)
	public java.lang.String getVersionId() {
		return versionId;
	}

	public void setVersionId(java.lang.String versionId) {
		this.versionId = versionId;
	}
	
	@Column(name = "library_id", length = 32)
	public java.lang.String getLibraryId() {
		return libraryId;
	}

	public void setLibraryId(java.lang.String libraryId) {
		this.libraryId = libraryId;
	}
	
	@Column(name = "version_rule", length = 6)
	public String getVersionRule() {
		return versionRule;
	}

	public void setVersionRule(String versionRule) {
		this.versionRule = versionRule;
	}
	
	@Column(name = "version_no", length = 6)
	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	
	@Column(name = "drawing_no", length = 64)
	public String getDrawingNo() {
		return drawingNo;
	}

	public void setDrawingNo(String drawingNo) {
		this.drawingNo = drawingNo;
	}
	
	@Column(name = "drawing_name", length = 64)
	public String getDrawingName() {
		return drawingName;
	}

	public void setDrawingName(String drawingName) {
		this.drawingName = drawingName;
	}

	@Column(name = "created_id", length = 32)
	public java.lang.String getCreatedId() {
		return createdId;
	}

	public void setCreatedId(java.lang.String createdId) {
		this.createdId = createdId;
	}
	
	@Column(name = "created_name", length = 32)
	public java.lang.String getCreatedName() {
		return createdName;
	}

	public void setCreatedName(java.lang.String createdName) {
		this.createdName = createdName;
	}
	
	@Column(name = "created_date", length = 7)
	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "check_status", length = 11)
	public java.lang.Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(java.lang.Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	@Column(name = "check_date", length = 7)
	public java.util.Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(java.util.Date checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name = "file_path", length = 32)
	public java.lang.String getFilePath() {
		return filePath;
	}

	public void setFilePath(java.lang.String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "scan_id", length = 32)
	public java.lang.String getScanId() {
		return scanId;
	}

	public void setScanId(java.lang.String scanId) {
		this.scanId = scanId;
	}

	@Column(name = "scan_name", length = 32)
	public java.lang.String getScanName() {
		return scanName;
	}

	public void setScanName(java.lang.String scanName) {
		this.scanName = scanName;
	}
	@Column(name = "submit_date", length = 7)
	public java.util.Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(java.util.Date submitDate) {
		this.submitDate = submitDate;
	}
	@Column(name = "notice_date", length = 7)
	public java.util.Date getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(java.util.Date noticeDate) {
		this.noticeDate = noticeDate;
	}
	
}
