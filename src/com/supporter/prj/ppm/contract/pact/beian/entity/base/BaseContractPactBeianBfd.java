package com.supporter.prj.ppm.contract.pact.beian.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseContractPactBeianBfd implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bfdId;
	private String recordId;
	private String fileTypeId;
	private String fileTypeName;

	/**
	 * 无参构造
	 */
	public BaseContractPactBeianBfd() {
	}

	/**
	 * 构造函数.
	 * @param bfdId
	 */
	public BaseContractPactBeianBfd(String bfdId) {
		this.bfdId = bfdId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactBeianBfd(String bfdId, String recordId, String fileTypeId, String fileTypeName) {
		this.bfdId = bfdId;
		this.recordId = recordId;
		this.fileTypeId = fileTypeId;
		this.fileTypeName = fileTypeName;
	}

	@Id
	@Column(name = "BFD_ID", nullable = false, length = 32)
	public String getBfdId() {
		return this.bfdId;
	}

	public void setBfdId(String bfdId) {
		this.bfdId = bfdId;
	}

	@Column(name = "RECORD_ID", nullable = true, length = 32)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "FILE_TYPE_ID", nullable = true, length = 32)
	public String getFileTypeId() {
		return this.fileTypeId;
	}

	public void setFileTypeId(String fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	@Column(name = "FILE_TYPE_NAME", nullable = true, length = 256)
	public String getFileTypeName() {
		return this.fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

}
