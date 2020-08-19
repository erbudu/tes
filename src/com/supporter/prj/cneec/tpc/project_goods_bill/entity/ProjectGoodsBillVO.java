package com.supporter.prj.cneec.tpc.project_goods_bill.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @Title: ProjectGoodsBill
 * @Description: 项目货品清单实体类
 * @author: yanweichao
 * @date: 2017-11-08
 * @version: V1.0
 */
@Entity
public class ProjectGoodsBillVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String billId;
	private String contractId;
	private String contractNo;
	private String contractName;
	private String itemName;
	private String hsCode;
	private String specification;
	private String manufacturer;
	private Double num;
	private String currency;
	private Double amount;
	private Double rmbAmount;
	private Boolean isExistReturn;
	private Boolean isExistChange;
	private Boolean isExistMaintenance;
	private Integer billStatus;

	// Constructors

	/** default constructor */
	public ProjectGoodsBillVO() {
	}

	public ProjectGoodsBillVO(String billId, String contractId, String contractNo, String contractName) {
		this.billId = billId;
		this.contractId = contractId;
		this.contractNo = contractNo;
		this.contractName = contractName;
	}

	@Id
	public String getBillId() {
		return this.billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	@Transient
	public String getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Transient
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Transient
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Transient
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Transient
	public String getHsCode() {
		return this.hsCode;
	}

	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	@Transient
	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Transient
	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Transient
	public Double getNum() {
		return this.num;
	}

	public void setNum(Double num) {
		this.num = num;
	}

	@Transient
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getRmbAmount() {
		return this.rmbAmount;
	}

	public void setRmbAmount(Double rmbAmount) {
		this.rmbAmount = rmbAmount;
	}

	public Boolean isExistReturn() {
		return this.isExistReturn;
	}

	public void setExistReturn(Boolean isExistReturn) {
		this.isExistReturn = isExistReturn;
	}

	public Boolean isExistChange() {
		return this.isExistChange;
	}

	public void setExistChange(Boolean isExistChange) {
		this.isExistChange = isExistChange;
	}

	@Transient
	public Boolean isExistMaintenance() {
		return this.isExistMaintenance;
	}

	public void setExistMaintenance(Boolean isExistMaintenance) {
		this.isExistMaintenance = isExistMaintenance;
	}

	@Transient
	public Integer getBillStatus() {
		return this.billStatus;
	}

	public void setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
	}

	/**
	 * 清除合同信息（合同ID不能清除,关联parent）
	 */
	public void clearContract() {
		this.contractNo = null;
		this.contractName = null;
	}

	// 状态
	@Transient
	public String getBillStatusDesc() {
		if (this.getBillStatus() != null) {
			return ProjectGoodsBill.getBillStatusMap().get(this.getBillStatus());
		}
		return "";
	}

	// 是否存在退货
	@Transient
	public String getExistReturnDesc() {
		if (this.isExistReturn() != null) {
			return this.isExistReturn() ? "是" : "否";
		}
		return "";
	}

	// 是否存在换/补情况
	@Transient
	public String getExistChangeDesc() {
		if (this.isExistChange() != null) {
			return this.isExistChange() ? "是" : "否";
		}
		return "";
	}

	// 是否存在维修情况
	@Transient
	public String getExistMaintenanceDesc() {
		if (this.isExistMaintenance() != null) {
			return this.isExistMaintenance() ? "是" : "否";
		}
		return "";
	}

	/**
	 * 设置一级节点tree属性
	 * @param level
	 * @param isLeaf
	 * @param expanded
	 */
	public void putRootTreeVO(boolean expanded) {
		this.level = 0;
		this.isLeaf = false;
		this.expanded = expanded;
	}

	/**
	 * 树结构相关属性(默认为二级)
	 */
	private int level = 1;
	private String parent;
	private boolean isLeaf = true;
	private boolean expanded = true;// 默认展开

	@Transient
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Transient
	public String getParent() {
		if (this.getIsLeaf() && this.getContractId() != null) {
			parent = this.getContractId();
		}
		return String.valueOf(parent);
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Transient
	public boolean isLeaf() {
		return this.isLeaf;
	}

	@Transient
	public boolean getIsLeaf() {
		return this.isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Transient
	public boolean isExpanded() {
		return this.expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

}
