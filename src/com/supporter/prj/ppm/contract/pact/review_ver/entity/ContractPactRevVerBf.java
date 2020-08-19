package com.supporter.prj.ppm.contract.pact.review_ver.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.pact.review_ver.entity.base.BaseContractPactRevVerBf;

@Entity
@Table(name = "PPM_CONTRACT_PACT_REV_VER_BF", schema = "")
public class ContractPactRevVerBf extends BaseContractPactRevVerBf implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractPactRevVerBf() {
		super();
	}

	/**
	 * 构造函数.
	 * @param bfdId
	 */
	public ContractPactRevVerBf(String bfdId) {
		super(bfdId);
	}

	/**
	 * 全参构造
	 */
	public ContractPactRevVerBf(String bfdId, String revVerId, String fileTypeId, String fileTypeName) {
		super(bfdId, revVerId, fileTypeId, fileTypeName);
	}

}
