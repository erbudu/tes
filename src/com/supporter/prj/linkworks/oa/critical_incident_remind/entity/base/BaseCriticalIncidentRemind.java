package com.supporter.prj.linkworks.oa.critical_incident_remind.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseCriticalIncidentRemind implements java.io.Serializable {


    // Fields

     /**
	 * 
	 */
	 private static final long serialVersionUID = 1L;
	 private String incidentId;
     private String incidentName;
     private String incidentRoundup;
     private String incidentContent;
     private String expireDate;
     private String reminderDate;
     private String isRepeatRemind;//是否重复
     private Long repeatSpace;//重复天数
     private String fileName;
     private Long reminderStatus;
     private Long status;
     private String createdBy;
     private String createdById;
     private String createdDate;
     private String modifiedBy;
     private String modifiedById;
     private String modifiedDate;
     private String latestAgencyDate;
     private String messageId;
     private long remindingTimes;
     private String notifyPersonIds;//被提醒人id
     private String notifyPersonNames;//被提醒人姓名
     private String reminderType;//提醒类型（按人员提醒、按部门提醒）
     private String notifyDeptIds;//被提醒部门id
     private String notifyDeptNames;//被提醒部门名称
     private String generalPersonIds;//所有被提醒人id
     private String generalPersonNames;//所有被提醒人姓名
     //创建部门id
     private String deptId;
     //创建部门名称
     private String deptName;





    // Constructors
   


	/** default constructor */
    public BaseCriticalIncidentRemind() {
    }

	/** minimal constructor */
    public BaseCriticalIncidentRemind(String incidentId) {
        this.incidentId = incidentId;
    }
    
    /** full constructor */
    public BaseCriticalIncidentRemind(String incidentId, String incidentName, String incidentRoundup, String incidentContent, String expireDate, String reminderDate, String isRepeatRemind, Long repeatSpace, String fileName, Long reminderStatus, Long status, String createdBy, String createdById, String createdDate, String modifiedBy, String modifiedById, String modifiedDate,String latestAgencyDate,String messageId,Long remindingTimes,String notifyPersonIds,String notifyPersonNames,String reminderType,String notifyDeptIds,String notifyDeptNames,String generalPersonIds,String generalPersonNames,String deptId,String deptName) {
        this.incidentId = incidentId;
        this.incidentName = incidentName;
        this.incidentRoundup = incidentRoundup;
        this.incidentContent = incidentContent;
        this.expireDate = expireDate;
        this.reminderDate = reminderDate;
        this.isRepeatRemind = isRepeatRemind;
        this.repeatSpace = repeatSpace;
        this.fileName = fileName;
        this.reminderStatus = reminderStatus;
        this.status = status;
        this.createdBy = createdBy;
        this.createdById = createdById;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedById = modifiedById;
        this.modifiedDate = modifiedDate;
        this.latestAgencyDate=latestAgencyDate;
        this.messageId=messageId;
        this.remindingTimes=remindingTimes;
        this.notifyPersonIds=notifyPersonIds;
        this.notifyPersonNames=notifyPersonNames;
        this.reminderType=reminderType;
        this.notifyDeptIds=notifyDeptIds;
        this.notifyDeptNames=notifyDeptNames;
        this.generalPersonIds=generalPersonIds;
        this.generalPersonNames=generalPersonNames;
        this.deptId=deptId;
        this.deptName=deptName;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="INCIDENT_ID", unique=true, nullable=false, length=32)

    public String getIncidentId() {
        return this.incidentId;
    }
    
    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }
    
    @Column(name="INCIDENT_NAME", length=256)

    public String getIncidentName() {
        return this.incidentName;
    }
    
    public void setIncidentName(String incidentName) {
        this.incidentName = incidentName;
    }
    
    @Column(name="INCIDENT_ROUNDUP", length=32)

    public String getIncidentRoundup() {
        return this.incidentRoundup;
    }
    
    public void setIncidentRoundup(String incidentRoundup) {
        this.incidentRoundup = incidentRoundup;
    }
    
    @Column(name="INCIDENT_CONTENT", length=3072)

    public String getIncidentContent() {
        return this.incidentContent;
    }
    
    public void setIncidentContent(String incidentContent) {
        this.incidentContent = incidentContent;
    }
    
    @Column(name="EXPIREDATE", length=32)

    public String getExpireDate() {
        return this.expireDate;
    }
    
    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
    
    @Column(name="REMINDERDATE", length=32)

    public String getReminderDate() {
        return this.reminderDate;
    }
    
    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }
    
    @Column(name="ISREPEATREMIND", length=32)

    public String getIsRepeatRemind() {
        return this.isRepeatRemind;
    }
    
    public void setIsRepeatRemind(String isRepeatRemind) {
        this.isRepeatRemind = isRepeatRemind;
    }
    
    @Column(name="REPEATSPACE", precision=22, scale=0)

    public Long getRepeatSpace() {
        return this.repeatSpace;
    }
    
    public void setRepeatSpace(Long repeatSpace) {
        this.repeatSpace = repeatSpace;
    }
    
    @Column(name="FILE_NAME", length=512)

    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @Column(name="REMINDERSTATUS", precision=22, scale=0)

    public Long getReminderStatus() {
        return this.reminderStatus;
    }
    
    public void setReminderStatus(Long reminderStatus) {
        this.reminderStatus = reminderStatus;
    }
    
    @Column(name="STATUS", precision=22, scale=0)

    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
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
    
    @Column(name="LATESTAGENCY_DATE", length=32)
    
	public String getLatestAgencyDate() {
		return latestAgencyDate;
	}

	public void setLatestAgencyDate(String latestAgencyDate) {
		this.latestAgencyDate = latestAgencyDate;
	}
	@Column(name="MESSAGE_ID", length=3500)
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	@Column(name="REMINDING_TIMES", precision=22, scale=0)
    public long getRemindingTimes() {
		return remindingTimes;
	}

	public void setRemindingTimes(long remindingTimes) {
		this.remindingTimes = remindingTimes;
	}
	@Column(name="NOTIFY_PERSONIDS", length=3072)
	public String getNotifyPersonIds() {
		return notifyPersonIds;
	}

	public void setNotifyPersonIds(String notifyPersonIds) {
		this.notifyPersonIds = notifyPersonIds;
	}
	@Column(name="NOTIFY_PERSONNAMES", length=3072)
	public String getNotifyPersonNames() {
		return notifyPersonNames;
	}

	public void setNotifyPersonNames(String notifyPersonNames) {
		this.notifyPersonNames = notifyPersonNames;
	}
	@Column(name="REMINDER_TYPE", length=10)
	public String getReminderType() {
		return reminderType;
	}

	public void setReminderType(String reminderType) {
		this.reminderType = reminderType;
	}
	
	@Column(name="NOTIFY_DEPTIDS", length=32)
	public String getNotifyDeptIds() {
		return notifyDeptIds;
	}

	public void setNotifyDeptIds(String notifyDeptIds) {
		this.notifyDeptIds = notifyDeptIds;
	}
	@Column(name="NOTIFY_DEPTNAMES", length=512)
	public String getNotifyDeptNames() {
		return notifyDeptNames;
	}

	public void setNotifyDeptNames(String notifyDeptNames) {
		this.notifyDeptNames = notifyDeptNames;
	}
	@Column(name="GENERAL_PERSONNEIDS", length=3072)
	public String getGeneralPersonIds() {
		return generalPersonIds;
	}

	public void setGeneralPersonIds(String generalPersonIds) {
		this.generalPersonIds = generalPersonIds;
	}
	@Column(name="GENERAL_PERSONNENAMES", length=3072)
	public String getGeneralPersonNames() {
		return generalPersonNames;
	}

	public void setGeneralPersonNames(String generalPersonNames) {
		this.generalPersonNames = generalPersonNames;
	}
	@Column(name="DEPT_ID", length=32)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name="DEPT_NAME", length=128)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
   
}
