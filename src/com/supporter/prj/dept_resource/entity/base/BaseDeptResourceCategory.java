package com.supporter.prj.dept_resource.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseDeptResourceCategory  implements java.io.Serializable {


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String categoryId;
     private String categoryName;
     private String deptId;
     private Long displayOrder;


    // Constructors

    /** default constructor */
    public BaseDeptResourceCategory() {
    }

	/** minimal constructor */
    public BaseDeptResourceCategory(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
    
    /** full constructor */
    public BaseDeptResourceCategory(String categoryId, String categoryName, String deptId, Long displayOrder) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.deptId = deptId;
        this.displayOrder = displayOrder;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="CATEGORY_ID", unique=true, nullable=false, length=18)

    public String getCategoryId() {
        return this.categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
    @Column(name="CATEGORY_NAME", nullable=false, length=64)

    public String getCategoryName() {
        return this.categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    @Column(name="DEPT_ID", length=20)

    public String getDeptId() {
        return this.deptId;
    }
    
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    
    @Column(name="DISPLAY_ORDER", precision=22, scale=0)

    public Long getDisplayOrder() {
        return this.displayOrder;
    }
    
    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }
   








}
