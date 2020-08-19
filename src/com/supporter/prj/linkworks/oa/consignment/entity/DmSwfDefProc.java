package com.supporter.prj.linkworks.oa.consignment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SwfDefProc entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DM_SWF_DEF_LAST", schema = "")
public class DmSwfDefProc implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private String id;
	private String class_;
	private String name;
	private String description;
	private String version;
	private String isTerminationImplicit;
	private String startState;
	private String procTitle;
	public DmSwfDefProc() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DmSwfDefProc(String id, String class_, String name,
			String description, String version, String isTerminationImplicit,
			String startState, String procTitle) {
		super();
		this.id = id;
		this.class_ = class_;
		this.name = name;
		this.description = description;
		this.version = version;
		this.isTerminationImplicit = isTerminationImplicit;
		this.startState = startState;
		this.procTitle = procTitle;
	}
	@Id
	@Column(name = "ID_", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "CLASS_", length = 64)
	public String getClass_() {
		return class_;
	}
	public void setClass_(String class_) {
		this.class_ = class_;
	}
	@Column(name = "NAME_", length = 64)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "DESCRIPTION_", length = 64)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "VERSION_", length = 64)
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Column(name = "ISTERMINATIONIMPLICIT_", length = 64)
	public String getIsTerminationImplicit() {
		return isTerminationImplicit;
	}
	public void setIsTerminationImplicit(String isTerminationImplicit) {
		this.isTerminationImplicit = isTerminationImplicit;
	}
	@Column(name = "STARTSTATE_", length = 64)
	public String getStartState() {
		return startState;
	}
	public void setStartState(String startState) {
		this.startState = startState;
	}
	@Column(name = "PROC_DEF_TITLE", length = 64)
	public String getProcTitle() {
		return procTitle;
	}
	public void setProcTitle(String procTitle) {
		this.procTitle = procTitle;
	}

	
}