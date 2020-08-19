package com.supporter.prj.cneec.textSearch.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * This is an object that contains data related to the fbox_folder table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="fbox_folder"
 */
@MappedSuperclass
public abstract class BaseFolder  implements Serializable {



	// constructors
	public BaseFolder () {
	}

	/**
	 * Constructor for primary key
	 */
	public BaseFolder (java.lang.String id) {
		this.setId(id);
	}





	// primary key
	@Id 
    @Column(name="folder_id", unique=true, nullable=false, length=32)
	private java.lang.String id;

	// fields
	@Column(name="folder_name", length=128)
	private java.lang.String folderName;
	@Column(name="folder_desc", length=128)
	private java.lang.String folderDesc;
	@Column(name="parent_folder_id", length=128)
	private java.lang.String parentFolderId;
	@Column(name="filebox_id", length=128)
	private java.lang.String fileboxId;
	@Column(name="display_order", length=128)
	private int displayOrder;
	@Column(name="created_by_id", length=128)
	private java.lang.String createdById;
	@Column(name="created_by", length=128)
	private java.lang.String createdBy;
	@Column(name="created_date", length=128)
	private java.util.Date createdDate;
	@Column(name="modified_by_id", length=128)
	private java.lang.String modifiedById;
	@Column(name="modified_by", length=128)
	private java.lang.String modifiedBy;
	@Column(name="modified_date", length=128)
	private java.util.Date modifiedDate;
	@Column(name="override_parent_acl")
	@org.hibernate.annotations.Type(type="true_false")
	private boolean overrideParentAcl;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="uuid"
     *  column="folder_id"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
	}




	/**
	 * Return the value associated with the column: folder_name
	 */
	public java.lang.String getFolderName () {
		return folderName;
	}

	/**
	 * Set the value related to the column: folder_name
	 * @param folderName the folder_name value
	 */
	public void setFolderName (java.lang.String folderName) {
		this.folderName = folderName;
	}



	/**
	 * Return the value associated with the column: folder_desc
	 */
	public java.lang.String getFolderDesc () {
		return folderDesc;
	}

	/**
	 * Set the value related to the column: folder_desc
	 * @param folderDesc the folder_desc value
	 */
	public void setFolderDesc (java.lang.String folderDesc) {
		this.folderDesc = folderDesc;
	}



	/**
	 * Return the value associated with the column: parent_folder_id
	 */
	public java.lang.String getParentFolderId () {
		return parentFolderId;
	}

	/**
	 * Set the value related to the column: parent_folder_id
	 * @param parentFolderId the parent_folder_id value
	 */
	public void setParentFolderId (java.lang.String parentFolderId) {
		this.parentFolderId = parentFolderId;
	}



	/**
	 * Return the value associated with the column: filebox_id
	 */
	public java.lang.String getFileboxId () {
		return fileboxId;
	}

	/**
	 * Set the value related to the column: filebox_id
	 * @param fileboxId the filebox_id value
	 */
	public void setFileboxId (java.lang.String fileboxId) {
		this.fileboxId = fileboxId;
	}



	/**
	 * Return the value associated with the column: display_order
	 */
	public int getDisplayOrder () {
		return displayOrder;
	}

	/**
	 * Set the value related to the column: display_order
	 * @param displayOrder the display_order value
	 */
	public void setDisplayOrder (int displayOrder) {
		this.displayOrder = displayOrder;
	}



	/**
	 * Return the value associated with the column: created_by_id
	 */
	public java.lang.String getCreatedById () {
		return createdById;
	}

	/**
	 * Set the value related to the column: created_by_id
	 * @param createdById the created_by_id value
	 */
	public void setCreatedById (java.lang.String createdById) {
		this.createdById = createdById;
	}



	/**
	 * Return the value associated with the column: created_by
	 */
	public java.lang.String getCreatedBy () {
		return createdBy;
	}

	/**
	 * Set the value related to the column: created_by
	 * @param createdBy the created_by value
	 */
	public void setCreatedBy (java.lang.String createdBy) {
		this.createdBy = createdBy;
	}



	/**
	 * Return the value associated with the column: created_date
	 */
	public java.util.Date getCreatedDate () {
		return createdDate;
	}

	/**
	 * Set the value related to the column: created_date
	 * @param createdDate the created_date value
	 */
	public void setCreatedDate (java.util.Date createdDate) {
		this.createdDate = createdDate;
	}



	/**
	 * Return the value associated with the column: modified_by_id
	 */
	public java.lang.String getModifiedById () {
		return modifiedById;
	}

	/**
	 * Set the value related to the column: modified_by_id
	 * @param modifiedById the modified_by_id value
	 */
	public void setModifiedById (java.lang.String modifiedById) {
		this.modifiedById = modifiedById;
	}



	/**
	 * Return the value associated with the column: modified_by
	 */
	public java.lang.String getModifiedBy () {
		return modifiedBy;
	}

	/**
	 * Set the value related to the column: modified_by
	 * @param modifiedBy the modified_by value
	 */
	public void setModifiedBy (java.lang.String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}



	/**
	 * Return the value associated with the column: modified_date
	 */
	public java.util.Date getModifiedDate () {
		return modifiedDate;
	}

	/**
	 * Set the value related to the column: modified_date
	 * @param modifiedDate the modified_date value
	 */
	public void setModifiedDate (java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}



	/**
	 * Return the value associated with the column: override_parent_acl
	 */
	public boolean isOverrideParentAcl () {
		return overrideParentAcl;
	}

	/**
	 * Set the value related to the column: override_parent_acl
	 * @param overrideParentAcl the override_parent_acl value
	 */
	public void setOverrideParentAcl (boolean overrideParentAcl) {
		this.overrideParentAcl = overrideParentAcl;
	}



}