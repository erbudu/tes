package com.supporter.prj.ppm.contract.pact.review_ver.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.pact.review_ver.entity.base.BaseContractPactRevVerRp;

@Entity
@Table(name = "PPM_CONTRACT_PACT_REV_VER_RP", schema = "")
public class ContractPactRevVerRp extends BaseContractPactRevVerRp implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractPactRevVerRp() {
		super();
	}

	/**
	 * 构造函数.
	 * @param rpId
	 */
	public ContractPactRevVerRp(String rpId) {
		super(rpId);
	}

	/**
	 * 全参构造
	 */
	public ContractPactRevVerRp(String rpId, String revVerId, String revId, String prjId, String revPointsId) {
		super(rpId, revVerId, revId, prjId, revPointsId);
	}

}
