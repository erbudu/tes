package com.supporter.prj.ppm.contract.pact.report.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseContractPactReportBfdF implements Serializable {

	private static final long serialVersionUID = 1L;

	private String recordId; // 主键ID
	private String bfdId; // 报审资料清单主键
	private String reportId; // 报审主键
	private String fuFileId; // 附件上传文件ID
	private int isUseSeal; // 是否需要用印
	private int displayOrder; // 同一文件类型的排序号

	/**
	 * 无参构造
	 */
	public BaseContractPactReportBfdF() {

	}

	/**
	 * 构造函数
	 */
	public BaseContractPactReportBfdF(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactReportBfdF(String recordId, String bfdId, String reportId, String fuFileId, int isUseSeal, int displayOrder) {
		this.recordId = recordId;
		this.bfdId = bfdId;
		this.reportId = reportId;
		this.fuFileId = fuFileId;
		this.isUseSeal = isUseSeal;
		this.displayOrder = displayOrder;
	}

	@Id
	@Column(name = "record_id", nullable = false, length = 32)
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "bfd_id", nullable = true, length = 32)
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

	@Column(name = "fu_file_id", nullable = true, length = 32)
	public String getFuFileId() {
		return fuFileId;
	}

	public void setFuFileId(String fuFileId) {
		this.fuFileId = fuFileId;
	}

	@Column(name = "is_use_seal", nullable = true)
	public int getIsUseSeal() {
		return isUseSeal;
	}

	public void setIsUseSeal(int isUseSeal) {
		this.isUseSeal = isUseSeal;
	}

	@Column(name = "display_order", nullable = true)
	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

}
