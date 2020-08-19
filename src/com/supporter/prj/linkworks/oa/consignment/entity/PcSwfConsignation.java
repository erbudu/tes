package com.supporter.prj.linkworks.oa.consignment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SwfConsignation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PC_SWF_CONSIGNATION", schema = "")
public class PcSwfConsignation implements java.io.Serializable {

	// Fields

	private Long consignId;
	private Long consignerId;
	private Long consigneeId;
	private String dateFrom;
	private String dateTo;
	private Long defType;
	private Long defId;
	private Long consignmentId;

	// Constructors

	/** default constructor */
	public PcSwfConsignation() {
	}

	/** minimal constructor */
	public PcSwfConsignation(Long consignId) {
		this.consignId = consignId;
	}

	/** full constructor */
	public PcSwfConsignation(Long consignId, Long consignerId, Long consigneeId,
			String dateFrom, String dateTo, Long defType, Long defId,
			Long consignmentId) {
		this.consignId = consignId;
		this.consignerId = consignerId;
		this.consigneeId = consigneeId;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.defType = defType;
		this.defId = defId;
		this.consignmentId = consignmentId;
	}

	// Property accessors
	@Id
	@Column(name = "CONSIGN_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getConsignId() {
		return this.consignId;
	}

	public void setConsignId(Long consignId) {
		this.consignId = consignId;
	}

	@Column(name = "CONSIGNER_ID", precision = 22, scale = 0)
	public Long getConsignerId() {
		return this.consignerId;
	}

	public void setConsignerId(Long consignerId) {
		this.consignerId = consignerId;
	}

	@Column(name = "CONSIGNEE_ID", precision = 22, scale = 0)
	public Long getConsigneeId() {
		return this.consigneeId;
	}

	public void setConsigneeId(Long consigneeId) {
		this.consigneeId = consigneeId;
	}

	@Column(name = "DATE_FROM", length = 27)
	public String getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	@Column(name = "DATE_TO", length = 27)
	public String getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	@Column(name = "DEF_TYPE", precision = 22, scale = 0)
	public Long getDefType() {
		return this.defType;
	}

	public void setDefType(Long defType) {
		this.defType = defType;
	}

	@Column(name = "DEF_ID", precision = 22, scale = 0)
	public Long getDefId() {
		return this.defId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	@Column(name = "CONSIGNMENT_ID", precision = 18, scale = 0)
	public Long getConsignmentId() {
		return this.consignmentId;
	}

	public void setConsignmentId(Long consignmentId) {
		this.consignmentId = consignmentId;
	}

}