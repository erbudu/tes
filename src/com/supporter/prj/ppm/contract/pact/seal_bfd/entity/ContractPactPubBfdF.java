package com.supporter.prj.ppm.contract.pact.seal_bfd.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.pact.seal_bfd.entity.base.BaseContractPactPubBfdF;

@Entity
@Table(name = "PPM_CONTRACT_PACT_PUB_BFD_F", schema = "")
public class ContractPactPubBfdF extends BaseContractPactPubBfdF implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造
	 */
	public ContractPactPubBfdF() {
		super();
	}

	/**
	 * 构造函数.
	 */
	public ContractPactPubBfdF(String recordId) {
		super(recordId);
	}

	/**
	 * 全参构造
	 */
	public ContractPactPubBfdF(String recordId, String bfdId, String publishId, String fuFileId, int isUseSeal, int displayOrder) {
		super(recordId, bfdId, publishId, fuFileId, isUseSeal, displayOrder);
	}

}
