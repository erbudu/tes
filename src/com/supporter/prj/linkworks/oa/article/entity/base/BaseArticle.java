package com.supporter.prj.linkworks.oa.article.entity.base;

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
public class BaseArticle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ARTICLE_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String articleId;
	
	//标题
	@Column(name="ARTICLE_TITLE" ,length=255, nullable = true)
	private String articleTitle;
	
	//发布状态
    @Column(name = "PUBLISH_STATUS", precision = 1, scale = 0, nullable = true)
	private Integer publishStatus;
    
    //发布到文章栏
	@Column(name="DEPT_RESOURCE_NAME" ,length=255, nullable = true)
	private String deptResourceName;
	
	//发布人Id
	@Column(name="PUBLISHER_ID" ,length=32, nullable = true)
	private String publisherId;
	
	//发布人姓名
	@Column(name="PUBLISHER_NAME" ,length=64, nullable = true)
	private String publisherName;
	
	//发布部门Id
	@Column(name="DEPT_ID" ,length=32, nullable = true)
	private String deptId;
	
	//发布部门
	@Column(name="DEPT_NAME" ,length=64, nullable = true)
	private String deptName;
	
	//发布到文章栏Id
	@Column(name="DEPT_RESOURCE_ID" ,length=32, nullable = true)
	private String deptResourceId;
	
	//发布时间
	@Column(name="PUBLISH_DATE" ,length=27, nullable = true)
	private String publishDate;
	
	//相关文件
	@Column(name="FILE_NAME" ,length=1024, nullable = true)
	private String fileName;
	
	//是否置顶
	@Column(name="ALWAYS_ON_TOP" ,length=255, nullable = true)
	private String alwaysOnTop;
	
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

	
	public BaseArticle() {
		super();
	}


	public BaseArticle(String articleId, String articleTitle,
			Integer publishStatus, String deptResourceName, String publisherId,
			String publisherName, String deptId, String deptName,
			String deptResourceId, String publishDate, String fileName,
			String alwaysOnTop, String createdDate, String createdBy,
			String createdById, String modifiedDate, String modifiedBy,
			String modifiedById) {
		super();
		this.articleId = articleId;
		this.articleTitle = articleTitle;
		this.publishStatus = publishStatus;
		this.deptResourceName = deptResourceName;
		this.publisherId = publisherId;
		this.publisherName = publisherName;
		this.deptId = deptId;
		this.deptName = deptName;
		this.deptResourceId = deptResourceId;
		this.publishDate = publishDate;
		this.fileName = fileName;
		this.alwaysOnTop = alwaysOnTop;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
	}


	public String getArticleId() {
		return articleId;
	}


	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}


	public String getArticleTitle() {
		return articleTitle;
	}


	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}


	public Integer getPublishStatus() {
		return publishStatus;
	}


	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}


	public String getDeptResourceName() {
		return deptResourceName;
	}


	public void setDeptResourceName(String deptResourceName) {
		this.deptResourceName = deptResourceName;
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


	public String getDeptId() {
		return deptId;
	}


	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}


	public String getDeptName() {
		return deptName;
	}


	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public String getDeptResourceId() {
		return deptResourceId;
	}


	public void setDeptResourceId(String deptResourceId) {
		this.deptResourceId = deptResourceId;
	}


	public String getPublishDate() {
		return publishDate;
	}


	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getAlwaysOnTop() {
		return alwaysOnTop;
	}


	public void setAlwaysOnTop(String alwaysOnTop) {
		this.alwaysOnTop = alwaysOnTop;
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
