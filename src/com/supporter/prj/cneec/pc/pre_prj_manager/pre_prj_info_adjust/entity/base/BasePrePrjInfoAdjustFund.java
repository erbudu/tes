package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.entity.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * PcPrePrjInfoAdjustFund entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BasePrePrjInfoAdjustFund implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private String fundId;
	private String adjustId;
	private String source;
	private String marchds;
	private String ifc;
	private String commercial;
	private String government;

	// Constructors

	/** default constructor */
	public BasePrePrjInfoAdjustFund() {
	}

	/** full constructor */
	public BasePrePrjInfoAdjustFund(String adjustId, String source, String marchds, String ifc, String commercial,
			String government) {
		this.adjustId = adjustId;
		this.source = source;
		this.marchds = marchds;
		this.ifc = ifc;
		this.commercial = commercial;
		this.government = government;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "FUND_ID", unique = true, nullable = false, length = 32)

	public String getFundId() {
		return this.fundId;
	}

	public void setFundId(String fundId) {
		this.fundId = fundId;
	}

	@Column(name = "ADJUST_ID", length = 32)

	public String getAdjustId() {
		return this.adjustId;
	}

	public void setAdjustId(String adjustId) {
		this.adjustId = adjustId;
	}

	@Column(name = "SOURCE", length = 32)

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "MARCHDS", length = 32)

	public String getMarchds() {
		return this.marchds;
	}

	public void setMarchds(String marchds) {
		this.marchds = marchds;
	}

	@Column(name = "IFC", length = 32)

	public String getIfc() {
		return this.ifc;
	}

	public void setIfc(String ifc) {
		this.ifc = ifc;
	}

	@Column(name = "COMMERCIAL", length = 32)

	public String getCommercial() {
		return this.commercial;
	}

	public void setCommercial(String commercial) {
		this.commercial = commercial;
	}

	@Column(name = "GOVERNMENT", length = 32)

	public String getGovernment() {
		return this.government;
	}

	public void setGovernment(String government) {
		this.government = government;
	}

}