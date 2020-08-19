			package com.supporter.prj.cneec.tpc.custom.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseCustomLinkman implements Serializable {
	private static final long serialVersionUID = 1L;
	//Fields
	
	private String linkmanId;
    private String customId;
    private String name;
    private String post;
    private String telephone;
    private String mobilePhone;
    private String fax;
    private String mail;
    private String isMainLinkman;
    private String remark;


   // Constructors

   /** default constructor */
   public BaseCustomLinkman() {
   }

	/** minimal constructor */
   public BaseCustomLinkman(String linkmanId) {
       this.linkmanId = linkmanId;
   }
   
   /** full constructor */
   public BaseCustomLinkman(String linkmanId, String customId, String name, String post, String telephone, String mobilePhone, String fax, String mail, String isMainLinkman, String remark) {
       this.linkmanId = linkmanId;
       this.customId = customId;
       this.name = name;
       this.post = post;
       this.telephone = telephone;
       this.mobilePhone = mobilePhone;
       this.fax = fax;
       this.mail = mail;
       this.isMainLinkman = isMainLinkman;
       this.remark = remark;
   }

  
   // Property accessors
   @Id 
   
   @Column(name="LINKMAN_ID", unique=true, nullable=false, length=32)
   public String getLinkmanId() {
       return this.linkmanId;
   }
   
   public void setLinkmanId(String linkmanId) {
       this.linkmanId = linkmanId;
   }
   
   @Column(name="CUSTOM_ID", length=32)

   public String getCustomId() {
       return this.customId;
   }
   
   public void setCustomId(String customId) {
       this.customId = customId;
   }
   
   @Column(name="NAME", length=32)

   public String getName() {
       return this.name;
   }
   
   public void setName(String name) {
       this.name = name;
   }
   
   @Column(name="POST", length=128)

   public String getPost() {
       return this.post;
   }
   
   public void setPost(String post) {
       this.post = post;
   }
   
   @Column(name="TELEPHONE", length=32)

   public String getTelephone() {
       return this.telephone;
   }
   
   public void setTelephone(String telephone) {
       this.telephone = telephone;
   }
   
   @Column(name="MOBILE_PHONE", length=32)

   public String getMobilePhone() {
       return this.mobilePhone;
   }
   
   public void setMobilePhone(String mobilePhone) {
       this.mobilePhone = mobilePhone;
   }
   
   @Column(name="FAX", length=32)

   public String getFax() {
       return this.fax;
   }
   
   public void setFax(String fax) {
       this.fax = fax;
   }
   
   @Column(name="MAIL", length=128)

   public String getMail() {
       return this.mail;
   }
   
   public void setMail(String mail) {
       this.mail = mail;
   }
   
   @Column(name="IS_MAIN_LINKMAN", length=32)

   public String getIsMainLinkman() {
       return this.isMainLinkman;
   }
   
   public void setIsMainLinkman(String isMainLinkman) {
       this.isMainLinkman = isMainLinkman;
   }
   
   @Column(name="REMARK", length=1024)

   public String getRemark() {
       return this.remark;
   }
   
   public void setRemark(String remark) {
       this.remark = remark;
   }
    
    
    
}
