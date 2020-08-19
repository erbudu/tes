package com.supporter.prj.linkworks.oa.consignment.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SwfConsignation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DM_SWF_CONSIGNATION", schema = "")
public class DmSwfConsignation implements java.io.Serializable {

	private Long consignId;
	private Long consignmentId;
	private String consignerId;
	private String consigneeId;
	private Date dateFrom;
	private Date dateTo;
	private Long defType;
	private String defId;

	// Constructors

	/** default constructor */
	public DmSwfConsignation() {
	}

	/** minimal constructor */
	public DmSwfConsignation(Long consignId) {
		this.consignId = consignId;
	}

	/** full constructor */
	public DmSwfConsignation(Long consignId, Long consignmentId,
			String consignerId, String consigneeId, Date dateFrom,
			Date dateTo, Long defType, String defId) {
		this.consignId = consignId;
		this.consignmentId = consignmentId;
		this.consignerId = consignerId;
		this.consigneeId = consigneeId;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.defType = defType;
		this.defId = defId;
	}

	// Property accessors
	@Id
	@Column(name = "CONSIGN_ID", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getConsignId() {
		return this.consignId;
	}

	public void setConsignId(Long consignId) {
		this.consignId = consignId;
	}

	@Column(name = "CONSIGNMENT_ID", precision = 18, scale = 0)
	public Long getConsignmentId() {
		return this.consignmentId;
	}

	public void setConsignmentId(Long consignmentId) {
		this.consignmentId = consignmentId;
	}

	@Column(name = "CONSIGNER_ID", length = 36)
	public String getConsignerId() {
		return this.consignerId;
	}

	public void setConsignerId(String consignerId) {
		this.consignerId = consignerId;
	}

	@Column(name = "CONSIGNEE_ID", length=64)
	public String getConsigneeId() {
		return this.consigneeId;
	}

	public void setConsigneeId(String consigneeId) {
		this.consigneeId = consigneeId;
	}
	@Column(name = "DATE_FROM")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	@Column(name = "DATE_TO")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	@Column(name = "DEF_TYPE", precision = 22, scale = 0)
	public Long getDefType() {
		return this.defType;
	}

	public void setDefType(Long defType) {
		this.defType = defType;
	}

	@Column(name = "DEF_ID", length = 64)
	public String getDefId() {
		return this.defId;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}
}