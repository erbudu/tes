package com.supporter.prj.cneec.tpc.purchase_demand.entity.base;

// default package

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * AbstractPurchaseDemandDetail entity provides the base persistence definition
 * of the PurchaseDemandDetail entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class BasePurchaseDemandDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private String detailId;
	private String demandId;
	private String recordId;
	private String itemName;
	private String specification;
	private String unit;
	private double num;
	private String deliveryTime;
	private String remarks;

	// Constructors

	/** default constructor */
	public BasePurchaseDemandDetail() {
	}

	/** minimal constructor */
	public BasePurchaseDemandDetail(String detailId) {
		this.detailId = detailId;
	}

	/** full constructor */
	public BasePurchaseDemandDetail(String demandId, String recordId, String itemName, String specification, String unit, double num, String remarks) {
		this.demandId = demandId;
		this.recordId = recordId;
		this.itemName = itemName;
		this.specification = specification;
		this.unit = unit;
		this.num = num;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "DETAIL_ID", unique = true, nullable = false, length = 32)
	public String getDetailId() {
		return this.detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	@Column(name = "DEMAND_ID", length = 32)
	public String getDemandId() {
		return this.demandId;
	}

	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}

	@Column(name = "RECORD_ID", length = 32)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "ITEM_NAME", length = 128)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "SPECIFICATION", length = 64)
	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Column(name = "UNIT", length = 32)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "NUM", precision = 18, scale = 3)
	public double getNum() {
		return this.num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	@Column(name = "DELIVERY_TIME", length = 27)
	public String getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	@Column(name = "REMARKS", length = 512)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}