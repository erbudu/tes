package com.supporter.prj.ppm.ecc.wg_review.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.ecc.wg_review.entity.base.BaseEccWgRevieCon;

/**
 * @Title: Entity
 * @Description: 出口管制工作组评审结论.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_WG_REVIE_CON", schema = "")
public class EccWgRevieCon extends BaseEccWgRevieCon implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccWgRevieCon() {
		super();
	}

	/**
	 * 构造函数.
	 * @param wgEccRvConId
	 */
	public EccWgRevieCon(String wgEccRvConId) {
		super(wgEccRvConId);
	}

}
