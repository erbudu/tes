package com.supporter.prj.ppm.contract.effect.review_ver.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.effect.review_ver.entity.base.BaseContractEffectRevVer;

/**
 * @Title: Entity
 * @Description: 评审验证表.
 * @author: YAN
 * @date: 2019-09-09
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_EFFECT_REV_VER", schema = "")
public class ContractEffectRevVer extends BaseContractEffectRevVer implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectRevVer() {
		super();
	}

	/**
	 * 构造函数.
	 * @param reviewVerId
	 */
	public ContractEffectRevVer(String reviewVerId) {
		super(reviewVerId);
	}

}
