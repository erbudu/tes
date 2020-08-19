package com.supporter.prj.ppm.template_register.entity.base;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;



/**   
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author liyinfeng
 * @date 2017-03-15 16:25:15
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseTemplateRegisterTable  implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	// Fields

	private String lineId;
	private String templateId;
	private String textDisplay;
	private String contentType;
	private String contentNo;
	private String inputType;
	private String listSelect;
	private String listSelectName;
	private String codeTableSelect;
	private String codeTableSelectName;
	
	private String titleText;
	private int numberOfColumns;
	private int displayOrder;
	private String detailId;
	
	private String tableNo;//表格编码

	// Constructors

	/** default constructor */
	public BaseTemplateRegisterTable() {
	}
	
	public BaseTemplateRegisterTable(String lineId) {
		this.lineId = lineId;
	}

	/** full constructor */
	public BaseTemplateRegisterTable(String templateId, String textDisplay,
			String contentType,	String contentNo, String inputType, String listSelect,
			String codeTableSelect, String rankOccupation, int displayOrder, String detailId
			) {
		this.templateId = templateId;
		this.textDisplay = textDisplay;
		this.contentType = contentType;
		this.contentNo = contentNo;
		this.inputType = inputType;
		this.listSelect = listSelect;
		this.codeTableSelect = codeTableSelect;
		this.displayOrder = displayOrder;
		this.detailId = detailId;
	}

	// Property accessors
	@Id
	@Column(name = "LINE_ID", unique = true, nullable = false, length = 32)
	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	@Column(name = "TEMPLATE_ID", length = 32)
	public String getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	@Column(name = "TEXT_DISPLAY", length = 32)
	public String getTextDisplay() {
		return this.textDisplay;
	}

	public void setTextDisplay(String textDisplay) {
		this.textDisplay = textDisplay;
	}

	@Column(name = "CONTENT_TYPE", length = 128)
	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Column(name = "CONTENT_NO", length = 128)
	public String getContentNo() {
		return this.contentNo;
	}

	public void setContentNo(String contentNo) {
		this.contentNo = contentNo;
	}

	@Column(name = "INPUT_TYPE", length = 128)
	public String getInputType() {
		return this.inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	@Column(name = "LIST_SELECT", length = 64)
	public String getListSelect() {
		return this.listSelect;
	}

	public void setListSelect(String listSelect) {
		this.listSelect = listSelect;
	}

	@Column(name = "CODE_TABLE_SELECT", length = 64)
	public String getCodeTableSelect() {
		return this.codeTableSelect;
	}

	public void setCodeTableSelect(String codeTableSelect) {
		this.codeTableSelect = codeTableSelect;
	}

	@Column(name = "DISPLAY_ORDER", length = 18)
	public int getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}


	@Column(name = "LIST_SELECT_NAME", length = 1)
	public String getListSelectName() {
		return listSelectName;
	}

	public void setListSelectName(String listSelectName) {
		this.listSelectName = listSelectName;
	}

	@Column(name = "CODE_TABLE_SELECT_NAME", length = 1)
	public String getCodeTableSelectName() {
		return codeTableSelectName;
	}

	public void setCodeTableSelectName(String codeTableSelectName) {
		this.codeTableSelectName = codeTableSelectName;
	}
	
	@Column(name = "TABLE_NO", length = 1)
	public String getTableNo() {
		return tableNo;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	@Column(name = "DETAIL_ID", length = 1)
	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	@Column(name = "TITLE_TEXT", length = 64)
	public String getTitleText() {
		return titleText;
	}

	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}

	@Column(name = "NUMBER_OF_COLUMNS", length = 18)
	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	 
}
