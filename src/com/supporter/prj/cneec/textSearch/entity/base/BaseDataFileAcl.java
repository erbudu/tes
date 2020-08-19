package com.supporter.prj.cneec.textSearch.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * This is an object that contains data related to the fbox_file_acl table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="fbox_file_acl"
 */
@MappedSuperclass
public abstract class BaseDataFileAcl  implements Serializable {



	// constructors
	public BaseDataFileAcl () {
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDataFileAcl (java.lang.String id) {
		this.setId(id);
	}





	// primary key
	@Id 
    @Column(name="record_id", unique=true, nullable=false, length=32)
	private java.lang.String id;

	// fields
	@Column(name="file_id", nullable=false, length=32)
	private java.lang.String fileId;
	@Column(name="authorizee_type", nullable=false, length=32)
	private java.lang.String authorizeeType;
	@Column(name="authorizee_id", nullable=false, length=32)
	private java.lang.String authorizeeId;
	@Column(name="can_read")
	@org.hibernate.annotations.Type(type="true_false")
	private boolean canRead;
	@Column(name="can_write")
	@org.hibernate.annotations.Type(type="true_false")
	private boolean canWrite;
	@Column(name="can_delete")
	@org.hibernate.annotations.Type(type="true_false")
	private boolean canDelete;
	@Column(name="full_access")
	@org.hibernate.annotations.Type(type="true_false")
	private boolean fullAccess;
	@Column(name="is_inherited")
	@org.hibernate.annotations.Type(type="true_false")
	private boolean inherited;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="uuid"
     *  column="record_id"
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
	 * Return the value associated with the column: file_id
	 */
	public java.lang.String getFileId () {
		return fileId;
	}

	/**
	 * Set the value related to the column: file_id
	 * @param fileId the file_id value
	 */
	public void setFileId (java.lang.String fileId) {
		this.fileId = fileId;
	}



	/**
	 * Return the value associated with the column: authorizee_type
	 */
	public java.lang.String getAuthorizeeType () {
		return authorizeeType;
	}

	/**
	 * Set the value related to the column: authorizee_type
	 * @param authorizeeType the authorizee_type value
	 */
	public void setAuthorizeeType (java.lang.String authorizeeType) {
		this.authorizeeType = authorizeeType;
	}



	/**
	 * Return the value associated with the column: authorizee_id
	 */
	public java.lang.String getAuthorizeeId () {
		return authorizeeId;
	}

	/**
	 * Set the value related to the column: authorizee_id
	 * @param authorizeeId the authorizee_id value
	 */
	public void setAuthorizeeId (java.lang.String authorizeeId) {
		this.authorizeeId = authorizeeId;
	}



	/**
	 * Return the value associated with the column: can_read
	 */
	public boolean isCanRead () {
		return canRead;
	}

	/**
	 * Set the value related to the column: can_read
	 * @param canRead the can_read value
	 */
	public void setCanRead (boolean canRead) {
		this.canRead = canRead;
	}



	/**
	 * Return the value associated with the column: can_write
	 */
	public boolean isCanWrite () {
		return canWrite;
	}

	/**
	 * Set the value related to the column: can_write
	 * @param canWrite the can_write value
	 */
	public void setCanWrite (boolean canWrite) {
		this.canWrite = canWrite;
	}



	/**
	 * Return the value associated with the column: can_delete
	 */
	public boolean isCanDelete () {
		return canDelete;
	}

	/**
	 * Set the value related to the column: can_delete
	 * @param canDelete the can_delete value
	 */
	public void setCanDelete (boolean canDelete) {
		this.canDelete = canDelete;
	}



	/**
	 * Return the value associated with the column: full_access
	 */
	public boolean isFullAccess () {
		return fullAccess;
	}

	/**
	 * Set the value related to the column: full_access
	 * @param fullAccess the full_access value
	 */
	public void setFullAccess (boolean fullAccess) {
		this.fullAccess = fullAccess;
	}



	/**
	 * Return the value associated with the column: is_inherited
	 */
	public boolean isInherited () {
		return inherited;
	}

	/**
	 * Set the value related to the column: is_inherited
	 * @param inherited the is_inherited value
	 */
	public void setInherited (boolean inherited) {
		this.inherited = inherited;
	}

}