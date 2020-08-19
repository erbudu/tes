package com.supporter.prj.cneec.tpc.purchase_demand.entity.base;

// default package

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * BasePurchaseDemandCustom entity provides the base persistence definition
 * of the PurchaseDemandCustom entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class BasePurchaseDemandCustom implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private String recordId;
	private String demandId;
	private String customerId;
	private String customerName;
	private String deliveryTime;

	// Constructors

	/** default constructor */
	public BasePurchaseDemandCustom() {
	}

	/** minimal constructor */
	public BasePurchaseDemandCustom(String recordId) {
		this.recordId = recordId;
	}

	public BasePurchaseDemandCustom(String demandId, String customerId, String customerName) {
		this.demandId = demandId;
		this.customerName = customerName;
	}

	/** full constructor */
	public BasePurchaseDemandCustom(String recordId, String demandId, String customerId, String customerName) {
		this.recordId = recordId;
		this.customerId = customerId;
		this.demandId = demandId;
		this.customerName = customerName;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "RECORD_ID", unique = true, nullable = false, length = 32)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "DEMAND_ID", length = 32)
	public String getDemandId() {
		return this.demandId;
	}

	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}

	@Column(name = "CUSTOMER_ID", length = 32)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CUSTOMER_NAME", length = 512)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "DELIVERY_TIME", length = 27)
	public String getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

}