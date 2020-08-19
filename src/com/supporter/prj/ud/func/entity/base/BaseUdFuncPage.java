package com.supporter.prj.ud.func.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * UdFuncPage entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseUdFuncPage implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private String pageId;
	private String funcName;
	private Long rowCount;
	private Long colCount;
	private Long useProc;
	private String flag;
	private Long labelWidth;
	private Long fieldWidth;
	private String createdBy;
	private String deptId;
	private Long fullAccessGroup;

	// Constructors

	/** default constructor */
	public BaseUdFuncPage() {
	}

	/** minimal constructor */
	public BaseUdFuncPage(String pageId) {
		this.pageId = pageId;
	}

	/** full constructor */
	public BaseUdFuncPage(String pageId, String funcName, Long rowCount,
			Long colCount, Long useProc, String flag, Long labelWidth,
			Long fieldWidth, String createdBy, String deptId,
			Long fullAccessGroup) {
		this.pageId = pageId;
		this.funcName = funcName;
		this.rowCount = rowCount;
		this.colCount = colCount;
		this.useProc = useProc;
		this.flag = flag;
		this.labelWidth = labelWidth;
		this.fieldWidth = fieldWidth;
		this.createdBy = createdBy;
		this.deptId = deptId;
		this.fullAccessGroup = fullAccessGroup;
	}

	// Property accessors
	@Id
	@Column(name = "PAGE_ID", unique = true, nullable = false, length = 32)
	public String getPageId() {
		return this.pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	@Column(name = "FUNC_NAME", length = 64)
	public String getFuncName() {
		return this.funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	@Column(name = "ROW_COUNT", precision = 22, scale = 0)
	public Long getRowCount() {
		return this.rowCount;
	}

	public void setRowCount(Long rowCount) {
		this.rowCount = rowCount;
	}

	@Column(name = "COL_COUNT", precision = 22, scale = 0)
	public Long getColCount() {
		return this.colCount;
	}

	public void setColCount(Long colCount) {
		this.colCount = colCount;
	}

	@Column(name = "USE_PROC", precision = 22, scale = 0)
	public Long getUseProc() {
		return this.useProc;
	}

	public void setUseProc(Long useProc) {
		this.useProc = useProc;
	}

	@Column(name = "FLAG", length = 64)
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "LABEL_WIDTH", precision = 22, scale = 0)
	public Long getLabelWidth() {
		return this.labelWidth;
	}

	public void setLabelWidth(Long labelWidth) {
		this.labelWidth = labelWidth;
	}

	@Column(name = "FIELD_WIDTH", precision = 22, scale = 0)
	public Long getFieldWidth() {
		return this.fieldWidth;
	}

	public void setFieldWidth(Long fieldWidth) {
		this.fieldWidth = fieldWidth;
	}

	@Column(name = "CREATED_BY", length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "DEPT_ID", length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "FULL_ACCESS_GROUP", precision = 22, scale = 0)
	public Long getFullAccessGroup() {
		return this.fullAccessGroup;
	}

	public void setFullAccessGroup(Long fullAccessGroup) {
		this.fullAccessGroup = fullAccessGroup;
	}

}