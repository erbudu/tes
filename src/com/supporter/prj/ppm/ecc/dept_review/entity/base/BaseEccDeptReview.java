package com.supporter.prj.ppm.ecc.dept_review.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 部门出口管制评审,字段与数据库字段一一对应.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@MappedSuperclass
public class BaseEccDeptReview implements Serializable {

	private static final long serialVersionUID = 1L;
	private String deptEccId;
	private String eccId;
	private String projectId;
	private String prjNameC;
	private String prjNameE;
	private String prjNo;
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
	/**
	 * 无参构造函数.
	 */
	public BaseEccDeptReview() {
	}

	/**
	 * 构造函数.
	 *
	 * @param deptEccId
	 */
	public BaseEccDeptReview(String deptEccId) {
		this.deptEccId = deptEccId;
	}

	/**
	 * 方法: 取得DEPT_ECC_ID.
	 * @return: java.lang.String DEPT_ECC_ID
	 */
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "DEPT_ECC_ID", nullable = false, length = 32)
	public String getDeptEccId() {
		return this.deptEccId;
	}

	/**
	 * 方法: 设置DEPT_ECC_ID.
	 * @param: java.lang.String DEPT_ECC_ID
	 */
	public void setDeptEccId(String deptEccId) {
		this.deptEccId = deptEccId;
	}

	/**
	 * 方法: 取得出口管制id.
	 * @return: java.lang.String 出口管制id
	 */
	@Column(name = "ECC_ID", nullable = true, length = 32)
	public String getEccId() {
		return this.eccId;
	}

	/**
	 * 方法: 设置出口管制id.
	 * @param: java.lang.String 出口管制id
	 */
	public void setEccId(String eccId) {
		this.eccId = eccId;
	}

	/**
	 * 方法: 取得项目id.
	 * @return: java.lang.String 项目id
	 */
	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	/**
	 * 方法: 设置项目id.
	 * @param: java.lang.String 项目id
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * 方法: 取得项目名称 中文.
	 * @return: java.lang.String 项目名称 中文
	 */
	@Column(name = "PRJ_NAME_C", nullable = true, length = 128)
	public String getPrjNameC() {
		return this.prjNameC;
	}

	/**
	 * 方法: 设置项目名称 中文.
	 * @param: java.lang.String 项目名称 中文
	 */
	public void setPrjNameC(String prjNameC) {
		this.prjNameC = prjNameC;
	}

	/**
	 * 方法: 取得项目名称 英文.
	 * @return: java.lang.String 项目名称 英文
	 */
	@Column(name = "PRJ_NAME_E", nullable = true, length = 128)
	public String getPrjNameE() {
		return this.prjNameE;
	}

	/**
	 * 方法: 设置项目名称 英文.
	 * @param: java.lang.String 项目名称 英文
	 */
	public void setPrjNameE(String prjNameE) {
		this.prjNameE = prjNameE;
	}

	/**
	 * 方法: 取得项目编码.
	 * @return: java.lang.String 项目编码
	 */
	@Column(name = "PRJ_NO", nullable = true, length = 128)
	public String getPrjNo() {
		return this.prjNo;
	}

	/**
	 * 方法: 设置项目编码.
	 * @param: java.lang.String 项目编码
	 */
	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
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
	 * 方法: 取得部门初审状态  0 草稿 1审批中 2审批完成.
	 * @return: int 部门初审状态  0 草稿 1审批中 2审批完成
	 */
	@Column(name = "DEPT_ECC_STATUS", nullable = true, precision = 10)
	public int getDeptEccStatus() {
		return this.deptEccStatus;
	}

	/**
	 * 方法: 设置部门初审状态  0 草稿 1审批中 2审批完成.
	 * @param: int 部门初审状态  0 草稿 1审批中 2审批完成
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
}
