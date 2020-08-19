package com.supporter.prj.cneec.tpc.contract_change.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * @Title: Entity
 * @Description: TPC_CONTRACT_SEAL,字段与数据库字段一一对应.
 * @version V1.0
 */
@MappedSuperclass
public class BaseContractChange implements Serializable {

	private static final long serialVersionUID = 1L;
	private String changeId;
	private String projectId;
	private String projectName;
	private String contractId;
	private String contractNo;
	private String contractName;
//	private String serialNumber;
	private Integer chType;
	private String adjustmentNatureId;
	private String adjustmentNature;
	private String changeModeId;
	private String changeMode;
	private double changeAmount;
	private String changeTypeId;
	private String changeType;
	private double onlineRmbAmount;
	private double contractRmbAmount;
	private String adjustmentReasons;
	private Integer hasProtocol;
	private String businessNo;  //协议编号
//	private boolean hasProtocol;
//	private String protocolFiles;
//	private String supportingFiles;
//	private String signingDate;
//	private String effectiveDate;
//	private String expirationDate;
//	private String internalPerson;
//	private String internalPersonId;
	private String deptAuditorId;
	private String deptAuditor;
	private String deptTechnicalAuditorId;
	private String deptTechnicalAuditor;
	private String telephone;
	private Integer swfStatus;
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private Date createdDate;
	private String modifiedBy;
	private String modifiedById;
	private Date modifiedDate;
	private String purchaseTypeCode;//采购类型code
	private String purchaseType;//采购类型

	/**
	 * 无参构造函数.
	 */
	public BaseContractChange() {
	}

	/**
	 * 构造函数.
	 *
	 * @param changeId
	 */
	public BaseContractChange(String changeId) {
		this.changeId = changeId;
	}

	/**
	 * GET方法: 取得CHANGE_ID.
	 *
	 * @return: String  CHANGE_ID
	 */
	@Id
	@Column(name = "CHANGE_ID", nullable = false, length = 32)
	public String getChangeId() {
		return this.changeId;
	}

	/**
	 * SET方法: 设置CHANGE_ID.
	 *
	 * @param: String  CHANGE_ID
	 */
	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}

	/**
	 * GET方法: 取得PROJECT_ID.
	 *
	 * @return: String  PROJECT_ID
	 */
	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	/**
	 * SET方法: 设置PROJECT_ID.
	 *
	 * @param: String  PROJECT_ID
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * GET方法: 取得PROJECT_NAME.
	 *
	 * @return: String  PROJECT_NAME
	 */
	@Column(name = "PROJECT_NAME", nullable = true, length = 128)
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * SET方法: 设置PROJECT_NAME.
	 *
	 * @param: String  PROJECT_NAME
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * GET方法: 取得CONTRACT_ID.
	 *
	 * @return: String  CONTRACT_ID
	 */
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	public String getContractId() {
		return this.contractId;
	}

	/**
	 * SET方法: 设置CONTRACT_ID.
	 *
	 * @param: String  CONTRACT_ID
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	/**
	 * GET方法: 取得CONTRACT_NO.
	 *
	 * @return: String  CONTRACT_NO
	 */
	@Column(name = "CONTRACT_NO", nullable = true, length = 64)
	public String getContractNo() {
		return this.contractNo;
	}

	/**
	 * SET方法: 设置CONTRACT_NO.
	 *
	 * @param: String  CONTRACT_NO
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * GET方法: 取得CONTRACT_NAME.
	 *
	 * @return: String  CONTRACT_NAME
	 */
	@Column(name = "CONTRACT_NAME", nullable = true, length = 128)
	public String getContractName() {
		return this.contractName;
	}

	/**
	 * SET方法: 设置CONTRACT_NAME.
	 *
	 * @param: String  CONTRACT_NAME
	 */
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	/**
	 * GET方法: 取得SERIAL_NUMBER.
	 *
	 * @return: String  SERIAL_NUMBER
	 */
//	@Column(name = "SERIAL_NUMBER", nullable = true, length = 32)
//	public String getSerialNumber() {
//		return serialNumber;
//	}
//
//	/**
//	 * SET方法: 设置SERIAL_NUMBER.
//	 *
//	 * @param: String  SERIAL_NUMBER
//	 */
//	public void setSerialNumber(String serialNumber) {
//		this.serialNumber = serialNumber;
//	}
	/**
	 * GET方法: 取得BUSINESS_NO.
	 *
	 * @return: String  BUSINESS_NO
	 */
	@Column(name = "BUSINESS_NO", nullable = true, length = 32)
	public String getBusinessNo() {
		return this.businessNo;
	}

	/**
	 * SET方法: 设置BUSINESS_NO.
	 *
	 * @param: String  BUSINESS_NO
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	/**
	 * GET方法: 取得ADJUSTMENT_NATURE_ID.
	 *
	 * @return: String  ADJUSTMENT_NATURE_ID
	 */
	@Column(name = "ADJUSTMENT_NATURE_ID", nullable = true, length = 512)
	public String getAdjustmentNatureId() {
		return this.adjustmentNatureId;
	}

	/**
	 * SET方法: 设置ADJUSTMENT_NATURE_ID.
	 *
	 * @param: String  ADJUSTMENT_NATURE_ID
	 */
	public void setAdjustmentNatureId(String adjustmentNatureId) {
		this.adjustmentNatureId = adjustmentNatureId;
	}

	/**
	 * GET方法: 取得ADJUSTMENT_NATURE.
	 *
	 * @return: String  ADJUSTMENT_NATURE
	 */
	@Column(name = "ADJUSTMENT_NATURE", nullable = true, length = 1024)
	public String getAdjustmentNature() {
		return this.adjustmentNature;
	}

	/**
	 * SET方法: 设置ADJUSTMENT_NATURE.
	 *
	 * @param: String  ADJUSTMENT_NATURE
	 */
	public void setAdjustmentNature(String adjustmentNature) {
		this.adjustmentNature = adjustmentNature;
	}

	/**
	 * GET方法: 取得CHANGE_MODE_ID.
	 *
	 * @return: String  CHANGE_MODE_ID
	 */
	@Column(name = "CHANGE_MODE_ID", nullable = true, length = 32)
	public String getChangeModeId() {
		return this.changeModeId;
	}

	/**
	 * SET方法: 设置CHANGE_MODE_ID.
	 *
	 * @param: String  CHANGE_MODE_ID
	 */
	public void setChangeModeId(String changeModeId) {
		this.changeModeId = changeModeId;
	}

	/**
	 * GET方法: 取得CHANGE_MODE.
	 *
	 * @return: String  CHANGE_MODE
	 */
	@Column(name = "CHANGE_MODE", nullable = true, length = 32)
	public String getChangeMode() {
		return this.changeMode;
	}

	/**
	 * SET方法: 设置CHANGE_MODE.
	 *
	 * @param: String  CHANGE_MODE
	 */
	public void setChangeMode(String changeMode) {
		this.changeMode = changeMode;
	}

	/**
	 * GET方法: 取得CHANGE_AMOUNT.
	 *
	 * @return: double  CHANGE_AMOUNT
	 */
	@Column(name = "CHANGE_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getChangeAmount() {
		return this.changeAmount;
	}

	/**
	 * SET方法: 设置CHANGE_AMOUNT.
	 *
	 * @param: double  CHANGE_AMOUNT
	 */
	public void setChangeAmount(double changeAmount) {
		this.changeAmount = changeAmount;
	}

	/**
	 * GET方法: 取得CHANGE_TYPE_ID.
	 *
	 * @return: String  CHANGE_TYPE_ID
	 */
	@Column(name = "CHANGE_TYPE_ID", nullable = true, length = 32)
	public String getChangeTypeId() {
		return this.changeTypeId;
	}

	/**
	 * SET方法: 设置CHANGE_TYPE_ID.
	 *
	 * @param: String  CHANGE_TYPE_ID
	 */
	public void setChangeTypeId(String changeTypeId) {
		this.changeTypeId = changeTypeId;
	}

	/**
	 * GET方法: 取得CHANGE_TYPE.
	 *
	 * @return: String  CHANGE_TYPE
	 */
	@Column(name = "CHANGE_TYPE", nullable = true, length = 32)
	public String getChangeType() {
		return this.changeType;
	}

	/**
	 * SET方法: 设置CHANGE_TYPE.
	 *
	 * @param: String  CHANGE_TYPE
	 */
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	/**
	 * GET方法: 取得ONLINE_RMB_AMOUNT.
	 *
	 * @return: double  ONLINE_RMB_AMOUNT
	 */
	@Column(name = "ONLINE_RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getOnlineRmbAmount() {
		return this.onlineRmbAmount;
	}

	/**
	 * SET方法: 设置ONLINE_RMB_AMOUNT.
	 *
	 * @param: double  ONLINE_RMB_AMOUNT
	 */
	public void setOnlineRmbAmount(double onlineRmbAmount) {
		this.onlineRmbAmount = onlineRmbAmount;
	}

	/**
	 * GET方法: 取得CONTRACT_RMB_AMOUNT.
	 *
	 * @return: double  CONTRACT_RMB_AMOUNT
	 */
	@Column(name = "CONTRACT_RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getContractRmbAmount() {
		return this.contractRmbAmount;
	}

	/**
	 * SET方法: 设置CONTRACT_RMB_AMOUNT.
	 *
	 * @param: double  CONTRACT_RMB_AMOUNT
	 */
	public void setContractRmbAmount(double contractRmbAmount) {
		this.contractRmbAmount = contractRmbAmount;
	}

	/**
	 * GET方法: 取得ADJUSTMENT_REASONS.
	 *
	 * @return: String  ADJUSTMENT_REASONS
	 */
	@Column(name = "ADJUSTMENT_REASONS", nullable = true, length = 512)
	public String getAdjustmentReasons() {
		return this.adjustmentReasons;
	}

	/**
	 * SET方法: 设置ADJUSTMENT_REASONS.
	 *
	 * @param: String  ADJUSTMENT_REASONS
	 */
	public void setAdjustmentReasons(String adjustmentReasons) {
		this.adjustmentReasons = adjustmentReasons;
	}

	/**
	 * GET方法: 取得IS_HAS_PROTOCOL.
	 *
	 * @return: String  IS_HAS_PROTOCOL
	 */
	@Column(name = "HAS_PROTOCOL",  nullable = true, precision = 10)
	public Integer getHasProtocol() {
		return hasProtocol;
	}

	/**
	 * SET方法: 设置IS_HAS_PROTOCOL.
	 *
	 * @param: String  IS_HAS_PROTOCOL
	 */
	public void setHasProtocol(Integer hasProtocol) {
		this.hasProtocol = hasProtocol;
	}

	/**
	 * GET方法: 取得PROTOCOL_FILES.
	 *
	 * @return: String  PROTOCOL_FILES
	 */
//	@Column(name = "PROTOCOL_FILES", nullable = true, length = 32)
//	public String getProtocolFiles() {
//		return this.protocolFiles;
//	}
//
//	/**
//	 * SET方法: 设置PROTOCOL_FILES.
//	 *
//	 * @param: String  PROTOCOL_FILES
//	 */
//	public void setProtocolFiles(String protocolFiles) {
//		this.protocolFiles = protocolFiles;
//	}

	/**
	 * GET方法: 取得SUPPORTING_FILES.
	 *
	 * @return: String  SUPPORTING_FILES
	 */
//	@Column(name = "SUPPORTING_FILES", nullable = true, length = 32)
//	public String getSupportingFiles() {
//		return this.supportingFiles;
//	}
//
//	/**
//	 * SET方法: 设置SUPPORTING_FILES.
//	 *
//	 * @param: String  SUPPORTING_FILES
//	 */
//	public void setSupportingFiles(String supportingFiles) {
//		this.supportingFiles = supportingFiles;
//	}

	/**
	 * GET方法: 取得SIGNING_DATE.
	 *
	 * @return: String  SIGNING_DATE
	 */
//	@Column(name = "SIGNING_DATE", nullable = true, length = 27)
//	public String getSigningDate() {
//		return this.signingDate;
//	}
//
//	/**
//	 * SET方法: 设置SIGNING_DATE.
//	 *
//	 * @param: String  SIGNING_DATE
//	 */
//	public void setSigningDate(String signingDate) {
//		this.signingDate = signingDate;
//	}

	/**
	 * GET方法: 取得EFFECTIVE_DATE.
	 *
	 * @return: String  EFFECTIVE_DATE
	 */
//	@Column(name = "EFFECTIVE_DATE", nullable = true, length = 27)
//	public String getEffectiveDate() {
//		return this.effectiveDate;
//	}
//
//	/**
//	 * SET方法: 设置EFFECTIVE_DATE.
//	 *
//	 * @param: String  EFFECTIVE_DATE
//	 */
//	public void setEffectiveDate(String effectiveDate) {
//		this.effectiveDate = effectiveDate;
//	}

	/**
	 * GET方法: 取得EXPIRATION_DATE.
	 *
	 * @return: String  EXPIRATION_DATE
	 */
//	@Column(name = "EXPIRATION_DATE", nullable = true, length = 27)
//	public String getExpirationDate() {
//		return this.expirationDate;
//	}
//
//	/**
//	 * SET方法: 设置EXPIRATION_DATE.
//	 *
//	 * @param: String  EXPIRATION_DATE
//	 */
//	public void setExpirationDate(String expirationDate) {
//		this.expirationDate = expirationDate;
//	}

	/**
	 * GET方法: 取得INTERNAL_PERSON.
	 *
	 * @return: String  INTERNAL_PERSON
	 */
//	@Column(name = "INTERNAL_PERSON", nullable = true, length = 32)
//	public String getInternalPerson() {
//		return this.internalPerson;
//	}
//
//	/**
//	 * SET方法: 设置INTERNAL_PERSON.
//	 *
//	 * @param: String  INTERNAL_PERSON
//	 */
//	public void setInternalPerson(String internalPerson) {
//		this.internalPerson = internalPerson;
//	}

	/**
	 * GET方法: 取得INTERNAL_PERSON_ID.
	 *
	 * @return: String  INTERNAL_PERSON_ID
	 */
//	@Column(name = "INTERNAL_PERSON_ID", nullable = true, length = 32)
//	public String getInternalPersonId() {
//		return this.internalPersonId;
//	}
//
//	/**
//	 * SET方法: 设置INTERNAL_PERSON_ID.
//	 *
//	 * @param: String  INTERNAL_PERSON_ID
//	 */
//	public void setInternalPersonId(String internalPersonId) {
//		this.internalPersonId = internalPersonId;
//	}

	/**
	 * GET方法: 取得DEPT_AUDITOR_ID.
	 *
	 * @return: String  DEPT_AUDITOR_ID
	 */
	@Column(name = "DEPT_AUDITOR_ID", nullable = true, length = 512)
	public String getDeptAuditorId() {
		return deptAuditorId;
	}

	/**
	 * SET方法: 设置DEPT_AUDITOR_ID.
	 *
	 * @param: String  DEPT_AUDITOR_ID
	 */
	public void setDeptAuditorId(String deptAuditorId) {
		this.deptAuditorId = deptAuditorId;
	}

	/**
	 * GET方法: 取得DEPT_AUDITOR.
	 *
	 * @return: String  DEPT_AUDITOR
	 */
	@Column(name = "DEPT_AUDITOR", nullable = true, length = 512)
	public String getDeptAuditor() {
		return deptAuditor;
	}

	/**
	 * SET方法: 设置DEPT_AUDITOR.
	 *
	 * @param: String  DEPT_AUDITOR
	 */
	public void setDeptAuditor(String deptAuditor) {
		this.deptAuditor = deptAuditor;
	}

	/**
	 * GET方法: 取得DEPT_TECHNICAL_AUDITOR_ID.
	 *
	 * @return: String  DEPT_TECHNICAL_AUDITOR_ID
	 */
	@Column(name = "DEPT_TECHNICAL_AUDITOR_ID", nullable = true, length = 128)
	public String getDeptTechnicalAuditorId() {
		return deptTechnicalAuditorId;
	}

	/**
	 * SET方法: 设置DEPT_TECHNICAL_AUDITOR_ID.
	 *
	 * @param: String  DEPT_TECHNICAL_AUDITOR_ID
	 */
	public void setDeptTechnicalAuditorId(String deptTechnicalAuditorId) {
		this.deptTechnicalAuditorId = deptTechnicalAuditorId;
	}

	/**
	 * GET方法: 取得DEPT_TECHNICAL_AUDITOR.
	 *
	 * @return: String  DEPT_TECHNICAL_AUDITOR
	 */
	@Column(name = "DEPT_TECHNICAL_AUDITOR", nullable = true, length = 128)
	public String getDeptTechnicalAuditor() {
		return deptTechnicalAuditor;
	}

	/**
	 * SET方法: 设置DEPT_TECHNICAL_AUDITOR.
	 *
	 * @param: String  DEPT_TECHNICAL_AUDITOR
	 */
	public void setDeptTechnicalAuditor(String deptTechnicalAuditor) {
		this.deptTechnicalAuditor = deptTechnicalAuditor;
	}

	/**
	 * GET方法: 取得TELEPHONE.
	 *
	 * @return: String  TELEPHONE
	 */
	@Column(name = "TELEPHONE", nullable = true, length = 32)
	public String getTelephone() {
		return telephone;
	}

	/**
	 * SET方法: 设置TELEPHONE.
	 *
	 * @param: String  TELEPHONE
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * GET方法: 取得SWF_STATUS.
	 *
	 * @return: Integer  SWF_STATUS
	 */
	@Column(name = "SWF_STATUS", nullable = true, precision = 10)
	public Integer getSwfStatus() {
		return this.swfStatus;
	}

	/**
	 * SET方法: 设置SWF_STATUS.
	 *
	 * @param: Integer  SWF_STATUS
	 */
	public void setSwfStatus(Integer swfStatus) {
		this.swfStatus = swfStatus;
	}
	/**
	 * GET方法
	 * @return: Integer
	 */
	@Column(name = "ch_type", nullable = true, precision = 10)
	public Integer getChType() {
		return this.chType;
	}

	/**
	 * SET方法
	 *  @param: Integer
	 */
	public void setChType(Integer chType) {
		this.chType = chType;
	}
	/**
	 * GET方法: 取得DEPT_NAME.
	 *
	 * @return: String  DEPT_NAME
	 */
	@Column(name = "DEPT_NAME", nullable = true, length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * SET方法: 设置DEPT_NAME.
	 *
	 * @param: String  DEPT_NAME
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * GET方法: 取得DEPT_ID.
	 *
	 * @return: String  DEPT_ID
	 */
	@Column(name = "DEPT_ID", nullable = true, length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	/**
	 * SET方法: 设置DEPT_ID.
	 *
	 * @param: String  DEPT_ID
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * GET方法: 取得CREATED_BY.
	 *
	 * @return: String  CREATED_BY
	 */
	@Column(name = "CREATED_BY", nullable = true, length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * SET方法: 设置CREATED_BY.
	 *
	 * @param: String  CREATED_BY
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * GET方法: 取得CREATED_BY_ID.
	 *
	 * @return: String  CREATED_BY_ID
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	/**
	 * SET方法: 设置CREATED_BY_ID.
	 *
	 * @param: String  CREATED_BY_ID
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = true, length = 32)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * GET方法: 取得MODIFIED_BY.
	 *
	 * @return: String  MODIFIED_BY
	 */
	@Column(name = "MODIFIED_BY", nullable = true, length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	/**
	 * SET方法: 设置MODIFIED_BY.
	 *
	 * @param: String  MODIFIED_BY
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * GET方法: 取得MODIFIED_BY_ID.
	 *
	 * @return: String  MODIFIED_BY_ID
	 */
	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	/**
	 * SET方法: 设置MODIFIED_BY_ID.
	 *
	 * @param: String  MODIFIED_BY_ID
	 */
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date", nullable = true, length = 32)
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	/**
	 * GET方法: 取得PURCHASE_TYPE_CODE.
	 * 
	 * @return: String PURCHASE_TYPE_CODE
	 */
	@Column(name = "PURCHASE_TYPE_CODE", nullable = true, length = 64)
	public String getPurchaseTypeCode() {
		return this.purchaseTypeCode;
	}

	/**
	 * SET方法: 设置PURCHASE_TYPE_CODE.
	 * 
	 * @param: String PURCHASE_TYPE_CODE
	 */
	public void setPurchaseTypeCode(String purchaseTypeCode) {
		this.purchaseTypeCode = purchaseTypeCode;
	}
	
	/**
	 * GET方法: 取得PURCHASE_TYPE.
	 * 
	 * @return: String PURCHASE_TYPE
	 */
	@Column(name = "PURCHASE_TYPE", nullable = true, length = 200)
	public String getPurchaseType() {
		return this.purchaseType;
	}

	/**
	 * SET方法: 设置PURCHASE_TYPE.
	 * 
	 * @param: String PURCHASE_TYPE
	 */
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
}
