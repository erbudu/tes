package com.supporter.prj.cneec.tpc.supplier.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractTpcSupplier entity provides the base persistence definition of the
 * TpcSupplier entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class BaseSupplier implements java.io.Serializable {

	// Fields

	private String supplierId;
	private String supplierName;
	private String address;
	private String isForeign;
	private String areaName;
	private String areaCode;
	private String areaItemName;
	private String areaItemCode;
	private String postCode;
	private String website;
	private String fax;
	private String linkmanName;
	private String linkmanTel;
	private String remarks;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;
	private String supplierControlStatus;
	private String supplierControlStatusCode;
	private String filesName;

	// Constructors

	/** default constructor */
	public BaseSupplier() {
	}

	/** minimal constructor */
	public BaseSupplier(String supplierId) {
		this.supplierId = supplierId;
	}

	/** full constructor */
	public BaseSupplier(String supplierId, String supplierName,
			String address, String isForeign, String areaName, String areaCode,
			String areaItemName, String areaItemCode, String postCode,
			String website, String fax, String linkmanName, String linkmanTel,
			String remarks, String createdBy, String createdById,
			String createdDate, String modifiedBy, String modifiedById,
			String modifiedDate, String supplierControlStatus,
			String supplierControlStatusCode, String filesName) {
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.address = address;
		this.isForeign = isForeign;
		this.areaName = areaName;
		this.areaCode = areaCode;
		this.areaItemName = areaItemName;
		this.areaItemCode = areaItemCode;
		this.postCode = postCode;
		this.website = website;
		this.fax = fax;
		this.linkmanName = linkmanName;
		this.linkmanTel = linkmanTel;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
		this.supplierControlStatus = supplierControlStatus;
		this.supplierControlStatusCode = supplierControlStatusCode;
		this.filesName = filesName;
	}

	// Property accessors
	@Id
	@Column(name = "SUPPLIER_ID", unique = true, nullable = false, length = 32)
	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "SUPPLIER_NAME", length = 512)
	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name = "ADDRESS", length = 512)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "IS_FOREIGN", length = 1)
	public String getIsForeign() {
		return this.isForeign;
	}

	public void setIsForeign(String isForeign) {
		this.isForeign = isForeign;
	}

	@Column(name = "AREA_NAME", length = 128)
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "AREA_CODE", length = 32)
	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "AREA_ITEM_NAME", length = 128)
	public String getAreaItemName() {
		return this.areaItemName;
	}

	public void setAreaItemName(String areaItemName) {
		this.areaItemName = areaItemName;
	}

	@Column(name = "AREA_ITEM_CODE", length = 32)
	public String getAreaItemCode() {
		return this.areaItemCode;
	}

	public void setAreaItemCode(String areaItemCode) {
		this.areaItemCode = areaItemCode;
	}

	@Column(name = "POST_CODE", length = 128)
	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Column(name = "WEBSITE", length = 128)
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Column(name = "FAX", length = 32)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "LINKMAN_NAME", length = 32)
	public String getLinkmanName() {
		return this.linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	@Column(name = "LINKMAN_TEL", length = 32)
	public String getLinkmanTel() {
		return this.linkmanTel;
	}

	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel;
	}

	@Column(name = "REMARKS", length = 1024)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "CREATED_BY", length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_BY_ID", length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "CREATED_DATE", length = 32)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_BY_ID", length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "MODIFIED_DATE", length = 32)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "SUPPLIER_CONTROL_STATUS", length = 32)
	public String getSupplierControlStatus() {
		return this.supplierControlStatus;
	}

	public void setSupplierControlStatus(String supplierControlStatus) {
		this.supplierControlStatus = supplierControlStatus;
	}

	@Column(name = "SUPPLIER_CONTROL_STATUS_CODE", length = 32)
	public String getSupplierControlStatusCode() {
		return this.supplierControlStatusCode;
	}

	public void setSupplierControlStatusCode(String supplierControlStatusCode) {
		this.supplierControlStatusCode = supplierControlStatusCode;
	}

	@Column(name = "FILES_NAME", length = 1024)
	public String getFilesName() {
		return filesName;
	}

	public void setFilesName(String filesName) {
		this.filesName = filesName;
	}
	
}
