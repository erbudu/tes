package com.supporter.prj.linkworks.oa.authority_apply.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * OaAuthorityApplyContent entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseAuthorityApplyContent  implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String applyId;
     private String authorityApplyReason;
     private String authorityApplyContent;
     private String authorityApplyNote;
     private String authorityApplyReasonE;


    // Constructors

    /** default constructor */
    public BaseAuthorityApplyContent() {
    }

	/** minimal constructor */
    public BaseAuthorityApplyContent(String applyId) {
        this.applyId = applyId;
    }
    
    /** full constructor */
    public BaseAuthorityApplyContent(String applyId, String authorityApplyReason, String authorityApplyContent, String authorityApplyNote, String authorityApplyReasonE) {
        this.applyId = applyId;
        this.authorityApplyReason = authorityApplyReason;
        this.authorityApplyContent = authorityApplyContent;
        this.authorityApplyNote = authorityApplyNote;
        this.authorityApplyReasonE = authorityApplyReasonE;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="APPLY_ID", unique=true, nullable=false, length=32)

    public String getApplyId() {
        return this.applyId;
    }
    
    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
    
    @Column(name="AUTHORITY_APPLY_REASON")

    public String getAuthorityApplyReason() {
        return this.authorityApplyReason;
    }
    
    public void setAuthorityApplyReason(String authorityApplyReason) {
        this.authorityApplyReason = authorityApplyReason;
    }
    
    @Column(name="AUTHORITY_APPLY_CONTENT")

    public String getAuthorityApplyContent() {
        return this.authorityApplyContent;
    }
    
    public void setAuthorityApplyContent(String authorityApplyContent) {
        this.authorityApplyContent = authorityApplyContent;
    }
    
    @Column(name="AUTHORITY_APPLY_NOTE")

    public String getAuthorityApplyNote() {
        return this.authorityApplyNote;
    }
    
    public void setAuthorityApplyNote(String authorityApplyNote) {
        this.authorityApplyNote = authorityApplyNote;
    }
    
    @Column(name="AUTHORITY_APPLY_REASON_E")

    public String getAuthorityApplyReasonE() {
        return this.authorityApplyReasonE;
    }
    
    public void setAuthorityApplyReasonE(String authorityApplyReasonE) {
        this.authorityApplyReasonE = authorityApplyReasonE;
    }
   








}