package com.supporter.prj.linkworks.oa.news_alerts.entity.base;

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
public class BaseNewsAlerts implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "NEWS_ALERTS_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String newsAlertsId;
	
	//新闻标题
	@Column(name="NEWS_TITLE" ,length=512, nullable = true)
	private String newsTitle;
	
	//（T,F）
	@Column(name="TOP_DISPLAY" ,length=1, nullable = true)
	private String topDisplay ;
    
    //发布开始时间
	@Column(name="PUBLISH_START_DATE" ,length=27, nullable = true)
	private String publishStartDate;
	
	//发布结束时间
	@Column(name="PUBLISH_END_DATE" ,length=27, nullable = true)
	private String publishEndDate;
	
	//图片宽度
    @Column(name = "IMAGE_WIDTH", precision = 22, scale = 0, nullable = true)
	private Integer imageWidth;
    
    //图片高度
    @Column(name = "IMAGE_HEIGHT", precision = 22, scale = 0, nullable = true)
	private Integer imageHeight;
    
    //弹框宽度
    @Column(name = "WINDOW_WIDTH", precision = 22, scale = 0, nullable = true)
	private Integer windowWidth;
    
    //弹框高度
    @Column(name = "WINDOW_HEIGHT", precision = 22, scale = 0, nullable = true)
	private Integer windowHeight;
    
    //发布状态
    @Column(name = "PUBLISH_STATUS", precision = 2, scale = 0, nullable = true)
	private Integer publishStatus = 0;
    
    //显示方式
    @Column(name = "DISPLAY_TYPE", precision = 2, scale = 0, nullable = true)
	private Integer displayType = 0;

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
    

	
	
	
	public BaseNewsAlerts(String newsAlertsId, String newsTitle,
			String topDisplay, String publishStartDate, String publishEndDate,
			Integer imageWidth, Integer imageHeight, Integer windowWidth,
			Integer windowHeight, Integer publishStatus, Integer displayType,
			String createdDate, String createdBy, String createdById,
			String modifiedDate, String modifiedBy, String modifiedById) {
		super();
		this.newsAlertsId = newsAlertsId;
		this.newsTitle = newsTitle;
		this.topDisplay = topDisplay;
		this.publishStartDate = publishStartDate;
		this.publishEndDate = publishEndDate;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.publishStatus = publishStatus;
		this.displayType = displayType;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
	}



	public BaseNewsAlerts() {
		super();
	}



	public String getNewsAlertsId() {
		return newsAlertsId;
	}

	public void setNewsAlertsId(String newsAlertsId) {
		this.newsAlertsId = newsAlertsId;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getTopDisplay() {
		return topDisplay;
	}

	public void setTopDisplay(String topDisplay) {
		this.topDisplay = topDisplay;
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

	public Integer getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public Integer getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
	}

	public Integer getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(Integer windowWidth) {
		this.windowWidth = windowWidth;
	}

	public Integer getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(Integer windowHeight) {
		this.windowHeight = windowHeight;
	}

	public Integer getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}

	public Integer getDisplayType() {
		return displayType;
	}

	public void setDisplayType(Integer displayType) {
		this.displayType = displayType;
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
