package com.supporter.prj.pm.enginee_negotiate.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

/**   
 * @Title: 签证
 * @Description: PM_ENGINEE_VISA, 字段与数据库字段一一对应.
 * @author Administrator
 * @date 2018-07-04 18:08:56
 * @version V1.0   
 *
 */
@MappedSuperclass
public  class BaseEngineeVisa  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *VISA_ID.
	 */
	@Id
	@Column(name = "VISA_ID", unique = true, nullable = false, length = 32)
	private java.lang.String visaId;
	
	@Column(name = "NEGOTIATE_ID", nullable = true, length = 32)
	private java.lang.String negotiateId;
	@Column(name = "NEGOTIATE_NO", nullable = true, length = 128)
	private java.lang.String negotiateNo;
	
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	private java.lang.String contractId;
	@Column(name = "CONTRACT_NO", nullable = true, length = 64)
	private java.lang.String contractNo;
	@Column(name = "CONTRACT_NAME", nullable = true, length = 128)
	private java.lang.String contractName;
	@Column(name = "CURRENCY_ID", nullable = true, length = 32)
	private java.lang.String currencyId;
	@Column(name = "CURRENCY", nullable = true, length = 32)
	private java.lang.String currency;
	
	@Column(name = "prj_id", nullable = true, length = 32)
	private java.lang.String prjId;
	@Column(name = "prj_name", nullable = true, length = 128)
	private java.lang.String prjName;
	/**
	 *APPLY_NAME.
	 */
	@Column(name = "APPLY_NAME", nullable = true, length = 128)
	private java.lang.String applyName;
	/**
	 *APPLY_NO.
	 */
	@Column(name = "APPLY_NO", nullable = true, length = 128)
	private java.lang.String applyNo;
	/**
	 *APPLY_TYPE_ID.
	 */
	@Column(name = "APPLY_TYPE_ID", nullable = true, length = 32)
	private java.lang.String applyTypeId;
	/**
	 *APPLY_TYPE_NAME.
	 */
	@Column(name = "APPLY_TYPE_NAME", nullable = true, length = 32)
	private java.lang.String applyTypeName;
	/**
	 *CREATED_BY_ID.
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	private java.lang.String createdById;
	/**
	 *CREATED_BY_NAME.
	 */
	@Column(name = "CREATED_BY_NAME", nullable = true, length = 64)
	private java.lang.String createdByName;
	/**
	 *CREATED_DEPT_ID.
	 */
	@Column(name = "CREATED_DEPT_ID", nullable = true, length = 32)
	private java.lang.String createdDeptId;
	/**
	 *CREATED_DEPT_NAME.
	 */
	@Column(name = "CREATED_DEPT_NAME", nullable = true, length = 128)
	private java.lang.String createdDeptName;
	/**
	 *CREATED_DATE.
	 */
	@Column(name = "CREATED_DATE", nullable = true)
	private java.util.Date createdDate;
	/**
	 *MODIFIED_BY_ID.
	 */
	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	private java.lang.String modifiedById;
	/**
	 *MODIFIED_BY_NAME.
	 */
	@Column(name = "MODIFIED_BY_NAME", nullable = true, length = 64)
	private java.lang.String modifiedByName;
	/**
	 *MODIFIED_DATE.
	 */
	@Column(name = "MODIFIED_DATE", nullable = true)
	private java.util.Date modifiedDate;
	
	@Column(name = "STATUS", nullable = true, precision = 10)
	private int status = -1;
	
	/**
	 *speciality_id.
	 */
	@Column(name = "speciality_id", nullable = true, length = 32)
	private java.lang.String specialityId;
	/**
	 *CONTRACT_ID.
	 */
	@Column(name = "speciality", nullable = true, length = 64)
	private java.lang.String speciality;
	
	@Column(name = "file_type", nullable = true, length = 32)
	private java.lang.String fileType;
	@Column(name = "file_type_id", nullable = true, length = 32)
	private java.lang.String fileTypeId;
	
	@Column(name = "model_type", nullable = true, length = 32)
	private java.lang.String modelType;
	@Column(name = "model_type_id", nullable = true, length = 32)
	private java.lang.String modelTypeId;
	
	@Column(name = "confirm_model_type", nullable = true, length = 32)
	private java.lang.String confirmModelType;
	@Column(name = "confirm_model_type_id", nullable = true, length = 32)
	private java.lang.String confirmModelTypeId;

	@Column(name = "is_rewinding", nullable = false, length = 1)
	@Type(type = "true_false")
 	private boolean isRewinding; //文档编写是否归卷
	
	@Column(name = "is_rewind_two", nullable = false, length = 1)
	@Type(type = "true_false")
	private boolean isRewindTwo; // 文档2是否归卷

	@Column(name = "is_exist")
 	private int isExist; //文档编写是否存在
	
	@Column(name = "is_exist_two")
	private int isExistTwo; // 文档编写是否存在

	@Column(name = "status_scan")
	private int statusScan; //扫描是否完成,1完成
	
	@Column(name = "proc_id",length = 32)
	private String procId; 
	
	@Column(name = "en_name",length = 128)
	private String enName; 
	
	@Column(name = "visa_item")
	private int visaItem;

	public java.lang.String getVisaId() {
		return this.visaId;
	}
	public void setVisaId(java.lang.String visaId) {
		this.visaId = visaId;
	}
	
	public java.lang.String getNegotiateId() {
		return this.negotiateId;
	}
	public void setNegotiateId(java.lang.String negotiateId) {
		this.negotiateId = negotiateId;
	}
	
	public java.lang.String getNegotiateNo() {
		return this.negotiateNo;
	}
	public void setNegotiateNo(java.lang.String negotiateNo) {
		this.negotiateNo = negotiateNo;
	}
	
	public java.lang.String getContractId() {
		return this.contractId;
	}
	public void setContractId(java.lang.String contractId) {
		this.contractId = contractId;
	}
	public java.lang.String getContractNo() {
		return this.contractNo;
	}
	public void setContractNo(java.lang.String contractNo) {
		this.contractNo = contractNo;
	}
	public java.lang.String getContractName() {
		return this.contractName;
	}
	public void setContractName(java.lang.String contractName) {
		this.contractName = contractName;
	}
	public java.lang.String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(java.lang.String currencyId) {
		this.currencyId = currencyId;
	}
	public java.lang.String getCurrency() {
		return currency;
	}
	public void setCurrency(java.lang.String currency) {
		this.currency = currency;
	}
	@Column(name = "prj_id", length = 32)
	public java.lang.String getPrjId() {
		return prjId;
	}

	public void setPrjId(java.lang.String prjId) {
		this.prjId = prjId;
	}
	
	@Column(name = "prj_name", length = 128)
	public java.lang.String getPrjName() {
		return prjName;
	}

	public void setPrjName(java.lang.String prjName) {
		this.prjName = prjName;
	}
	public java.lang.String getApplyName() {
		return this.applyName;
	}
	public void setApplyName(java.lang.String applyName) {
		this.applyName = applyName;
	}
	
	public java.lang.String getApplyNo() {
		return this.applyNo;
	}
	public void setApplyNo(java.lang.String applyNo) {
		this.applyNo = applyNo;
	}
	
	public java.lang.String getApplyTypeId() {
		return this.applyTypeId;
	}
	public void setApplyTypeId(java.lang.String applyTypeId) {
		this.applyTypeId = applyTypeId;
	}
	
	public java.lang.String getApplyTypeName() {
		return this.applyTypeName;
	}
	public void setApplyTypeName(java.lang.String applyTypeName) {
		this.applyTypeName = applyTypeName;
	}
	
	public java.lang.String getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(java.lang.String specialityId) {
		this.specialityId = specialityId;
	}

	public java.lang.String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(java.lang.String speciality) {
		this.speciality = speciality;
	}
	
	public java.lang.String getCreatedById() {
		return this.createdById;
	}
	public void setCreatedById(java.lang.String createdById) {
		this.createdById = createdById;
	}
	
	public java.lang.String getCreatedByName() {
		return this.createdByName;
	}
	public void setCreatedByName(java.lang.String createdByName) {
		this.createdByName = createdByName;
	}
	
	public java.lang.String getCreatedDeptId() {
		return this.createdDeptId;
	}
	public void setCreatedDeptId(java.lang.String createdDeptId) {
		this.createdDeptId = createdDeptId;
	}
	
	public java.lang.String getCreatedDeptName() {
		return this.createdDeptName;
	}
	public void setCreatedDeptName(java.lang.String createdDeptName) {
		this.createdDeptName = createdDeptName;
	}
	
	public java.util.Date getCreatedDate() {
		return this.createdDate;
	}
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public java.lang.String getModifiedById() {
		return this.modifiedById;
	}
	public void setModifiedById(java.lang.String modifiedById) {
		this.modifiedById = modifiedById;
	}
	
	public java.lang.String getModifiedByName() {
		return this.modifiedByName;
	}
	public void setModifiedByName(java.lang.String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}
	
	public java.util.Date getModifiedDate() {
		return this.modifiedDate;
	}
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public int getStatus() {
		return this.status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public java.lang.String getFileType() {
		return fileType;
	}
	public void setFileType(java.lang.String fileType) {
		this.fileType = fileType;
	}

	public java.lang.String getFileTypeId() {
		return fileTypeId;
	}
	public void setFileTypeId(java.lang.String fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	public java.lang.String getModelTypeId() {
		return modelTypeId;
	}
	public void setModelTypeId(java.lang.String modelTypeId) {
		this.modelTypeId = modelTypeId;
	}

	public java.lang.String getModelType() {
		return modelType;
	}
	public void setModelType(java.lang.String modelType) {
		this.modelType = modelType;
	}
	
	public java.lang.String getConfirmModelType() {
		return confirmModelType;
	}
	public void setConfirmModelType(java.lang.String confirmModelType) {
		this.confirmModelType = confirmModelType;
	}

	public java.lang.String getConfirmModelTypeId() {
		return confirmModelTypeId;
	}
	public void setConfirmModelTypeId(java.lang.String confirmModelTypeId) {
		this.confirmModelTypeId = confirmModelTypeId;
	}

	public boolean getIsRewinding() {
		return isRewinding;
	}

	public void setIsRewinding(boolean isRewinding) {
		this.isRewinding = isRewinding;
	}
	
	public boolean getIsRewindTwo() {
		return isRewindTwo;
	}
	public void setIsRewindTwo(boolean isRewindTwo) {
		this.isRewindTwo = isRewindTwo;
	}

	public int isExist() {
		return isExist;
	}

	public void setExist(int isExist) {
		this.isExist = isExist;
	}
	
	public int getIsExistTwo() {
		return isExistTwo;
	}
	public void setIsExistTwo(int isExistTwo) {
		this.isExistTwo = isExistTwo;
	}

	public int getStatusScan() {
		return statusScan;
	}
	public void setStatusScan(int statusScan) {
		this.statusScan = statusScan;
	}
	
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	
	public String getEnName() {
		return enName;
	}
	
	public void setEnName(String enName) {
		this.enName = enName;
	}
	
	public int getVisaItem() {
		return visaItem;
	}

	public void setVisaItem(int visaItem) {
		this.visaItem = visaItem;
	}

	/**
	 * 无参构造函数.
	 */
	public BaseEngineeVisa() {
	
	}
	
	/**
	 * 构造函数.
	 * @param visaId
	 */
	public BaseEngineeVisa(String visaId) {
		this.visaId = visaId;
	} 
}
