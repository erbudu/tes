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
 * @Description: OA_PERSON_BIRTH_PLACE,字段与数据库字段一一对应.
 * @author T
 * @date 2017-09-16 10:11:39
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BasePersonBirthPlace  implements Serializable {
	private static final long serialVersionUID = 1L;
	//EMP_ID.
	@Id
	@GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "EMP_ID",nullable = false,length = 30)
	private java.lang.String empId;
	//BIRTH_PLACE.
	@Column(name = "BIRTH_PLACE",nullable = true,length = 6)
	private java.lang.String birthPlace;
	//REAL_BIRTHPLACE.
	@Column(name = "REAL_BIRTHPLACE",nullable = true,length = 6)
	private java.lang.String realBirthplace;
	
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
	 *方法: 取得BIRTH_PLACE.
	 *@return: java.lang.String  BIRTH_PLACE
	 */
	public java.lang.String getBirthPlace(){
		return this.birthPlace;
	}

	/**
	 *方法: 设置BIRTH_PLACE.
	 *@param: java.lang.String  BIRTH_PLACE
	 */
	public void setBirthPlace(java.lang.String birthPlace){
		this.birthPlace = birthPlace;
	}
	/**
	 *方法: 取得REAL_BIRTHPLACE.
	 *@return: java.lang.String  REAL_BIRTHPLACE
	 */
	public java.lang.String getRealBirthplace(){
		return this.realBirthplace;
	}

	/**
	 *方法: 设置REAL_BIRTHPLACE.
	 *@param: java.lang.String  REAL_BIRTHPLACE
	 */
	public void setRealBirthplace(java.lang.String realBirthplace){
		this.realBirthplace = realBirthplace;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BasePersonBirthPlace(){
	
	}
	
	/**
	 * 构造函数.
	 * @param empId
	 */
	public BasePersonBirthPlace(String empId){
		this.empId = empId;
	} 
}
