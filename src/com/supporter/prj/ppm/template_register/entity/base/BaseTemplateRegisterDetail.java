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
public  class BaseTemplateRegisterDetail  implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	// Fields

	private String detailId;
	private String templateId;
	private String textDisplay;
	private String styleNo;//暂不用，样式放在主表
	private int directoryStructure;
	private String contentType;
	private String contentNo;
	private String inputType;
	private String listSelect;
	private String listSelectName;
	private String codeTableSelect;
	private String codeTableSelectName;
	private String automaticGeneration;
	
	private String dataReadIn;
	private String domainObjectAttrId;
	private String moduleId;
	private String moduleName;
	private String domainObjectId;
	private String domainObjectName;
	private String entityName;//实体名称
	private String entityId;//实体主键ID名称
	private String busiTypeField;
	private String busiType;//业务类型，用于判断读取一张表中某特定类型的数据
	private String selectType;//数据读入获取的数据，需要再通过码表转换，码表类型；
	
	private String isUpdate;
	private String automaticRecognition;
	private String isReviewKeyPoint;
	private String underLineType;
	private String isLineFeed;
	private String rankOccupation;
	private int displayOrder;
	private String parentId;
	private String remark;
	private String isRequired;
	private String paragraphNo;
	private String isActive;
	
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
	public BaseTemplateRegisterDetail() {
	}
	
	public BaseTemplateRegisterDetail(String detailId) {
		this.detailId = detailId;
	}

	/** full constructor */
	public BaseTemplateRegisterDetail(String templateId, String textDisplay,
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
		this.textDisplay = textDisplay;
		this.styleNo = styleNo;
		this.directoryStructure = directoryStructure;
		this.contentType = contentType;
		this.contentNo = contentNo;
		this.inputType = inputType;
		this.listSelect = listSelect;
		this.codeTableSelect = codeTableSelect;
		this.automaticGeneration = automaticGeneration;
		this.dataReadIn = dataReadIn;
		this.isUpdate = isUpdate;
		this.automaticRecognition = automaticRecognition;
		this.isReviewKeyPoint = isReviewKeyPoint;
		this.underLineType = underLineType;
		this.isLineFeed = isLineFeed;
		this.rankOccupation = rankOccupation;
		this.displayOrder = displayOrder;
		this.parentId = parentId;
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

	@Column(name = "TEXT_DISPLAY", length = 32)
	public String getTextDisplay() {
		return this.textDisplay;
	}

	public void setTextDisplay(String textDisplay) {
		this.textDisplay = textDisplay;
	}

	@Column(name = "STYLE_NO", length = 32)
	public String getStyleNo() {
		return this.styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}

	@Column(name = "DIRECTORY_STRUCTURE", length = 32)
	public int getDirectoryStructure() {
		return this.directoryStructure;
	}

	public void setDirectoryStructure(int directoryStructure) {
		this.directoryStructure = directoryStructure;
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

	@Column(name = "AUTOMATIC_GENERATION", length = 64)
	public String getAutomaticGeneration() {
		return this.automaticGeneration;
	}

	public void setAutomaticGeneration(String automaticGeneration) {
		this.automaticGeneration = automaticGeneration;
	}

	@Column(name = "DATA_READ_IN", length = 64)
	public String getDataReadIn() {
		return this.dataReadIn;
	}

	public void setDataReadIn(String dataReadIn) {
		this.dataReadIn = dataReadIn;
	}

	@Column(name = "IS_UPDATE", length = 1)
	public String getIsUpdate() {
		return this.isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	@Column(name = "AUTOMATIC_RECOGNITION", length = 64)
	public String getAutomaticRecognition() {
		return this.automaticRecognition;
	}

	public void setAutomaticRecognition(String automaticRecognition) {
		this.automaticRecognition = automaticRecognition;
	}

	@Column(name = "IS_REVIEW_KEY_POINT", length = 1)
	public String getIsReviewKeyPoint() {
		return this.isReviewKeyPoint;
	}

	public void setIsReviewKeyPoint(String isReviewKeyPoint) {
		this.isReviewKeyPoint = isReviewKeyPoint;
	}

	@Column(name = "UNDER_LINE_TYPE", length = 64)
	public String getUnderLineType() {
		return this.underLineType;
	}

	public void setUnderLineType(String underLineType) {
		this.underLineType = underLineType;
	}

	@Column(name = "IS_LINE_FEED", length = 1)
	public String getIsLineFeed() {
		return this.isLineFeed;
	}

	public void setIsLineFeed(String isLineFeed) {
		this.isLineFeed = isLineFeed;
	}

	@Column(name = "RANK_OCCUPATION", length = 64)
	public String getRankOccupation() {
		return this.rankOccupation;
	}

	public void setRankOccupation(String rankOccupation) {
		this.rankOccupation = rankOccupation;
	}

	@Column(name = "DISPLAY_ORDER", length = 18)
	public int getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Column(name = "PARENT_ID", length = 32)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	
	@Column(name = "IS_REQUIRED", length = 1)
	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	@Column(name = "MODULE_ID", length = 1)
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name = "DOMAIN_OBJECT_ID", length = 1)
	public String getDomainObjectId() {
		return domainObjectId;
	}

	public void setDomainObjectId(String domainObjectId) {
		this.domainObjectId = domainObjectId;
	}

	@Column(name = "ENTITY_NAME", length = 1)
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	@Column(name = "ENTITY_ID", length = 1)
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	@Column(name = "PARAGRAPH_NO", length = 1)
	public String getParagraphNo() {
		return paragraphNo;
	}

	public void setParagraphNo(String paragraphNo) {
		this.paragraphNo = paragraphNo;
	}

	@Column(name="IS_ACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "DOMAIN_OBJECT_ATTR_ID", length = 1)
	public String getDomainObjectAttrId() {
		return domainObjectAttrId;
	}

	public void setDomainObjectAttrId(String domainObjectAttrId) {
		this.domainObjectAttrId = domainObjectAttrId;
	}

	@Column(name = "MODULE_NAME", length = 1)
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Column(name = "DOMAIN_OBJECT_NAME", length = 1)
	public String getDomainObjectName() {
		return domainObjectName;
	}

	public void setDomainObjectName(String domainObjectName) {
		this.domainObjectName = domainObjectName;
	}

	@Column(name = "BUSI_TYPE", length = 1)
	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	@Column(name = "SELECT_TYPE", length = 1)
	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	@Column(name = "BUSI_TYPE_FIELD", length = 1)
	public String getBusiTypeField() {
		return busiTypeField;
	}

	public void setBusiTypeField(String busiTypeField) {
		this.busiTypeField = busiTypeField;
	}

	 
}
