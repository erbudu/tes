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
 * @Description: OA_PERSON_CERTIFICATIONS_INFO,字段与数据库字段一一对应.
 * @author T
 * @date 2017-09-16 10:20:07
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BasePersonCertificationsInfo  implements Serializable {
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
	//CERTIFICATIONS_NAME.
	@Column(name = "CERTIFICATIONS_NAME",nullable = true,length = 50)
	private java.lang.String certificationsName;
	//CERTIFICATIONS_LEVEL.
	@Column(name = "CERTIFICATIONS_LEVEL",nullable = true,length = 6)
	private java.lang.String certificationsLevel;
	//VALIDIT_DATE.
	@Column(name = "VALIDIT_DATE",nullable = true)
	private java.util.Date validitDate;
	
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
	 *方法: 取得CERTIFICATIONS_NAME.
	 *@return: java.lang.String  CERTIFICATIONS_NAME
	 */
	public java.lang.String getCertificationsName(){
		return this.certificationsName;
	}

	/**
	 *方法: 设置CERTIFICATIONS_NAME.
	 *@param: java.lang.String  CERTIFICATIONS_NAME
	 */
	public void setCertificationsName(java.lang.String certificationsName){
		this.certificationsName = certificationsName;
	}
	/**
	 *方法: 取得CERTIFICATIONS_LEVEL.
	 *@return: java.lang.String  CERTIFICATIONS_LEVEL
	 */
	public java.lang.String getCertificationsLevel(){
		return this.certificationsLevel;
	}

	/**
	 *方法: 设置CERTIFICATIONS_LEVEL.
	 *@param: java.lang.String  CERTIFICATIONS_LEVEL
	 */
	public void setCertificationsLevel(java.lang.String certificationsLevel){
		this.certificationsLevel = certificationsLevel;
	}
	/**
	 *方法: 取得VALIDIT_DATE.
	 *@return: java.util.Date  VALIDIT_DATE
	 */
	public java.util.Date getValiditDate(){
		return this.validitDate;
	}

	/**
	 *方法: 设置VALIDIT_DATE.
	 *@param: java.util.Date  VALIDIT_DATE
	 */
	public void setValiditDate(java.util.Date validitDate){
		this.validitDate = validitDate;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BasePersonCertificationsInfo(){
	
	}
	
	/**
	 * 构造函数.
	 * @param recordId
	 */
	public BasePersonCertificationsInfo(String recordId){
		this.recordId = recordId;
	} 
}
