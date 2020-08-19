package com.supporter.prj.linkworks.oa.roll_information.entity.base;

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
public class BaseRollInformation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//id
	@Id
	@Column(name = "ROLL_INFOR_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String rollInforId;
	
	//信息主题、标题
	@Column(name="INFOR_TITLE" ,length=512, nullable = true)
	private String inforTitle;
	
	//发布日期、发布开始时间
	@Column(name="PUBLISH_START_DATE" ,length=27, nullable = true)
	private String publishStartDate;
	
	//发布结束时间
	@Column(name="PUBLISH_END_DATE" ,length=27, nullable = true)
	private String publishEndDate;
	
	//状态
	@Column(name = "PUBLISH_STATUS", precision = 1, scale = 0, nullable = true)
	private Integer publishStatus;
	
	//发布类型
	@Column(name = "CREATED_TYPE", precision = 1, scale = 0, nullable = true)
	private Integer createType;
	
	
	//标题字体颜色
	@Column(name="INFOR_TITLE_COLOR" ,length=32, nullable = true)
	private String inforTitleColor;
	
	//阅读次数
	@Column(name = "READ_COUNT", precision = 5, scale = 0, nullable = true)
	private Integer readCount;
	
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
	
	
	
	

	public BaseRollInformation() {
		super();
	}
	
	

	public BaseRollInformation(String rollInforId, String inforTitle,
			String publishStartDate, String publishEndDate,
			Integer publishStatus, Integer createType, String inforTitleColor,
			Integer readCount, String createdDate, String createdBy,
			String createdById, String modifiedDate, String modifiedBy,
			String modifiedById) {
		super();
		this.rollInforId = rollInforId;
		this.inforTitle = inforTitle;
		this.publishStartDate = publishStartDate;
		this.publishEndDate = publishEndDate;
		this.publishStatus = publishStatus;
		this.createType = createType;
		this.inforTitleColor = inforTitleColor;
		this.readCount = readCount;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
	}



	public String getRollInforId() {
		return rollInforId;
	}

	public void setRollInforId(String rollInforId) {
		this.rollInforId = rollInforId;
	}

	public String getInforTitle() {
		return inforTitle;
	}

	public void setInforTitle(String inforTitle) {
		this.inforTitle = inforTitle;
	}

	public String getPublishStartDate() {
		return publishStartDate;
	}

	public void setPublishStartDate(String publishStartDate) {
		this.publishStartDate = publishStartDate;
	}

	public String getPublishEndDate() {
		return publishEndDate;
	}

	public void setPublishEndDate(String publishEndDate) {
		this.publishEndDate = publishEndDate;
	}

	public Integer getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}

	public Integer getCreateType() {
		return createType;
	}

	public void setCreateType(Integer createType) {
		this.createType = createType;
	}

	public String getInforTitleColor() {
		return inforTitleColor;
	}

	public void setInforTitleColor(String inforTitleColor) {
		this.inforTitleColor = inforTitleColor;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
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
	
	
}
