package com.supporter.prj.ppm.ecc.base_info.owner.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.ecc.base_info.owner.entity.base.BaseEccOwner;

/**
 * @Title: Entity
 * @Description: 出口管制客户.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_OWNER", schema = "")
public class EccOwner extends BaseEccOwner implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccOwner() {
		super();
	}

	/**
	 * 构造函数.
	 * @param ownerId
	 */
	public EccOwner(String ownerId) {
		super(ownerId);
	}
	private EccOwnerFso fso;
	private EccOwnerPartner partner;
	@Transient
	public EccOwnerFso getFso() {
		return fso;
	}

	public void setFso(EccOwnerFso fso) {
		this.fso = fso;
	}
	@Transient
	public EccOwnerPartner getPartner() {
		return partner;
	}

	public void setPartner(EccOwnerPartner partner) {
		this.partner = partner;
	}
}
