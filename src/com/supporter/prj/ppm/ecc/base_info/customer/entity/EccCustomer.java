package com.supporter.prj.ppm.ecc.base_info.customer.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.ecc.base_info.customer.entity.base.BaseEccCustomer;

/**
 * @Title: Entity
 * @Description: 出口管制客户.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_CUSTOMER", schema = "")
public class EccCustomer extends BaseEccCustomer implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccCustomer() {
		super();
	}

	/**
	 * 构造函数.
	 * @param customerId
	 */
	public EccCustomer(String customerId) {
		super(customerId);
	}
	//客户办事处信息
	private EccCustomerFso fso;
	//客户合作伙伴
	private EccCustomerPartner partner;
	//客户警告
	private EccCustomerWarn warn;
	@Transient
	public EccCustomerFso getFso() {
		return fso;
	}

	public void setFso(EccCustomerFso fso) {
		this.fso = fso;
	}
	@Transient
	public EccCustomerPartner getPartner() {
		return partner;
	}

	public void setPartner(EccCustomerPartner partner) {
		this.partner = partner;
	}
	@Transient
	public EccCustomerWarn getWarn() {
		return warn;
	}

	public void setWarn(EccCustomerWarn warn) {
		this.warn = warn;
	}
}
