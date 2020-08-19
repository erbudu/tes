package com.supporter.prj.pm.contract_balance.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: 合同结算.施工合同.涉及工程部位
 * @Description: PM_CONTRACT_BALANCE_CONST_SITE, 字段与数据库字段一一对应.
 * @author Administrator
 * @date 2018-07-04 18:07:39
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseContractBalanceConstSite  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *SITE_ID.
	 */
	@Id
	@GeneratedValue(generator = "assigned")
  	@GenericGenerator(name = "assigned",  strategy = "assigned")
	@Column(name = "SITE_ID", nullable = false, length = 32)
	private java.lang.String siteId;
	/**
	 *PARENT_SITE_ID.
	 */
	@Column(name = "PARENT_SITE_ID", nullable = true, length = 32)
	private java.lang.String parentSiteId;
	/**
	 *BALANCE_ID.
	 */
	@Column(name = "BALANCE_ID", nullable = true, length = 32)
	private java.lang.String balanceId;
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	private java.lang.String contractId;
	/**
	 *WBS_ID.
	 */
	@Column(name = "WBS_ID", nullable = true, length = 32)
	private java.lang.String wbsId;
	/**
	 *WBS_NAME.
	 */
	@Column(name = "WBS_NAME", nullable = true, length = 1024)
	private java.lang.String wbsName;
	/**
	 *COUNT_UNIT.
	 */
	@Column(name = "COUNT_UNIT", nullable = true, length = 32)
	private java.lang.String countUnit;
	/**
	 *PRICE_UNIT.
	 */
	@Column(name = "PRICE_UNIT", nullable = true, length = 32)
	private java.lang.String priceUnit;
	/**
	 *CONTRACT_AMOUNT.
	 */
	@Column(name = "CONTRACT_AMOUNT", nullable = true, precision = 10, scale = 3)
	private double contractAmount;
	/**
	 *CHANGE_AMOUNT.
	 */
	@Column(name = "CHANGE_AMOUNT", nullable = true, precision = 10, scale = 3)
	private double changeAmount;
	/**
	 *ADJUSTED_AMOUNT.
	 */
	@Column(name = "ADJUSTED_AMOUNT", nullable = true, precision = 10, scale = 3)
	private double adjustedAmount;
	
	/**
	 * 合同工程量
	 */
	@Column(name = "PRICE_C", nullable = true, precision = 10, scale = 3)
	private double priceC;
	
	@Column(name = "BUY_COUNT_C", nullable = true, precision = 10, scale = 4)
	private double buyCountC;
	
	/**
	 * 合同内
	 */
	@Column(name = "PRICE", nullable = true, precision = 10, scale = 3)
	private double price;
	
	@Column(name = "CURRENT_COUNT", nullable = true, precision = 10, scale = 4)
	private double currentCount;
	
	@Column(name = "CURRENT_PAY", nullable = true, precision = 10, scale = 3)
	private double currentPay;
	
	@Column(name = "ACCUMULATIVE_COUNT", nullable = true, precision = 10, scale = 4)
	private double accumulativeCount;
	
	@Column(name = "ACCUMULATIVE_PAY", nullable = true, precision = 10, scale = 3)
	private double accumulativePay;
	
	/**
	 * 合同外（签证）
	 */
	@Column(name = "PRICE_V", nullable = true, precision = 10, scale = 3)
	private double priceV;
	
	@Column(name = "CURRENT_COUNT_V", nullable = true, precision = 10, scale = 4)
	private double currentCountV;
	
	@Column(name = "CURRENT_PAY_V", nullable = true, precision = 10, scale = 3)
	private double currentPayV;

	@Column(name = "ACCUMULATIVE_COUNT_V", nullable = true, precision = 10, scale = 4)
	private double accumulativeCountV;

	@Column(name = "ACCUMULATIVE_PAY_V", nullable = true, precision = 10, scale = 3)
	private double accumulativePayV;
	
	/**
	 * 审核确认-合同内
	 */
	@Column(name = "PRICE_EXAM", nullable = true, precision = 10, scale = 3)
	private double priceExam;
	
	@Column(name = "CURRENT_COUNT_EXAM", nullable = true, precision = 10, scale = 4)
	private double currentCountExam;
	
	@Column(name = "CURRENT_PAY_EXAM", nullable = true, precision = 10, scale = 3)
	private double currentPayExam;

	@Column(name = "ACCUMULATIVE_COUNT_EXAM", nullable = true, precision = 10, scale = 4)
	private double accumulativeCountExam;

	@Column(name = "ACCUMULATIVE_PAY_EXAM", nullable = true, precision = 10, scale = 3)
	private double accumulativePayExam;
	
	/**
	 * 审核确认-合同外（签证）
	 */
	@Column(name = "PRICE_EXAM_V", nullable = true, precision = 10, scale = 3)
	private double priceExamV;
	
	@Column(name = "CURRENT_COUNT_EXAM_V", nullable = true, precision = 10, scale = 4)
	private double currentCountExamV;
	
	@Column(name = "CURRENT_PAY_EXAM_V", nullable = true, precision = 10, scale = 3)
	private double currentPayExamV;

	@Column(name = "ACCUMULATIVE_COUNT_EXAM_V", nullable = true, precision = 10, scale = 4)
	private double accumulativeCountExamV;

	@Column(name = "ACCUMULATIVE_PAY_EXAM_V", nullable = true, precision = 10, scale = 3)
	private double accumulativePayExamV;
	
	@Column(name = "notes", nullable = true, length = 32)
	private java.lang.String notes;
	
	@Column(name = "price_v2", nullable = false, precision = 18, scale = 3)
	private double priceV2;

	@Column(name = "current_count_v2", nullable = false, precision = 18, scale = 4)
	private double currentCountV2;

	@Column(name = "current_pay_v2", nullable = false, precision = 18, scale = 3)
	private double currentPayV2;

	@Column(name = "accumulative_count_v2", nullable = false, precision = 18, scale = 4)
	private double accumulativeCountV2;

	@Column(name = "accumulative_pay_v2", nullable = false, precision = 18, scale = 3)
	private double accumulativePayV2;

	@Column(name = "price_exam_v2", nullable = false, precision = 18, scale = 3)
	private double priceExamV2;

	@Column(name = "current_count_exam_v2", nullable = false, precision = 18, scale = 4)
	private double currentCountExamV2;

	@Column(name = "current_pay_exam_v2", nullable = false, precision = 18, scale = 3)
	private double currentPayExamV2;

	@Column(name = "accumulative_count_exam_v2", nullable = false, precision = 18, scale = 4)
	private double accumulativeCountExamV2;

	@Column(name = "accumulative_pay_exam_v2", nullable = false, precision = 18, scale = 3)
	private double accumulativePayExamV2;

	@Column(name = "current_fin_ratio", nullable = false, precision = 18, scale = 4)
	private double currentFinRatio;

	@Column(name = "accumu_fin_ratio", nullable = false, precision = 18, scale = 4)
	private double accumuFinRatio;

	@Column(name = "current_fin_ratio_exam", nullable = false, precision = 18, scale = 4)
	private double currentFinRatioExam;

	@Column(name = "accumu_fin_ratio_exam", nullable = false, precision = 18, scale = 4)
	private double accumuFinRatioExam;

	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setSiteId(java.lang.String siteId) {
		this.siteId = siteId;
	}
	
	public java.lang.String getParentSiteId() {
		return this.parentSiteId;
	}
	public void setParentSiteId(java.lang.String parentSiteId) {
		this.parentSiteId = parentSiteId;
	}
	
	public java.lang.String getBalanceId() {
		return this.balanceId;
	}
	public void setBalanceId(java.lang.String balanceId) {
		this.balanceId = balanceId;
	}
	
	public java.lang.String getContractId() {
		return this.contractId;
	}
	public void setContractId(java.lang.String contractId) {
		this.contractId = contractId;
	}
	
	public java.lang.String getWbsId() {
		return this.wbsId;
	}
	public void setWbsId(java.lang.String wbsId) {
		this.wbsId = wbsId;
	}
	
	public java.lang.String getWbsName() {
		return this.wbsName;
	}
	public void setWbsName(java.lang.String wbsName) {
		this.wbsName = wbsName;
	}
	
	public java.lang.String getCountUnit() {
		return this.countUnit;
	}
	public void setCountUnit(java.lang.String countUnit) {
		this.countUnit = countUnit;
	}
	
	public java.lang.String getPriceUnit() {
		return this.priceUnit;
	}
	public void setPriceUnit(java.lang.String priceUnit) {
		this.priceUnit = priceUnit;
	}
	
	public double getContractAmount() {
		return this.contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	
	public double getChangeAmount() {
		return this.changeAmount;
	}
	public void setChangeAmount(double changeAmount) {
		this.changeAmount = changeAmount;
	}
	
	public double getAdjustedAmount() {
		return this.adjustedAmount;
	}
	public void setAdjustedAmount(double adjustedAmount) {
		this.adjustedAmount = adjustedAmount;
	}
	
	/**
	 * 合同工程量
	 */
	public double getPriceC() {
		return this.priceC;
	}
	public void setPriceC(double priceC) {
		this.priceC = priceC;
	}
	
	public double getBuyCountC() {
		return this.buyCountC;
	}
	public void setBuyCountC(double buyCountC) {
		this.buyCountC = buyCountC;
	}
	
	/**
	 * 合同内
	 */
	public double getPrice() {
		return this.price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getCurrentCount() {
		return this.currentCount;
	}
	public void setCurrentCount(double currentCount) {
		this.currentCount = currentCount;
	}
	
	public double getCurrentPay() {
		return this.currentPay;
	}
	public void setCurrentPay(double currentPay) {
		this.currentPay = currentPay;
	}
	
	public double getAccumulativeCount() {
		return this.accumulativeCount;
	}
	public void setAccumulativeCount(double accumulativeCount) {
		this.accumulativeCount = accumulativeCount;
	}
	
	public double getAccumulativePay() {
		return this.accumulativePay;
	}
	public void setAccumulativePay(double accumulativePay) {
		this.accumulativePay = accumulativePay;
	}
	
	/**
	 * 合同外（签证）
	 */
	public double getPriceV() {
		return this.priceV;
	}
	public void setPriceV(double priceV) {
		this.priceV = priceV;
	}
	
	public double getCurrentCountV() {
		return this.currentCountV;
	}
	public void setCurrentCountV(double currentCountV) {
		this.currentCountV = currentCountV;
	}
	
	public double getCurrentPayV() {
		return this.currentPayV;
	}
	public void setCurrentPayV(double currentPayV) {
		this.currentPayV = currentPayV;
	}
	
	public double getAccumulativeCountV() {
		return this.accumulativeCountV;
	}
	public void setAccumulativeCountV(double accumulativeCountV) {
		this.accumulativeCountV = accumulativeCountV;
	}
	
	public double getAccumulativePayV() {
		return this.accumulativePayV;
	}
	public void setAccumulativePayV(double accumulativePayV) {
		this.accumulativePayV = accumulativePayV;
	}

	
	/**
	 * 确认审核-合同内
	 */
	public double getPriceExam() {
		return this.priceExam;
	}
	public void setPriceExam(double priceExam) {
		this.priceExam = priceExam;
	}
	
	public double getCurrentCountExam() {
		return this.currentCountExam;
	}
	public void setCurrentCountExam(double currentCountExam) {
		this.currentCountExam = currentCountExam;
	}
	
	public double getCurrentPayExam() {
		return this.currentPayExam;
	}
	public void setCurrentPayExam(double currentPayExam) {
		this.currentPayExam = currentPayExam;
	}
	
	public double getAccumulativeCountExam() {
		return this.accumulativeCountExam;
	}
	public void setAccumulativeCountExam(double accumulativeCountExam) {
		this.accumulativeCountExam = accumulativeCountExam;
	}
	
	public double getAccumulativePayExam() {
		return this.accumulativePayExam;
	}
	public void setAccumulativePayExam(double accumulativePayExam) {
		this.accumulativePayExam = accumulativePayExam;
	}
	
	/**
	 * 合同外（签证）
	 */
	public double getPriceExamV() {
		return this.priceExamV;
	}
	public void setPriceExamV(double priceExamV) {
		this.priceExamV = priceExamV;
	}
	
	public double getCurrentCountExamV() {
		return this.currentCountExamV;
	}
	public void setCurrentCountExamV(double currentCountExamV) {
		this.currentCountExamV = currentCountExamV;
	}
	
	public double getCurrentPayExamV() {
		return this.currentPayExamV;
	}
	public void setCurrentPayExamV(double currentPayExamV) {
		this.currentPayExamV = currentPayExamV;
	}
	
	public double getAccumulativeCountExamV() {
		return this.accumulativeCountExamV;
	}
	public void setAccumulativeCountExamV(double accumulativeCountExamV) {
		this.accumulativeCountExamV = accumulativeCountExamV;
	}
	
	public double getAccumulativePayExamV() {
		return this.accumulativePayExamV;
	}
	public void setAccumulativePayExamV(double accumulativePayExamV) {
		this.accumulativePayExamV = accumulativePayExamV;
	}
	
	public java.lang.String getNotes() {
		return this.notes;
	}
	public void setNotes(java.lang.String notes) {
		this.notes = notes;
	}
	
	public double getPriceV2() {
		return priceV2;
	}

	public void setPriceV2(double priceV2) {
		this.priceV2 = priceV2;
	}

	public double getCurrentCountV2() {
		return currentCountV2;
	}

	public void setCurrentCountV2(double currentCountV2) {
		this.currentCountV2 = currentCountV2;
	}

	public double getCurrentPayV2() {
		return currentPayV2;
	}

	public void setCurrentPayV2(double currentPayV2) {
		this.currentPayV2 = currentPayV2;
	}

	public double getAccumulativeCountV2() {
		return accumulativeCountV2;
	}

	public void setAccumulativeCountV2(double accumulativeCountV2) {
		this.accumulativeCountV2 = accumulativeCountV2;
	}

	public double getAccumulativePayV2() {
		return accumulativePayV2;
	}

	public void setAccumulativePayV2(double accumulativePayV2) {
		this.accumulativePayV2 = accumulativePayV2;
	}

	public double getPriceExamV2() {
		return priceExamV2;
	}

	public void setPriceExamV2(double priceExamV2) {
		this.priceExamV2 = priceExamV2;
	}

	public double getCurrentCountExamV2() {
		return currentCountExamV2;
	}

	public void setCurrentCountExamV2(double currentCountExamV2) {
		this.currentCountExamV2 = currentCountExamV2;
	}

	public double getCurrentPayExamV2() {
		return currentPayExamV2;
	}

	public void setCurrentPayExamV2(double currentPayExamV2) {
		this.currentPayExamV2 = currentPayExamV2;
	}

	public double getAccumulativeCountExamV2() {
		return accumulativeCountExamV2;
	}

	public void setAccumulativeCountExamV2(double accumulativeCountExamV2) {
		this.accumulativeCountExamV2 = accumulativeCountExamV2;
	}

	public double getAccumulativePayExamV2() {
		return accumulativePayExamV2;
	}

	public void setAccumulativePayExamV2(double accumulativePayExamV2) {
		this.accumulativePayExamV2 = accumulativePayExamV2;
	}

	public double getCurrentFinRatio() {
		return currentFinRatio;
	}

	public void setCurrentFinRatio(double currentFinRatio) {
		this.currentFinRatio = currentFinRatio;
	}

	public double getAccumuFinRatio() {
		return accumuFinRatio;
	}

	public void setAccumuFinRatio(double accumuFinRatio) {
		this.accumuFinRatio = accumuFinRatio;
	}

	public double getCurrentFinRatioExam() {
		return currentFinRatioExam;
	}

	public void setCurrentFinRatioExam(double currentFinRatioExam) {
		this.currentFinRatioExam = currentFinRatioExam;
	}

	public double getAccumuFinRatioExam() {
		return accumuFinRatioExam;
	}

	public void setAccumuFinRatioExam(double accumuFinRatioExam) {
		this.accumuFinRatioExam = accumuFinRatioExam;
	}

	/**
	 * 无参构造函数.
	 */
	public BaseContractBalanceConstSite() {
	
	}
	
	/**
	 * 构造函数.
	 * @param siteId
	 */
	public BaseContractBalanceConstSite(String siteId) {
		this.siteId = siteId;
	} 
}
