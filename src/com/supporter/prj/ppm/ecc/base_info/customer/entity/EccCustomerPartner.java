package com.supporter.prj.ppm.ecc.base_info.customer.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.ecc.base_info.customer.entity.base.BaseEccCustomerPartner;

/**
 * @Title: Entity
 * @Description: 出口管制客户的合伙人.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_CUSTOMER_PARTNER", schema = "")
public class EccCustomerPartner extends BaseEccCustomerPartner implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccCustomerPartner() {
		super();
	}

	/**
	 * 构造函数.
	 * @param customerId
	 */
	public EccCustomerPartner(String customerId) {
		super(customerId);
	}

}
