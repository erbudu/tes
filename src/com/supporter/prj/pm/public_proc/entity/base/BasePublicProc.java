package com.supporter.prj.pm.public_proc.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BasePublicProc implements java.io.Serializable{

	// primary key
	private java.lang.String id;//主键

	// fields
	private java.lang.String entityId;//业务单ID
	private java.lang.String entityName;//业务来源
	private java.lang.String examOne;//审核人
	private java.lang.String examTwo;//批准人
	private java.lang.String examThree;//预留审批人1
	private java.lang.String examFour;//预留审批人2

	/** default constructor */
	public BasePublicProc() {
	}

	/** minimal constructor */
	public BasePublicProc(String id) {
		this.id = id;
	}
	
	/** full constructor */
	public BasePublicProc(String id, String entityId,String entityName,
			String examOne,String examTwo,String examThree, String examFour ) {
		this.id = id;
		this.entityId = entityId;
		this.entityName = entityName;
		this.examOne = examOne;
		this.examTwo = examTwo;
		this.examThree = examThree;
		this.examFour = examFour;
	}
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	@Column(name = "entity_id", length = 32)
	public java.lang.String getEntityId() {
		return entityId;
	}

	public void setEntityId(java.lang.String entityId) {
		this.entityId = entityId;
	}

	@Column(name = "entity_name", length = 32)
	public java.lang.String getEntityName() {
		return entityName;
	}

	public void setEntityName(java.lang.String entityName) {
		this.entityName = entityName;
	}
	
	@Column(name = "exam_one", length = 256)
	public java.lang.String getExamOne() {
		return examOne;
	}

	public void setExamOne(java.lang.String examOne) {
		this.examOne = examOne;
	}
	
	@Column(name = "exam_two", length = 256)
	public java.lang.String getExamTwo() {
		return examTwo;
	}

	public void setExamTwo(java.lang.String examTwo) {
		this.examTwo = examTwo;
	}
	
	@Column(name = "exam_three", length = 256)
	public java.lang.String getExamThree() {
		return examThree;
	}

	public void setExamThree(java.lang.String examThree) {
		this.examThree = examThree;
	}
	
	@Column(name = "exam_four", length = 256)
	public java.lang.String getExamFour() {
		return examFour;
	}

	public void setExamFour(java.lang.String examFour) {
		this.examFour = examFour;
	}

}
