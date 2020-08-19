package com.supporter.prj.cneec.tpc.custom.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseCustom implements Serializable {
	private static final long serialVersionUID = 1L;
	// Fields
	
	private String customId;
    private String customerName;
    private String customerNo;
    private String address;
    private String postCode;
    private String website;
    private String isForeign;
    private String areaName;
    private String areaCode;
    private String areaItemName;
    private String areaItemCode;
    private Double regAmount;
    private String currency;
    private String currencyTypeCode;
    private String isListed;
    private String createYear;
    private String employeeNumber;
    private String remarks;
    private Integer status;
    private String createdBy;
    private String createdById;
    private String createdDate;
    private String modifiedBy;
    private String modifiedById;
    private String modifiedDate;
    private String customControlStatus;
    private String customControlStatusCode;


   // Constructors

   /** default constructor */
   public BaseCustom() {
   }

	/** minimal constructor */
   public BaseCustom(String customId) {
       this.customId = customId;
   }
   
   /** full constructor */
   public BaseCustom(String customId, String customerName, String customerNo, String address, String postCode, String website, String isForeign, String areaName, String areaCode, String areaItemName, String areaItemCode, Double regAmount, String currency, String currencyTypeCode, String isListed, String createYear, String employeeNumber, String remarks, Integer status, String createdBy, String createdById, String createdDate, String modifiedBy, String modifiedById, String modifiedDate, String customControlStatus, String customControlStatusCode) {
       this.customId = customId;
       this.customerName = customerName;
       this.customerNo = customerNo;
       this.address = address;
       this.postCode = postCode;
       this.website = website;
       this.isForeign = isForeign;
       this.areaName = areaName;
       this.areaCode = areaCode;
       this.areaItemName = areaItemName;
       this.areaItemCode = areaItemCode;
       this.regAmount = regAmount;
       this.currency = currency;
       this.currencyTypeCode = currencyTypeCode;
       this.isListed = isListed;
       this.createYear = createYear;
       this.employeeNumber = employeeNumber;
       this.remarks = remarks;
       this.status = status;
       this.createdBy = createdBy;
       this.createdById = createdById;
       this.createdDate = createdDate;
       this.modifiedBy = modifiedBy;
       this.modifiedById = modifiedById;
       this.modifiedDate = modifiedDate;
       this.customControlStatus = customControlStatus;
       this.customControlStatusCode = customControlStatusCode;
   }

  
   // Property accessors
   @Id 
   
   @Column(name="CUSTOM_ID", unique=true, nullable=false, length=32)
   public String getCustomId() {
       return this.customId;
   }
   
   public void setCustomId(String customId) {
       this.customId = customId;
   }
   
   @Column(name="CUSTOMER_NAME", length=512)

   public String getCustomerName() {
       return this.customerName;
   }
   
   public void setCustomerName(String customerName) {
       this.customerName = customerName;
   }
   
   @Column(name="CUSTOMER_NO", length=32)

   public String getCustomerNo() {
       return this.customerNo;
   }
   
   public void setCustomerNo(String customerNo) {
       this.customerNo = customerNo;
   }
   
   @Column(name="ADDRESS", length=512)

   public String getAddress() {
       return this.address;
   }
   
   public void setAddress(String address) {
       this.address = address;
   }
   
   @Column(name="POST_CODE", length=128)

   public String getPostCode() {
       return this.postCode;
   }
   
   public void setPostCode(String postCode) {
       this.postCode = postCode;
   }
   
   @Column(name="WEBSITE", length=128)

   public String getWebsite() {
       return this.website;
   }
   
   public void setWebsite(String website) {
       this.website = website;
   }
   
   @Column(name="IS_FOREIGN", length=1)

   public String getIsForeign() {
       return this.isForeign;
   }
   
   public void setIsForeign(String isForeign) {
       this.isForeign = isForeign;
   }
   
   @Column(name="AREA_NAME", length=128)

   public String getAreaName() {
       return this.areaName;
   }
   
   public void setAreaName(String areaName) {
       this.areaName = areaName;
   }
   
   @Column(name="AREA_CODE", length=32)

   public String getAreaCode() {
       return this.areaCode;
   }
   
   public void setAreaCode(String areaCode) {
       this.areaCode = areaCode;
   }
   
   @Column(name="AREA_ITEM_NAME", length=128)

   public String getAreaItemName() {
       return this.areaItemName;
   }
   
   public void setAreaItemName(String areaItemName) {
       this.areaItemName = areaItemName;
   }
   
   @Column(name="AREA_ITEM_CODE", length=32)

   public String getAreaItemCode() {
       return this.areaItemCode;
   }
   
   public void setAreaItemCode(String areaItemCode) {
       this.areaItemCode = areaItemCode;
   }
   
   @Column(name="REG_AMOUNT", precision=18, scale=3)

   public Double getRegAmount() {
       return this.regAmount;
   }
   
   public void setRegAmount(Double regAmount) {
       this.regAmount = regAmount;
   }
   
   @Column(name="CURRENCY", length=32)

   public String getCurrency() {
       return this.currency;
   }
   
   public void setCurrency(String currency) {
       this.currency = currency;
   }
   
   @Column(name="CURRENCY_TYPE_CODE", length=32)

   public String getCurrencyTypeCode() {
       return this.currencyTypeCode;
   }
   
   public void setCurrencyTypeCode(String currencyTypeCode) {
       this.currencyTypeCode = currencyTypeCode;
   }
   
   @Column(name="IS_LISTED", length=1)

   public String getIsListed() {
       return this.isListed;
   }
   
   public void setIsListed(String isListed) {
       this.isListed = isListed;
   }
   
   @Column(name="CREATE_YEAR", length=32)

   public String getCreateYear() {
       return this.createYear;
   }
   
   public void setCreateYear(String createYear) {
       this.createYear = createYear;
   }
   
   @Column(name="EMPLOYEE_NUMBER", length=32)

   public String getEmployeeNumber() {
       return this.employeeNumber;
   }
   
   public void setEmployeeNumber(String employeeNumber) {
       this.employeeNumber = employeeNumber;
   }
   
   @Column(name="REMARKS", length=1024)

   public String getRemarks() {
       return this.remarks;
   }
   
   public void setRemarks(String remarks) {
       this.remarks = remarks;
   }
   
   @Column(name="STATUS", precision=6, scale=0)

   public Integer getStatus() {
       return this.status;
   }
   
   public void setStatus(Integer status) {
       this.status = status;
   }
   
   @Column(name="CREATED_BY", length=32)

   public String getCreatedBy() {
       return this.createdBy;
   }
   
   public void setCreatedBy(String createdBy) {
       this.createdBy = createdBy;
   }
   
   @Column(name="CREATED_BY_ID", length=32)

   public String getCreatedById() {
       return this.createdById;
   }
   
   public void setCreatedById(String createdById) {
       this.createdById = createdById;
   }
   
   @Column(name="CREATED_DATE", length=32)

   public String getCreatedDate() {
       return this.createdDate;
   }
   
   public void setCreatedDate(String createdDate) {
       this.createdDate = createdDate;
   }
   
   @Column(name="MODIFIED_BY", length=32)

   public String getModifiedBy() {
       return this.modifiedBy;
   }
   
   public void setModifiedBy(String modifiedBy) {
       this.modifiedBy = modifiedBy;
   }
   
   @Column(name="MODIFIED_BY_ID", length=32)

   public String getModifiedById() {
       return this.modifiedById;
   }
   
   public void setModifiedById(String modifiedById) {
       this.modifiedById = modifiedById;
   }
   
   @Column(name="MODIFIED_DATE", length=32)

   public String getModifiedDate() {
       return this.modifiedDate;
   }
   
   public void setModifiedDate(String modifiedDate) {
       this.modifiedDate = modifiedDate;
   }
   
   @Column(name="CUSTOM_CONTROL_STATUS", length=32)

   public String getCustomControlStatus() {
       return this.customControlStatus;
   }
   
   public void setCustomControlStatus(String customControlStatus) {
       this.customControlStatus = customControlStatus;
   }
   
   @Column(name="CUSTOM_CONTROL_STATUS_CODE", length=32)

   public String getCustomControlStatusCode() {
       return this.customControlStatusCode;
   }
   
   public void setCustomControlStatusCode(String customControlStatusCode) {
       this.customControlStatusCode = customControlStatusCode;
   }
	
	
}
