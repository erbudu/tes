package com.supporter.prj.linkworks.oa.netin.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * BaseOaNetinPerson entity provides the base persistence definition of the OaNetinPerson entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass

public abstract class BaseOaNetinPerson  implements java.io.Serializable {


    // Fields    

     private String id;
     private String netinId;
     private String name;
     private String nameSpell;
     private String sex;
     private String userType;
     private String accountType;
     private String userAccount;
     private String mail;


    // Constructors

    /** default constructor */
    public BaseOaNetinPerson() {
    }

	/** minimal constructor */
    public BaseOaNetinPerson(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public BaseOaNetinPerson(String id, String netinId, String name, String nameSpell, String sex, String userType, String accountType, String userAccount, String mail) {
        this.id = id;
        this.netinId = netinId;
        this.name = name;
        this.nameSpell = nameSpell;
        this.sex = sex;
        this.userType = userType;
        this.accountType = accountType;
        this.userAccount = userAccount;
        this.mail = mail;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ID", unique=true, nullable=false, length=32)

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name="NETIN_ID", length=32)

    public String getNetinId() {
        return this.netinId;
    }
    
    public void setNetinId(String netinId) {
        this.netinId = netinId;
    }
    
    @Column(name="NAME", length=64)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="NAME_SPELL", length=64)
    
    public String getNameSpell() {
    	return this.nameSpell;
    }
    
    public void setNameSpell(String nameSpell) {
    	this.nameSpell = nameSpell;
    }
    
    @Column(name="SEX", length=32)

    public String getSex() {
        return this.sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    @Column(name="USER_TYPE", length=32)

    public String getUserType() {
        return this.userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    @Column(name="ACCOUNT_TYPE", length=32)

    public String getAccountType() {
        return this.accountType;
    }
    
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    @Column(name="USER_ACCOUNT", length=64)

    public String getUserAccount() {
        return this.userAccount;
    }
    
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    
    @Column(name="MAIL", length=64)

    public String getMail() {
        return this.mail;
    }
    
    public void setMail(String mail) {
        this.mail = mail;
    }
   








}