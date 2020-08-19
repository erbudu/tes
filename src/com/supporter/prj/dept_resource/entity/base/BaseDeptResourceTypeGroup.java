package com.supporter.prj.dept_resource.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseDeptResourceTypeGroup  implements java.io.Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String recordId;
     private String resourceTypeCode;
     private String authType;
     private String canRead;
     private String canNew;
     private String canWrite;
     private String canDelete;
     private String fullAccess;
     private String groupId;


    // Constructors

    /** default constructor */
    public BaseDeptResourceTypeGroup() {
    }

	/** minimal constructor */
    public BaseDeptResourceTypeGroup(String recordId) {
        this.recordId = recordId;
    }
    
    /** full constructor */
    public BaseDeptResourceTypeGroup(String recordId, String resourceTypeCode, String authType, String canRead, String canNew, String canWrite, String canDelete, String fullAccess, String groupId) {
        this.recordId = recordId;
        this.resourceTypeCode = resourceTypeCode;
        this.authType = authType;
        this.canRead = canRead;
        this.canNew = canNew;
        this.canWrite = canWrite;
        this.canDelete = canDelete;
        this.fullAccess = fullAccess;
        this.groupId = groupId;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="RECORD_ID", unique=true, nullable=false, length=18)

    public String getRecordId() {
        return this.recordId;
    }
    
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    
    @Column(name="RESOURCE_TYPE_CODE", length=32)

    public String getResourceTypeCode() {
        return this.resourceTypeCode;
    }
    
    public void setResourceTypeCode(String resourceTypeCode) {
        this.resourceTypeCode = resourceTypeCode;
    }
    
    @Column(name="AUTH_TYPE", length=32)

    public String getAuthType() {
        return this.authType;
    }
    
    public void setAuthType(String authType) {
        this.authType = authType;
    }
    
    @Column(name="CAN_READ", length=1)

    public String getCanRead() {
        return this.canRead;
    }
    
    public void setCanRead(String canRead) {
    	if(canRead==null){
    		canRead="0";
    	 }
    	this.canRead = canRead;
    }
    
    @Column(name="CAN_NEW", length=1)

    public String getCanNew() {
        return this.canNew;
    }
    
    public void setCanNew(String canNew) {
    	if(canNew==null){
    		canNew="0";
    	 }
    	this.canNew = canNew;
    }
    
    @Column(name="CAN_WRITE", length=1)

    public String getCanWrite() {
        return this.canWrite;
    }
    
    public void setCanWrite(String canWrite) {
    	if(canWrite==null){
    		canWrite="0";
    	 } 

    	this.canWrite = canWrite;
    }
    
    @Column(name="CAN_DELETE", length=1)

    public String getCanDelete() {
        return this.canDelete;
    }
    
    public void setCanDelete(String canDelete) {
    	if(canDelete==null){
    		canDelete="0";
    	 }
    	this.canDelete = canDelete;
    }
    
    @Column(name="FULL_ACCESS", length=1)

    public String getFullAccess() {
        return this.fullAccess;
    }
    
    public void setFullAccess(String fullAccess) {
    	if(fullAccess==null){
    		fullAccess="0";
    	 }
    	this.fullAccess = fullAccess;
    }
    
    @Column(name="GROUP_ID", length=18)

    public String getGroupId() {
        return this.groupId;
    }
    
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

}
