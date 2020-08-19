package com.supporter.prj.linkworks.oa.invitation_f.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * OaInvitationPersons entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseInvitationPersons implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String recordId;
	private String invitationId;
	private String empName;
	private String nationality;
	private String workUnit;
	private String workDuty;
	private String passportNo;
	private String sex;
	private String birthdate;
	private String passportFileName;
	private String familyName;
	private String givenName;

	// Constructors

	/** default constructor */
	public BaseInvitationPersons() {
	}

	/** minimal constructor */
	public BaseInvitationPersons(String recordId) {
		this.recordId = recordId;
	}

	/** full constructor */
	public BaseInvitationPersons(String recordId, String invitationId,
			String empName, String nationality, String workUnit,
			String workDuty, String passportNo, String sex, String birthdate,
			String passportFileName, String familyName, String givenName) {
		this.recordId = recordId;
		this.invitationId = invitationId;
		this.empName = empName;
		this.nationality = nationality;
		this.workUnit = workUnit;
		this.workDuty = workDuty;
		this.passportNo = passportNo;
		this.sex = sex;
		this.birthdate = birthdate;
		this.passportFileName = passportFileName;
		this.familyName = familyName;
		this.givenName = givenName;
	}

	// Property accessors
	@Id
	@Column(name = "RECORD_ID", unique = true, nullable = false, length = 32)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "INVITATION_ID", length = 32)
	public String getInvitationId() {
		return this.invitationId;
	}

	public void setInvitationId(String invitationId) {
		this.invitationId = invitationId;
	}

	@Column(name = "EMP_NAME", length = 512)
	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name = "NATIONALITY", length = 128)
	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "WORK_UNIT", length = 1024)
	public String getWorkUnit() {
		return this.workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	@Column(name = "WORK_DUTY", length = 128)
	public String getWorkDuty() {
		return this.workDuty;
	}

	public void setWorkDuty(String workDuty) {
		this.workDuty = workDuty;
	}

	@Column(name = "PASSPORT_NO", length = 128)
	public String getPassportNo() {
		return this.passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	@Column(name = "SEX", length = 32)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "BIRTHDATE", length = 27)
	public String getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	@Column(name = "PASSPORT_FILE_NAME", length = 1024)
	public String getPassportFileName() {
		return this.passportFileName;
	}

	public void setPassportFileName(String passportFileName) {
		this.passportFileName = passportFileName;
	}

	@Column(name = "FAMILY_NAME", length = 128)
	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	@Column(name = "GIVEN_NAME", length = 128)
	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

}