package com.supporter.prj.linkworks.oa.abroad.entity.base;

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
public class BaseAbroadRealDate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//id
	@Id
	@Column(name = "REAL_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String realId;
	
	//出国审批表ID
	@Column(name="ABROAD_ID" ,length=32, nullable = true)
	private String abroadId;
	
	//实际出国时间
	@Column(name="REAL_DATE_APPLY" ,length=32, nullable = true)
	private String realDateApply;
	
	//
	@Column(name="REAL_DATE_CONFIRM" ,length=32, nullable = true)
	private String realDateConfirm;
	
	//
	@Column(name="CONFIRM_DATE" ,length=32, nullable = true)
	private String confirmDate;
	
	//审批状态
    @Column(name = "SWF_STATUS", precision = 2, scale = 0, nullable = true)
    private int swfStatus;
    

  //创建时间
	@Column(name="CREATED_DATE" ,length=27, nullable = true)
	private String createdDate;
	
	//创建人
	@Column(name="CREATED_BY" ,length=64, nullable = true)
	private String createdBy;
	
	//创建人Id
	@Column(name="CREATED_BY_ID" ,length=32, nullable = true)
	private String createdById;
    
	//修改时间
	@Column(name="MODIFIED_DATE" ,length=27, nullable = true)
	private String modifiedDate;
	
	//修改人
	@Column(name="MODIFIED_BY" ,length=64, nullable = true)
	private String modifiedBy;
	
	//修改人Id
	@Column(name="MODIFIED_BY_ID" ,length=32, nullable = true)
	private String modifiedById;
	  //流程Id
    @Column(name="PROC_ID" ,length=32, nullable = true)
    private String procId;
	//标识历史数据
	@Column(name = "IS_HISTORY")
	@org.hibernate.annotations.Type(type="true_false")
    private boolean history;

	public boolean getHistory() {
		return history;
	}
	
	
	
	public BaseAbroadRealDate(String realId, String abroadId,
			String realDateApply, String realDateConfirm, String confirmDate,
			int swfStatus, String createdDate, String createdBy,
			String createdById, String modifiedDate, String modifiedBy,
			String modifiedById) {
		super();
		this.realId = realId;
		this.abroadId = abroadId;
		this.realDateApply = realDateApply;
		this.realDateConfirm = realDateConfirm;
		this.confirmDate = confirmDate;
		this.swfStatus = swfStatus;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
	}

	
	
	
	public BaseAbroadRealDate() {
		super();
	}




	public String getRealId() {
		return realId;
	}

	public void setRealId(String realId) {
		this.realId = realId;
	}

	public String getAbroadId() {
		return abroadId;
	}

	public void setAbroadId(String abroadId) {
		this.abroadId = abroadId;
	}

	public String getRealDateApply() {
		return realDateApply;
	}

	public void setRealDateApply(String realDateApply) {
		this.realDateApply = realDateApply;
	}

	public String getRealDateConfirm() {
		return realDateConfirm;
	}

	public void setRealDateConfirm(String realDateConfirm) {
		this.realDateConfirm = realDateConfirm;
	}

	public String getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	public int getSwfStatus() {
		return swfStatus;
	}

	public void setSwfStatus(int swfStatus) {
		this.swfStatus = swfStatus;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}	
	
}
