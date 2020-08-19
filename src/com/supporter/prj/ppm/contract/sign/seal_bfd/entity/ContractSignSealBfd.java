package com.supporter.prj.ppm.contract.sign.seal_bfd.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.sign.seal_bfd.entity.base.BaseContractSignSealBfd;

/**
 * @Title: Entity
 * @Description: 主合同签约出版资料清单.
 * @author: YAN
 * @date: 2019-09-10
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_SIGN_SEAL_BFD", schema = "")
public class ContractSignSealBfd extends BaseContractSignSealBfd implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignSealBfd() {
		super();
	}

	/**
	 * 构造函数.
	 * @param bfdId
	 */
	public ContractSignSealBfd(String bfdId) {
		super(bfdId);
	}

}
