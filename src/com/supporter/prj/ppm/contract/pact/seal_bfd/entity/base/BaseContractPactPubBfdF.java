package com.supporter.prj.ppm.contract.pact.seal_bfd.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseContractPactPubBfdF implements Serializable {

	private static final long serialVersionUID = 1L;
	private String recordId; // 主键ID
	private String bfdId; // 协议出版资料清单主键
	private String publishId; // 协议出版主键
	private String fuFileId; // 实际附件上传组件的文件ID
	private int isUseSeal; // 是否需要用印
	private int displayOrder; // 同一文件类型的排序号

	/**
	 * 无参构造.
	 */
	public BaseContractPactPubBfdF() {
	}

	/**
	 * 构造函数.
	 * @param recordId
	 */
	public BaseContractPactPubBfdF(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactPubBfdF(String recordId, String bfdId, String publishId, String fuFileId, int isUseSeal, int displayOrder) {
		this.recordId = recordId;
		this.bfdId = bfdId;
		this.publishId = publishId;
		this.fuFileId = fuFileId;
		this.isUseSeal = isUseSeal;
		this.displayOrder = displayOrder;
	}

	@Id
	@Column(name = "RECORD_ID", nullable = false, length = 32)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "BFD_ID", nullable = true, length = 32)
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

	@Column(name = "FU_FILE_ID", nullable = true, length = 32)
	public String getFuFileId() {
		return this.fuFileId;
	}

	public void setFuFileId(String fuFileId) {
		this.fuFileId = fuFileId;
	}

	@Column(name = "IS_USE_SEAL", nullable = true, precision = 10)
	public int getIsUseSeal() {
		return this.isUseSeal;
	}

	public void setIsUseSeal(int isUseSeal) {
		this.isUseSeal = isUseSeal;
	}

	@Column(name = "DISPLAY_ORDER", nullable = true, precision = 10)
	public int getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

}
