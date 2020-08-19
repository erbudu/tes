package com.supporter.prj.ppm.contract.effect.review_ver.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 评审验证表,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-09
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractEffectRevVer implements Serializable {

	private static final long serialVersionUID = 1L;
	private String reviewVerId;
	private String contractEffectReviewId;
	private String contractEffectRevConId;
	private java.util.Date reviewSubmitDate;
	private int result;
	private int status;
	private String reviewVerificatContent;
	private int modifyEvalPoint;
	private String createdBy;
	private String createdById;
	private java.util.Date createdDate;
	private String deptId;
	private String deptName;
	private String modifiedById;
	private String modifiedBy;
	private java.util.Date modifiedDate;
	private String procId;

	/**
	 * 无参构造函数.
	 */
	public BaseContractEffectRevVer() {
	}

	/**
	 * 构造函数.
	 *
	 * @param reviewVerId
	 */
	public BaseContractEffectRevVer(String reviewVerId) {
		this.reviewVerId = reviewVerId;
	}

	/**
	 * 方法: 取得主键.
	 * @return: java.lang.String 主键
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "REVIEW_VER_ID", nullable = false, length = 32)
	public String getReviewVerId() {
		return this.reviewVerId;
	}

	/**
	 * 方法: 设置主键.
	 * @param: java.lang.String 主键
	 */
	public void setReviewVerId(String reviewVerId) {
		this.reviewVerId = reviewVerId;
	}

	/**
	 * 方法: 取得投标评审id.
	 * @return: java.lang.String 投标评审id
	 */
	@Column(name = "CONTRACT_EFFECT_REVIEW_ID", nullable = true, length = 32)
	public String getContractEffectReviewId() {
		return this.contractEffectReviewId;
	}

	/**
	 * 方法: 设置投标评审id.
	 * @param: java.lang.String 投标评审id
	 */
	public void setContractEffectReviewId(String contractEffectReviewId) {
		this.contractEffectReviewId = contractEffectReviewId;
	}

	/**
	 * 方法: 取得评审结论主键.
	 * @return: java.lang.String 评审结论主键
	 */
	@Column(name = "CONTRACT_EFFECT_REV_CON_ID", nullable = true, length = 32)
	public String getContractEffectRevConId() {
		return this.contractEffectRevConId;
	}

	/**
	 * 方法: 设置评审结论主键.
	 * @param: java.lang.String 评审结论主键
	 */
	public void setContractEffectRevConId(String contractEffectRevConId) {
		this.contractEffectRevConId = contractEffectRevConId;
	}

	/**
	 * 方法: 取得评审提交日期.
	 * @return: java.util.Date 评审提交日期
	 */
	@Column(name = "REVIEW_SUBMIT_DATE", nullable = true)
	public java.util.Date getReviewSubmitDate() {
		return this.reviewSubmitDate;
	}

	/**
	 * 方法: 设置评审提交日期.
	 * @param: java.util.Date 评审提交日期
	 */
	public void setReviewSubmitDate(java.util.Date reviewSubmitDate) {
		this.reviewSubmitDate = reviewSubmitDate;
	}

	/**
	 * 方法: 取得审批结果.
	 * @return: int 审批结果
	 */
	@Column(name = "RESULT", nullable = true, precision = 10)
	public int getResult() {
		return this.result;
	}

	/**
	 * 方法: 设置审批结果.
	 * @param: int 审批结果
	 */
	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * 方法: 取得状态.
	 * @return: int 状态
	 */
	@Column(name = "STATUS", nullable = true, precision = 10)
	public int getStatus() {
		return this.status;
	}

	/**
	 * 方法: 设置状态.
	 * @param: int 状态
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 方法: 取得评审验证内容.
	 * @return: java.lang.String 评审验证内容
	 */
	@Column(name = "REVIEW_VERIFICAT_CONTENT", nullable = true, length = 512)
	public String getReviewVerificatContent() {
		return this.reviewVerificatContent;
	}

	/**
	 * 方法: 设置评审验证内容.
	 * @param: java.lang.String 评审验证内容
	 */
	public void setReviewVerificatContent(String reviewVerificatContent) {
		this.reviewVerificatContent = reviewVerificatContent;
	}

	/**
	 * 方法: 取得选择调整评审要点.
	 * @return: int 选择调整评审要点
	 */
	@Column(name = "MODIFY_EVAL_POINT", nullable = true, precision = 10)
	public int getModifyEvalPoint() {
		return this.modifyEvalPoint;
	}

	/**
	 * 方法: 设置选择调整评审要点.
	 * @param: int 选择调整评审要点
	 */
	public void setModifyEvalPoint(int modifyEvalPoint) {
		this.modifyEvalPoint = modifyEvalPoint;
	}

	/**
	 * 方法: 取得CREATED_BY.
	 * @return: java.lang.String CREATED_BY
	 */
	@Column(name = "CREATED_BY", nullable = true, length = 64)
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * 方法: 设置CREATED_BY.
	 * @param: java.lang.String CREATED_BY
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * 方法: 取得CREATED_BY_ID.
	 * @return: java.lang.String CREATED_BY_ID
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	/**
	 * 方法: 设置CREATED_BY_ID.
	 * @param: java.lang.String CREATED_BY_ID
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	/**
	 * 方法: 取得CREATED_DATE.
	 * @return: java.util.Date CREATED_DATE
	 */
	@Column(name = "CREATED_DATE", nullable = true)
	public java.util.Date getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * 方法: 设置CREATED_DATE.
	 * @param: java.util.Date CREATED_DATE
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * 方法: 取得DEPT_ID.
	 * @return: java.lang.String DEPT_ID
	 */
	@Column(name = "DEPT_ID", nullable = true, length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	/**
	 * 方法: 设置DEPT_ID.
	 * @param: java.lang.String DEPT_ID
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * 方法: 取得DEPT_NAME.
	 * @return: java.lang.String DEPT_NAME
	 */
	@Column(name = "DEPT_NAME", nullable = true, length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * 方法: 设置DEPT_NAME.
	 * @param: java.lang.String DEPT_NAME
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * 方法: 取得MODIFIED_BY_ID.
	 * @return: java.lang.String MODIFIED_BY_ID
	 */
	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	/**
	 * 方法: 设置MODIFIED_BY_ID.
	 * @param: java.lang.String MODIFIED_BY_ID
	 */
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	/**
	 * 方法: 取得MODIFIED_BY.
	 * @return: java.lang.String MODIFIED_BY
	 */
	@Column(name = "MODIFIED_BY", nullable = true, length = 64)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	/**
	 * 方法: 设置MODIFIED_BY.
	 * @param: java.lang.String MODIFIED_BY
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * 方法: 取得MODIFIED_DATE.
	 * @return: java.util.Date MODIFIED_DATE
	 */
	@Column(name = "MODIFIED_DATE", nullable = true)
	public java.util.Date getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 * 方法: 设置MODIFIED_DATE.
	 * @param: java.util.Date MODIFIED_DATE
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * 方法: 取得PROC_ID.
	 * @return: java.lang.String PROC_ID
	 */
	@Column(name = "PROC_ID", nullable = true, length = 32)
	public String getProcId() {
		return this.procId;
	}

	/**
	 * 方法: 设置PROC_ID.
	 * @param: java.lang.String PROC_ID
	 */
	public void setProcId(String procId) {
		this.procId = procId;
	}

}
