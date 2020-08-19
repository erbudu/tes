package com.supporter.prj.pm.procure_contract.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**   
 * @Title: 现场采购
 * @Description: PM_PROCURE_CONTRACT, 字段与数据库字段一一对应.
 * @author Administrator
 * @date 2018-07-04 18:04:16
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseProcureContract  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *CONTRACT_ID.
	 */
	@Id
	@GeneratedValue(generator = "assigned")
  	@GenericGenerator(name = "assigned",  strategy = "assigned")
	@Column(name = "CONTRACT_ID", nullable = false, length = 32)
	private java.lang.String contractId;
	/**
	 *CONTRACT_NAME.
	 */
	@Column(name = "CONTRACT_NAME", nullable = true, length = 128)
	private java.lang.String contractName;
	/**
	 *CONTRACT_NO.
	 */
	@Column(name = "CONTRACT_NO", nullable = true, length = 64)
	private java.lang.String contractNo;
	/**
	 *BUY_TYPE.
	 */
	@Column(name = "BUY_TYPE", nullable = true, precision = 10)
	private int buyType;
	/**
	 *BUY_TYPE.
	 */
	@Column(name = "IS_DOUBLE_COIN", nullable = true, precision = 10)
	private int isDoubleCoin;
	/**
	 *CONTRACT_TYPE.
	 */
	@Column(name = "CONTRACT_TYPE", nullable = true, precision = 10)
	private int contractType = -1;

	@Column(name = "CONTRACT_CATEGORY", nullable = true, precision = 10)
	private int contractCategory = 0;

	/**
	 *CONTRACT_LEVEL.
	 */
	@Column(name = "CONTRACT_LEVEL", nullable = true, length = 16)
	private java.lang.String contractLevel;
	/**
	 *PRI_ID.
	 */
	@Column(name = "PRJ_ID", nullable = true, length = 32)
	private java.lang.String prjId;
	/**
	 *PRJ_NAME.
	 */
	@Column(name = "PRJ_NAME", nullable = true, length = 128)
	private java.lang.String prjName;
	/**
	 *PRJ_NAME_SHORT.
	 */
	@Column(name = "PRJ_NAME_SHORT", nullable = true, length = 128)
	private java.lang.String prjNameShort;
	/**
	 *PRJ_NO.
	 */
	@Column(name = "PRJ_NO", nullable = true, length = 32)
	private java.lang.String prjNo;
	/**
	 *PRJ_START_YEAR.
	 */
	@Column(name = "PRJ_START_YEAR", nullable = true, precision = 10)
	private int prjStartYear;
	/**
	 *SUPPLIER_NAME.
	 */
	@Column(name = "SUPPLIER_NAME", nullable = true, length = 128)
	private java.lang.String supplierName;
	/**
	 *SUPPLIER_ADDRESS.
	 */
	@Column(name = "SUPPLIER_ADDRESS", nullable = true, length = 128)
	private java.lang.String supplierAddress;
	/**
	 *ENTRUST_DEPT.
	 */
	@Column(name = "ENTRUST_DEPT", nullable = true, length = 128)
	private java.lang.String entrustDept;
	/**
	 *RELATIVE_CONTRACT_ID.
	 */
	@Column(name = "RELATIVE_CONTRACT_ID", nullable = true, length = 32)
	private java.lang.String relativeContractId;
	/**
	 *ENTRUST_DATE.
	 */
	@Column(name = "ENTRUST_DATE", nullable = true)
	private java.util.Date entrustDate;
	/**
	 *SIGN_DATE.
	 */
	@Column(name = "SIGN_DATE", nullable = true)
	private java.util.Date signDate;
	/**
	 *SUBMIT_DATE.
	 */
	@Column(name = "SUBMIT_DATE", nullable = true)
	private java.util.Date submitDate;
	/**
	 *END_DATE.
	 */
	@Column(name = "END_DATE", nullable = true)
	private java.util.Date endDate;
	/**
	 *SIGN_AMOUNT.
	 */
	@Column(name = "SIGN_AMOUNT", nullable = true, precision = 10, scale = 3)
	private double signAmount;
	/**
	 *SIGN_AMOUNT_TWO.
	 */
	@Column(name = "SIGN_AMOUNT_TWO", nullable = true, precision = 10, scale = 3)
	private double signAmountTwo;
	/**
	 *SIGN_CURRENCY.
	 */
	@Column(name = "SIGN_CURRENCY", nullable = true, length = 32)
	private java.lang.String signCurrency;
	@Column(name = "SIGN_CURRENCY_ID", nullable = true, length = 32)
	private java.lang.String signCurrencyId;
	
	/**
	 *FOREIGN_CURRENCY.
	 */
	@Column(name = "FOREIGN_CURRENCY", nullable = true, length = 32)
	private java.lang.String foreignCurrency;
	@Column(name = "FOREIGN_CURRENCY_ID", nullable = true, length = 32)
	private java.lang.String foreignCurrencyId;
	
	@Column(name = "USD_RATE", nullable = true, precision = 10, scale = 4)
	private double usdRate;
	@Column(name = "USD_AMOUNT", nullable = true, precision = 10, scale = 3)
	private double usdAmount;
	@Column(name = "USD_RATE_TWO", nullable = true, precision = 10, scale = 4)
	private double usdRateTwo;
	@Column(name = "USD_AMOUNT_TWO", nullable = true, precision = 10, scale = 3)
	private double usdAmountTwo;
	@Column(name = "CNY_AMOUNT", nullable = true, precision = 10, scale = 3)
	private double cnyAmount;
	
	/**
	 *REFERENCE_RATE.
	 */
	@Column(name = "REFERENCE_RATE", nullable = true, precision = 10, scale = 4)
	private double referenceRate;
	/**
	 *PAY_OUT.
	 */
	@Column(name = "PAY_OUT", nullable = true, precision = 10, scale = 3)
	private double payOut;
	/**
	 *PAY_IN.
	 */
	@Column(name = "PAY_IN", nullable = true, precision = 10, scale = 3)
	private double payIn;
	/**
	 *PAY_OUT_TWO.
	 */
	@Column(name = "PAY_OUT_TWO", nullable = true, precision = 10, scale = 3)
	private double payOutTwo;
	/**
	 *PAY_IN_TWO.
	 */
	@Column(name = "PAY_IN_TWO", nullable = true, precision = 10, scale = 3)
	private double payInTwo;
	/**
	 *BUDGET_CIVIL.
	 */
	@Column(name = "BUDGET_CIVIL", nullable = true, precision = 10, scale = 3)
	private double budgetCivil;
	/**
	 *BUDGET_INSTALLATION.
	 */
	@Column(name = "BUDGET_INSTALLATION", nullable = true, precision = 10, scale = 3)
	private double budgetInstallation;
	/**
	 *BUDGET_ITEM_ID.
	 */
	@Column(name = "BUDGET_ITEM_ID", nullable = true, length = 32)
	private java.lang.String budgetItemId;
	/**
	 *BUDGET_ITEM.
	 */
	@Column(name = "BUDGET_ITEM", nullable = true, length = 64)
	private java.lang.String budgetItem;
	/**
	 *SUBMIT_CONDITION.
	 */
	@Column(name = "SUBMIT_CONDITION", nullable = true, length = 256)
	private java.lang.String submitCondition;
	/**
	 *STATUS.
	 */
	@Column(name = "STATUS", nullable = true, precision = 10)
	private int status = -1;
	/**
	 *STATUS.
	 */
	@Column(name = "STATUS_SCAN", nullable = true, precision = 10)
	private int statusScan;
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
	@Column(name = "CREATED_DEPT_NAME", nullable = true, length = 128)
	private java.lang.String createdDeptName;
	/**
	 *CREATED_DATE.
	 */
	@Column(name = "CREATED_DATE", nullable = true, length = 32)
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
	@Column(name = "MODIFIED_DATE", nullable = true, length = 32)
	private java.util.Date modifiedDate;
	/**
	 *PROC_ID.
	 */
	@Column(name = "proc_id",length = 32)
	private java.lang.String procId;
	/**
	 * CONTRACT_ID.
	 * @return String
	 */
	
	//模块名称
	@Column(name = "en_name",length = 128)
	private java.lang.String enName;
	
	@Column(name = "is_company_contract", nullable = false, length = 1)
	@Type(type = "true_false")
 	private boolean isCompanyContract = false; //是否公司合同
	
	public java.lang.String getContractId() {
		return this.contractId;
	}

	/**
	 *方法: 设置CONTRACT_ID.
	 * @param contractId  CONTRACT_ID
	 */
	public void setContractId(java.lang.String contractId) {
		this.contractId = contractId;
	}
	/**
	 *方法: 取得CONTRACT_NAME.
	 *@return java.lang.String  CONTRACT_NAME
	 */
	public java.lang.String getContractName() {
		return this.contractName;
	}

	/**
	 *方法: 设置CONTRACT_NAME.
	 *@param contractName  CONTRACT_NAME
	 */
	public void setContractName(java.lang.String contractName) {
		this.contractName = contractName;
	}
	/**
	 *方法: 取得CONTRACT_NO.
	 *@return java.lang.String  CONTRACT_NO
	 */
	public java.lang.String getContractNo() {
		return this.contractNo;
	}

	/**
	 *方法: 设置CONTRACT_NO.
	 *@param contractNo  CONTRACT_NO
	 */
	public void setContractNo(java.lang.String contractNo) {
		this.contractNo = contractNo;
	}
	/**
	 *方法: 取得BUY_TYPE.
	 *@return int  BUY_TYPE
	 */
	public int getBuyType() {
		return this.buyType;
	}

	/**
	 *方法: 设置BUY_TYPE.
	 *@param buyType  BUY_TYPE
	 */
	public void setBuyType(int buyType) {
		this.buyType = buyType;
	}
	/**
	 *方法: 取得IS_DOUBLE_COIN.
	 *@return int  IS_DOUBLE_COIN
	 */
	public int getIsDoubleCoin() {
		return this.isDoubleCoin;
	}

	/**
	 *方法: 设置IS_DOUBLE_COIN.
	 *@param buyType  IS_DOUBLE_COIN
	 */
	public void setIsDoubleCoin(int isDoubleCoin) {
		this.isDoubleCoin = isDoubleCoin;
	}
	/**
	 *方法: 取得CONTRACT_TYPE.
	 *@return int  CONTRACT_TYPE
	 */
	public int getContractType() {
		return this.contractType;
	}

	/**
	 *方法: 设置CONTRACT_TYPE.
	 *@param contractType  CONTRACT_TYPE
	 */
	public void setContractType(int contractType) {
		this.contractType = contractType;
	}

	public int getContractCategory() {
		return this.contractCategory;
	}

	public void setContractCategory(int contractCategory) {
		this.contractCategory = contractCategory;
	}
	/**
	 *方法: 取得CONTRACT_LEVEL.
	 *@return java.lang.String  CONTRACT_LEVEL
	 */
	public java.lang.String getContractLevel() {
		return this.contractLevel;
	}

	/**
	 *方法: 设置CONTRACT_LEVEL.
	 *@param contractLevel  CONTRACT_LEVEL
	 */
	public void setContractLevel(java.lang.String contractLevel) {
		this.contractLevel = contractLevel;
	}
	/**
	 *方法: 取得PRI_ID.
	 *@return java.lang.String  PRI_ID
	 */
	public java.lang.String getPrjId() {
		return this.prjId;
	}

	/**
	 *方法: 设置PRI_ID.
	 *@param priId  PRI_ID
	 */
	public void setPrjId(java.lang.String prjId) {
		this.prjId = prjId;
	}
	/**
	 *方法: 取得PRJ_NAME.
	 *@return java.lang.String  PRJ_NAME
	 */
	public java.lang.String getPrjName() {
		return this.prjName;
	}

	/**
	 *方法: 设置PRJ_NAME.
	 *@param prjName  PRJ_NAME
	 */
	public void setPrjName(java.lang.String prjName) {
		this.prjName = prjName;
	}
	/**
	 *方法: 取得PRJ_NAME_SHORT.
	 *@return java.lang.String  PRJ_NAME_SHORT
	 */
	public java.lang.String getPrjNameShort() {
		return this.prjNameShort;
	}

	/**
	 *方法: 设置PRJ_NAME_SHORT.
	 *@param prjNameShort  PRJ_NAME_SHORT
	 */
	public void setPrjNameShort(java.lang.String prjNameShort) {
		this.prjNameShort = prjNameShort;
	}
	/**
	 *方法: 取得PRJ_NO.
	 *@return java.lang.String  PRJ_NO
	 */
	public java.lang.String getPrjNo() {
		return this.prjNo;
	}

	/**
	 *方法: 设置PRJ_NO.
	 *@param prjNo  PRJ_NO
	 */
	public void setPrjNo(java.lang.String prjNo) {
		this.prjNo = prjNo;
	}
	/**
	 *方法: 取得PRJ_START_YEAR.
	 *@return int  PRJ_START_YEAR
	 */
	public int getPrjStartYear() {
		return this.prjStartYear;
	}

	/**
	 *方法: 设置PRJ_START_YEAR.
	 *@param prjStartYear  PRJ_START_YEAR
	 */
	public void setPrjStartYear(int prjStartYear) {
		this.prjStartYear = prjStartYear;
	}
	/**
	 *方法: 取得SUPPLIER_NAME.
	 *@return java.lang.String  SUPPLIER_NAME
	 */
	public java.lang.String getSupplierName() {
		return this.supplierName;
	}

	/**
	 *方法: 设置SUPPLIER_NAME.
	 *@param supplierName  SUPPLIER_NAME
	 */
	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}
	/**
	 *方法: 取得SUPPLIER_ADDRESS.
	 *@return java.lang.String  SUPPLIER_ADDRESS
	 */
	public java.lang.String getSupplierAddress() {
		return this.supplierAddress;
	}

	/**
	 *方法: 设置SUPPLIER_ADDRESS.
	 *@param supplierAddress  SUPPLIER_ADDRESS
	 */
	public void setSupplierAddress(java.lang.String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}
	/**
	 *方法: 取得ENTRUST_DEPT.
	 *@return java.lang.String  ENTRUST_DEPT
	 */
	public java.lang.String getEntrustDept() {
		return this.entrustDept;
	}

	/**
	 *方法: 设置ENTRUST_DEPT.
	 *@param entrustDept  ENTRUST_DEPT
	 */
	public void setEntrustDept(java.lang.String entrustDept) {
		this.entrustDept = entrustDept;
	}
	/**
	 *方法: 取得RELATIVE_CONTRACT_ID.
	 *@return java.lang.String  RELATIVE_CONTRACT_ID
	 */
	public java.lang.String getRelativeContractId() {
		return this.relativeContractId;
	}

	/**
	 *方法: 设置RELATIVE_CONTRACT_ID.
	 *@param relativeContractId  RELATIVE_CONTRACT_ID
	 */
	public void setRelativeContractId(java.lang.String relativeContractId) {
		this.relativeContractId = relativeContractId;
	}
	/**
	 *方法: 取得ENTRUST_DATE.
	 *@return java.util.Date  ENTRUST_DATE
	 */
	public java.util.Date getEntrustDate() {
		return this.entrustDate;
	}

	/**
	 *方法: 设置ENTRUST_DATE.
	 *@param entrustDate  ENTRUST_DATE
	 */
	public void setEntrustDate(java.util.Date entrustDate) {
		this.entrustDate = entrustDate;
	}
	/**
	 *方法: 取得SIGN_DATE.
	 *@return java.util.Date  SIGN_DATE
	 */
	public java.util.Date getSignDate() {
		return this.signDate;
	}

	/**
	 *方法: 设置SIGN_DATE.
	 *@param signDate  SIGN_DATE
	 */
	public void setSignDate(java.util.Date signDate) {
		this.signDate = signDate;
	}
	/**
	 *方法: 取得SUBMIT_DATE.
	 *@return java.util.Date  SUBMIT_DATE
	 */
	public java.util.Date getSubmitDate() {
		return this.submitDate;
	}

	/**
	 *方法: 设置SUBMIT_DATE.
	 *@param submitDate  SUBMIT_DATE
	 */
	public void setSubmitDate(java.util.Date submitDate) {
		this.submitDate = submitDate;
	}
	/**
	 *方法: 取得END_DATE.
	 *@return java.util.Date  END_DATE
	 */
	public java.util.Date getEndDate() {
		return this.endDate;
	}

	/**
	 *方法: 设置END_DATE.
	 *@param endDate  END_DATE
	 */
	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}
	/**
	 *方法: 取得SIGN_AMOUNT.
	 *@return double  SIGN_AMOUNT
	 */
	public double getSignAmount() {
		return this.signAmount;
	}

	/**
	 *方法: 设置SIGN_AMOUNT.
	 *@param signAmount  SIGN_AMOUNT
	 */
	public void setSignAmount(double signAmount) {
		this.signAmount = signAmount;
	}
	/**
	 *方法: 取得SIGN_AMOUNT_TWO.
	 *@return double  SIGN_AMOUNT_TWO
	 */
	public double getSignAmountTwo() {
		return this.signAmountTwo;
	}

	/**
	 *方法: 设置SIGN_AMOUNT_TWO.
	 *@param signAmount  SIGN_AMOUNT_TWO
	 */
	public void setSignAmountTwo(double signAmountTwo) {
		this.signAmountTwo = signAmountTwo;
	}
	/**
	 *方法: 取得SIGN_CURRENCY.
	 *@return java.lang.String  SIGN_CURRENCY
	 */
	public java.lang.String getSignCurrency() {
		return this.signCurrency;
	}
	public void setSignCurrency(java.lang.String signCurrency) {
		this.signCurrency = signCurrency;
	}
	public java.lang.String getSignCurrencyId() {
		return this.signCurrencyId;
	}
	public void setSignCurrencyId(java.lang.String signCurrencyId) {
		this.signCurrencyId = signCurrencyId;
	}
	
	/**
	 *方法: 取得FOREIGN_CURRENCY.
	 *@return java.lang.String  FOREIGN_CURRENCY
	 */
	public java.lang.String getForeignCurrency() {
		return this.foreignCurrency;
	}
	public void setForeignCurrency(java.lang.String foreignCurrency) {
		this.foreignCurrency = foreignCurrency;
	}
	public java.lang.String getForeignCurrencyId() {
		return this.foreignCurrencyId;
	}
	public void setForeignCurrencyId(java.lang.String foreignCurrencyId) {
		this.foreignCurrencyId = foreignCurrencyId;
	}
	
	/**
	 *方法: 取得USD_RATE.
	 *@return double  USD_RATE
	 */
	public double getUsdRate() {
		return this.usdRate;
	}

	/**
	 *方法: 设置USD_RATE.
	 *@param signAmount  USD_RATE
	 */
	public void setUsdRate(double usdRate) {
		this.usdRate = usdRate;
	}
	
	/**
	 *方法: 取得USD_AMOUNT.
	 *@return double  USD_AMOUNT
	 */
	public double getUsdAmount() {
		return this.usdAmount;
	}

	/**
	 *方法: 设置USD_AMOUNT.
	 *@param signAmount  USD_AMOUNT
	 */
	public void setUsdAmount(double usdAmount) {
		this.usdAmount = usdAmount;
	}
	
	/**
	 *方法: 取得USD_RATE_TWO.
	 *@return double  USD_RATE_TWO
	 */
	public double getUsdRateTwo() {
		return this.usdRateTwo;
	}

	/**
	 *方法: 设置USD_RATE_TWO.
	 *@param signAmount  USD_RATE_TWO
	 */
	public void setUsdRateTwo(double usdRateTwo) {
		this.usdRateTwo = usdRateTwo;
	}
	
	/**
	 *方法: 取得USD_AMOUNT_TWO.
	 *@return double  USD_AMOUNT_TWO
	 */
	public double getUsdAmountTwo() {
		return this.usdAmountTwo;
	}

	/**
	 *方法: 设置USD_AMOUNT_TWO.
	 *@param signAmount  USD_AMOUNT_TWO
	 */
	public void setUsdAmountTwo(double usdAmountTwo) {
		this.usdAmountTwo = usdAmountTwo;
	}
	
	public double getCnyAmount() {
		return this.cnyAmount;
	}

	public void setCnyAmount(double cnyAmount) {
		this.cnyAmount = cnyAmount;
	}
	
	/**
	 *方法: 取得REFERENCE_RATE.
	 *@return double  REFERENCE_RATE
	 */
	public double getReferenceRate() {
		return this.referenceRate;
	}

	/**
	 *方法: 设置REFERENCE_RATE.
	 *@param referenceRate  REFERENCE_RATE
	 */
	public void setReferenceRate(double referenceRate) {
		this.referenceRate = referenceRate;
	}
	/**
	 *方法: 取得PAY_OUT.
	 *@return double  PAY_OUT
	 */
	public double getPayOut() {
		return this.payOut;
	}

	/**
	 *方法: 设置PAY_OUT.
	 *@param payOut  PAY_OUT
	 */
	public void setPayOut(double payOut) {
		this.payOut = payOut;
	}
	/**
	 *方法: 取得PAY_IN.
	 *@return double  PAY_IN
	 */
	public double getPayIn() {
		return this.payIn;
	}

	/**
	 *方法: 设置PAY_IN.
	 *@param payIn  PAY_IN
	 */
	public void setPayIn(double payIn) {
		this.payIn = payIn;
	}
	
	/**
	 *方法: 取得PAY_OUT_TWO.
	 *@return double  PAY_OUT_TWO
	 */
	public double getPayOutTwo() {
		return this.payOutTwo;
	}

	/**
	 *方法: 设置PAY_OUT_TWO.
	 *@param payOut  PAY_OUT_TWO
	 */
	public void setPayOutTwo(double payOutTwo) {
		this.payOutTwo = payOutTwo;
	}
	/**
	 *方法: 取得PAY_IN_TWO.
	 *@return double  PAY_IN_TWO
	 */
	public double getPayInTwo() {
		return this.payInTwo;
	}

	/**
	 *方法: 设置PAY_IN_TWO.
	 *@param payIn  PAY_IN_TWO
	 */
	public void setPayInTwo(double payInTwo) {
		this.payInTwo = payInTwo;
	}
	
	/**
	 *方法: 取得BUDGET_CIVIL.
	 *@return double  BUDGET_CIVIL
	 */
	public double getBudgetCivil() {
		return this.budgetCivil;
	}

	/**
	 *方法: 设置BUDGET_CIVIL.
	 *@param budgetCivil  BUDGET_CIVIL
	 */
	public void setBudgetCivil(double budgetCivil) {
		this.budgetCivil = budgetCivil;
	}
	/**
	 *方法: 取得BUDGET_INSTALLATION.
	 *@return double  BUDGET_INSTALLATION
	 */
	public double getBudgetInstallation() {
		return this.budgetInstallation;
	}

	/**
	 *方法: 设置BUDGET_INSTALLATION.
	 *@param budgetInstallation  BUDGET_INSTALLATION
	 */
	public void setBudgetInstallation(double budgetInstallation) {
		this.budgetInstallation = budgetInstallation;
	}
	/**
	 *方法: 取得BUDGET_ITEM_ID.
	 *@return java.lang.String  BUDGET_ITEM_ID
	 */
	public java.lang.String getBudgetItemId() {
		return this.budgetItemId;
	}

	/**
	 *方法: 设置BUDGET_ITEM_ID.
	 *@param budgetItem  BUDGET_ITEM_ID
	 */
	public void setBudgetItemId(java.lang.String budgetItemId) {
		this.budgetItemId = budgetItemId;
	}
	/**
	 *方法: 取得BUDGET_ITEM.
	 *@return java.lang.String  BUDGET_ITEM
	 */
	public java.lang.String getBudgetItem() {
		return this.budgetItem;
	}

	/**
	 *方法: 设置BUDGET_ITEM.
	 *@param budgetItem  BUDGET_ITEM
	 */
	public void setBudgetItem(java.lang.String budgetItem) {
		this.budgetItem = budgetItem;
	}
	/**
	 *方法: 取得SUBMIT_CONDITION.
	 *@return java.lang.String  SUBMIT_CONDITION
	 */
	public java.lang.String getSubmitCondition() {
		return this.submitCondition;
	}

	/**
	 *方法: 设置SUBMIT_CONDITION.
	 *@param submitCondition  SUBMIT_CONDITION
	 */
	public void setSubmitCondition(java.lang.String submitCondition) {
		this.submitCondition = submitCondition;
	}
	/**
	 *方法: 取得STATUS.
	 *@return int  STATUS
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 *方法: 设置STATUS.
	 *@param status  STATUS
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column(name = "status_scan")
	public int getStatusScan() {
		return this.statusScan;
	}

	public void setStatusScan(int statusScan) {
		this.statusScan = statusScan;
	}
	
	
	
	/**
	 *方法: 取得CREATED_BY_ID.
	 *@return java.lang.String  CREATED_BY_ID
	 */
	public java.lang.String getCreatedById() {
		return this.createdById;
	}

	/**
	 *方法: 设置CREATED_BY_ID.
	 *@param createdById  CREATED_BY_ID
	 */
	public void setCreatedById(java.lang.String createdById) {
		this.createdById = createdById;
	}
	/**
	 *方法: 取得CREATED_BY_NAME.
	 *@return java.lang.String  CREATED_BY_NAME
	 */
	public java.lang.String getCreatedByName() {
		return this.createdByName;
	}

	/**
	 *方法: 设置CREATED_BY_NAME.
	 *@param createdByName  CREATED_BY_NAME
	 */
	public void setCreatedByName(java.lang.String createdByName) {
		this.createdByName = createdByName;
	}
	/**
	 *方法: 取得CREATED_DEPT_ID.
	 *@return java.lang.String  CREATED_DEPT_ID
	 */
	public java.lang.String getCreatedDeptId() {
		return this.createdDeptId;
	}

	/**
	 *方法: 设置CREATED_DEPT_ID.
	 *@param createdDeptId  CREATED_DEPT_ID
	 */
	public void setCreatedDeptId(java.lang.String createdDeptId) {
		this.createdDeptId = createdDeptId;
	}
	/**
	 *方法: 取得CREATED_DEPT_NAME.
	 *@return java.lang.String  CREATED_DEPT_NAME
	 */
	public java.lang.String getCreatedDeptName() {
		return this.createdDeptName;
	}

	/**
	 *方法: 设置CREATED_DEPT_NAME.
	 *@param createdDeptName  CREATED_DEPT_NAME
	 */
	public void setCreatedDeptName(java.lang.String createdDeptName) {
		this.createdDeptName = createdDeptName;
	}
	/**
	 *方法: 取得CREATED_DATE.
	 *@return java.lang.String  CREATED_DATE
	 */
	public java.util.Date getCreatedDate() {
		return this.createdDate;
	}

	/**
	 *方法: 设置CREATED_DATE.
	 *@param createdDate  CREATED_DATE
	 */
	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 *方法: 取得MODIFIED_BY_ID.
	 *@return java.lang.String  MODIFIED_BY_ID
	 */
	public java.lang.String getModifiedById() {
		return this.modifiedById;
	}

	/**
	 *方法: 设置MODIFIED_BY_ID.
	 *@param modifiedById  MODIFIED_BY_ID
	 */
	public void setModifiedById(java.lang.String modifiedById) {
		this.modifiedById = modifiedById;
	}
	/**
	 *方法: 取得MODIFIED_BY_NAME.
	 *@return java.lang.String  MODIFIED_BY_NAME
	 */
	public java.lang.String getModifiedByName() {
		return this.modifiedByName;
	}

	/**
	 *方法: 设置MODIFIED_BY_NAME.
	 *@param modifiedByName  MODIFIED_BY_NAME
	 */
	public void setModifiedByName(java.lang.String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}
	/**
	 *方法: 取得MODIFIED_DATE.
	 *@return java.lang.String  MODIFIED_DATE
	 */
	public java.util.Date getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 *方法: 设置MODIFIED_DATE.
	 * @param modifiedDate  MODIFIED_DATE
	 */
	public void setModifiedDate(java.util.Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public java.lang.String getProcId() {
		return procId;
	}

	public void setProcId(java.lang.String procId) {
		this.procId = procId;
	}
	
	public java.lang.String getEnName() {
		return enName;
	}

	public void setEnName(java.lang.String enName) {
		this.enName = enName;
	}

	public boolean isCompanyContract() {
		return isCompanyContract;
	}

	public void setCompanyContract(boolean isCompanyContract) {
		this.isCompanyContract = isCompanyContract;
	}

	/**
	 * 无参构造函数.
	 */
	public BaseProcureContract() {
	
	}
	
	/**
	 * 构造函数.
	 * @param contractId
	 */
	public BaseProcureContract(String contractId) {
		this.contractId = contractId;
	} 
}
