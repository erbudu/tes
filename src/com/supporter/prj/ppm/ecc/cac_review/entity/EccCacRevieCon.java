package com.supporter.prj.ppm.ecc.cac_review.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.ecc.cac_review.entity.base.BaseEccCacRevieCon;

/**
 * @Title: Entity
 * @Description: 出口管制委员会评审结论.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_CAC_REVIE_CON", schema = "")
public class EccCacRevieCon extends BaseEccCacRevieCon implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccCacRevieCon() {
		super();
	}

	/**
	 * 构造函数.
	 * @param cacEccRvConId
	 */
	public EccCacRevieCon(String cacEccRvConId) {
		super(cacEccRvConId);
	}

	public static int IN_CMEC = 1;//进入CMEC审核
	public static int NO_CMEC = 2;//不进入CMEC审核

	public static int BUSINESS_SURE = 1;//结果已确认
	public static int BUSINESS_NO_SURE = 0;//结果未确认

	public static int STATUS_PASS = 1;//审核通过
	public static int STATUS_NO_PASS = 2;//审核不通过
}
