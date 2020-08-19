package com.supporter.prj.cneec.tpc.contract_sign_review_dept.util;

/**
 * @Title: ContractSignItemBean
 * @Description: 合同货物/服务明细
 * @author: yanweichao
 * @date: 2018-8-17
 * @version: V1.0
 */
public interface ContractSignItemBean {

	public String getItemId();

	public void setItemId(String itemId);

	public String getParentItemId();

	public void setParentItemId(String parentItemId);

	public String getParentItemName();

	public void setParentItemName(String parentItemName);

	public String getSaleReviewId();

	public void setSaleReviewId(String saleReviewId);

	public String getPurchaseReviewId();

	public void setPurchaseReviewId(String purchaseReviewId);

	public int getInitialReviewType();

	public void setInitialReviewType(int initialReviewType);

	public String getSaleContractId();

	public void setSaleContractId(String saleContractId);

	public String getPurchaseContractId();

	public void setPurchaseContractId(String purchaseContractId);

	public String getDetailId();

	public void setDetailId(String detailId);

	public String getDemandId();

	public void setDemandId(String demandId);

	public String getRecordId();

	public void setRecordId(String recordId);

	public String getCustomerId();

	public void setCustomerId(String customerId);

	public String getItemName();

	public void setItemName(String itemName);

	public String getSpecification();

	public void setSpecification(String specification);

	public String getUnit();

	public void setUnit(String unit);

	public double getNum();

	public void setNum(double num);

	public String getDeliveryTime();

	public void setDeliveryTime(String deliveryTime);

	public String getRemarks();

	public void setRemarks(String remarks);

	public String getHsCode();

	public void setHsCode(String hsCode);

	public String getCountry();

	public void setCountry(String country);

	public String getCountryId();

	public void setCountryId(String countryId);

	public String getManufacturer();

	public void setManufacturer(String manufacturer);

	public String getCurrency();

	public void setCurrency(String currency);

	public String getCurrencyId();

	public void setCurrencyId(String currencyId);

	public double getAmount();

	public void setAmount(double amount);

	public double getRmbAmount();

	public void setRmbAmount(double rmbAmount);

	public double getTaxRebateRate();

	public void setTaxRebateRate(double taxRebateRate);

	public boolean isLeaf();

	public void setLeaf(boolean leaf);

	public String getParent();

	public void setParent(String parent);

}
