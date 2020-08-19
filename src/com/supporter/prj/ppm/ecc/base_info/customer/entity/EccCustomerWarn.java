package com.supporter.prj.ppm.ecc.base_info.customer.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.ecc.base_info.customer.entity.base.BaseEccCustomerWarn;

/**
 * @Title: Entity
 * @Description: 出口管制客户警告.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_CUSTOMER_WARN", schema = "")
public class EccCustomerWarn extends BaseEccCustomerWarn implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccCustomerWarn() {
		super();
	}

	/**
	 * 构造函数.
	 * @param warnId
	 */
	public EccCustomerWarn(String warnId) {
		super(warnId);
	}

}
