package com.supporter.prj.linkworks.oa.outside_account_manager.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * BaseOutsideAccountManagerRec entity provides the base persistence
 * definition of the OaOutsideAccountManagerRec entity. @author MyEclipse
 * Persistence Tools
 */
@MappedSuperclass
public abstract class BaseOutsideAccountManagerRec implements
		java.io.Serializable {

	// Fields

	private String id;
	private String managerId;
	private String outsidePersonId;
	private String name;
	private String nameSpell;
	private String sex;
	private String deptId;
	private String deptName;
	private String userAccount;
	private String mail;
	private String thisTimeStatus;

	// Constructors

	/** default constructor */
	public BaseOutsideAccountManagerRec() {
	}

	/** minimal constructor */
	public BaseOutsideAccountManagerRec(String id) {
		this.id = id;
	}

	/** full constructor */
	public BaseOutsideAccountManagerRec(String id, String managerId,
			String outsidePersonId, String name, String nameSpell, String sex,
			String deptId, String deptName, String userAccount, String mail,
			String thisTimeStatus) {
		this.id = id;
		this.managerId = managerId;
		this.outsidePersonId = outsidePersonId;
		this.name = name;
		this.nameSpell = nameSpell;
		this.sex = sex;
		this.deptId = deptId;
		this.deptName = deptName;
		this.userAccount = userAccount;
		this.mail = mail;
		this.thisTimeStatus = thisTimeStatus;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "MANAGER_ID", length = 32)
	public String getManagerId() {
		return this.managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	@Column(name = "OUTSIDE_PERSON_ID", length = 32)
	public String getOutsidePersonId() {
		return this.outsidePersonId;
	}

	public void setOutsidePersonId(String outsidePersonId) {
		this.outsidePersonId = outsidePersonId;
	}

	@Column(name = "NAME", length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NAME_SPELL", length = 64)
	public String getNameSpell() {
		return this.nameSpell;
	}

	public void setNameSpell(String nameSpell) {
		this.nameSpell = nameSpell;
	}

	@Column(name = "SEX", length = 32)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "DEPT_ID", length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DEPT_NAME", length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "USER_ACCOUNT", length = 64)
	public String getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	@Column(name = "MAIL", length = 64)
	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

//	@Column(name = "LAST_TIME_STATUS", length = 32)
//	public String getLastTimeStatus() {
//		return this.lastTimeStatus;
//	}
//
//	public void setLastTimeStatus(String lastTimeStatus) {
//		this.lastTimeStatus = lastTimeStatus;
//	}

	@Column(name = "THIS_TIME_STATUS", length = 32)
	public String getThisTimeStatus() {
		return this.thisTimeStatus;
	}

	public void setThisTimeStatus(String thisTimeStatus) {
		this.thisTimeStatus = thisTimeStatus;
	}

}