package com.supporter.prj.cneec.public_doc.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**   
 * @Title: Entity
 * @Description: OA_PUBLIC_DOC,字段与数据库字段一一对应.
 * @author Administrator
 * @date 2018-06-04 16:05:03
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BasePublicDoc  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *DOC_ID.
	 */
	@Id
	@GeneratedValue(generator = "uuid")
  	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "DOC_ID", nullable = false, length = 32)
	private java.lang.String docId;
	/**
	 *DOC_NAME.
	 */
	@Column(name = "DOC_NAME", nullable = true, length = 128)
	private java.lang.String docName;
	/**
	 *PARENT_DOC_ID.
	 */
	@Column(name = "PARENT_DOC_ID", nullable = true, length = 32)
	private java.lang.String parentDocId;
	/**
	 *IS_FILE.
	 */
	@Column(name = "IS_FILE", nullable = true, length = 1)
	@Type(type = "true_false")
	private boolean file;
	/**
	 *CATEGORY_ID.
	 */
	@Column(name = "CATEGORY_ID", nullable = true, length = 32)
	private java.lang.String categoryId;
	/**
	 *CATEGORY_NAME.
	 */
	@Column(name = "CATEGORY_NAME", nullable = true, length = 64)
	private java.lang.String categoryName;
	/**
	 *DISPLAY_ORDER.
	 */
	@Column(name = "DISPLAY_ORDER", nullable = true, precision = 10)
	private int displayOrder;
	/**
	 *UPLOAD_FILE_ID.
	 */
	@Column(name = "UPLOAD_FILE_ID", nullable = true, length = 32)
	private java.lang.String uploadFileId;
	/**
	 *CREATED_BY_ID.
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	private java.lang.String createdById;
	/**
	 *CREATED_BY_NAME.
	 */
	@Column(name = "CREATED_BY_NAME", nullable = true, length = 64)
	private java.lang.String createdByName;
	/**
	 *CREATED_DATE.
	 */
	@Column(name = "CREATED_DATE", nullable = true)
	private java.util.Date createdDate;
	/**
	 *MODIFIED_BY_ID.
	 */
	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	private java.lang.String modifiedById;
	/**
	 *MODIFIED_BY_NAME.
	 */
	@Column(name = "MODIFIED_BY_NAME", nullable = true, length = 64)
	private java.lang.String modifiedByName;
	/**
	 *MODIFIED_DATE.
	 */
	@Column(name = "MODIFIED_DATE", nullable = true)
	private java.util.Date modifiedDate;
	/**
	 *TYPE.
	 */
	@Column(name = "TYPE", nullable = true, length = 8)
	private java.lang.String type;
	/**
	 *URL.
	 */
	@Column(name = "URL", nullable = true, length = 1024)
	private java.lang.String url;

	/**
	 *方法: 取得DOC_ID.
	 *@return java.lang.String  DOC_ID
	 */
	public java.lang.String getDocId() {
		return this.docId;
	}

	/**
	 *方法: 设置DOC_ID.
	 *@param docId  DOC_ID
	 */
	public void setDocId(java.lang.String docId) {
		this.docId = docId;
	}
	/**
	 *方法: 取得DOC_NAME.
	 *@return java.lang.String  DOC_NAME
	 */
	public java.lang.String getDocName() {
		return this.docName;
	}

	/**
	 *方法: 设置DOC_NAME.
	 *@param docName  DOC_NAME
	 */
	public void setDocName(java.lang.String docName) {
		this.docName = docName;
	}
	/**
	 *方法: 取得PARENT_DOC_ID.
	 *@return java.lang.String  PARENT_DOC_ID
	 */
	public java.lang.String getParentDocId() {
		return this.parentDocId;
	}

	/**
	 *方法: 设置PARENT_DOC_ID.
	 *@param parentDocId  PARENT_DOC_ID
	 */
	public void setParentDocId(java.lang.String parentDocId) {
		this.parentDocId = parentDocId;
	}
	/**
	 *方法: 取得IS_FILE.
	 *@return java.lang.String  IS_FILE
	 */
	public boolean isFile() {
		return this.file;
	}

	/**
	 *方法: 设置IS_FILE.
	 *@param isFile  IS_FILE
	 */
	public void setFile(boolean isFile) {
		this.file = isFile;
	}
	/**
	 *方法: 取得CATEGORY_ID.
	 *@return java.lang.String  CATEGORY_ID
	 */
	public java.lang.String getCategoryId() {
		return this.categoryId;
	}

	/**
	 *方法: 设置CATEGORY_ID.
	 *@param categoryId CATEGORY_ID
	 */
	public void setCategoryId(java.lang.String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 *方法: 取得CATEGORY_NAME.
	 *@return java.lang.String  CATEGORY_NAME
	 */
	public java.lang.String getCategoryName() {
		return this.categoryName;
	}

	/**
	 *方法: 设置CATEGORY_NAME.
	 *@param categoryName  CATEGORY_NAME
	 */
	public void setCategoryName(java.lang.String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 *方法: 取得DISPLAY_ORDER.
	 *@return int  DISPLAY_ORDER
	 */
	public int getDisplayOrder() {
		return this.displayOrder;
	}

	/**
	 *方法: 设置DISPLAY_ORDER.
	 *@param  displayOrder  DISPLAY_ORDER
	 */
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	/**
	 *方法: 取得UPLOAD_FILE_ID.
	 *@return java.lang.String  UPLOAD_FILE_ID
	 */
	public java.lang.String getUploadFileId() {
		return this.uploadFileId;
	}

	/**
	 *方法: 设置UPLOAD_FILE_ID.
	 *@param uploadFileId  UPLOAD_FILE_ID
	 */
	public void setUploadFileId(java.lang.String uploadFileId) {
		this.uploadFileId = uploadFileId;
	}
	/**
	 *方法: 取得CREATED_BY_ID.
	 *@return java.lang.String  CREATED_BY_ID
	 */
	public java.lang.String getCreatedById() {
		return this.createdById;
	}

	/**
	 *方法: 设置CREATED_BY_ID.
	 *@param createdById  CREATED_BY_ID
	 */
	public void setCreatedById(java.lang.String createdById) {
		this.createdById = createdById;
	}
	/**
	 *方法: 取得CREATED_BY_NAME.
	 *@return java.lang.String  CREATED_BY_NAME
	 */
	public java.lang.String getCreatedByName() {
		return this.createdByName;
	}

	/**
	 *方法: 设置CREATED_BY_NAME.
	 *@param createdByName CREATED_BY_NAME
	 */
	public void setCreatedByName(java.lang.String createdByName) {
		this.createdByName = createdByName;
	}
	/**
	 *方法: 取得CREATED_DATE.
	 *@return java.util.Date  CREATED_DATE
	 */
	public java.util.Date getCreatedDate() {
		return this.createdDate;
	}

	/**
	 *方法: 设置CREATED_DATE.
	 *@param createdDate  CREATED_DATE
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 *方法: 取得MODIFIED_BY_ID.
	 *@return java.lang.String  MODIFIED_BY_ID
	 */
	public java.lang.String getModifiedById() {
		return this.modifiedById;
	}

	/**
	 *方法: 设置MODIFIED_BY_ID.
	 *@param modifiedById  MODIFIED_BY_ID
	 */
	public void setModifiedById(java.lang.String modifiedById) {
		this.modifiedById = modifiedById;
	}
	/**
	 *方法: 取得MODIFIED_BY_NAME.
	 *@return java.lang.String  MODIFIED_BY_NAME
	 */
	public java.lang.String getModifiedByName() {
		return this.modifiedByName;
	}

	/**
	 *方法: 设置MODIFIED_BY_NAME.
	 *@param modifiedByName  MODIFIED_BY_NAME
	 */
	public void setModifiedByName(java.lang.String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}
	/**
	 *方法: 取得MODIFIED_DATE.
	 *@return java.util.Date  MODIFIED_DATE
	 */
	public java.util.Date getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 *方法: 设置MODIFIED_DATE.
	 *@param modifiedDate  MODIFIED_DATE
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	/**
	 *方法: 取得TYPE.
	 *@return java.lang.String  TYPE
	 */
	public java.lang.String getType() {
		return type;
	}

	/**
	 *方法: 设置TYPE.
	 *@param type  TYPE
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}

	/**
	 *方法: 取得URL.
	 *@return java.lang.String  URL
	 */
	public java.lang.String getUrl() {
		return url;
	}

	/**
	 *方法: 设置URL.
	 *@param url  URL
	 */
	public void setUrl(java.lang.String url) {
		this.url = url;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BasePublicDoc() {
	
	}
	
	/**
	 * 构造函数.
	 * @param docId
	 */
	public BasePublicDoc(String docId) {
		this.docId = docId;
	} 
}
