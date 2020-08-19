package com.supporter.prj.linkworks.oa.authority_apply.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;



/**
 * OaAuthorityApplyPerson entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseAuthorityApplyPerson  implements java.io.Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String authorityApplyPersonId;
     private String applyId;
     private String addName;
     private String addsName;
     private String authName;
     private String passNo;
     private String madam;
     private String men;
     private String authorityName;
     private String passportNo;
     private Long poleId;
     private String nameInE;
     private String nameOutE;
     private String gender;
     private String authNameE;
     private String authNameF;


    // Constructors

    /** default constructor */
    public BaseAuthorityApplyPerson() {
    }

	/** minimal constructor */
    public BaseAuthorityApplyPerson(String authorityApplyPersonId) {
        this.authorityApplyPersonId = authorityApplyPersonId;
    }
    
    /** full constructor */
    public BaseAuthorityApplyPerson(String authorityApplyPersonId, String applyId, String addName, String addsName, String authName, String passNo, String madam, String men, String authorityName, String passportNo, Long poleId, String nameInE, String nameOutE, String gender, String authNameE, String authNameF) {
        this.authorityApplyPersonId = authorityApplyPersonId;
        this.applyId = applyId;
        this.addName = addName;
        this.addsName = addsName;
        this.authName = authName;
        this.passNo = passNo;
        this.madam = madam;
        this.men = men;
        this.authorityName = authorityName;
        this.passportNo = passportNo;
        this.poleId = poleId;
        this.nameInE = nameInE;
        this.nameOutE = nameOutE;
        this.gender = gender;
        this.authNameE = authNameE;
        this.authNameF = authNameF;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="AUTHORITY_APPLY_PERSON_ID", unique=true, nullable=false, precision=32, scale=0)

    public String getAuthorityApplyPersonId() {
        return this.authorityApplyPersonId;
    }
    
    public void setAuthorityApplyPersonId(String authorityApplyPersonId) {
        this.authorityApplyPersonId = authorityApplyPersonId;
    }
    
    @Column(name="APPLY_ID", length=18)

    public String getApplyId() {
        return this.applyId;
    }
    
    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
    
    @Column(name="ADD_NAME", length=32)

    public String getAddName() {
        return this.addName;
    }
    
    public void setAddName(String addName) {
        this.addName = addName;
    }
    
    @Column(name="ADDS_NAME", length=32)

    public String getAddsName() {
        return this.addsName;
    }
    
    public void setAddsName(String addsName) {
        this.addsName = addsName;
    }
    
    @Column(name="AUTH_NAME", length=32)

    public String getAuthName() {
        return this.authName;
    }
    
    public void setAuthName(String authName) {
        this.authName = authName;
    }
    
    @Column(name="PASS_NO", length=32)

    public String getPassNo() {
        return this.passNo;
    }
    
    public void setPassNo(String passNo) {
        this.passNo = passNo;
    }
    
    @Column(name="MADAM", length=27)

    public String getMadam() {
        return this.madam;
    }
    
    public void setMadam(String madam) {
        this.madam = madam;
    }
    
    @Column(name="MEN", length=27)

    public String getMen() {
        return this.men;
    }
    
    public void setMen(String men) {
        this.men = men;
    }
    
    @Column(name="AUTHORITY_NAME", length=32)

    public String getAuthorityName() {
        return this.authorityName;
    }
    
    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }
    
    @Column(name="PASSPORT_NO", length=32)

    public String getPassportNo() {
        return this.passportNo;
    }
    
    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }
    
    @Column(name="POLE_ID", precision=22, scale=0)

    public Long getPoleId() {
        return this.poleId;
    }
    
    public void setPoleId(Long poleId) {
        this.poleId = poleId;
    }
    
    @Column(name="NAME_IN_E", length=32)

    public String getNameInE() {
        return this.nameInE;
    }
    
    public void setNameInE(String nameInE) {
        this.nameInE = nameInE;
    }
    
    @Column(name="NAME_OUT_E", length=32)

    public String getNameOutE() {
        return this.nameOutE;
    }
    
    public void setNameOutE(String nameOutE) {
        this.nameOutE = nameOutE;
    }
    
    @Column(name="GENDER", length=8)

    public String getGender() {
        return this.gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    @Column(name="AUTH_NAME_E", length=32)

    public String getAuthNameE() {
        return this.authNameE;
    }
    
    public void setAuthNameE(String authNameE) {
        this.authNameE = authNameE;
    }
    
    @Column(name="AUTH_NAME_F", length=32)

    public String getAuthNameF() {
        return this.authNameF;
    }
    
    public void setAuthNameF(String authNameF) {
        this.authNameF = authNameF;
    }
   








}