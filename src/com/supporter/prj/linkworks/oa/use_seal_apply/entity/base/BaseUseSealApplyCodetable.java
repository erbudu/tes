package com.supporter.prj.linkworks.oa.use_seal_apply.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseUseSealApplyCodetable implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

     private String tableId;
     private String tableName;
     private String tableDesc;
     private String tableType;


    // Constructors

    /** default constructor */
    public BaseUseSealApplyCodetable() {
    }

	/** minimal constructor */
    public BaseUseSealApplyCodetable(String tableId) {
        this.tableId = tableId;
    }
    
    /** full constructor */
    public BaseUseSealApplyCodetable(String tableId, String tableName, String tableDesc, String tableType) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.tableDesc = tableDesc;
        this.tableType = tableType;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="TABLE_ID", unique=true, nullable=false, length=32)

    public String getTableId() {
        return this.tableId;
    }
    
    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
    
    @Column(name="TABLE_NAME", length=64)

    public String getTableName() {
        return this.tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    @Column(name="TABLE_DESC", length=218)

    public String getTableDesc() {
        return this.tableDesc;
    }
    
    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }
    
    @Column(name="TABLE_TYPE", length=64)

    public String getTableType() {
        return this.tableType;
    }
    
    public void setTableType(String tableType) {
        this.tableType = tableType;
    }
   

}
