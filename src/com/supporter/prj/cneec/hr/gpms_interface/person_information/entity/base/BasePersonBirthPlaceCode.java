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
 * @Description: OA_PERSON_BIRTH_PLACE_CODE,字段与数据库字段一一对应.
 * @author T
 * @date 2017-09-16 10:17:07
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BasePersonBirthPlaceCode  implements Serializable {
	private static final long serialVersionUID = 1L;
	//CODE_ID.
	@Id
	@GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "CODE_ID",nullable = false,length = 20)
	private java.lang.String codeId;
	//BIRTH_PLACE.
	@Column(name = "BIRTH_PLACE",nullable = true,length = 70)
	private java.lang.String birthPlace;
	
	/**
	 *方法: 取得CODE_ID.
	 *@return: java.lang.String  CODE_ID
	 */
	public java.lang.String getCodeId(){
		return this.codeId;
	}

	/**
	 *方法: 设置CODE_ID.
	 *@param: java.lang.String  CODE_ID
	 */
	public void setCodeId(java.lang.String codeId){
		this.codeId = codeId;
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
	 * 无参构造函数.
	 */
	public BasePersonBirthPlaceCode(){
	
	}
	
	/**
	 * 构造函数.
	 * @param codeId
	 */
	public BasePersonBirthPlaceCode(String codeId){
		this.codeId = codeId;
	} 
}
