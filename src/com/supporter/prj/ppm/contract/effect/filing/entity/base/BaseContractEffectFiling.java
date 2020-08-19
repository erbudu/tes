package com.supporter.prj.ppm.contract.effect.filing.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: PPM_CONTRACT_FILING,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-09-17
 * @version: V1.0
 */
@MappedSuperclass
public class BaseContractEffectFiling implements Serializable {

	private static final long serialVersionUID = 1L;
	private String filingId;
	private String prjId;
	private String filingNo;
	private java.util.Date filingDate;
	private java.util.Date effectDate;
	private java.util.Date endDate;
	private String linkmanId;
	private String linkmanName;
	private String linkmanTel;
	private String linkmanPhone;
	private String linkmanTax;
	private String createdBy;
	private String createdById;
	private java.util.Date createdDate;
	private String modifiedBy;
	private String modifiedById;
	private java.util.Date modifiedDate;
	private String deptId;
	private String deptName;
	private String contractStatus;
	private int status;
	private String procId;

	/**
	 * 无参构造函数.
	 */
	public BaseContractEffectFiling() {
	}

	/**
	 * 构造函数.
	 *
	 * @param filingId
	 */
	public BaseContractEffectFiling(String filingId) {
		this.filingId = filingId;
	}

	/**
	 * 方法: 取得FILING_ID.
	 * @return: java.lang.String FILING_ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "FILING_ID", nullable = false, length = 32)
	public String getFilingId() {
		return this.filingId;
	}

	/**
	 * 方法: 设置FILING_ID.
	 * @param: java.lang.String FILING_ID
	 */
	public void setFilingId(String filingId) {
		this.filingId = filingId;
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
	@Column(name = "FILING_NO", nullable = true, length = 32)
	public String getFilingNo() {
		return filingNo;
	}

	public void setFilingNo(String filingNo) {
		this.filingNo = filingNo;
	}

	/**
	 * 方法: 取得备案日期.
	 * @return: java.util.Date 备案日期
	 */
	@Column(name = "FILING_DATE", nullable = true)
	public java.util.Date getFilingDate() {
		return this.filingDate;
	}

	/**
	 * 方法: 设置备案日期.
	 * @param: java.util.Date 备案日期
	 */
	public void setFilingDate(java.util.Date filingDate) {
		this.filingDate = filingDate;
	}



	/**
	 * 方法: 取得竣工日期.
	 * @return: java.util.Date 竣工日期
	 */
	@Column(name = "END_DATE", nullable = true)
	public java.util.Date getEndDate() {
		return this.endDate;
	}

	/**
	 * 方法: 设置竣工日期.
	 * @param: java.util.Date 竣工日期
	 */
	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 方法: 取得生效日期.
	 * @return: java.util.Date 生效日期
	 */
	@Column(name = "EFFECT_DATE", nullable = true)
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
	 * 方法: 取得联系人.
	 * @return: java.lang.String 联系人
	 */
	@Column(name = "LINKMAN_ID", nullable = true, length = 32)
	public String getLinkmanId() {
		return this.linkmanId;
	}

	/**
	 * 方法: 设置联系人.
	 * @param: java.lang.String 联系人
	 */
	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}

	/**
	 * 方法: 取得LINKMAN_NAME.
	 * @return: java.lang.String LINKMAN_NAME
	 */
	@Column(name = "LINKMAN_NAME", nullable = true, length = 64)
	public String getLinkmanName() {
		return this.linkmanName;
	}

	/**
	 * 方法: 设置LINKMAN_NAME.
	 * @param: java.lang.String LINKMAN_NAME
	 */
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	/**
	 * 方法: 取得联系电话.
	 * @return: java.lang.String 联系电话
	 */
	@Column(name = "LINKMAN_TEL", nullable = true, length = 32)
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
	@Column(name = "LINKMAN_PHONE", nullable = true, length = 32)
	public String getLinkmanPhone() {
		return this.linkmanPhone;
	}

	/**
	 * 方法: 设置联系手机.
	 * @param: java.lang.String 联系手机
	 */
	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}

	/**
	 * 方法: 取得联系传真.
	 * @return: java.lang.String 联系传真
	 */
	@Column(name = "LINKMAN_TAX", nullable = true, length = 32)
	public String getLinkmanTax() {
		return this.linkmanTax;
	}

	/**
	 * 方法: 设置联系传真.
	 * @param: java.lang.String 联系传真
	 */
	public void setLinkmanTax(String linkmanTax) {
		this.linkmanTax = linkmanTax;
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

	/**
	 * 方法: 取得合同状态.
	 * @return: java.lang.String 合同状态
	 */
	@Column(name = "CONTRACT_STATUS", nullable = true, length = 32)
	public String getContractStatus() {
		return this.contractStatus;
	}

	/**
	 * 方法: 设置合同状态.
	 * @param: java.lang.String 合同状态
	 */
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
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

	@Column(name = "proc_id", nullable = true, length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

}
