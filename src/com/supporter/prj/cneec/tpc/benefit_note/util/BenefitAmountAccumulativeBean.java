package com.supporter.prj.cneec.tpc.benefit_note.util;

import com.ibm.icu.math.BigDecimal;

/**
 * @Title: BenefitAmountAccumulativeBean
 * @Description: 项目效益测算/预算表金额累计
 * @author: yanweichao
 * @date: 2018-6-7
 * @version: V1.0
 */
public class BenefitAmountAccumulativeBean {

	private double currency1Amount;
	private double currency2Amount;
	private double currency3Amount;
	private double currency4Amount;
	private double currency5Amount;
	private double currency6Amount;
	private double currency7Amount;
	private double currency8Amount;
	private double currency9Amount;
	private double totalRmbAmount;

	public BenefitAmountAccumulativeBean() {
		super();
	}

	public BenefitAmountAccumulativeBean(double totalRmbAmount) {
		super();
		this.totalRmbAmount = totalRmbAmount;
	}

	public double getCurrency1Amount() {
		return this.currency1Amount;
	}

	public void setCurrency1Amount(double currency1Amount) {
		this.currency1Amount += currency1Amount;
	}

	public double getCurrency2Amount() {
		return this.currency2Amount;
	}

	public void setCurrency2Amount(double currency2Amount) {
		this.currency2Amount += currency2Amount;
	}

	public double getCurrency3Amount() {
		return this.currency3Amount;
	}

	public void setCurrency3Amount(double currency3Amount) {
		this.currency3Amount += currency3Amount;
	}

	public double getCurrency4Amount() {
		return this.currency4Amount;
	}

	public void setCurrency4Amount(double currency4Amount) {
		this.currency4Amount += currency4Amount;
	}

	public double getCurrency5Amount() {
		return this.currency5Amount;
	}

	public void setCurrency5Amount(double currency5Amount) {
		this.currency5Amount += currency5Amount;
	}

	public double getCurrency6Amount() {
		return this.currency6Amount;
	}

	public void setCurrency6Amount(double currency6Amount) {
		this.currency6Amount += currency6Amount;
	}

	public double getCurrency7Amount() {
		return this.currency7Amount;
	}

	public void setCurrency7Amount(double currency7Amount) {
		this.currency7Amount += currency7Amount;
	}

	public double getCurrency8Amount() {
		return this.currency8Amount;
	}

	public void setCurrency8Amount(double currency8Amount) {
		this.currency8Amount += currency8Amount;
	}

	public double getCurrency9Amount() {
		return this.currency9Amount;
	}

	public void setCurrency9Amount(double currency9Amount) {
		this.currency9Amount += currency9Amount;
	}

	public double getTotalRmbAmount() {
		return totalRmbAmount;
	}

	public void setTotalRmbAmount(double totalRmbAmount) {
		this.totalRmbAmount = BigDecimal.valueOf(this.totalRmbAmount).add(BigDecimal.valueOf(totalRmbAmount)).doubleValue();
	}

}
