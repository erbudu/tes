package com.supporter.prj.ppm.contract.pact.seal_bfd.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseContractPactPublishBfd implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bfdId; // 主键ID
	private String publishId; // 协议出版主键
	private String fileTypeId; // 文件类型ID
	private String fileTypeName; // 文件类型名称

	/**
	 * 无参构造.
	 */
	public BaseContractPactPublishBfd() {
	}

	/**
	 * 构造函数.
	 * @param bfdId
	 */
	public BaseContractPactPublishBfd(String bfdId) {
		this.bfdId = bfdId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactPublishBfd(String bfdId, String publishId, String fileTypeId, String fileTypeName) {
		this.bfdId = bfdId;
		this.publishId = publishId;
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

	@Column(name = "PUBLISH_ID", nullable = true, length = 32)
	public String getPublishId() {
		return this.publishId;
	}

	public void setPublishId(String publishId) {
		this.publishId = publishId;
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
