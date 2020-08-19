package com.supporter.prj.linkworks.oa.bulletin.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;



/**
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author linxiaosong
 * @version V1.0   
 */

@MappedSuperclass
public class BaseBulletin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "BULLETIN_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String bulletinId;
	
	//公告标题
	@Column(name="BULLETIN_TITLE" ,length=64, nullable = true)
	private String bulletinTitle;
	
	//是否发布（0未发布，1已发布）
    @Column(name = "PUBLISH_STATUS", precision = 2, scale = 0, nullable = true)
	private Integer publishStatus = 0;
    
    //是否置顶
    @Column(name="ALWAYS_ON_TOP" ,length=1,nullable=true)
	private String alwaysOnTop = "0";
    
    
    //
	@Column(name="ORG_ID" ,length=32, nullable = true)
	private String orgId;
	
	//创建时间
	@Column(name="CREATED_DATE" ,length=27, nullable = true)
	private String createdDate;
	
	//创建人
	@Column(name="CREATED_BY" ,length=64, nullable = true)
	private String createdBy;
	
	//创建人Id
	@Column(name="CREATED_BY_ID" ,length=32, nullable = true)
	private String createdById;
	
	//修改时间
	@Column(name="MODIFIED_DATE" ,length=27, nullable = true)
	private String modifiedDate;
	
	//修改人
	@Column(name="MODIFIED_BY" ,length=64, nullable = true)
	private String modifiedBy;
	
	//修改人Id
	@Column(name="MODIFIED_BY_ID" ,length=32, nullable = true)
	private String modifiedById;
	
	//
	@Column(name="CREATOR_NAME" ,length=64, nullable = true)
	private String creatorName;
	
	//发布时间
	@Column(name="MESSAGE_DATE" ,length=27, nullable = true)
	private String messageDate;
	
	//公告类型
	@Column(name="BULLETIN_TYPE" ,length=32, nullable = true)
	private String bulletinType;
	
	//
	@Column(name="MODIFIER_NAME" ,length=32, nullable = true)
	private String modifierName;
	
	//相关文件名称
	@Column(name="FILE_NAME" ,length=1024, nullable = true)
	private String fileName;
	
	//发布人id
	@Column(name="PUBLISHER_ID" ,length=32, nullable = true)
	private String publisherId;
	
	//发布人
	@Column(name="PUBLISHER_NAME" ,length=64, nullable = true)
	private String publisherName;
	
	//部门名称
	@Column(name="DEPT_NAME" ,length=64, nullable = true)
	private String deptName;
	
	//部门Id
	@Column(name="DEPT_ID" ,length=32, nullable = true)
	private String deptId;
	
	//发布到的部门id
	@Column(name="DEPT_RESOURCE_ID" ,length=32, nullable = true)
	private String deptResourceId;
	
	//发布到的部门名
	@Column(name="DEPT_RESOURCE_Name" ,length=255, nullable = true)
	private String deptResourceName;
	
	//相关外键值（如发布的授权书id）
	@Column(name="RELATED_ID_VAL" ,length=32, nullable = true)
	private String relatedIdVal;
	
	//相关数据表名称（授权书添加）
	@Column(name="RELATED_TABLE" ,length=64, nullable = true)
	private String relatedTable;


	
	public BaseBulletin() {
		super();
	}

	
	
	
	public BaseBulletin(String bulletinId, String bulletinTitle,
			Integer publishStatus, String alwaysOnTop, String orgId,
			String createdDate, String createdBy, String createdById,
			String modifiedDate, String modifiedBy, String modifiedById,
			String creatorName, String messageDate, String bulletinType,
			String modifierName, String fileName, String publisherId,
			String publisherName, String deptName, String deptId,String deptResourceName,
			String deptResourceId, String relatedIdVal, String relatedTable) {
		super();
		this.bulletinId = bulletinId;
		this.bulletinTitle = bulletinTitle;
		this.publishStatus = publishStatus;
		this.alwaysOnTop = alwaysOnTop;
		this.orgId = orgId;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.creatorName = creatorName;
		this.messageDate = messageDate;
		this.bulletinType = bulletinType;
		this.modifierName = modifierName;
		this.fileName = fileName;
		this.publisherId = publisherId;
		this.publisherName = publisherName;
		this.deptName = deptName;
		this.deptId = deptId;
		this.deptResourceId = deptResourceId;
		this.relatedIdVal = relatedIdVal;
		this.relatedTable = relatedTable;
		this.deptResourceName = deptResourceName;
	}




	public String getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(String bulletinId) {
		this.bulletinId = bulletinId;
	}

	public String getBulletinTitle() {
		return bulletinTitle;
	}

	public void setBulletinTitle(String bulletinTitle) {
		this.bulletinTitle = bulletinTitle;
	}

	public Integer getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}


	public String getAlwaysOnTop() {
		return alwaysOnTop;
	}




	public void setAlwaysOnTop(String alwaysOnTop) {
		this.alwaysOnTop = alwaysOnTop;
	}


	

	public String getDeptResourceName() {
		return deptResourceName;
	}




	public void setDeptResourceName(String deptResourceName) {
		this.deptResourceName = deptResourceName;
	}




	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}

	public String getBulletinType() {
		return bulletinType;
	}

	public void setBulletinType(String bulletinType) {
		this.bulletinType = bulletinType;
	}

	public String getModifierName() {
		return modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptResourceId() {
		return deptResourceId;
	}

	public void setDeptResourceId(String deptResourceId) {
		this.deptResourceId = deptResourceId;
	}

	public String getRelatedIdVal() {
		return relatedIdVal;
	}

	public void setRelatedIdVal(String relatedIdVal) {
		this.relatedIdVal = relatedIdVal;
	}

	public String getRelatedTable() {
		return relatedTable;
	}

	public void setRelatedTable(String relatedTable) {
		this.relatedTable = relatedTable;
	}
	
	
    
}
