package com.supporter.prj.ppm.ecc.base_info.partner.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.ecc.base_info.partner.entity.base.BaseEccPartnerWarn;

/**
 * @Title: Entity
 * @Description: 出口管制合伙人警告.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_PARTNER_WARN", schema = "")
public class EccPartnerWarn extends BaseEccPartnerWarn implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccPartnerWarn() {
		super();
	}

	/**
	 * 构造函数.
	 * @param warnId
	 */
	public EccPartnerWarn(String warnId) {
		super(warnId);
	}

}
