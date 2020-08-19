package com.supporter.prj.ppm.ecc.dept_review.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.ecc.dept_review.entity.base.BaseEccDeptRevieCon;

/**
 * @Title: Entity
 * @Description: 部门出口管制结论.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC_DEPT_REVIE_CON", schema = "")
public class EccDeptRevieCon extends BaseEccDeptRevieCon implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public EccDeptRevieCon() {
		super();
	}

	/**
	 * 构造函数.
	 * @param deptEccRvConId
	 */
	public EccDeptRevieCon(String deptEccRvConId) {
		super(deptEccRvConId);
	}

	public static int needNext = 1; //需要进一步审核 到CMEC
	public static int notNeedNext = 2; //不需要进一步审核 到出口管制委员会
	public static int pass = 1; //审核通过
	public static int noPass = 2; //审核不通过



}
