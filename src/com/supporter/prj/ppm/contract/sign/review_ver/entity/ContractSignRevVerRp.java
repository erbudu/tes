package com.supporter.prj.ppm.contract.sign.review_ver.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.sign.review_ver.entity.base.BaseContractSignRevVerRp;

/**
 * @Title: Entity
 * @Description: 验证评审要点表.
 * @author: YAN
 * @date: 2019-09-09
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_SIGN_REV_VER_RP", schema = "")
public class ContractSignRevVerRp extends BaseContractSignRevVerRp implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractSignRevVerRp() {
		super();
	}

	/**
	 * 构造函数.
	 * @param rpId
	 */
	public ContractSignRevVerRp(String rpId) {
		super(rpId);
	}

}
