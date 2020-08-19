package com.supporter.prj.ppm.template_register.entity.base;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**   
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author liyinfeng
 * @date 2017-03-15 16:25:15
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseTemplateRegisterData  implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	// Fields

	private String detailId;
	private String templateId;
	private String paragraphNo;//段落
	private String dataId;
	private String keyName;
	private String valueText;
	private String valueClob;
	private int fieldType;
	private String remark;
	
	private String createdBy;
	private String createdById;
	private Date createdDate;
	private String modifiedBy;
	private String modifiedById;
	private Date modifiedDate;
	private String deptName;
	private String deptId;
	

	// Constructors

	/** default constructor */
	public BaseTemplateRegisterData() {
	}
	
	public BaseTemplateRegisterData(String detailId) {
		this.detailId = detailId;
	}

	/** full constructor */
	public BaseTemplateRegisterData(String templateId, String textDisplay,
			String styleNo, int directoryStructure, String contentType,
			String contentNo, String inputType, String listSelect,
			String codeTableSelect, String automaticGeneration,
			String dataReadIn, String isUpdate, String automaticRecognition,
			String isReviewKeyPoint, String underLineType, String isLineFeed,
			String rankOccupation, int displayOrder, String parentId,
			String remark, String createdBy, String createdById,
			Date createdDate, String modifiedBy, String modifiedById,
			Date modifiedDate, String deptName, String deptId) {
		this.templateId = templateId;
		this.remark = remark;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
		this.deptName = deptName;
		this.deptId = deptId;
	}

	// Property accessors
	@Id
	@Column(name = "DETAIL_ID", unique = true, nullable = false, length = 32)
	public String getDetailId() {
		return this.detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	@Column(name = "TEMPLATE_ID", length = 32)
	public String getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}


	@Column(name = "REMARK", length = 1024)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CREATED_BY", length = 64)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_BY_ID", length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", length = 11)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 64)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_BY_ID", length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIED_DATE", length = 11)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "DEPT_NAME", length = 200)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "DEPT_ID", length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DATA_ID", length = 1)
	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	@Column(name = "KEY_NAME", length = 1)
	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	@Column(name = "VALUE_TEXT", length = 1)
	public String getValueText() {
		return valueText;
	}

	public void setValueText(String valueText) {
		this.valueText = valueText;
	}

	@Column(name = "VALUE_CLOB", length = 1)
	public String getValueClob() {
		return valueClob;
	}

	public void setValueClob(String valueClob) {
		this.valueClob = valueClob;
	}

	@Column(name = "FIELD_TYPE", length = 1)
	public int getFieldType() {
		return fieldType;
	}

	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	@Column(name = "PARAGRAPH_NO", length = 1)
	public String getParagraphNo() {
		return paragraphNo;
	}

	public void setParagraphNo(String paragraphNo) {
		this.paragraphNo = paragraphNo;
	}
	 
}
