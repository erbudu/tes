package com.supporter.prj.linkworks.oa.abroad.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author linxiaosong
 * @version V1.0   
 */
@MappedSuperclass
public class BaseAbroadPerson implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "RECORD_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String recordId;
	
	//出国审批表ID
	@Column(name="ABROAD_RECORD_ID" ,length=32, nullable = true)
	private String abroadRecordId;
	//人员类型（内部1还是外部0）
    @Column(name = "PERSON_TYPE", precision = 2, scale = 0, nullable = true)
	private Integer personType = 0;
	//人员Id
	@Column(name="PERSON_ID" ,length=32, nullable = true)
	private String personId;
	//人员名称
	@Column(name="PERSON_NAME" ,length=64, nullable = true)
	private String personName;
	//人员性别
	@Column(name="PERSON_GENDER" ,length=8, nullable = true)
	private String personGender;
	//出生日期
	@Column(name="BIRTH_DATE" ,length=27, nullable = true)
	private String birthDate;
	//出生地
	@Column(name="BIRTH_PLACE" ,length=64, nullable = true)
	private String birthPlace;
	//工作单位
	@Column(name="WORKING_UNIT" ,length=255, nullable = true)
	private String workingUnit;
	//职务
	@Column(name="POSITION_TITLE" ,length=64, nullable = true)
	private String positionTitle;
	//对外身份
	@Column(name="EXTERNAL_POSITION" ,length=64, nullable = true)
	private String externalPosition;

	
	
	
	public BaseAbroadPerson() {
		super();
	}


	public BaseAbroadPerson(String recordId) {
		super();
		this.recordId = recordId;
	}


	public BaseAbroadPerson(String recordId, String abroadRecordId,
			Integer personType, String personId, String personName,
			String personGender, String birthDate, String birthPlace,
			String workingUnit, String positionTitle, String externalPosition) {
		super();
		this.recordId = recordId;
		this.abroadRecordId = abroadRecordId;
		this.personType = personType;
		this.personId = personId;
		this.personName = personName;
		this.personGender = personGender;
		this.birthDate = birthDate;
		this.birthPlace = birthPlace;
		this.workingUnit = workingUnit;
		this.positionTitle = positionTitle;
		this.externalPosition = externalPosition;
	}
	
	
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getAbroadRecordId() {
		return abroadRecordId;
	}
	public void setAbroadRecordId(String abroadRecordId) {
		this.abroadRecordId = abroadRecordId;
	}
	public Integer getPersonType() {
		return personType;
	}
	public void setPersonType(Integer personType) {
		this.personType = personType;
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
	public String getPersonGender() {
		return personGender;
	}
	public void setPersonGender(String personGender) {
		this.personGender = personGender;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	public String getWorkingUnit() {
		return workingUnit;
	}
	public void setWorkingUnit(String workingUnit) {
		this.workingUnit = workingUnit;
	}
	public String getPositionTitle() {
		return positionTitle;
	}
	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}
	public String getExternalPosition() {
		return externalPosition;
	}
	public void setExternalPosition(String externalPosition) {
		this.externalPosition = externalPosition;
	}
	

	
}
