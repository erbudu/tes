package com.supporter.prj.ppm.contract.sign.filing.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.sign.filing.entity.base.BaseContractFilingBfdF;

/**
 * @Title: Entity
 * @Description: PPM_CONTRACT_FILING_BFD_F.
 * @author: YAN
 * @date: 2019-09-17
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_FILING_BFD_F", schema = "")
public class ContractFilingBfdF extends BaseContractFilingBfdF implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractFilingBfdF() {
		super();
	}

	/**
	 * 构造函数.
	 * @param recordId
	 */
	public ContractFilingBfdF(String recordId) {
		super(recordId);
	}

}
