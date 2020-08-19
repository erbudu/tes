package com.supporter.prj.linkworks.oa.article.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class BaseVArticle  implements java.io.Serializable{


    // Fields

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String articleId;
     private String articleTitle;
     private String publisherId;
     private String publisherName;
     private String deptId;
     private String deptName;
     private String deptResourceId;
     private String publishDate;
     private String fileName;
     private Long publishStatus;
     private String alwaysOnTop;
     private String createdBy;
     private String createdById;
     private String createdDate;
     private String modifiedBy;
     private String modifiedById;
     private String modifiedDate;
     private String deptResourceName;
     private String authorizeeId;
     private String canRead;
     private String canWrite;
     private String canDelete;
     private String canNew;
     private String fullAccess;


    // Constructors

    /** default constructor */
    public BaseVArticle() {
    }

	/** minimal constructor */
    public BaseVArticle(String articleId) {
        this.articleId = articleId;
    }
    
    /** full constructor */
    public BaseVArticle(String articleId, String articleTitle, String publisherId, String publisherName, String deptId, String deptName, String deptResourceId, String publishDate, String fileName, Long publishStatus, String alwaysOnTop, String createdBy, String createdById, String createdDate, String modifiedBy, String modifiedById, String modifiedDate, String deptResourceName, String authorizeeId, String canRead, String canWrite, String canDelete, String canNew, String fullAccess) {
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptResourceId = deptResourceId;
        this.publishDate = publishDate;
        this.fileName = fileName;
        this.publishStatus = publishStatus;
        this.alwaysOnTop = alwaysOnTop;
        this.createdBy = createdBy;
        this.createdById = createdById;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedById = modifiedById;
        this.modifiedDate = modifiedDate;
        this.deptResourceName = deptResourceName;
        this.authorizeeId = authorizeeId;
        this.canRead = canRead;
        this.canWrite = canWrite;
        this.canDelete = canDelete;
        this.canNew = canNew;
        this.fullAccess = fullAccess;
    }

   
    // Property accessors
    @Id
    @Column(name="ARTICLE_ID", nullable=false, length=32)

    public String getArticleId() {
        return this.articleId;
    }
    
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    @Column(name="ARTICLE_TITLE")

    public String getArticleTitle() {
        return this.articleTitle;
    }
    
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    @Column(name="PUBLISHER_ID", length=32)

    public String getPublisherId() {
        return this.publisherId;
    }
    
    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    @Column(name="PUBLISHER_NAME", length=64)

    public String getPublisherName() {
        return this.publisherName;
    }
    
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @Column(name="DEPT_ID", length=32)

    public String getDeptId() {
        return this.deptId;
    }
    
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Column(name="DEPT_NAME", length=64)

    public String getDeptName() {
        return this.deptName;
    }
    
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Column(name="DEPT_RESOURCE_ID", length=32)

    public String getDeptResourceId() {
        return this.deptResourceId;
    }
    
    public void setDeptResourceId(String deptResourceId) {
        this.deptResourceId = deptResourceId;
    }

    @Column(name="PUBLISH_DATE", length=27)

    public String getPublishDate() {
        return this.publishDate;
    }
    
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    @Column(name="FILE_NAME", length=1024)

    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name="PUBLISH_STATUS", precision=22, scale=0)

    public Long getPublishStatus() {
        return this.publishStatus;
    }
    
    public void setPublishStatus(Long publishStatus) {
        this.publishStatus = publishStatus;
    }

    @Column(name="ALWAYS_ON_TOP", length=1)

    public String getAlwaysOnTop() {
        return this.alwaysOnTop;
    }
    
    public void setAlwaysOnTop(String alwaysOnTop) {
        this.alwaysOnTop = alwaysOnTop;
    }

    @Column(name="CREATED_BY", length=64)

    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name="CREATED_BY_ID", length=32)

    public String getCreatedById() {
        return this.createdById;
    }
    
    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    @Column(name="CREATED_DATE", length=27)

    public String getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name="MODIFIED_BY", length=64)

    public String getModifiedBy() {
        return this.modifiedBy;
    }
    
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Column(name="MODIFIED_BY_ID", length=32)

    public String getModifiedById() {
        return this.modifiedById;
    }
    
    public void setModifiedById(String modifiedById) {
        this.modifiedById = modifiedById;
    }

    @Column(name="MODIFIED_DATE", length=27)

    public String getModifiedDate() {
        return this.modifiedDate;
    }
    
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Column(name="DEPT_RESOURCE_NAME")

    public String getDeptResourceName() {
        return this.deptResourceName;
    }
    
    public void setDeptResourceName(String deptResourceName) {
        this.deptResourceName = deptResourceName;
    }

    @Column(name="AUTHORIZEE_ID", length=32)

    public String getAuthorizeeId() {
        return this.authorizeeId;
    }
    
    public void setAuthorizeeId(String authorizeeId) {
        this.authorizeeId = authorizeeId;
    }

    @Column(name="CAN_READ", length=1)

    public String getCanRead() {
        return this.canRead;
    }
    
    public void setCanRead(String canRead) {
        this.canRead = canRead;
    }

    @Column(name="CAN_WRITE", length=1)

    public String getCanWrite() {
        return this.canWrite;
    }
    
    public void setCanWrite(String canWrite) {
        this.canWrite = canWrite;
    }

    @Column(name="CAN_DELETE", length=1)

    public String getCanDelete() {
        return this.canDelete;
    }
    
    public void setCanDelete(String canDelete) {
        this.canDelete = canDelete;
    }

    @Column(name="CAN_NEW", length=1)

    public String getCanNew() {
        return this.canNew;
    }
    
    public void setCanNew(String canNew) {
        this.canNew = canNew;
    }

    @Column(name="FULL_ACCESS", length=1)

    public String getFullAccess() {
        return this.fullAccess;
    }
    
    public void setFullAccess(String fullAccess) {
        this.fullAccess = fullAccess;
    }
   
}
