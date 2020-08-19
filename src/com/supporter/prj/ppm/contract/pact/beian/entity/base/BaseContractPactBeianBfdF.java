package com.supporter.prj.ppm.contract.pact.beian.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseContractPactBeianBfdF implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String bfdId;
	private String recordId;
	private String fuFileId;
	private int isUseSeal;
	private int displayOrder;

	/**
	 * 无参构造.
	 */
	public BaseContractPactBeianBfdF() {
	}

	/**
	 * 构造函数.
	 * @param bfdId
	 */
	public BaseContractPactBeianBfdF(String bfdId) {
		this.bfdId = bfdId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactBeianBfdF(String id, String bfdId, String recordId, String fuFileId, int isUseSeal, int displayOrder) {
		this.id = id;
		this.bfdId = bfdId;
		this.recordId = recordId;
		this.fuFileId = fuFileId;
		this.isUseSeal = isUseSeal;
		this.displayOrder = displayOrder;
	}

	@Id
	@Column(name = "ID", nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "BFD_ID", nullable = true, length = 32)
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
