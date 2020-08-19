package com.supporter.prj.ppm.contract.sign.report.entity.base;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 报审审核结果,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-05
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractSignReportCon implements Serializable {

	private static final long serialVersionUID = 1L;
	private String contractSignRvConId;
	private String contractSignId;
	private int rvConStatus;
	private int rvConBussinesStatus;

	/**
	 * 无参构造函数.
	 */
	public BaseContractSignReportCon() {
	}

	/**
	 * 构造函数.
	 *
	 * @param contractSignRvConId
	 */
	public BaseContractSignReportCon(String contractSignRvConId) {
		this.contractSignRvConId = contractSignRvConId;
	}

	/**
	 * 方法: 取得CONTRACT_SIGN_RV_CON_ID.
	 * @return: java.lang.String CONTRACT_SIGN_RV_CON_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "CONTRACT_SIGN_RV_CON_ID", nullable = false, length = 32)
	public String getContractSignRvConId() {
		return this.contractSignRvConId;
	}

	/**
	 * 方法: 设置CONTRACT_SIGN_RV_CON_ID.
	 * @param: java.lang.String CONTRACT_SIGN_RV_CON_ID
	 */
	public void setContractSignRvConId(String contractSignRvConId) {
		this.contractSignRvConId = contractSignRvConId;
	}

	/**
	 * 方法: 取得CONTRACT_SIGN_ID.
	 * @return: java.lang.String CONTRACT_SIGN_ID
	 */
	@Column(name = "CONTRACT_SIGN_ID", nullable = true, length = 32)
	public String getContractSignId() {
		return this.contractSignId;
	}

	/**
	 * 方法: 设置CONTRACT_SIGN_ID.
	 * @param: java.lang.String CONTRACT_SIGN_ID
	 */
	public void setContractSignId(String contractSignId) {
		this.contractSignId = contractSignId;
	}

	/**
	 * 方法: 取得评审结论状态 1：通过 2：未通过.
	 * @return: int 评审结论状态 1：通过 2：未通过
	 */
	@Column(name = "RV_CON_STATUS", nullable = true, precision = 10)
	public int getRvConStatus() {
		return this.rvConStatus;
	}

	/**
	 * 方法: 设置评审结论状态 1：通过 2：未通过.
	 * @param: int 评审结论状态 1：通过 2：未通过
	 */
	public void setRvConStatus(int rvConStatus) {
		this.rvConStatus = rvConStatus;
	}

	/**
	 * 方法: 取得评审结论业务状态 .
	 * @return: int 评审结论业务状态 
	 */
	@Column(name = "RV_CON_BUSSINES_STATUS", nullable = true, precision = 10)
	public int getRvConBussinesStatus() {
		return this.rvConBussinesStatus;
	}

	/**
	 * 方法: 设置评审结论业务状态 .
	 * @param: int 评审结论业务状态 
	 */
	public void setRvConBussinesStatus(int rvConBussinesStatus) {
		this.rvConBussinesStatus = rvConBussinesStatus;
	}

}
