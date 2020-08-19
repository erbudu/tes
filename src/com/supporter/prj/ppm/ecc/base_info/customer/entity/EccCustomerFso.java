package com.supporter.prj.ppm.ecc.base_info.customer.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.ecc.base_info.customer.entity.base.BaseEccCustomerFso;

/**
 * @Title: Entity
 * @Description: 出口管制客户办事处.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_CUSTOMER_FSO", schema = "")
public class EccCustomerFso extends BaseEccCustomerFso implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccCustomerFso() {
		super();
	}

	/**
	 * 构造函数.
	 * @param fsoId
	 */
	public EccCustomerFso(String fsoId) {
		super(fsoId);
	}

}
