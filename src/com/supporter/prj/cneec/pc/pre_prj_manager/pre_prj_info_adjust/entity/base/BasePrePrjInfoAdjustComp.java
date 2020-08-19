package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.entity.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * PcPrePrjInfoAdjustComp entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BasePrePrjInfoAdjustComp implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String competitorId;
	private String adjustId;
	private String competitor;

	// Constructors

	/** default constructor */
	public BasePrePrjInfoAdjustComp() {
	}

	/** full constructor */
	public BasePrePrjInfoAdjustComp(String adjustId, String competitor) {
		this.adjustId = adjustId;
		this.competitor = competitor;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "COMPETITOR_ID", unique = true, nullable = false, length = 32)

	public String getCompetitorId() {
		return this.competitorId;
	}

	public void setCompetitorId(String competitorId) {
		this.competitorId = competitorId;
	}

	@Column(name = "ADJUST_ID", length = 32)

	public String getAdjustId() {
		return this.adjustId;
	}

	public void setAdjustId(String adjustId) {
		this.adjustId = adjustId;
	}

	@Column(name = "COMPETITOR", length = 512)

	public String getCompetitor() {
		return this.competitor;
	}

	public void setCompetitor(String competitor) {
		this.competitor = competitor;
	}

}