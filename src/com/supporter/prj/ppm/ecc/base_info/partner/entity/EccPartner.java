package com.supporter.prj.ppm.ecc.base_info.partner.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.ecc.base_info.partner.entity.base.BaseEccPartner;

/**
 * @Title: Entity
 * @Description: 出口管制项目合作伙伴.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_PARTNER", schema = "")
public class EccPartner extends BaseEccPartner implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccPartner() {
		super();
	}

	/**
	 * 构造函数.
	 * @param partnerId
	 */
	public EccPartner(String partnerId) {
		super(partnerId);
	}

	private EccPartnerFso fso;
	private EccPartnerP partner;
	private EccPartnerWarn warn;
	@Transient
	public EccPartnerFso getFso() {
		return fso;
	}

	public void setFso(EccPartnerFso fso) {
		this.fso = fso;
	}
	@Transient
	public EccPartnerP getPartner() {
		return partner;
	}

	public void setPartner(EccPartnerP partner) {
		this.partner = partner;
	}
	@Transient
	public EccPartnerWarn getWarn() {
		return warn;
	}

	public void setWarn(EccPartnerWarn warn) {
		this.warn = warn;
	}
}
