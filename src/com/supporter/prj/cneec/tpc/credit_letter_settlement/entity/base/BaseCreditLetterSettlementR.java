package com.supporter.prj.cneec.tpc.credit_letter_settlement.entity.base;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author liyinfeng
 * @date 2017-11-22 16:25:15
 * @version V1.0   
 *
 */
@MappedSuperclass
public  class BaseCreditLetterSettlementR  implements Serializable {
	private static final long serialVersionUID = 1L; 
	// Fields

	private String recordId;
	private String creditLetterSettlementId;
	private String itemId;
	private Double usableAmount;
	private Double useSumAmount;
	private int budgetYear;
	private int budgetMonth;
	private String itemGroupId;
	private Double settlementAmount;
	private String itemName;
	private Double contractBudgetValue;
	private Double settlementPlanAmount;
	private Double usedSettlementAmount;
	private Double useableSettlementAmount;
	private Double settlementAmountAct;
	 
	// Constructors

	/** default constructor */
	public BaseCreditLetterSettlementR() {
	}
	
	/**
	 *方法: 设置功能模块id.
	 *@param: java.lang.String  功能模块id
	 */
	public BaseCreditLetterSettlementR(String recordId){
		this.recordId = recordId;
	}
	
	/** full constructor */
	public BaseCreditLetterSettlementR(String creditLetterSettlementId,
			String itemId, Double usableAmount, Double useSumAmount,
			int budgetYear, int budgetMonth, String itemGroupId,
			Double settlementAmount, String itemName,
			Double contractBudgetValue, Double settlementPlanAmount,
			Double usedSettlementAmount, Double useableSettlementAmount,
			Double settlementAmountAct) {
		this.creditLetterSettlementId = creditLetterSettlementId;
		this.itemId = itemId;
		this.usableAmount = usableAmount;
		this.useSumAmount = useSumAmount;
		this.budgetYear = budgetYear;
		this.budgetMonth = budgetMonth;
		this.itemGroupId = itemGroupId;
		this.settlementAmount = settlementAmount;
		this.itemName = itemName;
		this.contractBudgetValue = contractBudgetValue;
		this.settlementPlanAmount = settlementPlanAmount;
		this.usedSettlementAmount = usedSettlementAmount;
		this.useableSettlementAmount = useableSettlementAmount;
		this.settlementAmountAct = settlementAmountAct;
	}
	
	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "assigned")
	@Column(name = "RECORD_ID", unique = true, nullable = false, length = 32)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "CREDIT_LETTER_SETTLEMENT_ID", length = 32)
	public String getCreditLetterSettlementId() {
		return this.creditLetterSettlementId;
	}

	public void setCreditLetterSettlementId(String creditLetterSettlementId) {
		this.creditLetterSettlementId = creditLetterSettlementId;
	}

	@Column(name = "ITEM_ID", length = 32)
	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Column(name = "USABLE_AMOUNT", precision = 18, scale = 3)
	public Double getUsableAmount() {
		return this.usableAmount;
	}

	public void setUsableAmount(Double usableAmount) {
		this.usableAmount = usableAmount;
	}

	@Column(name = "USE_SUM_AMOUNT", precision = 18, scale = 3)
	public Double getUseSumAmount() {
		return this.useSumAmount;
	}

	public void setUseSumAmount(Double useSumAmount) {
		this.useSumAmount = useSumAmount;
	}

	@Column(name = "BUDGET_YEAR", precision = 4, scale = 0)
	public int getBudgetYear() {
		return this.budgetYear;
	}

	public void setBudgetYear(int budgetYear) {
		this.budgetYear = budgetYear;
	}

	@Column(name = "BUDGET_MONTH", precision = 2, scale = 0)
	public int getBudgetMonth() {
		return this.budgetMonth;
	}

	public void setBudgetMonth(int budgetMonth) {
		this.budgetMonth = budgetMonth;
	}

	@Column(name = "ITEM_GROUP_ID", length = 32)
	public String getItemGroupId() {
		return this.itemGroupId;
	}

	public void setItemGroupId(String itemGroupId) {
		this.itemGroupId = itemGroupId;
	}

	@Column(name = "SETTLEMENT_AMOUNT", precision = 18, scale = 3)
	public Double getSettlementAmount() {
		return this.settlementAmount;
	}

	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	@Column(name = "ITEM_NAME", length = 1024)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "CONTRACT_BUDGET_VALUE", precision = 18, scale = 3)
	public Double getContractBudgetValue() {
		return this.contractBudgetValue;
	}

	public void setContractBudgetValue(Double contractBudgetValue) {
		this.contractBudgetValue = contractBudgetValue;
	}

	@Column(name = "SETTLEMENT_PLAN_AMOUNT", precision = 18, scale = 3)
	public Double getSettlementPlanAmount() {
		return this.settlementPlanAmount;
	}

	public void setSettlementPlanAmount(Double settlementPlanAmount) {
		this.settlementPlanAmount = settlementPlanAmount;
	}

	@Column(name = "USED_SETTLEMENT_AMOUNT", precision = 18, scale = 3)
	public Double getUsedSettlementAmount() {
		return this.usedSettlementAmount;
	}

	public void setUsedSettlementAmount(Double usedSettlementAmount) {
		this.usedSettlementAmount = usedSettlementAmount;
	}

	@Column(name = "USEABLE_SETTLEMENT_AMOUNT", precision = 18, scale = 3)
	public Double getUseableSettlementAmount() {
		return this.useableSettlementAmount;
	}

	public void setUseableSettlementAmount(Double useableSettlementAmount) {
		this.useableSettlementAmount = useableSettlementAmount;
	}

	@Column(name = "SETTLEMENT_AMOUNT_ACT", precision = 18, scale = 3)
	public Double getSettlementAmountAct() {
		return this.settlementAmountAct;
	}

	public void setSettlementAmountAct(Double settlementAmountAct) {
		this.settlementAmountAct = settlementAmountAct;
	}
	 
}
