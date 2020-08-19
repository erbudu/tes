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
 * @Description: OA_PERSON_TECHNICAL_POSITIONS_INFO,字段与数据库字段一一对应.
 * @author T
 * @date 2017-09-16 10:40:06
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BasePersonTechnicalPositionsInfo  implements Serializable {
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
	//STATUS.
	@Column(name = "STATUS",nullable = true,length = 10)
	private java.lang.String status;
	//TECHNICAL_POSITIONS.
	@Column(name = "TECHNICAL_POSITIONS",nullable = true,length = 70)
	private java.lang.String technicalPositions;
	
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
	 *方法: 取得TECHNICAL_POSITIONS.
	 *@return: java.lang.String  TECHNICAL_POSITIONS
	 */
	public java.lang.String getTechnicalPositions(){
		return this.technicalPositions;
	}

	/**
	 *方法: 设置TECHNICAL_POSITIONS.
	 *@param: java.lang.String  TECHNICAL_POSITIONS
	 */
	public void setTechnicalPositions(java.lang.String technicalPositions){
		this.technicalPositions = technicalPositions;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BasePersonTechnicalPositionsInfo(){
	
	}
	
	/**
	 * 构造函数.
	 * @param recordId
	 */
	public BasePersonTechnicalPositionsInfo(String recordId){
		this.recordId = recordId;
	} 
}
