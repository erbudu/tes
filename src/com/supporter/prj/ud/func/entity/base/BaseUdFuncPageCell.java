package com.supporter.prj.ud.func.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * UdFuncPageCell entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseUdFuncPageCell implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private String cellId;
	private String pageId;
	private String listId;
	private Long rowNo;
	private Long colNo;
	private String labelName;
	private String inputMode;
	private String selectValues;
	private String defaultValueSetting;

	// Constructors

	/** default constructor */
	public BaseUdFuncPageCell() {
	}

	/** minimal constructor */
	public BaseUdFuncPageCell(String cellId) {
		this.cellId = cellId;
	}

	/** full constructor */
	public BaseUdFuncPageCell(String cellId, String pageId, String listId,
			Long rowNo, Long colNo, String labelName, String inputMode,
			String selectValues, String defaultValueSetting) {
		this.cellId = cellId;
		this.pageId = pageId;
		this.listId = listId;
		this.rowNo = rowNo;
		this.colNo = colNo;
		this.labelName = labelName;
		this.inputMode = inputMode;
		this.selectValues = selectValues;
		this.defaultValueSetting = defaultValueSetting;
	}

	// Property accessors
	@Id
	@Column(name = "CELL_ID", unique = true, nullable = false, length = 32)
	public String getCellId() {
		return this.cellId;
	}

	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	@Column(name = "PAGE_ID", length = 32)
	public String getPageId() {
		return this.pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	@Column(name = "LIST_ID", length = 32)
	public String getListId() {
		return this.listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	@Column(name = "ROW_NO", precision = 22, scale = 0)
	public Long getRowNo() {
		return this.rowNo;
	}

	public void setRowNo(Long rowNo) {
		this.rowNo = rowNo;
	}

	@Column(name = "COL_NO", precision = 22, scale = 0)
	public Long getColNo() {
		return this.colNo;
	}

	public void setColNo(Long colNo) {
		this.colNo = colNo;
	}

	@Column(name = "LABEL_NAME", length = 64)
	public String getLabelName() {
		return this.labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	@Column(name = "INPUT_MODE", length = 64)
	public String getInputMode() {
		return this.inputMode;
	}

	public void setInputMode(String inputMode) {
		this.inputMode = inputMode;
	}

	@Column(name = "SELECT_VALUES", length = 64)
	public String getSelectValues() {
		return this.selectValues;
	}

	public void setSelectValues(String selectValues) {
		this.selectValues = selectValues;
	}

	@Column(name = "DEFAULT_VALUE_SETTING", length = 64)
	public String getDefaultValueSetting() {
		return this.defaultValueSetting;
	}

	public void setDefaultValueSetting(String defaultValueSetting) {
		this.defaultValueSetting = defaultValueSetting;
	}

}