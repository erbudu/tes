package com.supporter.prj.pm.bank_manage.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@MappedSuperclass
public class BaseBankAccount implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	// primary key
	private java.lang.String id;//主键

	// fields
	private java.lang.String bankAccount;
	private java.lang.String address;
	private java.util.Date openTime;
	private java.lang.String bankAccountNo;
	private java.lang.String currencyId;
	private java.lang.String currency;
	private java.lang.String nature;
	private java.lang.String postalCode;
	private java.lang.String contacts;
	private java.lang.String tel;
	private java.lang.String email;
	private java.lang.String authorTerm;
	private java.lang.String reserveSeal;
	private java.lang.String phone;
	private java.lang.String manager;
	private java.lang.String responsibler;
	private java.lang.String remarks;
	private java.lang.String createdById;
	private java.lang.String createdBy;
	private java.lang.String createdDeptId;
	private java.lang.String createdDept;
	private java.util.Date createdDate;
	private java.lang.String modifiedBy;
	private java.lang.String modifiedById;
	private java.util.Date modifiedDate;
	private double bankAmount;
	private Integer isCancel;
	private boolean isModified = true; // 是否发生了更改
	private java.lang.String prjId; // 项目id
	private java.lang.String prjName; // 项目名称
	
	/** default constructor */
	public BaseBankAccount() {
	}

	/** minimal constructor */
	public BaseBankAccount(String id) {
		this.id = id;
	}
	
	/** full constructor */
	public BaseBankAccount(String id, String bankAccount,String address,
			Date openTime, String bankAccountNo, String currency, String nature,
			String postalCode,String contacts,String tel,String email,String authorTerm,
			String reserveSeal,String phone,String manager,String responsibler,String remarks,
			String createdById,String createdBy,Date createdDate, String createdDeptId, String createdDept,
			String modifiedById, String modifiedBy, Date modifiedDate, double bankAmount, Integer isCancel,
			boolean isModified, String prjId, String prjName) {
		this.id = id;
		this.bankAccount = bankAccount;
		this.address = address;
		this.openTime = openTime;
		this.bankAccountNo = bankAccountNo;
		this.currency = currency;
		this.nature = nature;	
		this.postalCode = postalCode;
		this.contacts = contacts;
		this.tel = tel;
		this.email = email;
		this.authorTerm = authorTerm;
		this.reserveSeal = reserveSeal;
		this.phone = phone;
		this.manager = manager;
		this.responsibler = responsibler;
		this.remarks = remarks ;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.createdDeptId = createdDeptId;
		this.createdDept = createdDept;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
		this.bankAmount = bankAmount;
		this.isCancel = isCancel;
		this.isModified = isModified;
		this.prjId = prjId;
		this.prjName = prjName;
	}
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	@Column(name = "bank_account", length = 64)
	public java.lang.String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(java.lang.String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Column(name = "address", length = 128)
	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "open_time", length = 7)
	public java.util.Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(java.util.Date openTime) {
		this.openTime = openTime;
	}

	@Column(name = "bank_account_no", length = 64)
	public java.lang.String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(java.lang.String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	@Column(name = "currency_id", length = 32)
	public java.lang.String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(java.lang.String currencyId) {
		this.currencyId = currencyId;
	}
	
	@Column(name = "currency", length = 32)
	public java.lang.String getCurrency() {
		return currency;
	}

	public void setCurrency(java.lang.String currency) {
		this.currency = currency;
	}

	@Column(name = "nature", length = 32)
	public java.lang.String getNature() {
		return nature;
	}

	public void setNature(java.lang.String nature) {
		this.nature = nature;
	}

	@Column(name = "postal_code", length = 32)
	public java.lang.String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(java.lang.String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "contacts", length = 32)
	public java.lang.String getContacts() {
		return contacts;
	}

	public void setContacts(java.lang.String contacts) {
		this.contacts = contacts;
	}	
	
	@Column(name = "tel", length = 32)
	public java.lang.String getTel() {
		return tel;
	}

	public void setTel(java.lang.String tel) {
		this.tel = tel;
	}

	@Column(name = "email", length = 32)
	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	@Column(name = "author_term", length = 32)
	public java.lang.String getAuthorTerm() {
		return authorTerm;
	}

	public void setAuthorTerm(java.lang.String authorTerm) {
		this.authorTerm = authorTerm;
	}

	@Column(name = "reserve_seal", length = 32)
	public java.lang.String getReserveSeal() {
		return reserveSeal;
	}

	public void setReserveSeal(java.lang.String reserveSeal) {
		this.reserveSeal = reserveSeal;
	}

	@Column(name = "phone", length = 32)
	public java.lang.String getPhone() {
		return phone;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	@Column(name = "manager", length = 32)
	public java.lang.String getManager() {
		return manager;
	}

	public void setManager(java.lang.String manager) {
		this.manager = manager;
	}

	@Column(name = "responsibler", length = 32)
	public java.lang.String getResponsibler() {
		return responsibler;
	}

	public void setResponsibler(java.lang.String responsibler) {
		this.responsibler = responsibler;
	}

	@Column(name = "remarks", length = 256)
	public java.lang.String getRemarks() {
		return remarks;
	}

	public void setRemarks(java.lang.String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "created_by_id", length = 32)
	public java.lang.String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(java.lang.String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "created_by", length = 64)
	public java.lang.String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(java.lang.String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_date", length = 7)
	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "created_dept_id", length = 32)
	public java.lang.String getCreatedDeptId() {
		return createdDeptId;
	}

	public void setCreatedDeptId(java.lang.String createdDeptId) {
		this.createdDeptId = createdDeptId;
	}

	@Column(name = "created_dept", length = 200)
	public java.lang.String getCreatedDept() {
		return createdDept;
	}

	public void setCreatedDept(java.lang.String createdDept) {
		this.createdDept = createdDept;
	}

	@Column(name = "modified_by", length = 64)
	public java.lang.String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(java.lang.String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "modified_by_id", length = 32)
	public java.lang.String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(java.lang.String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "modified_date", length = 7)
	public java.util.Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "bank_amount", precision = 18, scale = 3)
	public double getBankAmount() {
		return bankAmount;
	}

	public void setBankAmount(double bankAmount) {
		this.bankAmount = bankAmount;
	}

	@Column(name = "is_cancel", length = 11)
	public Integer getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Integer isCancel) {
		this.isCancel = isCancel;
	}
	
	@Column(name = "is_modified", length = 1)
	@Type(type = "true_false")
	public boolean getIsModified() {
		return isModified;
	}

	public void setIsModified(boolean isModified) {
		this.isModified = isModified;
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

}
