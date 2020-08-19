package com.supporter.prj.cneec.tpc.credit_letter_collecting.entity.base;
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
public  class BaseCreditLetterCollectingR  implements Serializable {
	private static final long serialVersionUID = 1L; 
	// Fields

	private String recordId;
	private String creditLetterCollectingId;
	private String collectingClause;
	private String collectingClauseCode;
	private String itemId;
	private String itemName;
	private String itemGroupId;
	private String collectingDate;
	private Double collectingAmount;
	private Double collectingAmountAct;
	private String currencyType;
	private String currencyTypeCode;
	 
	// Constructors

	/** default constructor */
	public BaseCreditLetterCollectingR() {
	}
	
	/**
	 *方法: 设置功能模块id.
	 *@param: java.lang.String  功能模块id
	 */
	public BaseCreditLetterCollectingR(String recordId){
		this.recordId = recordId;
	}
	
	/** full constructor */
	public BaseCreditLetterCollectingR(String creditLetterCollectingId,
			String itemId, Double usableAmount, Double useSumAmount,
			int budgetYear, int budgetMonth, String itemGroupId,
			Double collectingAmount, String itemName,
			Double collectingAmountAct) {
		this.creditLetterCollectingId = creditLetterCollectingId;
		this.itemId = itemId;
		this.itemGroupId = itemGroupId;
		this.collectingAmount = collectingAmount;
		this.itemName = itemName;
		this.collectingAmountAct = collectingAmountAct;
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

	@Column(name = "CREDIT_LETTER_COLLECTING_ID", length = 32)
	public String getCreditLetterCollectingId() {
		return this.creditLetterCollectingId;
	}

	public void setCreditLetterCollectingId(String creditLetterCollectingId) {
		this.creditLetterCollectingId = creditLetterCollectingId;
	}

	@Column(name = "ITEM_ID", length = 32)
	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Column(name = "ITEM_GROUP_ID", length = 32)
	public String getItemGroupId() {
		return this.itemGroupId;
	}

	public void setItemGroupId(String itemGroupId) {
		this.itemGroupId = itemGroupId;
	}

	@Column(name = "COLLECTING_AMOUNT", precision = 18, scale = 3)
	public Double getCollectingAmount() {
		return this.collectingAmount;
	}

	public void setCollectingAmount(Double collectingAmount) {
		this.collectingAmount = collectingAmount;
	}

	@Column(name = "ITEM_NAME", length = 1024)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "COLLECTING_AMOUNT_ACT", precision = 18, scale = 3)
	public Double getCollectingAmountAct() {
		return this.collectingAmountAct;
	}

	public void setCollectingAmountAct(Double collectingAmountAct) {
		this.collectingAmountAct = collectingAmountAct;
	}
	
	@Column(name = "CURRENCY_TYPE", length = 32)
	public String getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	@Column(name = "CURRENCY_TYPE_CODE", length = 16)
	public String getCurrencyTypeCode() {
		return this.currencyTypeCode;
	}

	public void setCurrencyTypeCode(String currencyTypeCode) {
		this.currencyTypeCode = currencyTypeCode;
	}

	@Column(name = "COLLECTING_DATE", length = 27)
	public String getCollectingDate() {
		return this.collectingDate;
	}

	public void setCollectingDate(String collectingDate) {
		this.collectingDate = collectingDate;
	}
	
	@Column(name = "COLLECTION_CLAUSE", length = 32)
	public String getCollectingClause() {
		return this.collectingClause;
	}

	public void setCollectingClause(String collectingClause) {
		this.collectingClause = collectingClause;
	}
	@Column(name = "COLLECTION_CLAUSE_CODE", length = 32)
	public String getCollectingClauseCode() {
		return this.collectingClauseCode;
	}

	public void setCollectingClauseCode(String collectingClauseCode) {
		this.collectingClauseCode = collectingClauseCode;
	}
	 
}
