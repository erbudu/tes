package com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.base;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**   
 * @Title: Entity
 * @Description: OA_PERSON_EDUCATION_INFO,字段与数据库字段一一对应.
 * @author T
 * @date 2017-09-16 10:25:19
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BasePersonEducationInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	//RECORD_ID.
	@Id
	@GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "RECORD_ID",nullable = false,length = 34)
	private java.lang.String recordId;
	//EMP_ID.
	@Column(name = "EMP_ID",nullable = true,length = 30)
	private java.lang.String empId;
	//GRADUATED_SCHOLL.
	@Column(name = "GRADUATED_SCHOLL",nullable = true,length = 50)
	private java.lang.String graduatedScholl;
	//GRADUATION_DATE.
	@Column(name = "GRADUATION_DATE",nullable = true,length = 6)
	private java.lang.String graduationDate;
	//EDUCATION_CODE.
	@Column(name = "EDUCATION_CODE",nullable = true,length = 6)
	private java.lang.String educationCode;
	//EDUCATION_DESC.
	@Column(name = "EDUCATION_DESC",nullable = true,length = 50)
	private java.lang.String educationDesc;
	//DEGREE_DESC.
	@Column(name = "DEGREE_DESC",nullable = true,length = 6)
	private java.lang.String degreeDesc;
	//SPECIALTY_CLASS.
	@Column(name = "SPECIALTY_CLASS",nullable = true,length = 6)
	private java.lang.String specialtyClass;
	
	/**
	 *方法: 取得RECORD_ID.
	 *@return: java.lang.String  RECORD_ID
	 */
	public java.lang.String getRecordId(){
		return this.recordId;
	}

	/**
	 *方法: 设置RECORD_ID.
	 *@param: java.lang.String  RECORD_ID
	 */
	public void setRecordId(java.lang.String recordId){
		this.recordId = recordId;
	}
	/**
	 *方法: 取得EMP_ID.
	 *@return: java.lang.String  EMP_ID
	 */
	public java.lang.String getEmpId(){
		return this.empId;
	}

	/**
	 *方法: 设置EMP_ID.
	 *@param: java.lang.String  EMP_ID
	 */
	public void setEmpId(java.lang.String empId){
		this.empId = empId;
	}
	/**
	 *方法: 取得GRADUATED_SCHOLL.
	 *@return: java.lang.String  GRADUATED_SCHOLL
	 */
	public java.lang.String getGraduatedScholl(){
		return this.graduatedScholl;
	}

	/**
	 *方法: 设置GRADUATED_SCHOLL.
	 *@param: java.lang.String  GRADUATED_SCHOLL
	 */
	public void setGraduatedScholl(java.lang.String graduatedScholl){
		this.graduatedScholl = graduatedScholl;
	}
	/**
	 *方法: 取得GRADUATION_DATE.
	 *@return: java.lang.String  GRADUATION_DATE
	 */
	public java.lang.String getGraduationDate(){
		return this.graduationDate;
	}

	/**
	 *方法: 设置GRADUATION_DATE.
	 *@param: java.lang.String  GRADUATION_DATE
	 */
	public void setGraduationDate(java.lang.String graduationDate){
		this.graduationDate = graduationDate;
	}
	/**
	 *方法: 取得EDUCATION_CODE.
	 *@return: java.lang.String  EDUCATION_CODE
	 */
	public java.lang.String getEducationCode(){
		return this.educationCode;
	}

	/**
	 *方法: 设置EDUCATION_CODE.
	 *@param: java.lang.String  EDUCATION_CODE
	 */
	public void setEducationCode(java.lang.String educationCode){
		this.educationCode = educationCode;
	}
	/**
	 *方法: 取得EDUCATION_DESC.
	 *@return: java.lang.String  EDUCATION_DESC
	 */
	public java.lang.String getEducationDesc(){
		return this.educationDesc;
	}

	/**
	 *方法: 设置EDUCATION_DESC.
	 *@param: java.lang.String  EDUCATION_DESC
	 */
	public void setEducationDesc(java.lang.String educationDesc){
		this.educationDesc = educationDesc;
	}
	/**
	 *方法: 取得DEGREE_DESC.
	 *@return: java.lang.String  DEGREE_DESC
	 */
	public java.lang.String getDegreeDesc(){
		return this.degreeDesc;
	}

	/**
	 *方法: 设置DEGREE_DESC.
	 *@param: java.lang.String  DEGREE_DESC
	 */
	public void setDegreeDesc(java.lang.String degreeDesc){
		this.degreeDesc = degreeDesc;
	}
	/**
	 *方法: 取得SPECIALTY_CLASS.
	 *@return: java.lang.String  SPECIALTY_CLASS
	 */
	public java.lang.String getSpecialtyClass(){
		return this.specialtyClass;
	}

	/**
	 *方法: 设置SPECIALTY_CLASS.
	 *@param: java.lang.String  SPECIALTY_CLASS
	 */
	public void setSpecialtyClass(java.lang.String specialtyClass){
		this.specialtyClass = specialtyClass;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BasePersonEducationInfo(){
	
	}
	
	/**
	 * 构造函数.
	 * @param recordId
	 */
	public BasePersonEducationInfo(String recordId){
		this.recordId = recordId;
	} 
}
