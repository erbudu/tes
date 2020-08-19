package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_adjust.entity.base.BasePrePrjInfoAdjust;

@Entity
@Table(name = "PC_PRE_PRJ_INFO_ADJUST", schema = "")
public class PrePrjInfoAdjust extends BasePrePrjInfoAdjust {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean add;
	private String keyWords;
	// 初始预算总金额
	private Double oldAmount;
	
	private List<PrePrjInfoAdjustComp> comps;
	private String delComps;
	private List<PrePrjInfoAdjustFund> funds;
	private String delFunds;
	@Transient
	public List<PrePrjInfoAdjustComp> getComps() {
		return comps;
	}

	public void setComps(List<PrePrjInfoAdjustComp> comps) {
		this.comps = comps;
	}
	@Transient
	public String getDelComps() {
		return delComps;
	}

	public void setDelComps(String delComps) {
		this.delComps = delComps;
	}
	@Transient
	public List<PrePrjInfoAdjustFund> getFunds() {
		return funds;
	}

	public void setFunds(List<PrePrjInfoAdjustFund> funds) {
		this.funds = funds;
	}
	@Transient
	public String getDelFunds() {
		return delFunds;
	}

	public void setDelFunds(String delFunds) {
		this.delFunds = delFunds;
	}

	@Transient
	public Double getOldAmount() {
		return oldAmount;
	}

	public void setOldAmount(Double oldAmount) {
		this.oldAmount = oldAmount;
	}

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	@Transient
	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

}
