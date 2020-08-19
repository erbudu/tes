package com.supporter.prj.ppm.ecc.group_review.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 集团管制审核表,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccGroupReview implements Serializable {

	private static final long serialVersionUID = 1L;
	private String eccGroupId;
	private String eccId;
	private String prjId;
	private java.util.Date planStartDate;
	private java.util.Date planEndDate;
	private String linkmanName;
	private String linkmanTelephone;
	private String linkmanPhone;
	private String linkmanFax;
	private int groupEccStatus;
	private Date createdDate;
	private String createdBy;
	private String createdById;
	private Date modifiedDate;
	private String modifiedBy;
	private String modifiedById;
	private String deptId;
	private String deptName;
	private String procId;
	/**
	 * 无参构造函数.
	 */
	public BaseEccGroupReview() {
	}

	/**
	 * 构造函数.
	 *
	 * @param eccGroupId
	 */
	public BaseEccGroupReview(String eccGroupId) {
		this.eccGroupId = eccGroupId;
	}

	/**
	 * 方法: 取得ECC_GROUP_ID.
	 * @return: java.lang.String ECC_GROUP_ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "ECC_GROUP_ID", nullable = false, length = 32)
	public String getEccGroupId() {
		return this.eccGroupId;
	}

	/**
	 * 方法: 设置ECC_GROUP_ID.
	 * @param: java.lang.String ECC_GROUP_ID
	 */
	public void setEccGroupId(String eccGroupId) {
		this.eccGroupId = eccGroupId;
	}

	/**
	 * 方法: 取得管制审核主键.
	 * @return: java.lang.String 管制审核主键
	 */
	@Column(name = "ECC_ID", nullable = true, length = 32)
	public String getEccId() {
		return this.eccId;
	}

	/**
	 * 方法: 设置管制审核主键.
	 * @param: java.lang.String 管制审核主键
	 */
	public void setEccId(String eccId) {
		this.eccId = eccId;
	}

	/**
	 * 方法: 取得项目ID.
	 * @return: java.lang.String 项目ID
	 */
	@Column(name = "PRJ_ID", nullable = true, length = 32)
	public String getPrjId() {
		return this.prjId;
	}

	/**
	 * 方法: 设置项目ID.
	 * @param: java.lang.String 项目ID
	 */
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	/**
	 * 方法: 取得预计报审开始日期.
	 * @return: java.util.Date 预计报审开始日期
	 */
	@Column(name = "PLAN_START_DATE", nullable = true)
	public java.util.Date getPlanStartDate() {
		return this.planStartDate;
	}

	/**
	 * 方法: 设置预计报审开始日期.
	 * @param: java.util.Date 预计报审开始日期
	 */
	public void setPlanStartDate(java.util.Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	/**
	 * 方法: 取得预计报审完成日期.
	 * @return: java.util.Date 预计报审完成日期
	 */
	@Column(name = "PLAN_END_DATE", nullable = true)
	public java.util.Date getPlanEndDate() {
		return this.planEndDate;
	}

	/**
	 * 方法: 设置预计报审完成日期.
	 * @param: java.util.Date 预计报审完成日期
	 */
	public void setPlanEndDate(java.util.Date planEndDate) {
		this.planEndDate = planEndDate;
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
	 * 方法: 取得装填 0草稿 1审核中 2完成.
	 * @return: int 装填 0草稿 1审核中 2完成
	 */
	@Column(name = "GROUP_ECC_STATUS", nullable = true, precision = 10)
	public int getGroupEccStatus() {
		return this.groupEccStatus;
	}

	/**
	 * 方法: 设置装填 0草稿 1审核中 2完成.
	 * @param: int 装填 0草稿 1审核中 2完成
	 */
	public void setGroupEccStatus(int groupEccStatus) {
		this.groupEccStatus = groupEccStatus;
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
}
