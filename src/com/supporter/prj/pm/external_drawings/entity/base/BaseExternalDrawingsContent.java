package com.supporter.prj.pm.external_drawings.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseExternalDrawingsContent implements java.io.Serializable{

	// primary key
	private java.lang.String id;//主键

	// fields
	private java.lang.String externalId;
	private java.lang.String drawingId;
	private java.lang.String drawingNo;
	private java.lang.String drawingName;
	private java.lang.String suggestId;
	private java.lang.String suggest;
	private java.util.Date externalDate;
	private java.util.Date backDate;
	private java.lang.String drawingType;
	private java.lang.Integer isFor;
	/** default constructor */
	public BaseExternalDrawingsContent() {
	}

	/** minimal constructor */
	public BaseExternalDrawingsContent(String id) {
		this.id = id;
	}
	

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	@Column(name = "external_id", length = 32)
	public java.lang.String getExternalId() {
		return externalId;
	}

	public void setExternalId(java.lang.String externalId) {
		this.externalId = externalId;
	}
	
	@Column(name = "drawing_id", length = 32)
	public java.lang.String getDrawingId() {
		return drawingId;
	}

	public void setDrawingId(java.lang.String drawingId) {
		this.drawingId = drawingId;
	}
	
	@Column(name = "drawing_no", length = 64)
	public java.lang.String getDrawingNo() {
		return drawingNo;
	}

	public void setDrawingNo(java.lang.String drawingNo) {
		this.drawingNo = drawingNo;
	}
	
	@Column(name = "drawing_name", length = 64)
	public java.lang.String getDrawingName() {
		return drawingName;
	}

	public void setDrawingName(java.lang.String drawingName) {
		this.drawingName = drawingName;
	}
	
	@Column(name = "suggest_id", length = 32)
	public java.lang.String getSuggestId() {
		return suggestId;
	}

	public void setSuggestId(java.lang.String suggestId) {
		this.suggestId = suggestId;
	}
	
	@Column(name = "suggest", length = 128)
	public java.lang.String getSuggest() {
		return suggest;
	}

	public void setSuggest(java.lang.String suggest) {
		this.suggest = suggest;
	}
	
	@Column(name = "external_date", length = 7)
	public java.util.Date getExternalDate() {
		return externalDate;
	}

	public void setExternalDate(java.util.Date externalDate) {
		this.externalDate = externalDate;
	}
	
	@Column(name = "back_date", length = 7)
	public java.util.Date getBackDate() {
		return backDate;
	}

	public void setBackDate(java.util.Date backDate) {
		this.backDate = backDate;
	}
	@Column(name = "drawing_type", length = 32)
	public java.lang.String getDrawingType() {
		return drawingType;
	}

	public void setDrawingType(java.lang.String drawingType) {
		this.drawingType = drawingType;
	}
	
	@Column(name = "is_for", length = 11)
	public java.lang.Integer getIsFor() {
		return isFor;
	}

	public void setIsFor(java.lang.Integer isFor) {
		this.isFor = isFor;
	}

}
