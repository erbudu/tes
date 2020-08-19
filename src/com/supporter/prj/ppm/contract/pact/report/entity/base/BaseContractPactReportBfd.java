package com.supporter.prj.ppm.contract.pact.report.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseContractPactReportBfd implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String bfdId; // 主键ID
	private String reportId; // 报审主键ID
	private String fileTypeId; // 文件类型ID
	private String fileTypeName; // 文件类型名称

	/**
	 * 无参构造
	 */
	public BaseContractPactReportBfd() {

	}

	/**
	 * 构造函数
	 */
	public BaseContractPactReportBfd(String bfdId) {
		this.bfdId = bfdId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactReportBfd(String bfdId, String reportId, String fileTypeId, String fileTypeName) {
		this.bfdId = bfdId;
		this.reportId = reportId;
		this.fileTypeId = fileTypeId;
		this.fileTypeName = fileTypeName;
	}

	@Id
	@Column(name = "bfd_id", nullable = false, length = 32)
	public String getBfdId() {
		return bfdId;
	}

	public void setBfdId(String bfdId) {
		this.bfdId = bfdId;
	}

	@Column(name = "report_id", nullable = true, length = 32)
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	@Column(name = "file_type_id", nullable = true, length = 32)
	public String getFileTypeId() {
		return fileTypeId;
	}

	public void setFileTypeId(String fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	@Column(name = "file_type_name", nullable = true, length = 256)
	public String getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

}
