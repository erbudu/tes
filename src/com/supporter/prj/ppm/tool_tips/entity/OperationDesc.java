package com.supporter.prj.ppm.tool_tips.entity;


/**
 * @Title: ToolTips
 * @Description: 业务说明
 * @author: liyinfeng
 * @date: 2018-07-13
 * @version: V1.0
 */
public class OperationDesc implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String textDisplay;
	private String procDesc;
	private String moduleName;
	private String busiType;
	private String oneLevelId;

	// Constructors

	/** default constructor */
	public OperationDesc() {
		super();
	}

	public String getTextDisplay() {
		return textDisplay;
	}

	public void setTextDisplay(String textDisplay) {
		this.textDisplay = textDisplay;
	}

	public String getProcDesc() {
		return procDesc;
	}

	public void setProcDesc(String procDesc) {
		this.procDesc = procDesc;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getOneLevelId() {
		return oneLevelId;
	}

	public void setOneLevelId(String oneLevelId) {
		this.oneLevelId = oneLevelId;
	}


}
