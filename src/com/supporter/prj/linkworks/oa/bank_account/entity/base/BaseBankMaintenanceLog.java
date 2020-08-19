package com.supporter.prj.linkworks.oa.bank_account.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * BankMaintenanceLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@MappedSuperclass
public class BaseBankMaintenanceLog  implements java.io.Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String logId;
     private String logType;
     private String logOperId;
     private String logOperName;
     private String logOperDate;
     private String logContent;
     private String effectiveId;


    // Constructors

    /** default constructor */
    public BaseBankMaintenanceLog() {
    }

	/** minimal constructor */
    public BaseBankMaintenanceLog(String logId) {
        this.logId = logId;
    }
    
    /** full constructor */
    public BaseBankMaintenanceLog(String logId, String logType, String logOperId, String logOperName, String logOperDate, String logContent,String effectiveId) {
        this.logId = logId;
        this.logType = logType;
        this.logOperId = logOperId;
        this.logOperName = logOperName;
        this.logOperDate = logOperDate;
        this.logContent = logContent;
        this.effectiveId = effectiveId;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="LOG_ID", unique=true, nullable=false, length=32)

    public String getLogId() {
        return this.logId;
    }
    
    public void setLogId(String logId) {
        this.logId = logId;
    }
    
    @Column(name="LOG_TYPE", length=32)

    public String getLogType() {
        return this.logType;
    }
    
    public void setLogType(String logType) {
        this.logType = logType;
    }
    
    @Column(name="LOG_OPER_ID", length=32)

    public String getLogOperId() {
        return this.logOperId;
    }
    
    public void setLogOperId(String logOperId) {
        this.logOperId = logOperId;
    }
    
    @Column(name="LOG_OPER_NAME", length=64)

    public String getLogOperName() {
        return this.logOperName;
    }
    
    public void setLogOperName(String logOperName) {
        this.logOperName = logOperName;
    }
    
    @Column(name="LOG_OPER_DATE", length=27)

    public String getLogOperDate() {
        return this.logOperDate;
    }
    
    public void setLogOperDate(String logOperDate) {
        this.logOperDate = logOperDate;
    }
    
    @Column(name="LOG_CONTENT", length=3800)

    public String getLogContent() {
        return this.logContent;
    }
    
    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }
    @Column(name="EFFECTIVE_ID", length=32)
	public String getEffectiveId() {
		return effectiveId;
	}

	public void setEffectiveId(String effectiveId) {
		this.effectiveId = effectiveId;
	}
   








}