package com.supporter.prj.ppm.contract.pact.review_ver.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.pact.review_ver.entity.base.BaseContractPactRevVerF;

@Entity
@Table(name = "PPM_CONTRACT_PACT_REV_VER_F", schema = "")
public class ContractPactRevVerF extends BaseContractPactRevVerF implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractPactRevVerF() {
		super();
	}

	/**
	 * 构造函数.
	 * @param bfdId
	 */
	public ContractPactRevVerF(String bfdId) {
		super(bfdId);
	}

	/**
	 * 全参构造
	 */
	public ContractPactRevVerF(String recordId, String bfdId, String revVerId, String fuFileId, int isUseSeal, int displayOrder) {
		super(recordId, bfdId, revVerId, fuFileId, isUseSeal, displayOrder);
	}

}
