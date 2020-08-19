package com.supporter.prj.linkworks.oa.bulletin.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseVBulletin  implements java.io.Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String bulletinId;
     private String bulletinTitle;
     private String orgId;
     private String createdBy;
     private String createdDate;
     private String createdById;
     private String modifiedDate;
     private String modifiedBy;
     private String modifiedById;
     private String creatorName;
     private String messageDate;
     private String modifierName;
     private String bulletinType;
     private String fileName;
     private Integer publishStatus;
     private String publisherId;
     private String publisherName;
     private String deptId;
     private String deptName;
     private String deptResourceId;
     private String alwaysOnTop;
     private String deptResourceName;
     private String relatedTable;
     private String relatedIdVal;
     private String authorizeeId;
     private String canRead;
     private String canWrite;
     private String canDelete;
     private String canNew;
     private String fullAccess;


    // Constructors

    /** default constructor */
    public BaseVBulletin() {
    }

	/** minimal constructor */
    public BaseVBulletin(String bulletinId) {
        this.bulletinId = bulletinId;
    }
    
    /** full constructor */
    public BaseVBulletin(String bulletinId, String bulletinTitle, String orgId, String createdBy, String createdDate, String createdById, String modifiedDate, String modifiedBy, String modifiedById, String creatorName, String messageDate, String modifierName, String bulletinType, String fileName, Integer publishStatus, String publisherId, String publisherName, String deptId, String deptName, String deptResourceId, String alwaysOnTop, String deptResourceName, String relatedTable, String relatedIdVal, String authorizeeId, String canRead, String canWrite, String canDelete, String canNew, String fullAccess) {
        this.bulletinId = bulletinId;
        this.bulletinTitle = bulletinTitle;
        this.orgId = orgId;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.createdById = createdById;
        this.modifiedDate = modifiedDate;
        this.modifiedBy = modifiedBy;
        this.modifiedById = modifiedById;
        this.creatorName = creatorName;
        this.messageDate = messageDate;
        this.modifierName = modifierName;
        this.bulletinType = bulletinType;
        this.fileName = fileName;
        this.publishStatus = publishStatus;
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptResourceId = deptResourceId;
        this.alwaysOnTop = alwaysOnTop;
        this.deptResourceName = deptResourceName;
        this.relatedTable = relatedTable;
        this.relatedIdVal = relatedIdVal;
        this.authorizeeId = authorizeeId;
        this.canRead = canRead;
        this.canWrite = canWrite;
        this.canDelete = canDelete;
        this.canNew = canNew;
        this.fullAccess = fullAccess;
    }

   
    // Property accessors
    @Id
    @Column(name="BULLETIN_ID", nullable=false, length=32)

    public String getBulletinId() {
        return this.bulletinId;
    }
    
    public void setBulletinId(String bulletinId) {
        this.bulletinId = bulletinId;
    }

    @Column(name="BULLETIN_TITLE")

    public String getBulletinTitle() {
        return this.bulletinTitle;
    }
    
    public void setBulletinTitle(String bulletinTitle) {
        this.bulletinTitle = bulletinTitle;
    }

    @Column(name="ORG_ID", length=32)

    public String getOrgId() {
        return this.orgId;
    }
    
    public void setOrgId(String orgId) {
        this.orgId = orgId;
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

    @Column(name="CREATED_BY_ID", length=32)

    public String getCreatedById() {
        return this.createdById;
    }
    
    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    @Column(name="MODIFIED_DATE", length=27)

    public String getModifiedDate() {
        return this.modifiedDate;
    }
    
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Column(name="MODIFIED_BY", length=64)

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

    @Column(name="CREATOR_NAME", length=64)

    public String getCreatorName() {
        return this.creatorName;
    }
    
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Column(name="MESSAGE_DATE", length=27)

    public String getMessageDate() {
        return this.messageDate;
    }
    
    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    @Column(name="MODIFIER_NAME", length=64)

    public String getModifierName() {
        return this.modifierName;
    }
    
    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    @Column(name="BULLETIN_TYPE", length=32)

    public String getBulletinType() {
        return this.bulletinType;
    }
    
    public void setBulletinType(String bulletinType) {
        this.bulletinType = bulletinType;
    }

    @Column(name="FILE_NAME", length=1024)

    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name="PUBLISH_STATUS", precision=22, scale=0)

    public Integer getPublishStatus() {
        return this.publishStatus;
    }
    
    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    @Column(name="PUBLISHER_ID", length=32)

    public String getPublisherId() {
        return this.publisherId;
    }
    
    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    @Column(name="PUBLISHER_NAME", length=64)

    public String getPublisherName() {
        return this.publisherName;
    }
    
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @Column(name="DEPT_ID", length=32)

    public String getDeptId() {
        return this.deptId;
    }
    
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Column(name="DEPT_NAME", length=64)

    public String getDeptName() {
        return this.deptName;
    }
    
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Column(name="DEPT_RESOURCE_ID", length=32)

    public String getDeptResourceId() {
        return this.deptResourceId;
    }
    
    public void setDeptResourceId(String deptResourceId) {
        this.deptResourceId = deptResourceId;
    }

    @Column(name="ALWAYS_ON_TOP", length=1)

    public String getAlwaysOnTop() {
        return this.alwaysOnTop;
    }
    
    public void setAlwaysOnTop(String alwaysOnTop) {
        this.alwaysOnTop = alwaysOnTop;
    }

    @Column(name="DEPT_RESOURCE_NAME")

    public String getDeptResourceName() {
        return this.deptResourceName;
    }
    
    public void setDeptResourceName(String deptResourceName) {
        this.deptResourceName = deptResourceName;
    }

    @Column(name="RELATED_TABLE", length=64)

    public String getRelatedTable() {
        return this.relatedTable;
    }
    
    public void setRelatedTable(String relatedTable) {
        this.relatedTable = relatedTable;
    }

    @Column(name="RELATED_ID_VAL", length=32)

    public String getRelatedIdVal() {
        return this.relatedIdVal;
    }
    
    public void setRelatedIdVal(String relatedIdVal) {
        this.relatedIdVal = relatedIdVal;
    }

    @Column(name="AUTHORIZEE_ID", length=32)

    public String getAuthorizeeId() {
        return this.authorizeeId;
    }
    
    public void setAuthorizeeId(String authorizeeId) {
        this.authorizeeId = authorizeeId;
    }

    @Column(name="CAN_READ", length=1)

    public String getCanRead() {
        return this.canRead;
    }
    
    public void setCanRead(String canRead) {
        this.canRead = canRead;
    }

    @Column(name="CAN_WRITE", length=1)

    public String getCanWrite() {
        return this.canWrite;
    }
    
    public void setCanWrite(String canWrite) {
        this.canWrite = canWrite;
    }

    @Column(name="CAN_DELETE", length=1)

    public String getCanDelete() {
        return this.canDelete;
    }
    
    public void setCanDelete(String canDelete) {
        this.canDelete = canDelete;
    }

    @Column(name="CAN_NEW", length=1)

    public String getCanNew() {
        return this.canNew;
    }
    
    public void setCanNew(String canNew) {
        this.canNew = canNew;
    }

    @Column(name="FULL_ACCESS", length=1)

    public String getFullAccess() {
        return this.fullAccess;
    }
    
    public void setFullAccess(String fullAccess) {
        this.fullAccess = fullAccess;
    }
   
}
