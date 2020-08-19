package com.supporter.prj.cneec.tpc.credit_letter_settlement.entity.base;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;



/**   
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author liyinfeng
 * @date 2017-11-22 16:25:15
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseCreditLetterSettlement  implements Serializable {
	private static final long serialVersionUID = 1L;
	// Fields

	private String creditLetterSettlementId;
	private String creditLetterId;
	private String auditedById;
	private String auditedBy;
	private double amountSettlementAct;
	private double amountSettlement;
	private int settlementStatus;
	private String currencyType;
	private String currencyTypeCode;
	private String settlementDate;
	private String paidById;
	private String paidBy;
	private String payerDeptName;
	private String payerDeptId;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;
	private String creditLetterSettlementDate;
	private String creditSettlementOrderNo;
	private int settlementMonth;
	private int settlementYear;
	private int creditSettlementIndex;
	private double onWayAmount;
	private double onWayAmountF;
	private double realSettlementAmount;
	private double realSettlementAmountF;
	private int paymentStatus;
	private String controlStatus;
	private String controlStatusCode;
	private String procId;

	// Constructors

	/** default constructor */
	public BaseCreditLetterSettlement() {
	}
	/**
	 *方法: 设置功能模块id.
	 *@param: java.lang.String  功能模块id
	 */
	public BaseCreditLetterSettlement(String creditLetterSettlementId){
		this.creditLetterSettlementId = creditLetterSettlementId;
	}
	/** full constructor */
	public BaseCreditLetterSettlement(String creditLetterId, String auditedById,
			String auditedBy, double amountSettlementAct,
			double amountSettlement, int settlementStatus,
			String currencyType, String currencyTypeCode,
			String settlementDate, String paidById, String paidBy,
			String payerDeptName, String payerDeptId, String createdBy,
			String createdById, String createdDate, String modifiedBy,
			String modifiedById, String modifiedDate,
			String creditLetterSettlementDate, String creditSettlementOrderNo,
			int settlementMonth, int settlementYear,
			int creditSettlementIndex, double onWayAmount,
			double onWayAmountF, double realSettlementAmount,
			double realSettlementAmountF, int paymentStatus,
			String controlStatus, String controlStatusCode) {
		this.creditLetterId = creditLetterId;
		this.auditedById = auditedById;
		this.auditedBy = auditedBy;
		this.amountSettlementAct = amountSettlementAct;
		this.amountSettlement = amountSettlement;
		this.settlementStatus = settlementStatus;
		this.currencyType = currencyType;
		this.currencyTypeCode = currencyTypeCode;
		this.settlementDate = settlementDate;
		this.paidById = paidById;
		this.paidBy = paidBy;
		this.payerDeptName = payerDeptName;
		this.payerDeptId = payerDeptId;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
		this.creditLetterSettlementDate = creditLetterSettlementDate;
		this.creditSettlementOrderNo = creditSettlementOrderNo;
		this.settlementMonth = settlementMonth;
		this.settlementYear = settlementYear;
		this.creditSettlementIndex = creditSettlementIndex;
		this.onWayAmount = onWayAmount;
		this.onWayAmountF = onWayAmountF;
		this.realSettlementAmount = realSettlementAmount;
		this.realSettlementAmountF = realSettlementAmountF;
		this.paymentStatus = paymentStatus;
		this.controlStatus = controlStatus;
		this.controlStatusCode = controlStatusCode;
	}

	// Property accessors
	
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "credit_letter_settlement_id", unique = true, nullable = false, length = 32)
	public String getCreditLetterSettlementId() {
		return this.creditLetterSettlementId;
	}

	public void setCreditLetterSettlementId(String creditLetterSettlementId) {
		this.creditLetterSettlementId = creditLetterSettlementId;
	}

	@Column(name = "CREDIT_LETTER_ID", length = 32)
	public String getCreditLetterId() {
		return this.creditLetterId;
	}

	public void setCreditLetterId(String creditLetterId) {
		this.creditLetterId = creditLetterId;
	}

	@Column(name = "AUDITED_BY_ID", length = 20)
	public String getAuditedById() {
		return this.auditedById;
	}

	public void setAuditedById(String auditedById) {
		this.auditedById = auditedById;
	}

	@Column(name = "AUDITED_BY", length = 32)
	public String getAuditedBy() {
		return this.auditedBy;
	}

	public void setAuditedBy(String auditedBy) {
		this.auditedBy = auditedBy;
	}

	@Column(name = "AMOUNT_SETTLEMENT_ACT", precision = 18, scale = 3)
	public double getAmountSettlementAct() {
		return this.amountSettlementAct;
	}

	public void setAmountSettlementAct(double amountSettlementAct) {
		this.amountSettlementAct = amountSettlementAct;
	}

	@Column(name = "AMOUNT_SETTLEMENT", precision = 18, scale = 3)
	public double getAmountSettlement() {
		return this.amountSettlement;
	}

	public void setAmountSettlement(double amountSettlement) {
		this.amountSettlement = amountSettlement;
	}

	@Column(name = "SETTLEMENT_STATUS", precision = 2, scale = 0)
	public int getSettlementStatus() {
		return this.settlementStatus;
	}

	public void setSettlementStatus(int settlementStatus) {
		this.settlementStatus = settlementStatus;
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

	@Column(name = "SETTLEMENT_DATE", length = 27)
	public String getSettlementDate() {
		return this.settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	@Column(name = "PAID_BY_ID", length = 20)
	public String getPaidById() {
		return this.paidById;
	}

	public void setPaidById(String paidById) {
		this.paidById = paidById;
	}

	@Column(name = "PAID_BY", length = 64)
	public String getPaidBy() {
		return this.paidBy;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

	@Column(name = "PAYER_DEPT_NAME", length = 128)
	public String getPayerDeptName() {
		return this.payerDeptName;
	}

	public void setPayerDeptName(String payerDeptName) {
		this.payerDeptName = payerDeptName;
	}

	@Column(name = "PAYER_DEPT_ID", length = 20)
	public String getPayerDeptId() {
		return this.payerDeptId;
	}

	public void setPayerDeptId(String payerDeptId) {
		this.payerDeptId = payerDeptId;
	}

	@Column(name = "CREATED_BY", length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_BY_ID", length = 20)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "CREATED_DATE", length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 20)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_BY_ID", length = 20)
	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "MODIFIED_DATE", length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "CREDIT_LETTER_SETTLEMENT_DATE", length = 27)
	public String getCreditLetterSettlementDate() {
		return this.creditLetterSettlementDate;
	}

	public void setCreditLetterSettlementDate(String creditLetterSettlementDate) {
		this.creditLetterSettlementDate = creditLetterSettlementDate;
	}

	@Column(name = "CREDIT_SETTLEMENT_ORDER_NO", length = 64)
	public String getCreditSettlementOrderNo() {
		return this.creditSettlementOrderNo;
	}

	public void setCreditSettlementOrderNo(String creditSettlementOrderNo) {
		this.creditSettlementOrderNo = creditSettlementOrderNo;
	}

	@Column(name = "SETTLEMENT_MONTH", precision = 22, scale = 0)
	public int getSettlementMonth() {
		return this.settlementMonth;
	}

	public void setSettlementMonth(int settlementMonth) {
		this.settlementMonth = settlementMonth;
	}

	@Column(name = "SETTLEMENT_YEAR", precision = 22, scale = 0)
	public int getSettlementYear() {
		return this.settlementYear;
	}

	public void setSettlementYear(int settlementYear) {
		this.settlementYear = settlementYear;
	}

	@Column(name = "CREDIT_SETTLEMENT_INDEX", precision = 22, scale = 0)
	public int getCreditSettlementIndex() {
		return this.creditSettlementIndex;
	}

	public void setCreditSettlementIndex(int creditSettlementIndex) {
		this.creditSettlementIndex = creditSettlementIndex;
	}

	@Column(name = "ON_WAY_AMOUNT", precision = 18, scale = 3)
	public double getOnWayAmount() {
		return this.onWayAmount;
	}

	public void setOnWayAmount(double onWayAmount) {
		this.onWayAmount = onWayAmount;
	}

	@Column(name = "ON_WAY_AMOUNT_F", precision = 18, scale = 3)
	public double getOnWayAmountF() {
		return this.onWayAmountF;
	}

	public void setOnWayAmountF(double onWayAmountF) {
		this.onWayAmountF = onWayAmountF;
	}

	@Column(name = "REAL_SETTLEMENT_AMOUNT", precision = 18, scale = 3)
	public double getRealSettlementAmount() {
		return this.realSettlementAmount;
	}

	public void setRealSettlementAmount(double realSettlementAmount) {
		this.realSettlementAmount = realSettlementAmount;
	}

	@Column(name = "REAL_SETTLEMENT_AMOUNT_F", precision = 18, scale = 3)
	public double getRealSettlementAmountF() {
		return this.realSettlementAmountF;
	}

	public void setRealSettlementAmountF(double realSettlementAmountF) {
		this.realSettlementAmountF = realSettlementAmountF;
	}

	@Column(name = "PAYMENT_STATUS", precision = 2, scale = 0)
	public int getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Column(name = "CONTROL_STATUS", length = 32)
	public String getControlStatus() {
		return this.controlStatus;
	}

	public void setControlStatus(String controlStatus) {
		this.controlStatus = controlStatus;
	}

	@Column(name = "CONTROL_STATUS_CODE", length = 32)
	public String getControlStatusCode() {
		return this.controlStatusCode;
	}

	public void setControlStatusCode(String controlStatusCode) {
		this.controlStatusCode = controlStatusCode;
	}

	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
}
