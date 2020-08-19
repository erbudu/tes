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
 * @Description: OA_PERSON_PERFORMANCE_INFO,字段与数据库字段一一对应.
 * @author T
 * @date 2017-09-16 10:34:08
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BasePersonPerformanceInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	//RECORD_ID.
	@Id
	@GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "RECORD_ID",nullable = false,length = 40)
	private java.lang.String recordId;
	//EMP_ID.
	@Column(name = "EMP_ID",nullable = true,length = 30)
	private java.lang.String empId;
	//STATUS.
	@Column(name = "STATUS",nullable = true,length = 10)
	private java.lang.String status;
	//PRJ_NAME.
	@Column(name = "PRJ_NAME",nullable = true,length = 100)
	private java.lang.String prjName;
	//EXECU_DATE.
	@Column(name = "EXECU_DATE",nullable = true,length = 20)
	private java.lang.String execuDate;
	//PRJ_ROLE.
	@Column(name = "PRJ_ROLE",nullable = true,length = 40)
	private java.lang.String prjRole;
	
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
	 *方法: 取得PRJ_NAME.
	 *@return: java.lang.String  PRJ_NAME
	 */
	public java.lang.String getPrjName(){
		return this.prjName;
	}

	/**
	 *方法: 设置PRJ_NAME.
	 *@param: java.lang.String  PRJ_NAME
	 */
	public void setPrjName(java.lang.String prjName){
		this.prjName = prjName;
	}
	/**
	 *方法: 取得EXECU_DATE.
	 *@return: java.lang.String  EXECU_DATE
	 */
	public java.lang.String getExecuDate(){
		return this.execuDate;
	}

	/**
	 *方法: 设置EXECU_DATE.
	 *@param: java.lang.String  EXECU_DATE
	 */
	public void setExecuDate(java.lang.String execuDate){
		this.execuDate = execuDate;
	}
	/**
	 *方法: 取得PRJ_ROLE.
	 *@return: java.lang.String  PRJ_ROLE
	 */
	public java.lang.String getPrjRole(){
		return this.prjRole;
	}

	/**
	 *方法: 设置PRJ_ROLE.
	 *@param: java.lang.String  PRJ_ROLE
	 */
	public void setPrjRole(java.lang.String prjRole){
		this.prjRole = prjRole;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BasePersonPerformanceInfo(){
	
	}
	
	/**
	 * 构造函数.
	 * @param recordId
	 */
	public BasePersonPerformanceInfo(String recordId){
		this.recordId = recordId;
	} 
}
