package com.supporter.prj.cneec.data_migration.business_registration.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
/**
 * OaBusinessRegistration entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseBusinessRegistration implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String businessRegistrationId;
	private String businessName;
	private String businessInnerName;
	private String businessTable;
	private String isSpecifyField;
	private String deptIdField;
	private String deptNameField;
	private String businessType;
	private String isEnabled;
	private Long displayOrder;
	private String createdById;
	private String createdBy;
	private String createdDate;
	private String modifiedById;
	private String modifiedBy;
	private String modifiedDate;
	private String processStatus;
	private String processStatusValue;
	private String dataSource;
	private String field1;
	private String field2;
	private String isPrjSpecifyField;
	private String prjIdField;

	// Constructors

	/** default constructor */
	public BaseBusinessRegistration() {
	}

	/** minimal constructor */
	public BaseBusinessRegistration(String businessRegistrationId) {
		this.businessRegistrationId = businessRegistrationId;
	}

	/** full constructor */
	public BaseBusinessRegistration(String businessRegistrationId,
			String businessName, String businessInnerName,
			String businessTable, String isSpecifyField, String deptIdField,
			String deptNameField, String businessType, String isEnabled,
			Long displayOrder, String createdById, String createdBy,
			String createdDate, String modifiedById, String modifiedBy,
			String modifiedDate, String processStatus,
			String processStatusValue, String dataSource, String field1,
			String field2, String isPrjSpecifyField, String prjIdField) {
		this.businessRegistrationId = businessRegistrationId;
		this.businessName = businessName;
		this.businessInnerName = businessInnerName;
		this.businessTable = businessTable;
		this.isSpecifyField = isSpecifyField;
		this.deptIdField = deptIdField;
		this.deptNameField = deptNameField;
		this.businessType = businessType;
		this.isEnabled = isEnabled;
		this.displayOrder = displayOrder;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedById = modifiedById;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.processStatus = processStatus;
		this.processStatusValue = processStatusValue;
		this.dataSource = dataSource;
		this.field1 = field1;
		this.field2 = field2;
		this.isPrjSpecifyField = isPrjSpecifyField;
		this.prjIdField = prjIdField;
	}

	// Property accessors
	@Id
	@Column(name = "BUSINESS_REGISTRATION_ID", unique = true, nullable = false, length = 32)
	public String getBusinessRegistrationId() {
		return this.businessRegistrationId;
	}

	public void setBusinessRegistrationId(String businessRegistrationId) {
		this.businessRegistrationId = businessRegistrationId;
	}

	@Column(name = "BUSINESS_NAME")
	public String getBusinessName() {
		return this.businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Column(name = "BUSINESS_INNER_NAME")
	public String getBusinessInnerName() {
		return this.businessInnerName;
	}

	public void setBusinessInnerName(String businessInnerName) {
		this.businessInnerName = businessInnerName;
	}

	@Column(name = "BUSINESS_TABLE")
	public String getBusinessTable() {
		return this.businessTable;
	}

	public void setBusinessTable(String businessTable) {
		this.businessTable = businessTable;
	}

	@Column(name = "IS_SPECIFY_FIELD", length = 1)
	public String getIsSpecifyField() {
		return this.isSpecifyField;
	}

	public void setIsSpecifyField(String isSpecifyField) {
		this.isSpecifyField = isSpecifyField;
	}

	@Column(name = "DEPT_ID_FIELD", length = 32)
	public String getDeptIdField() {
		return this.deptIdField;
	}

	public void setDeptIdField(String deptIdField) {
		this.deptIdField = deptIdField;
	}

	@Column(name = "DEPT_NAME_FIELD", length = 64)
	public String getDeptNameField() {
		return this.deptNameField;
	}

	public void setDeptNameField(String deptNameField) {
		this.deptNameField = deptNameField;
	}

	@Column(name = "BUSINESS_TYPE", length = 128)
	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Column(name = "IS_ENABLED", length = 1)
	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(name = "DISPLAY_ORDER", precision = 22, scale = 0)
	public Long getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Column(name = "CREATED_BY_ID", length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "CREATED_BY", length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE", length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY_ID", length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "MODIFIED_BY", length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE", length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "PROCESS_STATUS", length = 64)
	public String getProcessStatus() {
		return this.processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	@Column(name = "PROCESS_STATUS_VALUE", length = 16)
	public String getProcessStatusValue() {
		return this.processStatusValue;
	}

	public void setProcessStatusValue(String processStatusValue) {
		this.processStatusValue = processStatusValue;
	}

	@Column(name = "DATA_SOURCE", length = 256)
	public String getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	@Column(name = "FIELD_1", length = 128)
	public String getField1() {
		return this.field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	@Column(name = "FIELD_2", length = 128)
	public String getField2() {
		return this.field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	@Column(name = "IS_PRJ_SPECIFY_FIELD", length = 1)
	public String getIsPrjSpecifyField() {
		return this.isPrjSpecifyField;
	}

	public void setIsPrjSpecifyField(String isPrjSpecifyField) {
		this.isPrjSpecifyField = isPrjSpecifyField;
	}

	@Column(name = "PRJ_ID_FIELD", length = 20)
	public String getPrjIdField() {
		return this.prjIdField;
	}

	public void setPrjIdField(String prjIdField) {
		this.prjIdField = prjIdField;
	}

}