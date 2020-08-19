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
 * @Description: OA_PERSON_EXPERIENCE_INFO,字段与数据库字段一一对应.
 * @author T
 * @date 2017-09-16 10:27:32
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BasePersonExperienceInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	//RECORD_ID.
	@Id
	@GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "RECORD_ID",nullable = false,length = 38)
	private java.lang.String recordId;
	//EMP_ID.
	@Column(name = "EMP_ID",nullable = true,length = 30)
	private java.lang.String empId;
	//START_DATE.
	@Column(name = "START_DATE",nullable = true)
	private java.util.Date startDate;
	//END_DATE.
	@Column(name = "END_DATE",nullable = true)
	private java.util.Date endDate;
	//WORK_PLACE.
	@Column(name = "WORK_PLACE",nullable = true,length = 70)
	private java.lang.String workPlace;
	//WORK_POST.
	@Column(name = "WORK_POST",nullable = true,length = 40)
	private java.lang.String workPost;
	
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
	 *方法: 取得WORK_PLACE.
	 *@return: java.lang.String  WORK_PLACE
	 */
	public java.lang.String getWorkPlace(){
		return this.workPlace;
	}

	/**
	 *方法: 设置WORK_PLACE.
	 *@param: java.lang.String  WORK_PLACE
	 */
	public void setWorkPlace(java.lang.String workPlace){
		this.workPlace = workPlace;
	}
	/**
	 *方法: 取得WORK_POST.
	 *@return: java.lang.String  WORK_POST
	 */
	public java.lang.String getWorkPost(){
		return this.workPost;
	}

	/**
	 *方法: 设置WORK_POST.
	 *@param: java.lang.String  WORK_POST
	 */
	public void setWorkPost(java.lang.String workPost){
		this.workPost = workPost;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BasePersonExperienceInfo(){
	
	}
	
	/**
	 * 构造函数.
	 * @param recordId
	 */
	public BasePersonExperienceInfo(String recordId){
		this.recordId = recordId;
	} 
}
