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
 * @Description: OA_PERSON_NATIONAL,字段与数据库字段一一对应.
 * @author T
 * @date 2017-09-16 10:31:33
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BasePersonNational  implements Serializable {
	private static final long serialVersionUID = 1L;
	//EMP_ID.
	@Id
	@GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "EMP_ID",nullable = false,length = 30)
	private java.lang.String empId;
	//PERSON_NATIONAL.
	@Column(name = "PERSON_NATIONAL",nullable = true,length = 70)
	private java.lang.String personNational;
	
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
	 *方法: 取得PERSON_NATIONAL.
	 *@return: java.lang.String  PERSON_NATIONAL
	 */
	public java.lang.String getPersonNational(){
		return this.personNational;
	}

	/**
	 *方法: 设置PERSON_NATIONAL.
	 *@param: java.lang.String  PERSON_NATIONAL
	 */
	public void setPersonNational(java.lang.String personNational){
		this.personNational = personNational;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BasePersonNational(){
	
	}
	
	/**
	 * 构造函数.
	 * @param empId
	 */
	public BasePersonNational(String empId){
		this.empId = empId;
	} 
}
