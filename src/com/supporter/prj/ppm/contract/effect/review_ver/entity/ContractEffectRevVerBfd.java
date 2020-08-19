package com.supporter.prj.ppm.contract.effect.review_ver.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.contract.effect.review_ver.entity.base.BaseContractEffectRevVerBfd;

/**
 * @Title: Entity
 * @Description: 验证标前评审资料清单.
 * @author: YAN
 * @date: 2019-09-09
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_EFF_REV_VER_BFD", schema = "")
public class ContractEffectRevVerBfd extends BaseContractEffectRevVerBfd implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectRevVerBfd() {
		super();
	}

	/**
	 * 构造函数.
	 * @param bfdId
	 */
	public ContractEffectRevVerBfd(String bfdId) {
		super(bfdId);
	}

}
