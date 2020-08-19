package com.supporter.prj.dept_resource.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseDeptResource  implements java.io.Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceId;
	private String resourceName;
	private String deptId;
	private String resourceTypeCode;
	private String categoryId;
	private String managerId;
	private String managerName;
	private Long displayOrder;
	private String createdBy;
	private String createdDate;
	private String needProc;
	private String createdById;
	private String typeCtblItemId;
	private String typeCtblItemName;


    // Constructors

    /** default constructor */
    public BaseDeptResource() {
    }

	/** minimal constructor */
    public BaseDeptResource(String resourceId, String resourceName) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
    }
    
    /** full constructor */
    public BaseDeptResource(String resourceId, String resourceName, String deptId, String resourceTypeCode, String categoryId, String managerId, String managerName, Long displayOrder, String createdBy, String createdDate, String needProc, String createdById) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.deptId = deptId;
        this.resourceTypeCode = resourceTypeCode;
        this.categoryId = categoryId;
        this.managerId = managerId;
        this.managerName = managerName;
        this.displayOrder = displayOrder;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.needProc = needProc;
        this.createdById = createdById;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="RESOURCE_ID", unique=true, nullable=false, length=32)

    public String getResourceId() {
        return this.resourceId;
    }
    
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    
    @Column(name="RESOURCE_NAME", nullable=false, length=64)

    public String getResourceName() {
        return this.resourceName;
    }
    
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    
    @Column(name="DEPT_ID", length=32)

    public String getDeptId() {
        return this.deptId;
    }
    
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    
    @Column(name="RESOURCE_TYPE_CODE", length=32)

    public String getResourceTypeCode() {
        return this.resourceTypeCode;
    }
    
    public void setResourceTypeCode(String resourceTypeCode) {
        this.resourceTypeCode = resourceTypeCode;
    }
    
    @Column(name="CATEGORY_ID", length=32)

    public String getCategoryId() {
        return this.categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
    @Column(name="MANAGER_ID", length=32)

    public String getManagerId() {
        return this.managerId;
    }
    
    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
    
    @Column(name="MANAGER_NAME", length=32)

    public String getManagerName() {
        return this.managerName;
    }
    
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
    
    @Column(name="DISPLAY_ORDER", precision=22, scale=0)

    public Long getDisplayOrder() {
        return this.displayOrder;
    }
    
    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
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
    
    @Column(name="NEED_PROC", length=1)

    public String getNeedProc() {
        return this.needProc;
    }
    
    public void setNeedProc(String needProc) {
       if(needProc==null){
    	   needProc="0";
       }
    	this.needProc = needProc;
    }
    
    @Column(name="CREATED_BY_ID", length=32)

    public String getCreatedById() {
        return this.createdById;
    }
    
    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    @Column(name="type_ctbl_item_id", length=32)
    public String getTypeCtblItemId(){
    	return this.typeCtblItemId;
    }
    public void setTypeCtblItemId(String typeCtblItemId){
    	this.typeCtblItemId = typeCtblItemId;
    }
    
    @Column(name="type_ctbl_item_name", length=64)
    public String getTypeCtblItemName(){
    	return this.typeCtblItemName;
    }
    public void setTypeCtblItemName(String typeCtblItemName){
    	this.typeCtblItemName = typeCtblItemName;
    }






}
