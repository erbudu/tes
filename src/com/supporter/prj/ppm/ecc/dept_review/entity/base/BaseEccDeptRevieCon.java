package com.supporter.prj.ppm.ecc.dept_review.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 部门出口管制结论,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccDeptRevieCon implements Serializable {

	private static final long serialVersionUID = 1L;
	private String deptEccRvConId;
	private String deptEccId;
	private String eccId;
	private int rvConStatus;
	private int rvConBussinesStatus;

	/**
	 * 无参构造函数.
	 */
	public BaseEccDeptRevieCon() {
	}

	/**
	 * 构造函数.
	 *
	 * @param deptEccRvConId
	 */
	public BaseEccDeptRevieCon(String deptEccRvConId) {
		this.deptEccRvConId = deptEccRvConId;
	}

	/**
	 * 方法: 取得DEPT_ECC_RV_CON_ID.
	 * @return: java.lang.String DEPT_ECC_RV_CON_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "DEPT_ECC_RV_CON_ID", nullable = false, length = 32)
	public String getDeptEccRvConId() {
		return this.deptEccRvConId;
	}

	/**
	 * 方法: 设置DEPT_ECC_RV_CON_ID.
	 * @param: java.lang.String DEPT_ECC_RV_CON_ID
	 */
	public void setDeptEccRvConId(String deptEccRvConId) {
		this.deptEccRvConId = deptEccRvConId;
	}

	/**
	 * 方法: 取得管制审核主键.
	 * @return: java.lang.String 管制审核主键
	 */
	@Column(name = "DEPT_ECC_ID", nullable = true, length = 32)
	public String getDeptEccId() {
		return this.deptEccId;
	}

	/**
	 * 方法: 设置管制审核主键.
	 * @param: java.lang.String 管制审核主键
	 */
	public void setDeptEccId(String deptEccId) {
		this.deptEccId = deptEccId;
	}

	/**
	 * 方法: 取得出口管制主键.
	 * @return: java.lang.String 出口管制主键
	 */
	@Column(name = "ECC_ID", nullable = true, length = 32)
	public String getEccId() {
		return this.eccId;
	}

	/**
	 * 方法: 设置出口管制主键.
	 * @param: java.lang.String 出口管制主键
	 */
	public void setEccId(String eccId) {
		this.eccId = eccId;
	}

	/**
	 * 方法: 取得评审结论状态 1通过 2未通过.
	 * @return: int 评审结论状态 1通过 2未通过
	 */
	@Column(name = "RV_CON_STATUS", nullable = true, precision = 10)
	public int getRvConStatus() {
		return this.rvConStatus;
	}

	/**
	 * 方法: 设置评审结论状态 1通过 2未通过.
	 * @param: int 评审结论状态 1通过 2未通过
	 */
	public void setRvConStatus(int rvConStatus) {
		this.rvConStatus = rvConStatus;
	}

	/**
	 * 方法: 取得p评审结论业务状态 1提交公司会商 2不提交公司会商.
	 * @return: int p评审结论业务状态 1提交公司会商 2不提交公司会商
	 */
	@Column(name = "RV_CON_BUSSINES_STATUS", nullable = true, precision = 10)
	public int getRvConBussinesStatus() {
		return this.rvConBussinesStatus;
	}

	/**
	 * 方法: 设置p评审结论业务状态 1提交公司会商 2不提交公司会商.
	 * @param: int p评审结论业务状态 1提交公司会商 2不提交公司会商
	 */
	public void setRvConBussinesStatus(int rvConBussinesStatus) {
		this.rvConBussinesStatus = rvConBussinesStatus;
	}

}
