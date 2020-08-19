package com.supporter.prj.ppm.contract.pact.review_ver.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseContractPactRevVerCon implements Serializable {

	private static final long serialVersionUID = 1L;
	private String revConId;
	private String revVerId;
	private int revConStatus;

	/**
	 * 无参构造
	 */
	public BaseContractPactRevVerCon() {
	}

	/**
	 * 构造函数.
	 * @param revConId
	 */
	public BaseContractPactRevVerCon(String revConId) {
		this.revConId = revConId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactRevVerCon(String revConId, String revVerId, int revConStatus) {
		this.revConId = revConId;
		this.revVerId = revVerId;
		this.revConStatus = revConStatus;
	}

	@Id
	@Column(name = "REV_CON_ID", nullable = false, length = 32)
	public String getRevConId() {
		return this.revConId;
	}

	public void setRevConId(String revConId) {
		this.revConId = revConId;
	}

	@Column(name = "REV_VER_ID", nullable = true, length = 32)
	public String getRevVerId() {
		return revVerId;
	}

	public void setRevVerId(String revVerId) {
		this.revVerId = revVerId;
	}

	@Column(name = "REV_CON_STATUS", nullable = true, precision = 10)
	public int getRevConStatus() {
		return this.revConStatus;
	}

	public void setRevConStatus(int revConStatus) {
		this.revConStatus = revConStatus;
	}

}
