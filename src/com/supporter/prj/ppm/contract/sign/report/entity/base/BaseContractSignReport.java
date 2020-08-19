package com.supporter.prj.ppm.contract.sign.report.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 主合同签约报审表,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-05
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractSignReport implements Serializable {

	private static final long serialVersionUID = 1L;
	private String contractSignId;
	private String prjId;
	private double prjPlanAmount;
	private java.util.Date reportStartDate;
	private java.util.Date predictEndDate;
	private String linkmanName;
	private String linkmanTel;
	private String linkmanMob;
	private String linkmanFax;
	private String createdById;
	private String createdBy;
	private java.util.Date createdDate;
	private String modifiedById;
	private String modifiedBy;
	private java.util.Date modifiedDate;
	private String deptId;
	private String deptName;
	private String procId;
	private int status;

	/**
	 * 无参构造函数.
	 */
	public BaseContractSignReport() {
	}

	/**
	 * 构造函数.
	 *
	 * @param contractSignId
	 */
	public BaseContractSignReport(String contractSignId) {
		this.contractSignId = contractSignId;
	}

	/**
	 * 方法: 取得主键.
	 * @return: java.lang.String 主键
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "CONTRACT_SIGN_ID", nullable = false, length = 32)
	public String getContractSignId() {
		return this.contractSignId;
	}

	/**
	 * 方法: 设置主键.
	 * @param: java.lang.String 主键
	 */
	public void setContractSignId(String contractSignId) {
		this.contractSignId = contractSignId;
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
	 * 方法: 取得项目估算金额.
	 * @return: java.math.BigDecimal 项目估算金额
	 */
	@Column(name = "PRJ_PLAN_AMOUNT", nullable = true, precision = 10, scale = 5)
	public double getPrjPlanAmount() {
		return this.prjPlanAmount;
	}

	/**
	 * 方法: 设置项目估算金额.
	 * @param: java.math.BigDecimal 项目估算金额
	 */
	public void setPrjPlanAmount(double prjPlanAmount) {
		this.prjPlanAmount = prjPlanAmount;
	}

	/**
	 * 方法: 取得报审开始日期.
	 * @return: java.util.Date 报审开始日期
	 */
	@Column(name = "REPORT_START_DATE", nullable = true)
	public java.util.Date getReportStartDate() {
		return this.reportStartDate;
	}

	/**
	 * 方法: 设置报审开始日期.
	 * @param: java.util.Date 报审开始日期
	 */
	public void setReportStartDate(java.util.Date reportStartDate) {
		this.reportStartDate = reportStartDate;
	}

	/**
	 * 方法: 取得预计报审完成日期.
	 * @return: java.util.Date 预计报审完成日期
	 */
	@Column(name = "PREDICT_END_DATE", nullable = true)
	public java.util.Date getPredictEndDate() {
		return this.predictEndDate;
	}

	/**
	 * 方法: 设置预计报审完成日期.
	 * @param: java.util.Date 预计报审完成日期
	 */
	public void setPredictEndDate(java.util.Date predictEndDate) {
		this.predictEndDate = predictEndDate;
	}

	/**
	 * 方法: 取得联系人.
	 * @return: java.lang.String 联系人
	 */
	@Column(name = "LINKMAN_NAME", nullable = true, length = 128)
	public String getLinkmanName() {
		return this.linkmanName;
	}

	/**
	 * 方法: 设置联系人.
	 * @param: java.lang.String 联系人
	 */
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	/**
	 * 方法: 取得联系电话.
	 * @return: java.lang.String 联系电话
	 */
	@Column(name = "LINKMAN_TEL", nullable = true, length = 128)
	public String getLinkmanTel() {
		return this.linkmanTel;
	}

	/**
	 * 方法: 设置联系电话.
	 * @param: java.lang.String 联系电话
	 */
	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel;
	}

	/**
	 * 方法: 取得联系手机.
	 * @return: java.lang.String 联系手机
	 */
	@Column(name = "LINKMAN_MOB", nullable = true, length = 128)
	public String getLinkmanMob() {
		return this.linkmanMob;
	}

	/**
	 * 方法: 设置联系手机.
	 * @param: java.lang.String 联系手机
	 */
	public void setLinkmanMob(String linkmanMob) {
		this.linkmanMob = linkmanMob;
	}

	/**
	 * 方法: 取得联系传真.
	 * @return: java.lang.String 联系传真
	 */
	@Column(name = "LINKMAN_FAX", nullable = true, length = 128)
	public String getLinkmanFax() {
		return this.linkmanFax;
	}

	/**
	 * 方法: 设置联系传真.
	 * @param: java.lang.String 联系传真
	 */
	public void setLinkmanFax(String linkmanFax) {
		this.linkmanFax = linkmanFax;
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
	@Column(name = "DEPT_NAME", nullable = true, length = 64)
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
	@Column(name = "PROC_ID", nullable = true, length = 64)

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}
	@Column(name = "status", nullable = true, length = 64)

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
