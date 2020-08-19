package com.supporter.prj.linkworks.oa.signed_report.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseSignedReportExamFile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "FILE_ID", unique = true, nullable = false, length = 32)
	private String fileId;
	
	@Column(name = "RELATED_ENTITY_NAME",  length = 128)
	private String relatedEntityName;
	
	@Column(name = "RELATED_ENTITY_ID", length = 32)
	private String relatedEntityId;
	
	@Column(name = "EXAM_ID", length = 32)
	private String examId;
	
	@Column(name = "FILE_NAME",  length = 256)
	private String fileName;
	
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

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getRelatedEntityName() {
		return relatedEntityName;
	}

	public void setRelatedEntityName(String relatedEntityName) {
		this.relatedEntityName = relatedEntityName;
	}

	public String getRelatedEntityId() {
		return relatedEntityId;
	}

	public void setRelatedEntityId(String relatedEntityId) {
		this.relatedEntityId = relatedEntityId;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	
	public BaseSignedReportExamFile(String fileId, String relatedEntityName,
			String relatedEntityId, String examId, String fileName,
			String createdDate, String createdBy, String createdById,
			String modifiedDate, String modifiedBy, String modifiedById) {
		super();
		this.fileId = fileId;
		this.relatedEntityName = relatedEntityName;
		this.relatedEntityId = relatedEntityId;
		this.examId = examId;
		this.fileName = fileName;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
	}

	public BaseSignedReportExamFile() {
		super();
	}
	
	
	
	
	
	

}
