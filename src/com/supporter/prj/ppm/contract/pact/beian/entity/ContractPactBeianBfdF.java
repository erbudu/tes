package com.supporter.prj.ppm.contract.pact.beian.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.pact.beian.entity.base.BaseContractPactBeianBfdF;

@Entity
@Table(name = "PPM_CONTRACT_PACT_RECORD_BFD_F", schema = "")
public class ContractPactBeianBfdF extends BaseContractPactBeianBfdF implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractPactBeianBfdF() {
		super();
	}

	/**
	 * 构造函数.
	 * @param bfdId
	 */
	public ContractPactBeianBfdF(String bfdId) {
		super(bfdId);
	}

	/**
	 * 全参构造
	 */
	public ContractPactBeianBfdF(String id, String bfdId, String recordId, String fuFileId, int isUseSeal, int displayOrder) {
		super(id, bfdId, recordId, fuFileId, isUseSeal, displayOrder);
	}

}
