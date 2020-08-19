package com.supporter.prj.ppm.ecc.cac_review.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 出口管制委员会审核表,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccCacReview implements Serializable {

	private static final long serialVersionUID = 1L;
	private String eccCacId;
	private String eccId;
	private String prjId;
	private java.util.Date applyDate;
	private String linkmanName;
	private String linkmanTelephone;
	private String linkmanPhone;
	private String linkmanFax;
	private int deptEccStatus;
	private Date createdDate;
	private String createdBy;
	private String createdById;
	private Date modifiedDate;
	private String modifiedBy;
	private String modifiedById;
	private String deptId;
	private String deptName;
	private String procId;
	private String memberIds;
	private String memberNames;



	private String memberNodeIds;
	/**
	 * 无参构造函数.
	 */
	public BaseEccCacReview() {
	}

	/**
	 * 构造函数.
	 *
	 * @param eccCacId
	 */
	public BaseEccCacReview(String eccCacId) {
		this.eccCacId = eccCacId;
	}

	/**
	 * 方法: 取得ECC_CAC_ID.
	 * @return: java.lang.String ECC_CAC_ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "ECC_CAC_ID", nullable = false, length = 32)
	public String getEccCacId() {
		return this.eccCacId;
	}

	/**
	 * 方法: 设置ECC_CAC_ID.
	 * @param: java.lang.String ECC_CAC_ID
	 */
	public void setEccCacId(String eccCacId) {
		this.eccCacId = eccCacId;
	}

	/**
	 * 方法: 取得管制审核逐渐.
	 * @return: java.lang.String 管制审核逐渐
	 */
	@Column(name = "ECC_ID", nullable = true, length = 32)
	public String getEccId() {
		return this.eccId;
	}

	/**
	 * 方法: 设置管制审核逐渐.
	 * @param: java.lang.String 管制审核逐渐
	 */
	public void setEccId(String eccId) {
		this.eccId = eccId;
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
	 * 方法: 取得申请日期.
	 * @return: java.util.Date 申请日期
	 */
	@Column(name = "APPLY_DATE", nullable = true)
	public java.util.Date getApplyDate() {
		return this.applyDate;
	}

	/**
	 * 方法: 设置申请日期.
	 * @param: java.util.Date 申请日期
	 */
	public void setApplyDate(java.util.Date applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * 方法: 取得申报部门联系方式-联系人姓名.
	 * @return: java.lang.String 申报部门联系方式-联系人姓名
	 */
	@Column(name = "LINKMAN_NAME", nullable = true, length = 32)
	public String getLinkmanName() {
		return this.linkmanName;
	}

	/**
	 * 方法: 设置申报部门联系方式-联系人姓名.
	 * @param: java.lang.String 申报部门联系方式-联系人姓名
	 */
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	/**
	 * 方法: 取得申报部门联系方式-联系人电话.
	 * @return: java.lang.String 申报部门联系方式-联系人电话
	 */
	@Column(name = "LINKMAN_TELEPHONE", nullable = true, length = 32)
	public String getLinkmanTelephone() {
		return this.linkmanTelephone;
	}

	/**
	 * 方法: 设置申报部门联系方式-联系人电话.
	 * @param: java.lang.String 申报部门联系方式-联系人电话
	 */
	public void setLinkmanTelephone(String linkmanTelephone) {
		this.linkmanTelephone = linkmanTelephone;
	}

	/**
	 * 方法: 取得申报部门联系方式-联系人手机.
	 * @return: java.lang.String 申报部门联系方式-联系人手机
	 */
	@Column(name = "LINKMAN_PHONE", nullable = true, length = 32)
	public String getLinkmanPhone() {
		return this.linkmanPhone;
	}

	/**
	 * 方法: 设置申报部门联系方式-联系人手机.
	 * @param: java.lang.String 申报部门联系方式-联系人手机
	 */
	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}

	/**
	 * 方法: 取得申报部门联系方式-联系人传真.
	 * @return: java.lang.String 申报部门联系方式-联系人传真
	 */
	@Column(name = "LINKMAN_FAX", nullable = true, length = 32)
	public String getLinkmanFax() {
		return this.linkmanFax;
	}

	/**
	 * 方法: 设置申报部门联系方式-联系人传真.
	 * @param: java.lang.String 申报部门联系方式-联系人传真
	 */
	public void setLinkmanFax(String linkmanFax) {
		this.linkmanFax = linkmanFax;
	}

	/**
	 * 方法: 取得状态 0草稿 1审批中 2完成.
	 * @return: int 状态 0草稿 1审批中 2完成
	 */
	@Column(name = "DEPT_ECC_STATUS", nullable = true, precision = 10)
	public int getDeptEccStatus() {
		return this.deptEccStatus;
	}

	/**
	 * 方法: 设置状态 0草稿 1审批中 2完成.
	 * @param: int 状态 0草稿 1审批中 2完成
	 */
	public void setDeptEccStatus(int deptEccStatus) {
		this.deptEccStatus = deptEccStatus;
	}
	@Column(name = "created_date", nullable = true, length = 32)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name = "created_by", nullable = true, length = 32)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name = "created_by_id", nullable = true, length = 32)
	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	@Column(name = "modified_date", nullable = true, length = 32)
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Column(name = "modified_by", nullable = true, length = 32)
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@Column(name = "modified_by_id", nullable = true, length = 32)
	public String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}
	@Column(name = "dept_id", nullable = true, length = 32)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name = "dept_name", nullable = true, length = 32)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Column(name = "proc_id", nullable = true, length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}
	@Column(name = "MEMEBER_IDS", nullable = true, length = 1024)
	public String getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(String memberIds) {
		this.memberIds = memberIds;
	}
	@Column(name = "MEMEBER_NAMES", nullable = true, length = 1024)
	public String getMemberNames() {
		return memberNames;
	}

	public void setMemberNames(String memberNames) {
		this.memberNames = memberNames;
	}
	@Column(name = "MEMEBER_NODE_IDS", nullable = true, length = 1024)
	public String getMemberNodeIds() {
		return memberNodeIds;
	}

	public void setMemberNodeIds(String memberNodeIds) {
		this.memberNodeIds = memberNodeIds;
	}
}
