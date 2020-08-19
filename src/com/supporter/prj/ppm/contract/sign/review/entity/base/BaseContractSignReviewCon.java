package com.supporter.prj.ppm.contract.sign.review.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 评审结果,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-06
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractSignReviewCon implements Serializable {

	private static final long serialVersionUID = 1L;
	private String contractSignRevConId;
	private String contractSignReviewId;
	private int rvConStatus;
	private String rvConBussinesStatus;

	/**
	 * 无参构造函数.
	 */
	public BaseContractSignReviewCon() {
	}

	/**
	 * 构造函数.
	 *
	 * @param contractSignRevConId
	 */
	public BaseContractSignReviewCon(String contractSignRevConId) {
		this.contractSignRevConId = contractSignRevConId;
	}

	/**
	 * 方法: 取得CONTRACT_SIGN_REV_CON_ID.
	 * @return: java.lang.String CONTRACT_SIGN_REV_CON_ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "CONTRACT_SIGN_REV_CON_ID", nullable = false, length = 32)
	public String getContractSignRevConId() {
		return this.contractSignRevConId;
	}

	/**
	 * 方法: 设置CONTRACT_SIGN_REV_CON_ID.
	 * @param: java.lang.String CONTRACT_SIGN_REV_CON_ID
	 */
	public void setContractSignRevConId(String contractSignRevConId) {
		this.contractSignRevConId = contractSignRevConId;
	}

	/**
	 * 方法: 取得CONTRACT_SIGN_REVIEW_ID.
	 * @return: java.lang.String CONTRACT_SIGN_REVIEW_ID
	 */
	@Column(name = "CONTRACT_SIGN_REVIEW_ID", nullable = true, length = 32)
	public String getContractSignReviewId() {
		return this.contractSignReviewId;
	}

	/**
	 * 方法: 设置CONTRACT_SIGN_REVIEW_ID.
	 * @param: java.lang.String CONTRACT_SIGN_REVIEW_ID
	 */
	public void setContractSignReviewId(String contractSignReviewId) {
		this.contractSignReviewId = contractSignReviewId;
	}

	/**
	 * 方法: 取得评审结论状态 1:通过2：有条件通过3、不通过.
	 * @return: int 评审结论状态 1:通过2：有条件通过3、不通过
	 */
	@Column(name = "RV_CON_STATUS", nullable = true, precision = 10)
	public int getRvConStatus() {
		return this.rvConStatus;
	}

	/**
	 * 方法: 设置评审结论状态 1:通过2：有条件通过3、不通过.
	 * @param: int 评审结论状态 1:通过2：有条件通过3、不通过
	 */
	public void setRvConStatus(int rvConStatus) {
		this.rvConStatus = rvConStatus;
	}

	/**
	 * 方法: 取得评审结论业务状态：补充附件资料;调整及修改评审信息
	 * @return: String 评审结论业务状态 ：补充附件资料;调整及修改评审信息
	 */
	@Column(name = "RV_CON_BUSSINES_STATUS", nullable = true, length = 128)
	public String getRvConBussinesStatus() {
		return this.rvConBussinesStatus;
	}

	/**
	 * 方法: 设置评审结论业务状态 ：补充附件资料;调整及修改评审信息
	 * @param: String 评审结论业务状态 ：补充附件资料;调整及修改评审信息
	 */
	public void setRvConBussinesStatus(String rvConBussinesStatus) {
		this.rvConBussinesStatus = rvConBussinesStatus;
	}

}
