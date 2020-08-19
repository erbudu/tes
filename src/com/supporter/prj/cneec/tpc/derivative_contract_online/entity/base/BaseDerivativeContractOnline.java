package com.supporter.prj.cneec.tpc.derivative_contract_online.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**   
 * @Title: Entity
 * @Description: TPC_CONTRACT_ONLINE,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-11-06
 * @version V1.0   
 *
 */
@MappedSuperclass
public class BaseDerivativeContractOnline implements Serializable {

	private static final long serialVersionUID = 1L;
	private String contractId;
	private String projectId;
	private String projectName;
	private String saleContractId;
	private String saleContractNo;
	private String saleContractName;
	private String filingId;
	private String filingNo;
	private String contractNo;
	private String contractName;
	private String paymentType;
	private String customerId;
	private String customerName;
	private String supplierId;
	private String supplierName;
	private String signingDate;
	private String effectiveDate;
	private String expirationDate;
	private double contractRmbAmount;
	private String internalPerson;
	private String internalPersonId;
	private Integer swfStatus;
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;
	private String thirdParty;
	private String preformId;// 是衍生合同单的derivativeId
	private String taxItemId;//印花税税目ID
	private String taxItemName;//印花税税目名称

	/**
	 * 无参构造函数.
	 */
	public BaseDerivativeContractOnline() {
	}

	/**
	 * 构造函数.
	 *
	 * @param contractId
	 */
	public BaseDerivativeContractOnline(String contractId) {
		this.contractId = contractId;
	}

	/**
	 * GET方法: 取得CONTRACT_ID.
	 *
	 * @return: String  CONTRACT_ID
	 */
	@Id
	@Column(name = "CONTRACT_ID", nullable = false, length = 32)
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

	@Column(name = "SALE_CONTRACT_ID", nullable = true, length = 512)
	public String getSaleContractId() {
		return this.saleContractId;
	}

	public void setSaleContractId(String saleContractId) {
		this.saleContractId = saleContractId;
	}

	@Column(name = "SALE_CONTRACT_NO", nullable = true, length = 512)
	public String getSaleContractNo() {
		return this.saleContractNo;
	}

	public void setSaleContractNo(String saleContractNo) {
		this.saleContractNo = saleContractNo;
	}

	@Column(name = "SALE_CONTRACT_NAME", nullable = true, length = 1024)
	public String getSaleContractName() {
		return this.saleContractName;
	}

	public void setSaleContractName(String saleContractName) {
		this.saleContractName = saleContractName;
	}

	/**
	 * GET方法: 取得FILING_ID.
	 *
	 * @return: String  FILING_ID
	 */
	@Column(name = "FILING_ID", nullable = true, length = 32)
	public String getFilingId() {
		return this.filingId;
	}

	/**
	 * SET方法: 设置FILING_ID.
	 *
	 * @param: String  FILING_ID
	 */
	public void setFilingId(String filingId) {
		this.filingId = filingId;
	}

	/**
	 * GET方法: 取得FILING_NO.
	 *
	 * @return: String  FILING_NO
	 */
	@Column(name = "FILING_NO", nullable = true, length = 64)
	public String getFilingNo() {
		return this.filingNo;
	}

	/**
	 * SET方法: 设置FILING_NO.
	 *
	 * @param: String  FILING_NO
	 */
	public void setFilingNo(String filingNo) {
		this.filingNo = filingNo;
	}

	@Column(name = "CONTRACT_NO", nullable = true, length = 64)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "CONTRACT_NAME", nullable = true, length = 128)
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Column(name = "PAYMENT_TYPE", nullable = true, length = 64)
	public String getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * GET方法: 取得CUSTOMER_ID.
	 *
	 * @return: String  CUSTOMER_ID
	 */
	@Column(name = "CUSTOMER_ID", nullable = true, length = 32)
	public String getCustomerId() {
		return this.customerId;
	}

	/**
	 * SET方法: 设置CUSTOMER_ID.
	 *
	 * @param: String  CUSTOMER_ID
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * GET方法: 取得CUSTOMER_NAME.
	 *
	 * @return: String  CUSTOMER_NAME
	 */
	@Column(name = "CUSTOMER_NAME", nullable = true, length = 512)
	public String getCustomerName() {
		return this.customerName;
	}

	/**
	 * SET方法: 设置CUSTOMER_NAME.
	 *
	 * @param: String  CUSTOMER_NAME
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "SUPPLIER_ID", nullable = true, length = 32)
	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "SUPPLIER_NAME", nullable = true, length = 512)
	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * GET方法: 取得SIGNING_DATE.
	 *
	 * @return: String  SIGNING_DATE
	 */
	@Column(name = "SIGNING_DATE", nullable = true, length = 27)
	public String getSigningDate() {
		return this.signingDate;
	}

	/**
	 * SET方法: 设置SIGNING_DATE.
	 *
	 * @param: String  SIGNING_DATE
	 */
	public void setSigningDate(String signingDate) {
		this.signingDate = signingDate;
	}

	/**
	 * GET方法: 取得EFFECTIVE_DATE.
	 *
	 * @return: String  EFFECTIVE_DATE
	 */
	@Column(name = "EFFECTIVE_DATE", nullable = true, length = 27)
	public String getEffectiveDate() {
		return this.effectiveDate;
	}

	/**
	 * SET方法: 设置EFFECTIVE_DATE.
	 *
	 * @param: String  EFFECTIVE_DATE
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * GET方法: 取得EXPIRATION_DATE.
	 *
	 * @return: String  EXPIRATION_DATE
	 */
	@Column(name = "EXPIRATION_DATE", nullable = true, length = 27)
	public String getExpirationDate() {
		return this.expirationDate;
	}

	/**
	 * SET方法: 设置EXPIRATION_DATE.
	 *
	 * @param: String  EXPIRATION_DATE
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * GET方法: 取得CONTRACT_RMB_AMOUNT.
	 *
	 * @return: double  CONTRACT_RMB_AMOUNT
	 */
	@Column(name = "CONTRACT_RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getContractRmbAmount() {
		return contractRmbAmount;
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
	 * GET方法: 取得INTERNAL_PERSON.
	 *
	 * @return: String  INTERNAL_PERSON
	 */
	@Column(name = "INTERNAL_PERSON", nullable = true, length = 32)
	public String getInternalPerson() {
		return this.internalPerson;
	}

	/**
	 * SET方法: 设置INTERNAL_PERSON.
	 *
	 * @param: String  INTERNAL_PERSON
	 */
	public void setInternalPerson(String internalPerson) {
		this.internalPerson = internalPerson;
	}

	/**
	 * GET方法: 取得INTERNAL_PERSON_ID.
	 *
	 * @return: String  INTERNAL_PERSON_ID
	 */
	@Column(name = "INTERNAL_PERSON_ID", nullable = true, length = 32)
	public String getInternalPersonId() {
		return this.internalPersonId;
	}

	/**
	 * SET方法: 设置INTERNAL_PERSON_ID.
	 *
	 * @param: String  INTERNAL_PERSON_ID
	 */
	public void setInternalPersonId(String internalPersonId) {
		this.internalPersonId = internalPersonId;
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

	/**
	 * GET方法: 取得CREATED_DATE.
	 *
	 * @return: String  CREATED_DATE
	 */
	@Column(name = "CREATED_DATE", nullable = true, length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * SET方法: 设置CREATED_DATE.
	 *
	 * @param: String  CREATED_DATE
	 */
	public void setCreatedDate(String createdDate) {
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

	/**
	 * GET方法: 取得MODIFIED_DATE.
	 *
	 * @return: String  MODIFIED_DATE
	 */
	@Column(name = "MODIFIED_DATE", nullable = true, length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 * SET方法: 设置MODIFIED_DATE.
	 *
	 * @param: String  MODIFIED_DATE
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name = "THIRD_PARTY", nullable = true, length = 32)
	public String getThirdParty() {
		return this.thirdParty;
	}

	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}
	
	@Column(name = "PREFORM_ID", nullable = true, length = 32)
	public String getPreformId() {
		return this.preformId;
	}

	public void setPreformId(String preformId) {
		this.preformId = preformId;
	}
	
	@Column(name = "TAX_ITEM_ID", nullable = true, length = 64)
	public String getTaxItemId() {
		return this.taxItemId;
	}
	
	public void setTaxItemId(String taxItemId) {
		this.taxItemId = taxItemId;
	}
	
	@Column(name = "TAX_ITEM_NAME", nullable = true, length = 128)
	public String getTaxItemName() {
		return this.taxItemName;
	}
	
	public void setTaxItemName(String taxItemName) {
		this.taxItemName = taxItemName;
	}

}
