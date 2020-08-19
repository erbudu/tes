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
 * @Description: OA_PERSON_LANGUAGE_INFO,字段与数据库字段一一对应.
 * @author T
 * @date 2017-09-16 10:30:01
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BasePersonLanguageInfo  implements Serializable {
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
	//LANGUAGE_CLASS.
	@Column(name = "LANGUAGE_CLASS",nullable = true,length = 70)
	private java.lang.String languageClass;
	//LANGUAGE_LEVEL.
	@Column(name = "LANGUAGE_LEVEL",nullable = true,length = 30)
	private java.lang.String languageLevel;
	
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
	 *方法: 取得LANGUAGE_CLASS.
	 *@return: java.lang.String  LANGUAGE_CLASS
	 */
	public java.lang.String getLanguageClass(){
		return this.languageClass;
	}

	/**
	 *方法: 设置LANGUAGE_CLASS.
	 *@param: java.lang.String  LANGUAGE_CLASS
	 */
	public void setLanguageClass(java.lang.String languageClass){
		this.languageClass = languageClass;
	}
	/**
	 *方法: 取得LANGUAGE_LEVEL.
	 *@return: java.lang.String  LANGUAGE_LEVEL
	 */
	public java.lang.String getLanguageLevel(){
		return this.languageLevel;
	}

	/**
	 *方法: 设置LANGUAGE_LEVEL.
	 *@param: java.lang.String  LANGUAGE_LEVEL
	 */
	public void setLanguageLevel(java.lang.String languageLevel){
		this.languageLevel = languageLevel;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BasePersonLanguageInfo(){
	
	}
	
	/**
	 * 构造函数.
	 * @param recordId
	 */
	public BasePersonLanguageInfo(String recordId){
		this.recordId = recordId;
	} 
}
