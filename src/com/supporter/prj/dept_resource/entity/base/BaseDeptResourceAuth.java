package com.supporter.prj.dept_resource.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseDeptResourceAuth  implements java.io.Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String authId;
     private String resourceId;
     private String authType;
     private String authorizeeType;
     private String authorizeeId;
     private String canRead;
     private String canWrite;
     private String canDelete;
     private String fullAccess;
     private String canNew;


    // Constructors

    /** default constructor */
    public BaseDeptResourceAuth() {
    }

	/** minimal constructor */
    public BaseDeptResourceAuth(String authId) {
        this.authId = authId;
    }
    
    /** full constructor */
    public BaseDeptResourceAuth(String authId, String resourceId, String authType, String authorizeeType, String authorizeeId, String canRead, String canWrite, String canDelete, String fullAccess, String canNew) {
        this.authId = authId;
        this.resourceId = resourceId;
        this.authType = authType;
        this.authorizeeType = authorizeeType;
        this.authorizeeId = authorizeeId;
        this.canRead = canRead;
        this.canWrite = canWrite;
        this.canDelete = canDelete;
        this.fullAccess = fullAccess;
        this.canNew = canNew;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="AUTH_ID", unique=true, nullable=false, length=18)

    public String getAuthId() {
        return this.authId;
    }
    
    public void setAuthId(String authId) {
        this.authId = authId;
    }
    
    @Column(name="RESOURCE_ID", length=18)

    public String getResourceId() {
        return this.resourceId;
    }
    
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    
    @Column(name="AUTH_TYPE", length=32)

    public String getAuthType() {
        return this.authType;
    }
    
    public void setAuthType(String authType) {
        this.authType = authType;
    }
    
    @Column(name="AUTHORIZEE_TYPE", length=16)

    public String getAuthorizeeType() {
        return this.authorizeeType;
    }
    
    public void setAuthorizeeType(String authorizeeType) {
        this.authorizeeType = authorizeeType;
    }
    
    @Column(name="AUTHORIZEE_ID", length=20)

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
    	if(canRead==null){
    	   canRead="0";
       }
    	this.canRead = canRead;
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
   










}
