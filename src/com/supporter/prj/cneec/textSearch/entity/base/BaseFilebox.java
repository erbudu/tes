package com.supporter.prj.cneec.textSearch.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * This is an object that contains data related to the fbox_filebox table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="fbox_filebox"
 */
@MappedSuperclass
public abstract class BaseFilebox  implements Serializable {



	// constructors
	public BaseFilebox () {
	}

	/**
	 * Constructor for primary key
	 */
	public BaseFilebox (java.lang.String id) {
		this.setId(id);
	}





	// primary key
	@Id 
    @Column(name="filebox_id", unique=true, nullable=false, length=32)
	private java.lang.String id;

	// fields
	@Column(name="filebox_name", nullable=false, length=64)
	private java.lang.String fileboxName;
	@Column(name="filebox_desc", length=128)
	private java.lang.String fileboxDesc;
	@Column(name="filebox_category", length=32)
	private java.lang.String fileboxCategory;
	@Column(name="owner_id", length=32)
	private java.lang.String ownerId;
	@Column(name="owner_name", length=64)
	private java.lang.String ownerName;
	@Column(name="manager_id", length=32)
	private java.lang.String managerId;
	@Column(name="display_order", length=10)
	private int displayOrder;
	@Column(name="created_by_id", length=32)
	private java.lang.String createdById;
	@Column(name="created_by", length=64)
	private java.lang.String createdBy;
	@Column(name="created_date", nullable=false, length=23)
	private java.util.Date createdDate;
	@Column(name="modified_by_id", nullable=false, length=128)
	private java.lang.String modifiedById;
	@Column(name="modified_by", nullable=false, length=128)
	private java.lang.String modifiedBy;
	@Column(name="modified_date", nullable=false, length=128)
	private java.util.Date modifiedDate;
	@Column(name="class_ids", nullable=false, length=128)
	private java.lang.String classIds;
	@Column(name="class_names", nullable=false, length=128)
	private java.lang.String classNames;
	@Column(name="display_on_portal", nullable=false, length=128)
	private int displayOnPortal;
	@Column(name="entity_name", nullable=false, length=128)
	private java.lang.String entityName;
	@Column(name="entity_id", nullable=false, length=128)
	private java.lang.String entityId;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="uuid"
     *  column="filebox_id"
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
	 * Return the value associated with the column: filebox_name
	 */
	public java.lang.String getFileboxName () {
		return fileboxName;
	}

	/**
	 * Set the value related to the column: filebox_name
	 * @param fileboxName the filebox_name value
	 */
	public void setFileboxName (java.lang.String fileboxName) {
		this.fileboxName = fileboxName;
	}



	/**
	 * Return the value associated with the column: filebox_desc
	 */
	public java.lang.String getFileboxDesc () {
		return fileboxDesc;
	}

	/**
	 * Set the value related to the column: filebox_desc
	 * @param fileboxDesc the filebox_desc value
	 */
	public void setFileboxDesc (java.lang.String fileboxDesc) {
		this.fileboxDesc = fileboxDesc;
	}



	/**
	 * Return the value associated with the column: filebox_category
	 */
	public java.lang.String getFileboxCategory () {
		return fileboxCategory;
	}

	/**
	 * Set the value related to the column: filebox_category
	 * @param fileboxCategory the filebox_category value
	 */
	public void setFileboxCategory (java.lang.String fileboxCategory) {
		this.fileboxCategory = fileboxCategory;
	}



	/**
	 * Return the value associated with the column: owner_id
	 */
	public java.lang.String getOwnerId () {
		return ownerId;
	}

	/**
	 * Set the value related to the column: owner_id
	 * @param ownerId the owner_id value
	 */
	public void setOwnerId (java.lang.String ownerId) {
		this.ownerId = ownerId;
	}



	/**
	 * Return the value associated with the column: owner_name
	 */
	public java.lang.String getOwnerName () {
		return ownerName;
	}

	/**
	 * Set the value related to the column: owner_name
	 * @param ownerName the owner_name value
	 */
	public void setOwnerName (java.lang.String ownerName) {
		this.ownerName = ownerName;
	}



	/**
	 * Return the value associated with the column: manager_id
	 */
	public java.lang.String getManagerId () {
		return managerId;
	}

	/**
	 * Set the value related to the column: manager_id
	 * @param managerId the manager_id value
	 */
	public void setManagerId (java.lang.String managerId) {
		this.managerId = managerId;
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
	 * Return the value associated with the column: class_ids
	 */
	public java.lang.String getClassIds () {
		return classIds;
	}

	/**
	 * Set the value related to the column: class_ids
	 * @param classIds the class_ids value
	 */
	public void setClassIds (java.lang.String classIds) {
		this.classIds = classIds;
	}



	/**
	 * Return the value associated with the column: class_names
	 */
	public java.lang.String getClassNames () {
		return classNames;
	}

	/**
	 * Set the value related to the column: class_names
	 * @param classNames the class_names value
	 */
	public void setClassNames (java.lang.String classNames) {
		this.classNames = classNames;
	}



	/**
	 * Return the value associated with the column: display_on_portal
	 */
	public int getDisplayOnPortal () {
		return displayOnPortal;
	}

	/**
	 * Set the value related to the column: display_on_portal
	 * @param displayOnPortal the display_on_portal value
	 */
	public void setDisplayOnPortal (int displayOnPortal) {
		this.displayOnPortal = displayOnPortal;
	}



	/**
	 * Return the value associated with the column: entity_name
	 */
	public java.lang.String getEntityName () {
		return entityName;
	}

	/**
	 * Set the value related to the column: entity_name
	 * @param entityName the entity_name value
	 */
	public void setEntityName (java.lang.String entityName) {
		this.entityName = entityName;
	}



	/**
	 * Return the value associated with the column: entity_id
	 */
	public java.lang.String getEntityId () {
		return entityId;
	}

	/**
	 * Set the value related to the column: entity_id
	 * @param entityId the entity_id value
	 */
	public void setEntityId (java.lang.String entityId) {
		this.entityId = entityId;
	}


}