package com.supporter.prj.ppm.contract.pact.review_ver.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseContractPactRevVerRp implements Serializable {

	private static final long serialVersionUID = 1L;
	private String rpId;
	private String revVerId;
	private String revId;
	private String prjId;
	private String revPointsId;

	/**
	 * 无参构造
	 */
	public BaseContractPactRevVerRp() {
	}

	/**
	 * 构造函数.
	 * @param rpId
	 */
	public BaseContractPactRevVerRp(String rpId) {
		this.rpId = rpId;
	}

	/**
	 * 全参构造
	 */
	public BaseContractPactRevVerRp(String rpId, String revVerId, String revId, String prjId, String revPointsId) {
		this.rpId = rpId;
		this.revVerId = revVerId;
		this.revId = revId;
		this.prjId = prjId;
		this.revPointsId = revPointsId;
	}

	@Id
	@Column(name = "RP_ID", nullable = false, length = 32)
	public String getRpId() {
		return this.rpId;
	}

	public void setRpId(String rpId) {
		this.rpId = rpId;
	}

	@Column(name = "REV_VER_ID", nullable = true, length = 32)
	public String getRevVerId() {
		return this.revVerId;
	}

	public void setRevVerId(String revVerId) {
		this.revVerId = revVerId;
	}

	@Column(name = "REV_ID", nullable = true, length = 32)
	public String getRevId() {
		return this.revId;
	}

	public void setRevId(String revId) {
		this.revId = revId;
	}

	@Column(name = "PRJ_ID", nullable = true, length = 32)
	public String getPrjId() {
		return this.prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	@Column(name = "REV_POINTS_ID", nullable = true, length = 32)
	public String getRevPointsId() {
		return this.revPointsId;
	}

	public void setRevPointsId(String revPointsId) {
		this.revPointsId = revPointsId;
	}

}
