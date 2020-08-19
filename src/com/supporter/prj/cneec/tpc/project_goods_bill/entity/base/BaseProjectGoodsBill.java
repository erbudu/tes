package com.supporter.prj.cneec.tpc.project_goods_bill.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * @Title: Entity
 * @Description: TPC_PROJECT_GOODS_BILL,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-11-08
 * @version V1.0
 */
@MappedSuperclass
public class BaseProjectGoodsBill implements Serializable {

	private static final long serialVersionUID = 1L;
	private String billId;
	private int goodsSource;
	private String projectId;
	private String projectName;
	private String contractId;
	private String contractNo;
	private String contractName;
	private String itemName;
	private String hsCode;
	private String specification;
	private String country;
	private String countryId;
	private String manufacturer;
	private double num;
	private String unit;
	private String currency;
	private String currencyId;
	private double amount;
	private double rmbAmount;
	private double taxRebateRate;
	private boolean isExistReturn;
	private boolean isExistChange;
	private boolean isExistMaintenance;
	private Integer billStatus;
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;

	/**
	 * 无参构造函数.
	 */
	public BaseProjectGoodsBill() {
	}

	/**
	 * 构造函数.
	 *
	 * @param billId
	 */
	public BaseProjectGoodsBill(String billId) {
		this.billId = billId;
	}

	/**
	 * GET方法: 取得BILL_ID.
	 *
	 * @return: String  BILL_ID
	 */
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "BILL_ID", nullable = false, length = 32)
	public String getBillId() {
		return this.billId;
	}

	/**
	 * SET方法: 设置BILL_ID.
	 *
	 * @param: String  BILL_ID
	 */
	public void setBillId(String billId) {
		this.billId = billId;
	}

	/**
	 * GET方法: 取得GOODS_SOURCE.
	 *
	 * @return: String  GOODS_SOURCE
	 */
	@Column(name = "GOODS_SOURCE", nullable = true, precision = 10)
	public int getGoodsSource() {
		return goodsSource;
	}

	/**
	 * SET方法: 设置GOODS_SOURCE.
	 *
	 * @param: String  GOODS_SOURCE
	 */
	public void setGoodsSource(int goodsSource) {
		this.goodsSource = goodsSource;
	}

	/**
	 * GET方法: 取得PROJECT_ID.
	 *
	 * @return: String  PROJECT_ID
	 */
	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	/**
	 * SET方法: 设置PROJECT_ID.
	 *
	 * @param: String  PROJECT_ID
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * GET方法: 取得PROJECT_NAME.
	 *
	 * @return: String  PROJECT_NAME
	 */
	@Column(name = "PROJECT_NAME", nullable = true, length = 128)
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * SET方法: 设置PROJECT_NAME.
	 *
	 * @param: String  PROJECT_NAME
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * GET方法: 取得CONTRACT_ID.
	 *
	 * @return: String  CONTRACT_ID
	 */
	@Column(name = "CONTRACT_ID", nullable = true, length = 32)
	public String getContractId() {
		return this.contractId;
	}

	/**
	 * SET方法: 设置CONTRACT_ID.
	 *
	 * @param: String  CONTRACT_ID
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	/**
	 * GET方法: 取得CONTRACT_NO.
	 *
	 * @return: String  CONTRACT_NO
	 */
	@Column(name = "CONTRACT_NO", nullable = true, length = 64)
	public String getContractNo() {
		return this.contractNo;
	}

	/**
	 * SET方法: 设置CONTRACT_NO.
	 *
	 * @param: String  CONTRACT_NO
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * GET方法: 取得CONTRACT_NAME.
	 *
	 * @return: String  CONTRACT_NAME
	 */
	@Column(name = "CONTRACT_NAME", nullable = true, length = 128)
	public String getContractName() {
		return this.contractName;
	}

	/**
	 * SET方法: 设置CONTRACT_NAME.
	 *
	 * @param: String  CONTRACT_NAME
	 */
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	/**
	 * GET方法: 取得ITEM_NAME.
	 *
	 * @return: String  ITEM_NAME
	 */
	@Column(name = "ITEM_NAME", nullable = true, length = 128)
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * SET方法: 设置ITEM_NAME.
	 *
	 * @param: String  ITEM_NAME
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * GET方法: 取得HS_CODE.
	 *
	 * @return: String  HS_CODE
	 */
	@Column(name = "HS_CODE", nullable = true, length = 64)
	public String getHsCode() {
		return this.hsCode;
	}

	/**
	 * SET方法: 设置HS_CODE.
	 *
	 * @param: String  HS_CODE
	 */
	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	/**
	 * GET方法: 取得SPECIFICATION.
	 *
	 * @return: String  SPECIFICATION
	 */
	@Column(name = "SPECIFICATION", nullable = true, length = 64)
	public String getSpecification() {
		return this.specification;
	}

	/**
	 * SET方法: 设置SPECIFICATION.
	 *
	 * @param: String  SPECIFICATION
	 */
	public void setSpecification(String specification) {
		this.specification = specification;
	}

	/**
	 * GET方法: 取得COUNTRY.
	 *
	 * @return: String  COUNTRY
	 */
	@Column(name = "COUNTRY", nullable = true, length = 64)
	public String getCountry() {
		return this.country;
	}

	/**
	 * SET方法: 设置COUNTRY.
	 *
	 * @param: String  COUNTRY
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * GET方法: 取得COUNTRY_ID.
	 *
	 * @return: String  COUNTRY_ID
	 */
	@Column(name = "COUNTRY_ID", nullable = true, length = 32)
	public String getCountryId() {
		return this.countryId;
	}

	/**
	 * SET方法: 设置COUNTRY_ID.
	 *
	 * @param: String  COUNTRY_ID
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	/**
	 * GET方法: 取得MANUFACTURER.
	 *
	 * @return: String  MANUFACTURER
	 */
	@Column(name = "MANUFACTURER", nullable = true, length = 512)
	public String getManufacturer() {
		return this.manufacturer;
	}

	/**
	 * SET方法: 设置MANUFACTURER.
	 *
	 * @param: String  MANUFACTURER
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * GET方法: 取得NUM.
	 *
	 * @return: double  NUM
	 */
	@Column(name = "NUM", nullable = true, precision = 10, scale = 3)
	public double getNum() {
		return this.num;
	}

	/**
	 * SET方法: 设置NUM.
	 *
	 * @param: double  NUM
	 */
	public void setNum(double num) {
		this.num = num;
	}

	/**
	 * GET方法: 取得UNIT.
	 *
	 * @return: String  UNIT
	 */
	@Column(name = "UNIT", nullable = true, length = 32)
	public String getUnit() {
		return this.unit;
	}

	/**
	 * SET方法: 设置UNIT.
	 *
	 * @param: String  UNIT
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * GET方法: 取得CURRENCY.
	 *
	 * @return: String  CURRENCY
	 */
	@Column(name = "CURRENCY", nullable = true, length = 32)
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * SET方法: 设置CURRENCY.
	 *
	 * @param: String  CURRENCY
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * GET方法: 取得CURRENCY_ID.
	 *
	 * @return: String  CURRENCY_ID
	 */
	@Column(name = "CURRENCY_ID", nullable = true, length = 32)
	public String getCurrencyId() {
		return this.currencyId;
	}

	/**
	 * SET方法: 设置CURRENCY_ID.
	 *
	 * @param: String  CURRENCY_ID
	 */
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	/**
	 * GET方法: 取得AMOUNT.
	 *
	 * @return: double  AMOUNT
	 */
	@Column(name = "AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getAmount() {
		return this.amount;
	}

	/**
	 * SET方法: 设置AMOUNT.
	 *
	 * @param: double  AMOUNT
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * GET方法: 取得RMB_AMOUNT.
	 *
	 * @return: double  RMB_AMOUNT
	 */
	@Column(name = "RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getRmbAmount() {
		return this.rmbAmount;
	}

	/**
	 * SET方法: 设置RMB_AMOUNT.
	 *
	 * @param: double  RMB_AMOUNT
	 */
	public void setRmbAmount(double rmbAmount) {
		this.rmbAmount = rmbAmount;
	}

	/**
	 * GET方法: 取得TAX_REBATE_RATE.
	 *
	 * @return: double  TAX_REBATE_RATE
	 */
	@Column(name = "TAX_REBATE_RATE", nullable = true, precision = 10, scale = 3)
	public double getTaxRebateRate() {
		return this.taxRebateRate;
	}

	/**
	 * SET方法: 设置TAX_REBATE_RATE.
	 *
	 * @param: double  TAX_REBATE_RATE
	 */
	public void setTaxRebateRate(double taxRebateRate) {
		this.taxRebateRate = taxRebateRate;
	}

	/**
	 * GET方法: 取得IS_EXIST_RETURN.
	 *
	 * @return: String  IS_EXIST_RETURN
	 */
	@Type(type = "true_false")
	@Column(name = "IS_EXIST_RETURN", nullable = true, length = 1)
	public boolean isExistReturn() {
		return isExistReturn;
	}

	/**
	 * SET方法: 设置IS_EXIST_RETURN.
	 *
	 * @param: String  IS_EXIST_RETURN
	 */
	public void setExistReturn(boolean isExistReturn) {
		this.isExistReturn = isExistReturn;
	}

	/**
	 * GET方法: 取得IS_EXIST_CHANGE.
	 *
	 * @return: String  IS_EXIST_CHANGE
	 */
	@Type(type = "true_false")
	@Column(name = "IS_EXIST_CHANGE", nullable = true, length = 1)
	public boolean isExistChange() {
		return isExistChange;
	}

	/**
	 * SET方法: 设置IS_EXIST_CHANGE.
	 *
	 * @param: String  IS_EXIST_CHANGE
	 */
	public void setExistChange(boolean isExistChange) {
		this.isExistChange = isExistChange;
	}

	/**
	 * GET方法: 取得IS_EXIST_MAINTENANCE.
	 *
	 * @return: String  IS_EXIST_MAINTENANCE
	 */
	@Type(type = "true_false")
	@Column(name = "IS_EXIST_MAINTENANCE", nullable = true, length = 1)
	public boolean isExistMaintenance() {
		return isExistMaintenance;
	}

	/**
	 * SET方法: 设置IS_EXIST_MAINTENANCE.
	 *
	 * @param: String  IS_EXIST_MAINTENANCE
	 */
	public void setExistMaintenance(boolean isExistMaintenance) {
		this.isExistMaintenance = isExistMaintenance;
	}

	/**
	 * GET方法: 取得BILL_STATUS.
	 *
	 * @return: int  BILL_STATUS
	 */
	@Column(name = "BILL_STATUS", nullable = true, precision = 10)
	public Integer getBillStatus() {
		return this.billStatus;
	}

	/**
	 * SET方法: 设置BILL_STATUS.
	 *
	 * @param: int  BILL_STATUS
	 */
	public void setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
	}

	/**
	 * GET方法: 取得DEPT_NAME.
	 *
	 * @return: String  DEPT_NAME
	 */
	@Column(name = "DEPT_NAME", nullable = true, length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * SET方法: 设置DEPT_NAME.
	 *
	 * @param: String  DEPT_NAME
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * GET方法: 取得DEPT_ID.
	 *
	 * @return: String  DEPT_ID
	 */
	@Column(name = "DEPT_ID", nullable = true, length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	/**
	 * SET方法: 设置DEPT_ID.
	 *
	 * @param: String  DEPT_ID
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * GET方法: 取得CREATED_BY.
	 *
	 * @return: String  CREATED_BY
	 */
	@Column(name = "CREATED_BY", nullable = true, length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * SET方法: 设置CREATED_BY.
	 *
	 * @param: String  CREATED_BY
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * GET方法: 取得CREATED_BY_ID.
	 *
	 * @return: String  CREATED_BY_ID
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	/**
	 * SET方法: 设置CREATED_BY_ID.
	 *
	 * @param: String  CREATED_BY_ID
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	/**
	 * GET方法: 取得CREATED_DATE.
	 *
	 * @return: String  CREATED_DATE
	 */
	@Column(name = "CREATED_DATE", nullable = true, length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * SET方法: 设置CREATED_DATE.
	 *
	 * @param: String  CREATED_DATE
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * GET方法: 取得MODIFIED_BY.
	 *
	 * @return: String  MODIFIED_BY
	 */
	@Column(name = "MODIFIED_BY", nullable = true, length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	/**
	 * SET方法: 设置MODIFIED_BY.
	 *
	 * @param: String  MODIFIED_BY
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * GET方法: 取得MODIFIED_BY_ID.
	 *
	 * @return: String  MODIFIED_BY_ID
	 */
	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	/**
	 * SET方法: 设置MODIFIED_BY_ID.
	 *
	 * @param: String  MODIFIED_BY_ID
	 */
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	/**
	 * GET方法: 取得MODIFIED_DATE.
	 *
	 * @return: String  MODIFIED_DATE
	 */
	@Column(name = "MODIFIED_DATE", nullable = true, length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 * SET方法: 设置MODIFIED_DATE.
	 *
	 * @param: String  MODIFIED_DATE
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
