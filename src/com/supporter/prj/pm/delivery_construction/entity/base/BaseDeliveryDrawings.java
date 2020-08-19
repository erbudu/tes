package com.supporter.prj.pm.delivery_construction.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseDeliveryDrawings implements java.io.Serializable{

	// primary key
	private java.lang.String id;//主键
	private java.lang.String deliveryId;
	private java.lang.String drawingId;
	private java.lang.String drawingNo;
	private java.lang.String drawingName;
	private java.util.Date deliveryDate;
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	@Column(name = "delivery_id", length = 32)
	public java.lang.String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(java.lang.String deliveryId) {
		this.deliveryId = deliveryId;
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
	
	@Column(name = "delivery_date", length = 7)
	public java.util.Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(java.util.Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

}
