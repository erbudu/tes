package com.supporter.prj.linkworks.oa.roll_information.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author linxiaosong
 * @version V1.0   
 */

@MappedSuperclass
public class BaseRollInformationContent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//id
	@Id
	@Column(name = "ROLL_INFOR_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String rollInforId;
	
	//信息内容
	@Column(name="INFORMATION_CONTENT" , nullable = true)
	private String informationContent;
	
	//
	@Column(name="READ_PERSONS" , nullable = true)
	private String readPersons;
	

	public BaseRollInformationContent() {
		super();
	}

	public BaseRollInformationContent(String rollInforId,
			String informationContent, String readPersons) {
		super();
		this.rollInforId = rollInforId;
		this.informationContent = informationContent;
		this.readPersons = readPersons;
	}

	public String getRollInforId() {
		return rollInforId;
	}

	public void setRollInforId(String rollInforId) {
		this.rollInforId = rollInforId;
	}

	public String getInformationContent() {
		return informationContent;
	}

	public void setInformationContent(String informationContent) {
		this.informationContent = informationContent;
	}

	public String getReadPersons() {
		return readPersons;
	}

	public void setReadPersons(String readPersons) {
		this.readPersons = readPersons;
	}
	
	

}
