package com.supporter.prj.ppm.contract.effect.review_ver.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.effect.review_ver.entity.base.BaseContractEffectRevVerBF;

/**
 * @Title: Entity
 * @Description: 验证标前评审资料清单单文件.
 * @author: YAN
 * @date: 2019-09-09
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_EFF_REV_VER_B_F", schema = "")
public class ContractEffectRevVerBF extends BaseContractEffectRevVerBF implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectRevVerBF() {
		super();
	}

	/**
	 * 构造函数.
	 * @param recordId
	 */
	public ContractEffectRevVerBF(String recordId) {
		super(recordId);
	}

}
