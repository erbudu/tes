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
 * @Description: OA_PERSON_BASIC_INFO,字段与数据库字段一一对应.
 * @author T
 * @date 2017-09-16 10:04:53
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BasePersonBasicInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	//HR_ID.
	@Id
	@GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "HR_ID",nullable = false,length = 30)
	private java.lang.String hrId;
	//EMP_ID.
	@Column(name = "EMP_ID",nullable = false,length = 30)
	private java.lang.String empId;
	//EMP_NAME.
	@Column(name = "EMP_NAME",nullable = true,length = 32)
	private java.lang.String empName;
	//BIRTH_DATE.
	@Column(name = "BIRTH_DATE",nullable = true)
	private java.util.Date birthDate;
	//SEX.
	@Column(name = "SEX",nullable = true,length = 70)
	private java.lang.String sex;
	//POLITICAL_LANDSCAPE.
	@Column(name = "POLITICAL_LANDSCAPE",nullable = true,length = 70)
	private java.lang.String politicalLandscape;
	//HR_DEPT_NAME.
	@Column(name = "HR_DEPT_NAME",nullable = true,length = 70)
	private java.lang.String hrDeptName;
	//BLOOD_TYPE.
	@Column(name = "BLOOD_TYPE",nullable = true,length = 70)
	private java.lang.String bloodType;
	
	/**
	 *方法: 取得HR_ID.
	 *@return: java.lang.String  HR_ID
	 */
	public java.lang.String getHrId(){
		return this.hrId;
	}

	/**
	 *方法: 设置HR_ID.
	 *@param: java.lang.String  HR_ID
	 */
	public void setHrId(java.lang.String hrId){
		this.hrId = hrId;
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
	 *方法: 取得EMP_NAME.
	 *@return: java.lang.String  EMP_NAME
	 */
	public java.lang.String getEmpName(){
		return this.empName;
	}

	/**
	 *方法: 设置EMP_NAME.
	 *@param: java.lang.String  EMP_NAME
	 */
	public void setEmpName(java.lang.String empName){
		this.empName = empName;
	}
	/**
	 *方法: 取得BIRTH_DATE.
	 *@return: java.util.Date  BIRTH_DATE
	 */
	public java.util.Date getBirthDate(){
		return this.birthDate;
	}

	/**
	 *方法: 设置BIRTH_DATE.
	 *@param: java.util.Date  BIRTH_DATE
	 */
	public void setBirthDate(java.util.Date birthDate){
		this.birthDate = birthDate;
	}
	/**
	 *方法: 取得SEX.
	 *@return: java.lang.String  SEX
	 */
	public java.lang.String getSex(){
		return this.sex;
	}

	/**
	 *方法: 设置SEX.
	 *@param: java.lang.String  SEX
	 */
	public void setSex(java.lang.String sex){
		this.sex = sex;
	}
	/**
	 *方法: 取得POLITICAL_LANDSCAPE.
	 *@return: java.lang.String  POLITICAL_LANDSCAPE
	 */
	public java.lang.String getPoliticalLandscape(){
		return this.politicalLandscape;
	}

	/**
	 *方法: 设置POLITICAL_LANDSCAPE.
	 *@param: java.lang.String  POLITICAL_LANDSCAPE
	 */
	public void setPoliticalLandscape(java.lang.String politicalLandscape){
		this.politicalLandscape = politicalLandscape;
	}
	/**
	 *方法: 取得HR_DEPT_NAME.
	 *@return: java.lang.String  HR_DEPT_NAME
	 */
	public java.lang.String getHrDeptName(){
		return this.hrDeptName;
	}

	/**
	 *方法: 设置HR_DEPT_NAME.
	 *@param: java.lang.String  HR_DEPT_NAME
	 */
	public void setHrDeptName(java.lang.String hrDeptName){
		this.hrDeptName = hrDeptName;
	}
	/**
	 *方法: 取得BLOOD_TYPE.
	 *@return: java.lang.String  BLOOD_TYPE
	 */
	public java.lang.String getBloodType(){
		return this.bloodType;
	}

	/**
	 *方法: 设置BLOOD_TYPE.
	 *@param: java.lang.String  BLOOD_TYPE
	 */
	public void setBloodType(java.lang.String bloodType){
		this.bloodType = bloodType;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BasePersonBasicInfo(){
	
	}
	
	/**
	 * 构造函数.
	 * @param hrId
	 */
	public BasePersonBasicInfo(String hrId){
		this.hrId = hrId;
	} 
}
