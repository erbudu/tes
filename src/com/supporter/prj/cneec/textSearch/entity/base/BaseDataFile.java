package com.supporter.prj.cneec.textSearch.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;



/**
 * This is an object that contains data related to the fbox_file table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="fbox_file"
 */
@MappedSuperclass
public abstract class BaseDataFile  implements Serializable {



	// constructors
	public BaseDataFile () {
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDataFile (java.lang.String id) {
		this.id = id;
	}





	// primary key
	@Id 
    @Column(name="file_id", unique=true, nullable=false, length=32)
	private java.lang.String id;

	// fields
	@Column(name="file_name", nullable=false, length=128)
	private java.lang.String fileName;
	@Column(name="file_ext", nullable=false, length=16)
	private java.lang.String fileExt;
	@Column(name="original_name", nullable=false, length=128)
	private java.lang.String originalName;
	@Column(name="file_size", nullable=false, length=128)
	private long fileSize;
	@Column(name="folder_id", nullable=false, length=128)
	private java.lang.String folderId;
	@Column(name="folder_name", nullable=false, length=128)
	private java.lang.String folderName;
	@Column(name="created_by_id", nullable=false, length=128)
	private java.lang.String createdById;
	@Column(name="created_by", nullable=false, length=128)
	private java.lang.String createdBy;
	@Column(name="created_date", nullable=false, length=128)
	private java.util.Date createdDate;
	@Column(name="modified_by_id", nullable=false, length=128)
	private java.lang.String modifiedById;
	@Column(name="modified_by", nullable=false, length=128)
	private java.lang.String modifiedBy;
	@Column(name="modified_date", nullable=false, length=23)
	private java.util.Date modifiedDate;
	@Column(name="filebox_id", nullable=false, length=128)
	private java.lang.String fileboxId;
	@Column(name="storage_path", nullable=false, length=32)
	private java.lang.String storagePath;
	@Column(name="is_locked")
	@org.hibernate.annotations.Type(type="true_false")
	private boolean locked;
	@Column(name="locked_by_id", nullable=false, length=32)
	private java.lang.String lockedById;
	@Column(name="locked_by", nullable=false, length=32)
	private java.lang.String lockedBy;
	@Column(name="override_parent_acl")
	@org.hibernate.annotations.Type(type="true_false")
	private boolean overrideParentAcl;


	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="uuid"
     *  column="file_id"
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
	 * Return the value associated with the column: file_name
	 */
	public java.lang.String getFileName () {
		return fileName;
	}

	/**
	 * Set the value related to the column: file_name
	 * @param fileName the file_name value
	 */
	public void setFileName (java.lang.String fileName) {
		this.fileName = fileName;
	}



	/**
	 * Return the value associated with the column: file_ext
	 */
	public java.lang.String getFileExt () {
		return fileExt;
	}

	/**
	 * Set the value related to the column: file_ext
	 * @param fileExt the file_ext value
	 */
	public void setFileExt (java.lang.String fileExt) {
		this.fileExt = fileExt;
	}



	/**
	 * Return the value associated with the column: original_name
	 */
	public java.lang.String getOriginalName () {
		return originalName;
	}

	/**
	 * Set the value related to the column: original_name
	 * @param originalName the original_name value
	 */
	public void setOriginalName (java.lang.String originalName) {
		this.originalName = originalName;
	}



	/**
	 * Return the value associated with the column: file_size
	 */
	public long getFileSize () {
		return fileSize;
	}

	/**
	 * Set the value related to the column: file_size
	 * @param fileSize the file_size value
	 */
	public void setFileSize (long fileSize) {
		this.fileSize = fileSize;
	}



	/**
	 * Return the value associated with the column: folder_id
	 */
	public java.lang.String getFolderId () {
		return folderId;
	}

	/**
	 * Set the value related to the column: folder_id
	 * @param folderId the folder_id value
	 */
	public void setFolderId (java.lang.String folderId) {
		this.folderId = folderId;
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
	 * Return the value associated with the column: storage_path
	 */
	public java.lang.String getStoragePath () {
		return storagePath;
	}

	/**
	 * Set the value related to the column: storage_path
	 * @param storagePath the storage_path value
	 */
	public void setStoragePath (java.lang.String storagePath) {
		this.storagePath = storagePath;
	}



	/**
	 * Return the value associated with the column: is_locked
	 */
	public boolean isLocked () {
		return locked;
	}

	/**
	 * Set the value related to the column: is_locked
	 * @param locked the is_locked value
	 */
	public void setLocked (boolean locked) {
		this.locked = locked;
	}



	/**
	 * Return the value associated with the column: locked_by_id
	 */
	public java.lang.String getLockedById () {
		return lockedById;
	}

	/**
	 * Set the value related to the column: locked_by_id
	 * @param lockedById the locked_by_id value
	 */
	public void setLockedById (java.lang.String lockedById) {
		this.lockedById = lockedById;
	}



	/**
	 * Return the value associated with the column: locked_by
	 */
	public java.lang.String getLockedBy () {
		return lockedBy;
	}

	/**
	 * Set the value related to the column: locked_by
	 * @param lockedBy the locked_by value
	 */
	public void setLockedBy (java.lang.String lockedBy) {
		this.lockedBy = lockedBy;
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