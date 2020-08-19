package com.supporter.prj.dept_resource.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseDeptResourceType  implements java.io.Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String typeCode;
     private String typeName;
     private String tableName;
     private String idField;
     private String deptIdField;
     private String creatorIdField;
     private String superUserGroup;
     private Long displayOrder;
     private String titleField;
     private String resourceTypeCtbl;


    // Constructors

    /** default constructor */
    public BaseDeptResourceType() {
    }

	/** minimal constructor */
    public BaseDeptResourceType(String typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }
    
    /** full constructor */
    public BaseDeptResourceType(String typeCode, String typeName, String tableName, String idField, String deptIdField, String creatorIdField, String superUserGroup, Long displayOrder, String titleField) {
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.tableName = tableName;
        this.idField = idField;
        this.deptIdField = deptIdField;
        this.creatorIdField = creatorIdField;
        this.superUserGroup = superUserGroup;
        this.displayOrder = displayOrder;
        this.titleField = titleField;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="TYPE_CODE", unique=true, nullable=false, length=32)

    public String getTypeCode() {
        return this.typeCode;
    }
    
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    
    @Column(name="TYPE_NAME", nullable=false, length=64)

    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    @Column(name="TABLE_NAME", length=64)

    public String getTableName() {
        return this.tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    @Column(name="ID_FIELD", length=64)

    public String getIdField() {
        return this.idField;
    }
    
    public void setIdField(String idField) {
        this.idField = idField;
    }
    
    @Column(name="DEPT_ID_FIELD", length=64)

    public String getDeptIdField() {
        return this.deptIdField;
    }
    
    public void setDeptIdField(String deptIdField) {
        this.deptIdField = deptIdField;
    }
    
    @Column(name="CREATOR_ID_FIELD", length=64)

    public String getCreatorIdField() {
        return this.creatorIdField;
    }
    
    public void setCreatorIdField(String creatorIdField) {
        this.creatorIdField = creatorIdField;
    }
    
    @Column(name="SUPER_USER_GROUP", length=32)

    public String getSuperUserGroup() {
        return this.superUserGroup;
    }
    
    public void setSuperUserGroup(String superUserGroup) {
        this.superUserGroup = superUserGroup;
    }
    
    @Column(name="DISPLAY_ORDER", precision=22, scale=0)

    public Long getDisplayOrder() {
        return this.displayOrder;
    }
    
    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }
    
    @Column(name="TITLE_FIELD", length=32)

    public String getTitleField() {
        return this.titleField;
    }
    
    public void setTitleField(String titleField) {
        this.titleField = titleField;
    }
    
    @Column(name = "resource_type_ctbl", length = 32)
    public String getResourceTypeCtbl(){
    	return this.resourceTypeCtbl;
    }
    public void setResourceTypeCtbl(String resourceTypeCtbl){
    	this.resourceTypeCtbl = resourceTypeCtbl;
    }







}