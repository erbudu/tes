package com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.base;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: OA_PERSON_ADMIN_DUTIES_INFO,字段与数据库字段一一对应.
 * @author T
 * @date 2017-09-21 10:45:16
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BasePersonAdminDutiesInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	//RECORD_ID.
	@Id
	@GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "RECORD_ID",nullable = false,length = 38)
	private java.lang.String recordId;
	//EMP_ID.
	@Column(name = "EMP_ID",nullable = false,length = 30)
	private java.lang.String empId;
	//STATUS.
	@Column(name = "STATUS",nullable = true,length = 10)
	private java.lang.String status;
	//ADMINISTRATIVE_DUTIES.
	@Column(name = "ADMINISTRATIVE_DUTIES",nullable = true,length = 70)
	private java.lang.String administrativeDuties;
	//ADMINISTRATIVE_DUTIES_OTHER.
	@Column(name = "ADMINISTRATIVE_DUTIES_OTHER",nullable = true,length = 70)
	private java.lang.String administrativeDutiesOther;
	//ROLE_NUMBER.
	@Column(name = "ROLE_NUMBER",nullable = true,length = 40)
	private java.lang.String roleNumber;
	//START_DATE.
	@Column(name = "START_DATE",nullable = true)
	private java.util.Date startDate;
	//END_DATE.
	@Column(name = "END_DATE",nullable = true)
	private java.util.Date endDate;
	
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
	 *方法: 取得STATUS.
	 *@return: java.lang.String  STATUS
	 */
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置STATUS.
	 *@param: java.lang.String  STATUS
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	/**
	 *方法: 取得ADMINISTRATIVE_DUTIES.
	 *@return: java.lang.String  ADMINISTRATIVE_DUTIES
	 */
	public java.lang.String getAdministrativeDuties(){
		return this.administrativeDuties;
	}

	/**
	 *方法: 设置ADMINISTRATIVE_DUTIES.
	 *@param: java.lang.String  ADMINISTRATIVE_DUTIES
	 */
	public void setAdministrativeDuties(java.lang.String administrativeDuties){
		this.administrativeDuties = administrativeDuties;
	}
	/**
	 *方法: 取得ADMINISTRATIVE_DUTIES_OTHER.
	 *@return: java.lang.String  ADMINISTRATIVE_DUTIES_OTHER
	 */
	public java.lang.String getAdministrativeDutiesOther(){
		return this.administrativeDutiesOther;
	}

	/**
	 *方法: 设置ADMINISTRATIVE_DUTIES_OTHER.
	 *@param: java.lang.String  ADMINISTRATIVE_DUTIES_OTHER
	 */
	public void setAdministrativeDutiesOther(java.lang.String administrativeDutiesOther){
		this.administrativeDutiesOther = administrativeDutiesOther;
	}
	/**
	 *方法: 取得ROLE_NUMBER.
	 *@return: java.lang.String  ROLE_NUMBER
	 */
	public java.lang.String getRoleNumber(){
		return this.roleNumber;
	}

	/**
	 *方法: 设置ROLE_NUMBER.
	 *@param: java.lang.String  ROLE_NUMBER
	 */
	public void setRoleNumber(java.lang.String roleNumber){
		this.roleNumber = roleNumber;
	}
	/**
	 *方法: 取得START_DATE.
	 *@return: java.util.Date  START_DATE
	 */
	public java.util.Date getStartDate(){
		return this.startDate;
	}

	/**
	 *方法: 设置START_DATE.
	 *@param: java.util.Date  START_DATE
	 */
	public void setStartDate(java.util.Date startDate){
		this.startDate = startDate;
	}
	/**
	 *方法: 取得END_DATE.
	 *@return: java.util.Date  END_DATE
	 */
	public java.util.Date getEndDate(){
		return this.endDate;
	}

	/**
	 *方法: 设置END_DATE.
	 *@param: java.util.Date  END_DATE
	 */
	public void setEndDate(java.util.Date endDate){
		this.endDate = endDate;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BasePersonAdminDutiesInfo(){
	
	}
	
	/**
	 * 构造函数.
	 * @param recordId
	 */
	public BasePersonAdminDutiesInfo(String recordId){
		this.recordId = recordId;
	} 
}
