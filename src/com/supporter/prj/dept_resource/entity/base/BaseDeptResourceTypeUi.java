package com.supporter.prj.dept_resource.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseDeptResourceTypeUi  implements java.io.Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uiId;
     private String resourceTypeCode;
     private String uiName;
     private String uiUrl;
     private String uiDesc;
     private String uiType;
     private Long displayOrderDesc;


    // Constructors

    /** default constructor */
    public BaseDeptResourceTypeUi() {
    }

	/** minimal constructor */
    public BaseDeptResourceTypeUi(String uiId) {
        this.uiId = uiId;
    }
    
    /** full constructor */
    public BaseDeptResourceTypeUi(String uiId, String resourceTypeCode, String uiName, String uiUrl, String uiDesc, String uiType, Long displayOrderDesc) {
        this.uiId = uiId;
        this.resourceTypeCode = resourceTypeCode;
        this.uiName = uiName;
        this.uiUrl = uiUrl;
        this.uiDesc = uiDesc;
        this.uiType = uiType;
        this.displayOrderDesc = displayOrderDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="UI_ID", unique=true, nullable=false, length=18)

    public String getUiId() {
        return this.uiId;
    }
    
    public void setUiId(String uiId) {
        this.uiId = uiId;
    }
    
    @Column(name="RESOURCE_TYPE_CODE", length=32)

    public String getResourceTypeCode() {
        return this.resourceTypeCode;
    }
    
    public void setResourceTypeCode(String resourceTypeCode) {
        this.resourceTypeCode = resourceTypeCode;
    }
    
    @Column(name="UI_NAME", length=64)

    public String getUiName() {
        return this.uiName;
    }
    
    public void setUiName(String uiName) {
        this.uiName = uiName;
    }
    
    @Column(name="UI_URL", length=128)

    public String getUiUrl() {
        return this.uiUrl;
    }
    
    public void setUiUrl(String uiUrl) {
        this.uiUrl = uiUrl;
    }
    
    @Column(name="UI_DESC")

    public String getUiDesc() {
        return this.uiDesc;
    }
    
    public void setUiDesc(String uiDesc) {
        this.uiDesc = uiDesc;
    }
    
    @Column(name="UI_TYPE", length=16)

    public String getUiType() {
        return this.uiType;
    }
    
    public void setUiType(String uiType) {
        this.uiType = uiType;
    }
    
    @Column(name="DISPLAY_ORDER", precision=22, scale=0)

    public Long getDisplayOrderDesc() {
        return this.displayOrderDesc;
    }
    
    public void setDisplayOrderDesc(Long displayOrderDesc) {
        this.displayOrderDesc = displayOrderDesc;
    }
   




}
