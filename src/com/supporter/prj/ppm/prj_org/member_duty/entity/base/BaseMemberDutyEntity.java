package com.supporter.prj.ppm.prj_org.member_duty.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 项目各业务负责人实体类
 * @author CHENHAO
 *@date 2019年12月2日
 */

@MappedSuperclass
public class BaseMemberDutyEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="RECORD_ID", nullable=false, length=32)
	private String recordId;						
	
	@Column(name="PRJ_ID", length=32)
	private String prjId;
	
	@Column(name="PRJ_CNAME", length=64)
	private String prjCName;
	
	@Column(name="PRJ_ENAME", length=64)
	private String prjEName;
	
	@Column(name="PERSON_ID", length=32)
	private String personId;
	
	@Column(name="PERSON_NAME", length=512)
	private String personName;
	
	@Column(name="RESPONSIBLE", length=1024)
	private String responsible;
	
	@Column(name="PERSON_DEPT_ID", length=32)
	private String personDeptId;
	
	@Column(name="PERSON_DEPT_NAME", length=128)
	private String personDeptName;
	
	@Column(name="LINK_TEL", length=64)
	private String linkTel;
	
	@Column(name="LINK_PHONE", length=64)
	private String linkPhone;
	
	@Column(name="LINK_TAX", length=64)
	private String linkTax;
	
	@Column(name="STATUS")
	private Integer status;	
	
	@Column(name="CREATED_BY",length=64)
	private String createdBy;		//CREATED_BY	Varchar2(64)	创建人名称

	@Column(name="CREATED_BY_ID",length=32)
	private String createdById;		//CREATED_BY_ID	Varchar2(32)	创建人ID
	
	@Column(name="CREATED_DATE")
	private Date createdDate;		//CREATED_DATE	DATE	创建时间

	@Column(name="MODIFIED_BY",length=64)
	private String modifiedBy;		//MODIFIED_BY	Varchar2(64)	修改人名称

	@Column(name="MODIFIED_BY_ID",length=32)
	private String modifiedById;	//MODIFIED_BY_ID	Varchar2(32)	修改人ID

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;		//MODIFIED_DATE	DATE	修改时间
	
	@Column(name="DEPT_NAME",length=200)
	private String deptName;		//DEPT_NAME	Varchar2(200)	创建人部门名称

	@Column(name="DEPT_ID",length=32)
	private String deptId;			//DEPT_ID	Varchar2(32)	创建人部门ID

	@Column(name="MEMBER_ROLE_ID",length=32)
	private String memberRoleId;	//MEMBER_ROLE_ID Varchar2(32) 成员角色id
	
	@Column(name="MEMBER_ROLE_NAME",length=64)
	private String memberRoleName; //MEMBER_ROLE_NAME Varchar2(64) 成员角色名称
	
	/*
	 * 无参构造方法
	 */
	public BaseMemberDutyEntity() {	}
	
	/*
	 * 有参构造方法 record  主键
	 */
	public BaseMemberDutyEntity(String recordId) {
		
		this.recordId = recordId;
	}
	
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	public String getPrjCName() {
		return prjCName;
	}

	public void setPrjCName(String prjCName) {
		this.prjCName = prjCName;
	}

	public String getPrjEName() {
		return prjEName;
	}

	public void setPrjEName(String prjEName) {
		this.prjEName = prjEName;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public String getPersonDeptId() {
		return personDeptId;
	}

	public void setPersonDeptId(String personDeptId) {
		this.personDeptId = personDeptId;
	}

	public String getPersonDeptName() {
		return personDeptName;
	}

	public void setPersonDeptName(String personDeptName) {
		this.personDeptName = personDeptName;
	}

	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getLinkTax() {
		return linkTax;
	}

	public void setLinkTax(String linkTax) {
		this.linkTax = linkTax;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return the memberRoleId
	 */
	public String getMemberRoleId() {
		return memberRoleId;
	}

	/**
	 * @param memberRoleId the memberRoleId to set
	 */
	public void setMemberRoleId(String memberRoleId) {
		this.memberRoleId = memberRoleId;
	}

	/**
	 * @return the memberRoleName
	 */
	public String getMemberRoleName() {
		return memberRoleName;
	}

	/**
	 * @param memberRoleName the memberRoleName to set
	 */
	public void setMemberRoleName(String memberRoleName) {
		this.memberRoleName = memberRoleName;
	}
	
	
	
}
