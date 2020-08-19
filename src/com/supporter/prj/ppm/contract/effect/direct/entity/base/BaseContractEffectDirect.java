package com.supporter.prj.ppm.contract.effect.direct.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 主合同直接生效,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-18
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractEffectDirect implements Serializable {

	private static final long serialVersionUID = 1L;
	private String directId;
	private String prjId;
	private String contractId;
	private String filingId;
	private java.util.Date effectDate;
	private String effectExplain;
	private int status;
	private String createdById;
	private String createdBy;
	private java.util.Date createdDate;
	private String modifiedById;
	private String modifiedBy;
	private java.util.Date modifiedDate;
	private String deptId;
	private String deptName;

	/**
	 * 无参构造函数.
	 */
	public BaseContractEffectDirect() {
	}

	/**
	 * 构造函数.
	 *
	 * @param directId
	 */
	public BaseContractEffectDirect(String directId) {
		this.directId = directId;
	}

	/**
	 * 方法: 取得DIRECT_ID.
	 * @return: java.lang.String DIRECT_ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "DIRECT_ID", nullable = false, length = 32)
	public String getDirectId() {
		return this.directId;
	}

	/**
	 * 方法: 设置DIRECT_ID.
	 * @param: java.lang.String DIRECT_ID
	 */
	public void setDirectId(String directId) {
		this.directId = directId;
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
	 * 方法: 取得合同id.
	 * @return: java.lang.String 合同id
	 */
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	public String getContractId() {
		return this.contractId;
	}

	/**
	 * 方法: 设置合同id.
	 * @param: java.lang.String 合同id
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	/**
	 * 方法: 取得备案id.
	 * @return: java.lang.String 备案id
	 */
	@Column(name = "FILING_ID", nullable = true, length = 32)
	public String getFilingId() {
		return this.filingId;
	}

	/**
	 * 方法: 设置备案id.
	 * @param: java.lang.String 备案id
	 */
	public void setFilingId(String filingId) {
		this.filingId = filingId;
	}

	/**
	 * 方法: 取得生效日期.
	 * @return: java.util.Date 生效日期
	 */
	@Column(name = "effect_DATE", nullable = true)
	public java.util.Date getEffectDate() {
		return this.effectDate;
	}

	/**
	 * 方法: 设置生效日期.
	 * @param: java.util.Date 生效日期
	 */
	public void setEffectDate(java.util.Date effectDate) {
		this.effectDate = effectDate;
	}

	/**
	 * 方法: 取得生效说明.
	 * @return: java.lang.String 生效说明
	 */
	@Column(name = "effect_EXPLAIN", nullable = true, length = 1024)
	public String getEffectExplain() {
		return this.effectExplain;
	}

	/**
	 * 方法: 设置生效说明.
	 * @param: java.lang.String 生效说明
	 */
	public void setEffectExplain(String effectExplain) {
		this.effectExplain = effectExplain;
	}

	/**
	 * 方法: 取得生效状态（0草稿；1生效）.
	 * @return: int 生效状态（0草稿；1生效）
	 */
	@Column(name = "STATUS", nullable = true, precision = 10)
	public int getStatus() {
		return this.status;
	}

	/**
	 * 方法: 设置生效状态（0草稿；1生效）.
	 * @param: int 生效状态（0草稿；1生效）
	 */
	public void setStatus(int status) {
		this.status = status;
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

}
