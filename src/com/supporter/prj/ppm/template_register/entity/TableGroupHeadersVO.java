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
public class TableGroupHeadersVO implements java.io.Serializable {
	// ~ Static Fields
	// ================================================================================================================
	private static final long serialVersionUID = 1L;
	
	
	// ~ Fields
	// ================================================================================================================
	private String startColumnName;
	private int numberOfColumns;
	private String titleText;
	// ~ Constructors
	// ================================================================================================================
	public TableGroupHeadersVO(){
		
	}
	

	// ~ Methods
	// ================================================================================================================
	


	public String getStartColumnName() {
		return startColumnName;
	}


	public void setStartColumnName(String startColumnName) {
		this.startColumnName = startColumnName;
	}


	public int getNumberOfColumns() {
		return numberOfColumns;
	}


	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}


	public String getTitleText() {
		return titleText;
	}


	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}
}
