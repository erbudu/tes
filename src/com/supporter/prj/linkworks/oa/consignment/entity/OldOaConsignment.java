package com.supporter.prj.linkworks.oa.consignment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * OaConsignment entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OLD_OA_CONSIGNMENT", schema = "")
public class OldOaConsignment implements java.io.Serializable {

	// Fields

	private Long consignmentId;
	private Long consignerId;
	private Long consigneeId;
	private String dateFrom;
	private String dateTo;
	private String consignerName;
	private String consigneeName;
	private String consignReason;
	private String publishBulletin;
	private Long isFailure;
	private String consignmentIdNew;

	// Constructors

	/** default constructor */
	public OldOaConsignment() {
	}

	/** full constructor */
	public OldOaConsignment(Long consignerId, Long consigneeId, String dateFrom,
			String dateTo, String consignerName, String consigneeName,
			String consignReason, String publishBulletin, Long isFailure,
			String consignmentIdNew) {
		this.consignerId = consignerId;
		this.consigneeId = consigneeId;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.consignerName = consignerName;
		this.consigneeName = consigneeName;
		this.consignReason = consignReason;
		this.publishBulletin = publishBulletin;
		this.isFailure = isFailure;
		this.consignmentIdNew = consignmentIdNew;
	}

	// Property accessors
	@Id
	@Column(name = "CONSIGNMENT_ID", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getConsignmentId() {
		return this.consignmentId;
	}

	public void setConsignmentId(Long consignmentId) {
		this.consignmentId = consignmentId;
	}

	@Column(name = "CONSIGNER_ID", precision = 18, scale = 0)
	public Long getConsignerId() {
		return this.consignerId;
	}

	public void setConsignerId(Long consignerId) {
		this.consignerId = consignerId;
	}

	@Column(name = "CONSIGNEE_ID", precision = 18, scale = 0)
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

	@Column(name = "CONSIGNER_NAME", length = 32)
	public String getConsignerName() {
		return this.consignerName;
	}

	public void setConsignerName(String consignerName) {
		this.consignerName = consignerName;
	}

	@Column(name = "CONSIGNEE_NAME", length = 32)
	public String getConsigneeName() {
		return this.consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	@Column(name = "CONSIGN_REASON")
	public String getConsignReason() {
		return this.consignReason;
	}

	public void setConsignReason(String consignReason) {
		this.consignReason = consignReason;
	}

	@Column(name = "PUBLISH_BULLETIN", length = 1)
	public String getPublishBulletin() {
		return this.publishBulletin;
	}

	public void setPublishBulletin(String publishBulletin) {
		this.publishBulletin = publishBulletin;
	}

	@Column(name = "IS_FAILURE", precision = 22, scale = 0)
	public Long getIsFailure() {
		return this.isFailure;
	}

	public void setIsFailure(Long isFailure) {
		this.isFailure = isFailure;
	}

	@Column(name = "CONSIGNMENT_ID_NEW", length = 32)
	public String getConsignmentIdNew() {
		return this.consignmentIdNew;
	}

	public void setConsignmentIdNew(String consignmentIdNew) {
		this.consignmentIdNew = consignmentIdNew;
	}

}