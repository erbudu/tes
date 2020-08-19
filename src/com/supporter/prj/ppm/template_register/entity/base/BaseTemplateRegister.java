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
public  class BaseTemplateRegister  implements Serializable {
	private static final long serialVersionUID = 1L;
	// Fields

	private String templateId;
	private String templateNo;
	private String templateName;
	private int displayOrder;
	private Date effectDate;
	private int version;
	private String isActive;
	private String remark;
	private String styleNo;
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
	public BaseTemplateRegister() {
	}
	/**
	 *方法: 设置功能模块id.
	 *@param: java.lang.String  功能模块id
	 */
	public BaseTemplateRegister(String templateId) {
		this.templateId = templateId;
	}

	/** full constructor */
	public BaseTemplateRegister(String templateId, String templateNo,
			String templateName, int displayOrder, Date effectDate,
			int version, String remark, String createdBy,
			String createdById, Date createdDate, String modifiedBy,
			String modifiedById, Date modifiedDate, String deptName,
			String deptId) {
		this.templateId = templateId;
		this.templateNo = templateNo;
		this.templateName = templateName;
		this.displayOrder = displayOrder;
		this.effectDate = effectDate;
		this.version = version;
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
	@Column(name = "TEMPLATE_ID", unique = true, nullable = false, length = 32)
	public String getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	@Column(name = "TEMPLATE_NO", length = 64)
	public String getTemplateNo() {
		return this.templateNo;
	}

	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}

	@Column(name = "TEMPLATE_NAME", length = 256)
	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Column(name = "DISPLAY_ORDER", precision = 22, scale = 0)
	public int getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFFECT_DATE", length = 11)
	public Date getEffectDate() {
		return this.effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	@Column(name = "VERSION", precision = 2, scale = 0)
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
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
	
	@Column(name = "STYLE_NO", length = 32)
	public String getStyleNo() {
		return styleNo;
	}
	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}
	
	@Column(name = "IS_ACTIVE", length = 32)
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
}
