package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * PcPreDevelopProgressAmount entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class PcPreDevelopProgressAmount implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private String recId;
	private String progressId;
	private String contractTime;
	private Double contractedAmount;
	private String remarks;

	// Constructors

	/** default constructor */
	public PcPreDevelopProgressAmount() {
	}

	/** full constructor */
	public PcPreDevelopProgressAmount(String progressId, String contractTime, Double contractedAmount, String remarks) {
		this.progressId = progressId;
		this.contractTime = contractTime;
		this.contractedAmount = contractedAmount;
		this.remarks = remarks;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "REC_ID", unique = true, nullable = false, length = 32)

	public String getRecId() {
		return this.recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	@Column(name = "PROGRESS_ID", length = 32)

	public String getProgressId() {
		return this.progressId;
	}

	public void setProgressId(String progressId) {
		this.progressId = progressId;
	}

	@Column(name = "CONTRACT_TIME", length = 32)

	public String getContractTime() {
		return this.contractTime;
	}

	public void setContractTime(String contractTime) {
		this.contractTime = contractTime;
	}

	@Column(name = "CONTRACTED_AMOUNT", precision = 18, scale = 3)

	public Double getContractedAmount() {
		return this.contractedAmount;
	}

	public void setContractedAmount(Double contractedAmount) {
		this.contractedAmount = contractedAmount;
	}

	@Column(name = "REMARKS", length = 512)

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}