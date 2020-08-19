package com.supporter.prj.linkworks.oa.doc_in.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseDocInSrcUnit implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String unitId ;
	private String unitName;
	private Integer displayOrder;
	
	public BaseDocInSrcUnit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BaseDocInSrcUnit(String unitId, String unitName, Integer displayOrder) {
		super();
		this.unitId = unitId;
		this.unitName = unitName;
		this.displayOrder = displayOrder;
	}
	@Id
	@Column(name = "UNIT_ID", unique = true, nullable = false, length = 32)
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	@Column(name = "UNIT_NAME", length = 64)
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	@Column(name = "display_order", precision = 22, scale = 0)
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
}
