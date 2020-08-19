package com.supporter.prj.ppm.ecc.base_info.partner.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.ecc.base_info.partner.entity.base.BaseEccPartnerP;

/**
 * @Title: Entity
 * @Description: 出口管制项目合伙人合作伙伴.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_PARTNER_P", schema = "")
public class EccPartnerP extends BaseEccPartnerP implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccPartnerP() {
		super();
	}

	/**
	 * 构造函数.
	 * @param partnerPId
	 */
	public EccPartnerP(String partnerPId) {
		super(partnerPId);
	}

}
