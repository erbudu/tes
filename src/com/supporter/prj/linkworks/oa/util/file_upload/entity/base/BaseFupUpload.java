package com.supporter.prj.linkworks.oa.util.file_upload.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * FupUpload entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseFupUpload implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long recordId;
	private String fileName;
	private String realName;
	private Double fileSize;
	private String contentType;
	private String relatedTable;
	private String relatedFields;
	private String relatedValues;
	private String fileExt;
	private String createdBy;
	private String createdDate;

	// Constructors

	/** default constructor */
	public BaseFupUpload() {
	}

	/** full constructor */
	public BaseFupUpload(String fileName, String realName, Double fileSize,
			String contentType, String relatedTable, String relatedFields,
			String relatedValues, String fileExt, String createdBy,
			String createdDate) {
		this.fileName = fileName;
		this.realName = realName;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.relatedTable = relatedTable;
		this.relatedFields = relatedFields;
		this.relatedValues = relatedValues;
		this.fileExt = fileExt;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "RECORD_ID", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	@Column(name = "FILE_NAME")
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "REAL_NAME", length = 128)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "FILE_SIZE", precision = 18)
	public Double getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name = "CONTENT_TYPE", length = 32)
	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Column(name = "RELATED_TABLE", length = 64)
	public String getRelatedTable() {
		return this.relatedTable;
	}

	public void setRelatedTable(String relatedTable) {
		this.relatedTable = relatedTable;
	}

	@Column(name = "RELATED_FIELDS", length = 20)
	public String getRelatedFields() {
		return this.relatedFields;
	}

	public void setRelatedFields(String relatedFields) {
		this.relatedFields = relatedFields;
	}

	@Column(name = "RELATED_VALUES", length = 50)
	public String getRelatedValues() {
		return this.relatedValues;
	}

	public void setRelatedValues(String relatedValues) {
		this.relatedValues = relatedValues;
	}

	@Column(name = "FILE_EXT", length = 16)
	public String getFileExt() {
		return this.fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	@Column(name = "CREATED_BY", length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE", length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

}