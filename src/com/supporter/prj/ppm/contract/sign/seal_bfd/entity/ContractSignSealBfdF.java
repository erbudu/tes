package com.supporter.prj.ppm.contract.sign.seal_bfd.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.sign.seal_bfd.entity.base.BaseContractSignSealBfdF;

/**
 * @Title: Entity
 * @Description: 资料清单文件.
 * @author: YAN
 * @date: 2019-09-10
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_SIGN_SEAL_BFD_F", schema = "")
public class ContractSignSealBfdF extends BaseContractSignSealBfdF implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignSealBfdF() {
		super();
	}

	/**
	 * 构造函数.
	 * @param recordId
	 */
	public ContractSignSealBfdF(String recordId) {
		super(recordId);
	}

}
