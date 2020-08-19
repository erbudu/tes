package com.supporter.prj.pm.contract_balance.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**   
 * @Title: 合同结算-施工合同
 * @Description: PM_CONTRACT_BALANCE_CONST, 字段与数据库字段一一对应.
 * @author Administrator
 * @date 2018-07-04 18:07:38
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseContractBalanceConst  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *BALANCE_ID.
	 */
	@Id
	@GeneratedValue(generator = "assigned")
  	@GenericGenerator(name = "assigned",  strategy = "assigned")
	@Column(name = "BALANCE_ID", nullable = false, length = 32)
	private java.lang.String balanceId;
	
	@Column(name = "PRJ_ID", nullable = true, length = 32)
	private java.lang.String prjId;
	
	@Column(name = "PRJ_NAME", nullable = true, length = 128)
	private java.lang.String prjName;
	/**
	 *APPLICATION_NAME.
	 */
	@Column(name = "APPLICATION_NAME", nullable = true, length = 128)
	private java.lang.String applicationName;
	/**
	 *APPLICATION_NO.
	 */
	@Column(name = "APPLICATION_NO", nullable = true, length = 32)
	private java.lang.String applicationNo;
	
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	private java.lang.String contractId;
	@Column(name = "CONTRACT_NAME", nullable = true, length = 128)
	private java.lang.String contractName;
	@Column(name = "CONTRACT_NO", nullable = true, length = 32)
	private java.lang.String contractNo;

	/**
	 *APPLICATION_DATE.
	 */
	@Column(name = "APPLICATION_DATE", nullable = true)
	private java.util.Date applicationDate;
	/**
	 *APPLICATION_AMOUNT.
	 */
	@Column(name = "APPLICATION_AMOUNT", nullable = true, precision = 18, scale = 3)
	private double applicationAmount;
	/**
	 *CONTRACT_TYPE.
	 */
	@Column(name = "CONTRACT_TYPE", nullable = true, precision = 10)
	private int contractType = -1;

	@Column(name = "period", nullable = true, precision = 10)
	private int period = 1;
	@Column(name = "calculation_rate", nullable = true, precision = 18, scale = 4)
	private double calculationRate;
	@Column(name = "period_start_date", nullable = true)
	private java.util.Date periodStartDate;
	@Column(name = "period_end_date", nullable = true)
	private java.util.Date periodEndDate;
	@Column(name = "is_final_balance", nullable = false, length = 1)
	@Type(type = "true_false")
 	private boolean isFinalBalance = false; //是否最终结算
	
	@Column(name = "is_exam_pay", nullable = false, length = 1)
	@Type(type = "true_false")
 	private boolean isExamPay = false; //是否进行付款审核，如果否，则paymentAmount=applicationAmount
	
	/**
	 * 预付款
	 */
	@Column(name = "yfk_rule", nullable = true, length = 2)
	private java.lang.String yfkRule;
	@Column(name = "yfk_ratio", nullable = true, precision = 18, scale = 3)
	private double yfkRatio;
	@Column(name = "yfk_total_amount", nullable = true, precision = 18, scale = 3)
	private double yfkTotalAmount;
	
	
	/**
	 *AGREED_INTERIM.
	 */
	@Column(name = "AGREED_INTERIM", nullable = true, precision = 18, scale = 3)
	private double agreedInterim;
	/**
	 *PAYMENT_AMOUNT.
	 */
	@Column(name = "PAYMENT_AMOUNT", nullable = true, precision = 18, scale = 3)
	private double paymentAmount;
	/**
	 *CUMULATIVE_DEDUCTIONS.
	 */
	@Column(name = "CUMULATIVE_DEDUCTIONS", nullable = true, precision = 18, scale = 3)
	private double cumulativeDeductions;
	
	@Column(name = "STATUS", nullable = false, precision = 10)
	private int status = -1;
	
	/**
	 * 保证金有关字段
	 */
	@Column(name = "is_reserve_product", nullable = false, length = 1)
	@Type(type = "true_false")
 	private boolean isReserveProduct = false; //是否提留安全生成措施费保证金
	
	@Column(name = "is_reserve_peasant", nullable = false, length = 1)
	@Type(type = "true_false")
 	private boolean isReservePeasant = false; //是否提留农民工工资保证金
	
	
	/**
	 *CREATED_BY_ID.
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	private java.lang.String createdById;
	/**
	 *CREATED_BY_NAME.
	 */
	@Column(name = "CREATED_BY_NAME", nullable = true, length = 64)
	private java.lang.String createdByName;
	/**
	 *CREATED_DEPT_ID.
	 */
	@Column(name = "CREATED_DEPT_ID", nullable = true, length = 32)
	private java.lang.String createdDeptId;
	/**
	 *CREATED_DEPT_NAME.
	 */
	@Column(name = "CREATED_DEPT_NAME", nullable = true, length = 200)
	private java.lang.String createdDeptName;
	/**
	 *CREATED_DATE.
	 */
	@Column(name = "CREATED_DATE", nullable = true)
	private java.util.Date createdDate;
	/**
	 *MODIFIED_BY_ID.
	 */
	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	private java.lang.String modifiedById;
	/**
	 *MODIFIED_BY_NAME.
	 */
	@Column(name = "MODIFIED_BY_NAME", nullable = true, length = 64)
	private java.lang.String modifiedByName;
	/**
	 *MODIFIED_DATE.
	 */
	@Column(name = "MODIFIED_DATE", nullable = true)
	private java.util.Date modifiedDate;
	/**
	 *PROC_ID.
	 */
	@Column(name = "proc_id", length = 32)
	private java.lang.String procId;
	
	public java.lang.String getBalanceId() {
		return this.balanceId;
	}
	public void setBalanceId(java.lang.String balanceId) {
		this.balanceId = balanceId;
	}
	
	public java.lang.String getPrjId() {
		return prjId;
	}
	public void setPrjId(java.lang.String prjId) {
		this.prjId = prjId;
	}
	public java.lang.String getPrjName() {
		return prjName;
	}
	public void setPrjName(java.lang.String prjName) {
		this.prjName = prjName;
	}
	public java.lang.String getApplicationName() {
		return this.applicationName;
	}
	public void setApplicationName(java.lang.String applicationName) {
		this.applicationName = applicationName;
	}
	
	public java.lang.String getApplicationNo() {
		return this.applicationNo;
	}
	public void setApplicationNo(java.lang.String applicationNo) {
		this.applicationNo = applicationNo;
	}
	
	public java.lang.String getContractId() {
		return this.contractId;
	}
	public void setContractId(java.lang.String contractId) {
		this.contractId = contractId;
	}
	public java.lang.String getContractName() {
		return this.contractName;
	}
	public void setContractName(java.lang.String contractName) {
		this.contractName = contractName;
	}
	public java.lang.String getContractNo() {
		return this.contractNo;
	}
	public void setContractNo(java.lang.String contractNo) {
		this.contractNo = contractNo;
	}
	
	public java.util.Date getApplicationDate() {
		return this.applicationDate;
	}
	public void setApplicationDate(java.util.Date applicationDate) {
		this.applicationDate = applicationDate;
	}
	
	public double getApplicationAmount() {
		return this.applicationAmount;
	}
	public void setApplicationAmount(double applicationAmount) {
		this.applicationAmount = applicationAmount;
	}
	
	public int getContractType() {
		return this.contractType;
	}
	public void setContractType(int contractType) {
		this.contractType = contractType;
	}
	
	public int getPeriod() {
		return this.period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	
	public double getCalculationRate() {
		return calculationRate;
	}
	public void setCalculationRate(double calculationRate) {
		this.calculationRate = calculationRate;
	}
	public java.util.Date getPeriodStartDate() {
		return this.periodStartDate;
	}
	public void setPeriodStartDate(java.util.Date periodStartDate) {
		this.periodStartDate = periodStartDate;
	}
	
	public java.util.Date getPeriodEndDate() {
		return this.periodEndDate;
	}
	public void setPeriodEndDate(java.util.Date periodEndDate) {
		this.periodEndDate = periodEndDate;
	}
	
	public boolean getIsFinalBalance() {
		return isFinalBalance;
	}
	public void setIsFinalBalance(boolean isFinalBalance) {
		this.isFinalBalance = isFinalBalance;
	}
	
	public boolean getIsExamPay() {
		return isExamPay;
	}
	public void setIsExamPay(boolean isExamPay) {
		this.isExamPay = isExamPay;
	}
	
	
	public double getAgreedInterim() {
		return this.agreedInterim;
	}
	public void setAgreedInterim(double agreedInterim) {
		this.agreedInterim = agreedInterim;
	}
	
	public double getPaymentAmount() {
		return this.paymentAmount;
	}
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	public double getCumulativeDeductions() {
		return this.cumulativeDeductions;
	}
	public void setCumulativeDeductions(double cumulativeDeductions) {
		this.cumulativeDeductions = cumulativeDeductions;
	}
	
	public int getStatus() {
		return this.status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public java.lang.String getCreatedById() {
		return this.createdById;
	}
	public void setCreatedById(java.lang.String createdById) {
		this.createdById = createdById;
	}
	
	public java.lang.String getCreatedByName() {
		return this.createdByName;
	}
	public void setCreatedByName(java.lang.String createdByName) {
		this.createdByName = createdByName;
	}
	
	public java.lang.String getCreatedDeptId() {
		return this.createdDeptId;
	}
	public void setCreatedDeptId(java.lang.String createdDeptId) {
		this.createdDeptId = createdDeptId;
	}
	
	public java.lang.String getCreatedDeptName() {
		return this.createdDeptName;
	}
	public void setCreatedDeptName(java.lang.String createdDeptName) {
		this.createdDeptName = createdDeptName;
	}
	
	public java.util.Date getCreatedDate() {
		return this.createdDate;
	}
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public java.lang.String getModifiedById() {
		return this.modifiedById;
	}
	public void setModifiedById(java.lang.String modifiedById) {
		this.modifiedById = modifiedById;
	}
	
	public java.lang.String getModifiedByName() {
		return this.modifiedByName;
	}
	public void setModifiedByName(java.lang.String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}
	
	public java.util.Date getModifiedDate() {
		return this.modifiedDate;
	}
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
		
	public java.lang.String getProcId() {
		return procId;
	}
	public void setProcId(java.lang.String procId) {
		this.procId = procId;
	}
	
	/**
	 * 预付款
	 */
	public String getYfkRule() {
		return this.yfkRule;
	}
	public void setYfkRule(String yfkRule) {
		this.yfkRule = yfkRule;
	}
	public double getYfkRatio() {
		return this.yfkRatio;
	}
	public void setYfkRatio(double yfkRatio) {
		this.yfkRatio = yfkRatio;
	}
	public double getYfkTotalAmount() {
		return this.yfkTotalAmount;
	}
	public void setYfkTotalAmount(double yfkTotalAmount) {
		this.yfkTotalAmount = yfkTotalAmount;
	}
	
	public boolean getIsReserveProduct() {
		return this.isReserveProduct;
	}
	public void setIsReserveProduct(boolean isReserveProduct) {
		this.isReserveProduct = isReserveProduct;
	}
	
	public boolean getIsReservePeasant() {
		return this.isReservePeasant;
	}
	public void setIsReservePeasant(boolean isReservePeasant) {
		this.isReservePeasant = isReservePeasant;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BaseContractBalanceConst() {
	
	}
	
	/**
	 * 构造函数.
	 * @param balanceId
	 */
	public BaseContractBalanceConst(String balanceId) {
		this.balanceId = balanceId;
	} 
}
