package com.supporter.prj.ppm.template_register.entity;


// ~ File Information
// ====================================================================================================================

/**
 * 表格结构对应的JSON结构类
 * <pre>
 *  用于装载table数据结构
 * </pre>
 * 
 * @author liyinfeng
 * @createDate 2019-08-15
 */
public class TableVO implements java.io.Serializable {
	// ~ Static Fields
	// ================================================================================================================
	private static final long serialVersionUID = 1L;
	
	
	// ~ Fields
	// ================================================================================================================
	private String label;
	private String name;
	private String index;
	private boolean key = false;
	private boolean hidden = false;
	private boolean sortable = false;
	// ~ Constructors
	// ================================================================================================================
	public TableVO(){
		
	}
	

	public TableVO(    
	String label,
	String name,        
	boolean key,      
	boolean hidden 
	){
		this.label = label;
		this.name = name;   
		this.key = key;     
		this.hidden = hidden;
	}
	// ~ Methods
	// ================================================================================================================
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean getKey() {
		return key;
	}

	public void setKey(boolean key) {
		this.key = key;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}


	public boolean isSortable() {
		return sortable;
	}


	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}


	public String getIndex() {
		return index;
	}


	public void setIndex(String index) {
		this.index = index;
	}
}
