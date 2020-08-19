package com.supporter.prj.ppm.ecc.base_info.product.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.ecc.base_info.product.entity.base.BaseEccProduct;

/**
 * @Title: Entity
 * @Description: 出口管制产品.
 * @author: YAN
 * @date: 2019-08-19
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_PRODUCT", schema = "")
public class EccProduct extends BaseEccProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccProduct() {
		super();
	}

	/**
	 * 构造函数.
	 * @param productId
	 */
	public EccProduct(String productId) {
		super(productId);
	}

}
