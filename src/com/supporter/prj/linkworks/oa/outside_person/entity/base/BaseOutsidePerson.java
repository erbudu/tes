package com.supporter.prj.linkworks.oa.outside_person.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * BaseOutsidePerson entity provides the base persistence definition of
 * the OaOutsidePerson entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class BaseOutsidePerson implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String nameSpell;
	private String sex;
	private String account;
	private String mail;
	private String deptId;
	private String deptName;
	private String confirmDate;
	private String status;

	// Constructors

	/** default constructor */
	public BaseOutsidePerson() {
	}

	/** minimal constructor */
	public BaseOutsidePerson(String id) {
		this.id = id;
	}

	/** full constructor */
	public BaseOutsidePerson(String id, String name, String nameSpell,
			String sex, String account, String mail, String deptId,
			String deptName, String confirmDate, String status) {
		this.id = id;
		this.name = name;
		this.nameSpell = nameSpell;
		this.sex = sex;
		this.account = account;
		this.mail = mail;
		this.deptId = deptId;
		this.deptName = deptName;
		this.confirmDate = confirmDate;
		this.status = status;
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

	@Column(name = "ACCOUNT", length = 64)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "MAIL", length = 64)
	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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

	@Column(name = "CONFIRM_DATE", length = 32)
	public String getConfirmDate() {
		return this.confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	@Column(name = "STATUS", length = 32)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}