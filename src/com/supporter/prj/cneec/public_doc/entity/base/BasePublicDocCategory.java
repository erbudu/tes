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
 * @Description: OA_PUBLIC_DOC_CATEGORY,字段与数据库字段一一对应.
 * @author Administrator
 * @date 2018-06-04 16:05:05
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BasePublicDocCategory  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *CATEGORY_ID.
	 */
	@Id
	@GeneratedValue(generator = "uuid")
  	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "CATEGORY_ID", nullable = false, length = 32)
	private java.lang.String categoryId;
	/**
	 *CATEGORY_NAME.
	 */
	@Column(name = "CATEGORY_NAME", nullable = true, length = 64)
	private java.lang.String categoryName;
	
	@Column(name = "PARENT_CATEGORY_ID", nullable = true, length = 32)
	private java.lang.String parentCategoryId;
	
	/**
	 *INNER_NAME.
	 */
	@Column(name = "INNER_NAME", nullable = true, length = 32)
	private java.lang.String innerName;
	/**
	 *DISPLAY_ORDER.
	 */
	@Column(name = "DISPLAY_ORDER", nullable = true, precision = 10)
	private int displayOrder;
	/**
	 *IS_ACTIVED.
	 */
	@Column(name = "IS_ACTIVED", nullable = true, length = 1)
	@Type(type = "true_false")
	private boolean actived;
	/**
	 *IS_IN_HOME.
	 */
	@Column(name = "IS_IN_HOME", nullable = true, length = 1)
	@Type(type = "true_false")
	private boolean inHome;
	/**
	 *IMG_URL.
	 */
	@Column(name = "IMG_URL", nullable = true, length = 128)
	private java.lang.String imgUrl;
	/**
	 *OWNER.
	 */
	@Column(name = "OWNER_IDS", nullable = true, length = 512)
	private java.lang.String ownerIds;
	
	@Column(name = "OWNER_NAMES", nullable = true, length = 128)
	private java.lang.String ownerNames;
	
	@Column(name = "expanded", nullable = true, length = 1)
	@Type(type = "true_false")
	private boolean expanded = false;
	
	/**
	 *方法: 取得CATEGORY_ID.
	 *@return java.lang.String  CATEGORY_ID
	 */
	public java.lang.String getCategoryId() {
		return this.categoryId;
	}

	/**
	 *方法: 设置CATEGORY_ID.
	 *@param categoryId  CATEGORY_ID
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
	
	public java.lang.String getParentCategoryId() {
		return this.parentCategoryId;
	}
	public void setParentCategoryId(java.lang.String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}
	
	/**
	 *方法: 取得INNER_NAME.
	 *@return java.lang.String  INNER_NAME
	 */
	public java.lang.String getInnerName() {
		return this.innerName;
	}

	/**
	 *方法: 设置INNER_NAME.
	 *@param innerName  INNER_NAME
	 */
	public void setInnerName(java.lang.String innerName) {
		this.innerName = innerName;
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
	 *方法: 取得IS_ACTIVED.
	 *@return java.lang.String  IS_ACTIVED
	 */
	public boolean isActived() {
		return this.actived;
	}

	/**
	 *方法: 设置IS_ACTIVED.
	 *@param actived  IS_ACTIVED
	 */
	public void setActived(boolean actived) {
		this.actived = actived;
	}
	/**
	 *方法: 取得IS_IN_HOME.
	 *@return java.lang.String  IS_IN_HOME
	 */
	public boolean isInHome() {
		return this.inHome;
	}

	/**
	 *方法: 设置IS_IN_HOME.
	 *@param inHome  IS_IN_HOME
	 */
	public void setInHome(boolean inHome) {
		this.inHome = inHome;
	}
	/**
	 *方法: 取得IMG_URL.
	 *@return java.lang.String  IMG_URL
	 */
	public java.lang.String getImgUrl() {
		return this.imgUrl;
	}

	/**
	 *方法: 设置IMG_URL.
	 *@param imgUrl IMG_URL
	 */
	public void setImgUrl(java.lang.String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 *方法: 取得OWNER.
	 *@return java.lang.String  OWNER
	 */
	public java.lang.String getOwnerIds() {
		return this.ownerIds;
	}

	/**
	 *方法: 设置OWNER.
	 *@param ownerIds  OWNER
	 */
	public void setOwnerIds(java.lang.String ownerIds) {
		this.ownerIds = ownerIds;
	}
	
	public java.lang.String getOwnerNames() {
		return this.ownerNames;
	}
	public void setOwnerNames(java.lang.String ownerNames) {
		this.ownerNames = ownerNames;
	}
	
	public boolean getExpanded() {
		return this.expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BasePublicDocCategory() {
	
	}
	
	/**
	 * 构造函数.
	 * @param categoryId
	 */
	public BasePublicDocCategory(String categoryId) {
		this.categoryId = categoryId;
	} 
}
