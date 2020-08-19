package com.supporter.prj.cneec.tpc.drawback.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: TPC_DRAWBACK,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-11-20
 * @version V1.0
 */
@MappedSuperclass
public class BaseDrawback implements Serializable {

	private static final long serialVersionUID = 1L;
	private String drawbackId;
	private String drawbackNo;
	private String projectId;
	private String projectName;
	private String contractId;
	private String contractNo;
	private String contractName;
	private String budgetId;
	private String budgetName;
	private double drawbackAmount;
	private String drawbackDate;
	private Integer swfStatus;
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;

	/**
	 * 无参构造函数.
	 */
	public BaseDrawback() {
	}

	/**
	 * 构造函数.
	 *
	 * @param drawbackId
	 */
	public BaseDrawback(String drawbackId) {
		this.drawbackId = drawbackId;
	}

	/**
	 * GET方法: 取得DRAWBACK_ID.
	 *
	 * @return: String  DRAWBACK_ID
	 */
	@Id
	@Column(name = "DRAWBACK_ID", nullable = false, length = 32)
	public String getDrawbackId() {
		return this.drawbackId;
	}

	/**
	 * SET方法: 设置DRAWBACK_ID.
	 *
	 * @param: String  DRAWBACK_ID
	 */
	public void setDrawbackId(String drawbackId) {
		this.drawbackId = drawbackId;
	}

	/**
	 * GET方法: 取得DRAWBACK_NO.
	 *
	 * @return: String  DRAWBACK_NO
	 */
	@Column(name = "DRAWBACK_NO", nullable = true, length = 32)
	public String getDrawbackNo() {
		return this.drawbackNo;
	}

	/**
	 * SET方法: 设置DRAWBACK_NO.
	 *
	 * @param: String  DRAWBACK_NO
	 */
	public void setDrawbackNo(String drawbackNo) {
		this.drawbackNo = drawbackNo;
	}

	/**
	 * GET方法: 取得PROJECT_ID.
	 *
	 * @return: String  PROJECT_ID
	 */
	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	/**
	 * SET方法: 设置PROJECT_ID.
	 *
	 * @param: String  PROJECT_ID
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * GET方法: 取得PROJECT_NAME.
	 *
	 * @return: String  PROJECT_NAME
	 */
	@Column(name = "PROJECT_NAME", nullable = true, length = 128)
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * SET方法: 设置PROJECT_NAME.
	 *
	 * @param: String  PROJECT_NAME
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * GET方法: 取得CONTRACT_ID.
	 *
	 * @return: String  CONTRACT_ID
	 */
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	public String getContractId() {
		return this.contractId;
	}

	/**
	 * SET方法: 设置CONTRACT_ID.
	 *
	 * @param: String  CONTRACT_ID
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	/**
	 * GET方法: 取得CONTRACT_NO.
	 *
	 * @return: String  CONTRACT_NO
	 */
	@Column(name = "CONTRACT_NO", nullable = true, length = 64)
	public String getContractNo() {
		return this.contractNo;
	}

	/**
	 * SET方法: 设置CONTRACT_NO.
	 *
	 * @param: String  CONTRACT_NO
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * GET方法: 取得CONTRACT_NAME.
	 *
	 * @return: String  CONTRACT_NAME
	 */
	@Column(name = "CONTRACT_NAME", nullable = true, length = 128)
	public String getContractName() {
		return this.contractName;
	}

	/**
	 * SET方法: 设置CONTRACT_NAME.
	 *
	 * @param: String  CONTRACT_NAME
	 */
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	/**
	 * GET方法: 取得BUDGET_ID.
	 *
	 * @return: String  BUDGET_ID
	 */
	@Column(name = "BUDGET_ID", nullable = true, length = 64)
	public String getBudgetId() {
		return this.budgetId;
	}

	/**
	 * SET方法: 设置BUDGET_ID.
	 *
	 * @param: String  BUDGET_ID
	 */
	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}

	/**
	 * GET方法: 取得BUDGET_NAME.
	 *
	 * @return: String  BUDGET_NAME
	 */
	@Column(name = "BUDGET_NAME", nullable = true, length = 64)
	public String getBudgetName() {
		return this.budgetName;
	}

	/**
	 * SET方法: 设置BUDGET_NAME.
	 *
	 * @param: String  BUDGET_NAME
	 */
	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}

	/**
	 * GET方法: 取得DRAWBACK_AMOUNT.
	 *
	 * @return: double  DRAWBACK_AMOUNT
	 */
	@Column(name = "DRAWBACK_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getDrawbackAmount() {
		return this.drawbackAmount;
	}

	/**
	 * SET方法: 设置DRAWBACK_AMOUNT.
	 *
	 * @param: double  DRAWBACK_AMOUNT
	 */
	public void setDrawbackAmount(double drawbackAmount) {
		this.drawbackAmount = drawbackAmount;
	}

	/**
	 * GET方法: 取得DRAWBACK_DATE.
	 *
	 * @return: String  DRAWBACK_DATE
	 */
	@Column(name = "DRAWBACK_DATE", nullable = true, length = 27)
	public String getDrawbackDate() {
		return this.drawbackDate;
	}

	/**
	 * SET方法: 设置DRAWBACK_DATE.
	 *
	 * @param: String  DRAWBACK_DATE
	 */
	public void setDrawbackDate(String drawbackDate) {
		this.drawbackDate = drawbackDate;
	}

	/**
	 * GET方法: 取得SWF_STATUS.
	 *
	 * @return: Integer  SWF_STATUS
	 */
	@Column(name = "SWF_STATUS", nullable = true, precision = 10)
	public Integer getSwfStatus() {
		return this.swfStatus;
	}

	/**
	 * SET方法: 设置SWF_STATUS.
	 *
	 * @param: Integer  SWF_STATUS
	 */
	public void setSwfStatus(Integer swfStatus) {
		this.swfStatus = swfStatus;
	}

	/**
	 * GET方法: 取得DEPT_NAME.
	 *
	 * @return: String  DEPT_NAME
	 */
	@Column(name = "DEPT_NAME", nullable = true, length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * SET方法: 设置DEPT_NAME.
	 *
	 * @param: String  DEPT_NAME
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * GET方法: 取得DEPT_ID.
	 *
	 * @return: String  DEPT_ID
	 */
	@Column(name = "DEPT_ID", nullable = true, length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	/**
	 * SET方法: 设置DEPT_ID.
	 *
	 * @param: String  DEPT_ID
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * GET方法: 取得CREATED_BY.
	 *
	 * @return: String  CREATED_BY
	 */
	@Column(name = "CREATED_BY", nullable = true, length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * SET方法: 设置CREATED_BY.
	 *
	 * @param: String  CREATED_BY
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * GET方法: 取得CREATED_BY_ID.
	 *
	 * @return: String  CREATED_BY_ID
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	/**
	 * SET方法: 设置CREATED_BY_ID.
	 *
	 * @param: String  CREATED_BY_ID
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	/**
	 * GET方法: 取得CREATED_DATE.
	 *
	 * @return: String  CREATED_DATE
	 */
	@Column(name = "CREATED_DATE", nullable = true, length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * SET方法: 设置CREATED_DATE.
	 *
	 * @param: String  CREATED_DATE
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * GET方法: 取得MODIFIED_BY.
	 *
	 * @return: String  MODIFIED_BY
	 */
	@Column(name = "MODIFIED_BY", nullable = true, length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	/**
	 * SET方法: 设置MODIFIED_BY.
	 *
	 * @param: String  MODIFIED_BY
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * GET方法: 取得MODIFIED_BY_ID.
	 *
	 * @return: String  MODIFIED_BY_ID
	 */
	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	/**
	 * SET方法: 设置MODIFIED_BY_ID.
	 *
	 * @param: String  MODIFIED_BY_ID
	 */
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	/**
	 * GET方法: 取得MODIFIED_DATE.
	 *
	 * @return: String  MODIFIED_DATE
	 */
	@Column(name = "MODIFIED_DATE", nullable = true, length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 * SET方法: 设置MODIFIED_DATE.
	 *
	 * @param: String  MODIFIED_DATE
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
