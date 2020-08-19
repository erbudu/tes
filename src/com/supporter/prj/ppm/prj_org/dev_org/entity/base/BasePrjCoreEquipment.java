/**
 * 
 */
package com.supporter.prj.ppm.prj_org.dev_org.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *<p>Title: BasePrjCoreEquipment</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月27日
 * 
 */
@MappedSuperclass
public class BasePrjCoreEquipment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="EQUIPMENT_ID",unique=true, nullable=false, length=32)
	private String equipmentId;//----------------------------------------核心设备供应商主键 DB:主键ID	EQUIPMENT_ID	Varchar2(32)
	
	@Column(name="PRJ_ID",length=32)
	private String prjId;//----------------------------------------------项目主键 DB:项目主键	PRJ_ID	Varchar2(32)	项目主键
	
	@Column(name="EQUIPMENT_NAME",length=128)
	private String equipmentName;//--------------------------------------核心设备供应商名称 DB：供货商名称	EQUIPMENT_NAME	Varchar2(128)	
	
	@Column(name="PROCUCT_NAME",length=512)
	private String productName;//----------------------------------------核心设备产品名称 DB： 产品名称	PROCUCT_NAME	Varchar2(512)	多个产品名称
	
	
	/*          The following is constructor  method   ↓↓        */
	public BasePrjCoreEquipment() {
		
	}

	
	public BasePrjCoreEquipment(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	
	/*           The following is set and get method    ↓↓    */
	
	/**
	 * @return the equipmentId
	 */
	public String getEquipmentId() {
		return equipmentId;
	}


	/**
	 * @param equipmentId the equipmentId to set
	 */
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}


	/**
	 * @return the prjId
	 */
	public String getPrjId() {
		return prjId;
	}


	/**
	 * @param prjId the prjId to set
	 */
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}


	/**
	 * @return the equipmentName
	 */
	public String getEquipmentName() {
		return equipmentName;
	}


	/**
	 * @param equipmentName the equipmentName to set
	 */
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}


	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}


	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	
}
