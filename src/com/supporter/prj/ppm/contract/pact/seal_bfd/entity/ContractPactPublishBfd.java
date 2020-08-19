package com.supporter.prj.ppm.contract.pact.seal_bfd.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.pact.seal_bfd.entity.base.BaseContractPactPublishBfd;

@Entity
@Table(name = "PPM_CONTRACT_PACT_PUBLISH_BFD", schema = "")
public class ContractPactPublishBfd extends BaseContractPactPublishBfd implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractPactPublishBfd() {
		super();
	}

	/**
	 * 构造函数.
	 */
	public ContractPactPublishBfd(String bfdId) {
		super(bfdId);
	}

	/**
	 * 全参构造
	 */
	public ContractPactPublishBfd(String bfdId, String publishId, String fileTypeId, String fileTypeName) {
		super(bfdId, publishId, fileTypeId, fileTypeName);
	}

}
