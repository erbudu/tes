package com.supporter.prj.ppm.contract.pact.review_ver.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseContractPactRevVerF implements Serializable {

	private static final long serialVersionUID = 1L;
	private String recordId;
	private String bfdId;
	private String revVerId;
	private String fuFileId;
	private int isUseSeal;
	private int displayOrder;

	/**
	 * 无参构造
	 */
	public BaseContractPactRevVerF() {
	}

	/**
	 * 构造函数.
	 * @param bfdId
	 */
	public BaseContractPactRevVerF(String bfdId) {
		this.bfdId = bfdId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactRevVerF(String recordId, String bfdId, String revVerId, String fuFileId, int isUseSeal, int displayOrder) {
		this.recordId = recordId;
		this.bfdId = bfdId;
		this.revVerId = revVerId;
		this.fuFileId = fuFileId;
		this.isUseSeal = isUseSeal;
		this.displayOrder = displayOrder;
	}

	@Column(name = "RECORD_ID", nullable = true, length = 32)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Id
	@Column(name = "BFD_ID", nullable = false, length = 32)
	public String getBfdId() {
		return this.bfdId;
	}

	public void setBfdId(String bfdId) {
		this.bfdId = bfdId;
	}

	@Column(name = "REV_VER_ID", nullable = true, length = 32)
	public String getRevVerId() {
		return this.revVerId;
	}

	public void setRevVerId(String revVerId) {
		this.revVerId = revVerId;
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
