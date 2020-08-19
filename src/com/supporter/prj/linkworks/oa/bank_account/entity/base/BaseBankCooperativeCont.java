package com.supporter.prj.linkworks.oa.bank_account.entity.base;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseBankCooperativeCont implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	  // Fields

    private String cooperativeId;
    private String bankAccountName;
    private String bankAddress;
    private String bankUrl;
    private String bankContact;
    private String contact;
    private String note;
    private String createdById;
    private String createdBy;
    private String createdDate;
    private String modifiedById;
    private String modifiedBy;
    private String modifiedDate;
    private Long status;
    private String openBankFirstNameId;
    private String openBankFirstName;

   // Constructors

   /** default constructor */
   public BaseBankCooperativeCont() {
   }

	/** minimal constructor */
   public BaseBankCooperativeCont(String cooperativeId) {
       this.cooperativeId = cooperativeId;
   }
   
   /** full constructor */
   public BaseBankCooperativeCont(String cooperativeId, String bankAccountName, String bankAddress, String bankUrl, String bankContact, String contact, String note, String createdById, String createdBy, String createdDate, String modifiedById, String modifiedBy, String modifiedDate, Long status) {
       this.cooperativeId = cooperativeId;
       this.bankAccountName = bankAccountName;
       this.bankAddress = bankAddress;
       this.bankUrl = bankUrl;
       this.bankContact = bankContact;
       this.contact = contact;
       this.note = note;
       this.createdById = createdById;
       this.createdBy = createdBy;
       this.createdDate = createdDate;
       this.modifiedById = modifiedById;
       this.modifiedBy = modifiedBy;
       this.modifiedDate = modifiedDate;
       this.status = status;
   }

  
   // Property accessors
   @Id 
   
   @Column(name="COOPERATIVE_ID", unique=true, nullable=false, length=32)

   public String getCooperativeId() {
       return this.cooperativeId;
   }
   
   public void setCooperativeId(String cooperativeId) {
       this.cooperativeId = cooperativeId;
   }
   
   @Column(name="BANK_ACCOUNT_NAME", length=256)

   public String getBankAccountName() {
       return this.bankAccountName;
   }
   
   public void setBankAccountName(String bankAccountName) {
       this.bankAccountName = bankAccountName;
   }
   
   @Column(name="BANK_ADDRESS", length=256)

   public String getBankAddress() {
       return this.bankAddress;
   }
   
   public void setBankAddress(String bankAddress) {
       this.bankAddress = bankAddress;
   }
   
   @Column(name="BANK_URL", length=256)

   public String getBankUrl() {
       return this.bankUrl;
   }
   
   public void setBankUrl(String bankUrl) {
       this.bankUrl = bankUrl;
   }
   
   @Column(name="BANK_CONTACT", length=64)

   public String getBankContact() {
       return this.bankContact;
   }
   
   public void setBankContact(String bankContact) {
       this.bankContact = bankContact;
   }
   
   @Column(name="CONTACT", length=64)

   public String getContact() {
       return this.contact;
   }
   
   public void setContact(String contact) {
       this.contact = contact;
   }
   
   @Column(name="NOTE", length=1024)

   public String getNote() {
       return this.note;
   }
   
   public void setNote(String note) {
       this.note = note;
   }
   
   @Column(name="CREATED_BY_ID", length=32)

   public String getCreatedById() {
       return this.createdById;
   }
   
   public void setCreatedById(String createdById) {
       this.createdById = createdById;
   }
   
   @Column(name="CREATED_BY", length=64)

   public String getCreatedBy() {
       return this.createdBy;
   }
   
   public void setCreatedBy(String createdBy) {
       this.createdBy = createdBy;
   }
   
   @Column(name="CREATED_DATE", length=27)

   public String getCreatedDate() {
       return this.createdDate;
   }
   
   public void setCreatedDate(String createdDate) {
       this.createdDate = createdDate;
   }
   
   @Column(name="MODIFIED_BY_ID", length=32)

   public String getModifiedById() {
       return this.modifiedById;
   }
   
   public void setModifiedById(String modifiedById) {
       this.modifiedById = modifiedById;
   }
   
   @Column(name="MODIFIED_BY", length=64)

   public String getModifiedBy() {
       return this.modifiedBy;
   }
   
   public void setModifiedBy(String modifiedBy) {
       this.modifiedBy = modifiedBy;
   }
   
   @Column(name="MODIFIED_DATE", length=27)

   public String getModifiedDate() {
       return this.modifiedDate;
   }
   
   public void setModifiedDate(String modifiedDate) {
       this.modifiedDate = modifiedDate;
   }
   
   @Column(name="STATUS", precision=22, scale=0)

   public Long getStatus() {
       return this.status;
   }
   
   public void setStatus(Long status) {
       this.status = status;
   }
  
   @Column(name="OPEN_BANK_FIRST_NAME_ID", length=32)

   public String getOpenBankFirstNameId() {
       return this.openBankFirstNameId;
   }
   
   public void setOpenBankFirstNameId(String openBankFirstNameId) {
       this.openBankFirstNameId = openBankFirstNameId;
   }
   
   @Column(name="OPEN_BANK_FIRST_NAME", length=256)

   public String getOpenBankFirstName() {
       return this.openBankFirstName;
   }
   
   public void setOpenBankFirstName(String openBankFirstName) {
       this.openBankFirstName = openBankFirstName;
   }





}
