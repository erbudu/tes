package com.supporter.prj.ppm.contract.sign.review.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @Title: Entity
 * @Description: 签约评审表,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-06
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractSignReview implements Serializable {

	private static final long serialVersionUID = 1L;
	private String contractSignReviewId;
	private String prjId;
	private String prjName;
	private String prjCode;
	private String reviewTypeId;
	private String reviewType;
	private String reviewLevelId;
	private String reviewLevel;
	private String reviewNo;
	private int reviewNumber;
	private String prjTypeId;
	private String prjType;
	private java.util.Date reviewSubmitDate;
	private int status;
	private String procId;
	private String createdBy;
	private String createdById;
	private java.util.Date createdDate;
	private String modifiedBy;
	private String modifiedById;
	private java.util.Date modifiedDate;
	private String deptId;
	private String deptName;

	/**
	 * 无参构造函数.
	 */
	public BaseContractSignReview() {
	}

	/**
	 * 构造函数.
	 *
	 * @param contractSignReviewId
	 */
	public BaseContractSignReview(String contractSignReviewId) {
		this.contractSignReviewId = contractSignReviewId;
	}

	/**
	 * 方法: 取得主键.
	 * @return: java.lang.String 主键
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "CONTRACT_SIGN_REVIEW_ID", nullable = false, length = 32)
	public String getContractSignReviewId() {
		return this.contractSignReviewId;
	}

	/**
	 * 方法: 设置主键.
	 * @param: java.lang.String 主键
	 */
	public void setContractSignReviewId(String contractSignReviewId) {
		this.contractSignReviewId = contractSignReviewId;
	}

	/**
	 * 方法: 取得项目id.
	 * @return: java.lang.String 项目id
	 */
	@Column(name = "PRJ_ID", nullable = true, length = 32)
	public String getPrjId() {
		return this.prjId;
	}

	/**
	 * 方法: 设置项目id.
	 * @param: java.lang.String 项目id
	 */
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	/**
	 * 方法: 取得项目名称.
	 * @return: java.lang.String 项目名称
	 */
	@Column(name = "PRJ_NAME", nullable = true, length = 128)
	public String getPrjName() {
		return this.prjName;
	}

	/**
	 * 方法: 设置项目名称.
	 * @param: java.lang.String 项目名称
	 */
	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	/**
	 * 方法: 取得项目代号.
	 * @return: java.lang.String 项目代号
	 */
	@Column(name = "PRJ_CODE", nullable = true, length = 128)
	public String getPrjCode() {
		return this.prjCode;
	}

	/**
	 * 方法: 设置项目代号.
	 * @param: java.lang.String 项目代号
	 */
	public void setPrjCode(String prjCode) {
		this.prjCode = prjCode;
	}

	/**
	 * 方法: 取得评估性质id.
	 * @return: java.lang.String 评估性质id
	 */
	@Column(name = "REVIEW_TYPE_ID", nullable = true, length = 32)
	public String getReviewTypeId() {
		return this.reviewTypeId;
	}

	/**
	 * 方法: 设置评估性质id.
	 * @param: java.lang.String 评估性质id
	 */
	public void setReviewTypeId(String reviewTypeId) {
		this.reviewTypeId = reviewTypeId;
	}

	/**
	 * 方法: 取得评估性质.
	 * @return: java.lang.String 评估性质
	 */
	@Column(name = "REVIEW_TYPE", nullable = true, length = 128)
	public String getReviewType() {
		return this.reviewType;
	}

	/**
	 * 方法: 设置评估性质.
	 * @param: java.lang.String 评估性质
	 */
	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}

	/**
	 * 方法: 取得项目级别id.
	 * @return: java.lang.String 项目级别id
	 */
	@Column(name = "REVIEW_LEVEL_ID", nullable = true, length = 32)
	public String getReviewLevelId() {
		return this.reviewLevelId;
	}

	/**
	 * 方法: 设置项目级别id.
	 * @param: java.lang.String 项目级别id
	 */
	public void setReviewLevelId(String reviewLevelId) {
		this.reviewLevelId = reviewLevelId;
	}

	/**
	 * 方法: 取得项目级别.
	 * @return: java.lang.String 项目级别
	 */
	@Column(name = "REVIEW_LEVEL", nullable = true, length = 128)
	public String getReviewLevel() {
		return this.reviewLevel;
	}

	/**
	 * 方法: 设置项目级别.
	 * @param: java.lang.String 项目级别
	 */
	public void setReviewLevel(String reviewLevel) {
		this.reviewLevel = reviewLevel;
	}

	/**
	 * 方法: 取得评估编号.
	 * @return: java.lang.String 评估编号
	 */
	@Column(name = "REVIEW_NO", nullable = true, length = 128)
	public String getReviewNo() {
		return this.reviewNo;
	}

	/**
	 * 方法: 设置评估编号.
	 * @param: java.lang.String 评估编号
	 */
	public void setReviewNo(String reviewNo) {
		this.reviewNo = reviewNo;
	}

	/**
	 * 方法: 取得评估次数.
	 * @return: int 评估次数
	 */
	@Column(name = "REVIEW_NUMBER", nullable = true, precision = 10)
	public int getReviewNumber() {
		return this.reviewNumber;
	}

	/**
	 * 方法: 设置评估次数.
	 * @param: int 评估次数
	 */
	public void setReviewNumber(int reviewNumber) {
		this.reviewNumber = reviewNumber;
	}

	/**
	 * 方法: 取得项目类型id.
	 * @return: java.lang.String 项目类型id
	 */
	@Column(name = "PRJ_TYPE_ID", nullable = true, length = 32)
	public String getPrjTypeId() {
		return this.prjTypeId;
	}

	/**
	 * 方法: 设置项目类型id.
	 * @param: java.lang.String 项目类型id
	 */
	public void setPrjTypeId(String prjTypeId) {
		this.prjTypeId = prjTypeId;
	}

	/**
	 * 方法: 取得项目类型.
	 * @return: java.lang.String 项目类型
	 */
	@Column(name = "PRJ_TYPE", nullable = true, length = 128)
	public String getPrjType() {
		return this.prjType;
	}

	/**
	 * 方法: 设置项目类型.
	 * @param: java.lang.String 项目类型
	 */
	public void setPrjType(String prjType) {
		this.prjType = prjType;
	}

	/**
	 * 方法: 取得评审提交日期.
	 * @return: java.util.Date 评审提交日期
	 */
	@Temporal(TemporalType.DATE)
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
	 * 方法: 取得流程id.
	 * @return: java.lang.String 流程id
	 */
	@Column(name = "PROC_ID", nullable = true, length = 32)
	public String getProcId() {
		return this.procId;
	}

	/**
	 * 方法: 设置流程id.
	 * @param: java.lang.String 流程id
	 */
	public void setProcId(String procId) {
		this.procId = procId;
	}

	/**
	 * 方法: 取得创建人.
	 * @return: java.lang.String 创建人
	 */
	@Column(name = "CREATED_BY", nullable = true, length = 64)
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * 方法: 设置创建人.
	 * @param: java.lang.String 创建人
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * 方法: 取得创建人id.
	 * @return: java.lang.String 创建人id
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	/**
	 * 方法: 设置创建人id.
	 * @param: java.lang.String 创建人id
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	/**
	 * 方法: 取得创建时间.
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATED_DATE", nullable = true)
	public java.util.Date getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * 方法: 设置创建时间.
	 * @param: java.util.Date 创建时间
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * 方法: 取得修改人.
	 * @return: java.lang.String 修改人
	 */
	@Column(name = "MODIFIED_BY", nullable = true, length = 64)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	/**
	 * 方法: 设置修改人.
	 * @param: java.lang.String 修改人
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * 方法: 取得修改人id.
	 * @return: java.lang.String 修改人id
	 */
	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	/**
	 * 方法: 设置修改人id.
	 * @param: java.lang.String 修改人id
	 */
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	/**
	 * 方法: 取得修改日志.
	 * @return: java.util.Date 修改日志
	 */
	@Column(name = "MODIFIED_DATE", nullable = true)
	public java.util.Date getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 * 方法: 设置修改日志.
	 * @param: java.util.Date 修改日志
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * 方法: 取得创建人部门id.
	 * @return: java.lang.String 创建人部门id
	 */
	@Column(name = "DEPT_ID", nullable = true, length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	/**
	 * 方法: 设置创建人部门id.
	 * @param: java.lang.String 创建人部门id
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * 方法: 取得创建人部门.
	 * @return: java.lang.String 创建人部门
	 */
	@Column(name = "DEPT_NAME", nullable = true, length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * 方法: 设置创建人部门.
	 * @param: java.lang.String 创建人部门
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
