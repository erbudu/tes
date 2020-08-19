package com.supporter.prj.ppm.contract.sign.filing.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.sign.filing.entity.base.BaseContractFilingBfd;

/**
 * @Title: Entity
 * @Description: PPM_CONTRACT_FILING_BFD.
 * @author: YAN
 * @date: 2019-09-17
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_FILING_BFD", schema = "")
public class ContractFilingBfd extends BaseContractFilingBfd implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractFilingBfd() {
		super();
	}

	/**
	 * 构造函数.
	 * @param bfdId
	 */
	public ContractFilingBfd(String bfdId) {
		super(bfdId);
	}

}
