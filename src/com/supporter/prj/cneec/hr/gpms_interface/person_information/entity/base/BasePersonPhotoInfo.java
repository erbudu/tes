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
 * @Description: OA_PERSON_PHOTO_INFO,字段与数据库字段一一对应.
 * @author T
 * @date 2017-09-16 10:36:20
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BasePersonPhotoInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	//EMP_ID.
	@Id
	@GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "EMP_ID",nullable = false,length = 30)
	private java.lang.String empId;
	//EMP_PHOTO.
	@Column(name = "EMP_PHOTO",nullable = true)
	private byte[] empPhoto;
	
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
	 *方法: 取得EMP_PHOTO.
	 *@return: byte[]  EMP_PHOTO
	 */
	public byte[] getEmpPhoto(){
		return this.empPhoto;
	}

	/**
	 *方法: 设置EMP_PHOTO.
	 *@param: byte[]  EMP_PHOTO
	 */
	public void setEmpPhoto(byte[] empPhoto){
		this.empPhoto = empPhoto;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BasePersonPhotoInfo(){
	
	}
	
	/**
	 * 构造函数.
	 * @param empId
	 */
	public BasePersonPhotoInfo(String empId){
		this.empId = empId;
	} 
}
