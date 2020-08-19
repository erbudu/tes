package com.supporter.prj.cneec.tpc.contract_sign_review.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: TPC_CONTRACT_SIGN_ITEM,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2018-04-10
 * @version V1.0
 */
@MappedSuperclass
public class BaseContractSignItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private String itemId;
	private String parentItemId;
	private String parentItemName;
	private String saleReviewId;
	private String purchaseReviewId;
	private int initialReviewType;
	private String saleContractId;
	private String purchaseContractId;
	// 客户采购需求单中货物/服务明细
	private String detailId;
	private String demandId;
	private String recordId;
	private String customerId;
	private String itemName;
	private String specification;
	private String unit;
	private double num;
	private String deliveryTime;
	private String remarks;
	// 按合同上线货物/服务追加字段
	private String hsCode;
	private String country;
	private String countryId;
	private String manufacturer;
	private String currency;
	private String currencyId;
	private double amount;
	private double rmbAmount;
	private double taxRebateRate;

	/**
	 * 无参构造函数.
	 */
	public BaseContractSignItem() {
	}

	/**
	 * 构造函数.
	 *
	 * @param itemId
	 */
	public BaseContractSignItem(String itemId) {
		this.itemId = itemId;
	}

	public BaseContractSignItem(int initialReviewType, String parentItemId, String parentItemName, String saleReviewId, String saleContractId) {
		this.initialReviewType = initialReviewType;
		this.parentItemId = parentItemId;
		this.parentItemName = parentItemName;
		this.saleReviewId = saleReviewId;
		this.saleContractId = saleContractId;
	}

	public BaseContractSignItem(int initialReviewType, String purchaseReviewId, String purchaseContractId) {
		this.initialReviewType = initialReviewType;
		this.purchaseReviewId = purchaseReviewId;
		this.purchaseContractId = purchaseContractId;
	}

	@Id
	@Column(name = "ITEM_ID", nullable = false, length = 32)
	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Column(name = "PARENT_ITEM_ID", nullable = true, length = 32)
	public String getParentItemId() {
		return parentItemId;
	}

	public void setParentItemId(String parentItemId) {
		this.parentItemId = parentItemId;
	}

	@Column(name = "PARENT_ITEM_NAME", nullable = true, length = 128)
	public String getParentItemName() {
		return parentItemName;
	}

	public void setParentItemName(String parentItemName) {
		this.parentItemName = parentItemName;
	}

	@Column(name = "SALE_REVIEW_ID", nullable = true, length = 32)
	public String getSaleReviewId() {
		return this.saleReviewId;
	}

	public void setSaleReviewId(String saleReviewId) {
		this.saleReviewId = saleReviewId;
	}

	@Column(name = "PURCHASE_REVIEW_ID", nullable = true, length = 32)
	public String getPurchaseReviewId() {
		return this.purchaseReviewId;
	}

	public void setPurchaseReviewId(String purchaseReviewId) {
		this.purchaseReviewId = purchaseReviewId;
	}

	@Column(name = "INITIAL_REVIEW_TYPE", nullable = true, precision = 10)
	public int getInitialReviewType() {
		return this.initialReviewType;
	}

	public void setInitialReviewType(int initialReviewType) {
		this.initialReviewType = initialReviewType;
	}

	@Column(name = "SALE_CONTRACT_ID", nullable = true, length = 32)
	public String getSaleContractId() {
		return this.saleContractId;
	}

	public void setSaleContractId(String saleContractId) {
		this.saleContractId = saleContractId;
	}

	@Column(name = "PURCHASE_CONTRACT_ID", nullable = true, length = 32)
	public String getPurchaseContractId() {
		return this.purchaseContractId;
	}

	public void setPurchaseContractId(String purchaseContractId) {
		this.purchaseContractId = purchaseContractId;
	}

	@Column(name = "DETAIL_ID", nullable = true, length = 32)
	public String getDetailId() {
		return this.detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	@Column(name = "DEMAND_ID", nullable = true, length = 32)
	public String getDemandId() {
		return this.demandId;
	}

	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}

	@Column(name = "RECORD_ID", nullable = true, length = 32)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "CUSTOMER_ID", nullable = true, length = 32)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "ITEM_NAME", nullable = true, length = 128)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "SPECIFICATION", nullable = true, length = 64)
	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Column(name = "UNIT", nullable = true, length = 32)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "NUM", nullable = true, precision = 10, scale = 3)
	public double getNum() {
		return this.num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	@Column(name = "DELIVERY_TIME", nullable = true, length = 27)
	public String getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	@Column(name = "REMARKS", nullable = true, length = 512)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "HS_CODE", nullable = true, length = 64)
	public String getHsCode() {
		return hsCode;
	}

	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	@Column(name = "COUNTRY", nullable = true, length = 64)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "COUNTRY_ID", nullable = true, length = 32)
	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	@Column(name = "MANUFACTURER", nullable = true, length = 512)
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Column(name = "CURRENCY", nullable = true, length = 32)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "CURRENCY_ID", nullable = true, length = 32)
	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Column(name = "RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getRmbAmount() {
		return rmbAmount;
	}

	public void setRmbAmount(double rmbAmount) {
		this.rmbAmount = rmbAmount;
	}

	@Column(name = "TAX_REBATE_RATE", nullable = true, precision = 10, scale = 3)
	public double getTaxRebateRate() {
		return taxRebateRate;
	}

	public void setTaxRebateRate(double taxRebateRate) {
		this.taxRebateRate = taxRebateRate;
	}

}
