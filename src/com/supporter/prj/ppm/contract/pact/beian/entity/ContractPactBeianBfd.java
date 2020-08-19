package com.supporter.prj.ppm.contract.pact.beian.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.pact.beian.entity.base.BaseContractPactBeianBfd;

@Entity
@Table(name = "PPM_CONTRACT_PACT_RECORD_BFD", schema = "")
public class ContractPactBeianBfd extends BaseContractPactBeianBfd implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractPactBeianBfd() {
		super();
	}

	/**
	 * 构造函数.
	 * @param bfdId
	 */
	public ContractPactBeianBfd(String bfdId) {
		super(bfdId);
	}

	/**
	 * 全参构造
	 */
	public ContractPactBeianBfd(String bfdId, String recordId, String fileTypeId, String fileTypeName) {
		super(bfdId, recordId, fileTypeId, fileTypeName);
	}

}
