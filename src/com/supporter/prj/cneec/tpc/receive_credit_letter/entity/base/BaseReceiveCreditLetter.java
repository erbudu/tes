package com.supporter.prj.cneec.tpc.receive_credit_letter.entity.base;
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
public  class BaseReceiveCreditLetter  implements Serializable {
	private static final long serialVersionUID = 1L;
	// Fields

	private String receiveCreditLetterId;
	private String creditLetterId;
	private int swfStatus;
	private String projectId;
	private String projectName;
	private String contractId;
	private String contractName;
	private String contractNo;
	private String creditLetterNo;
	private String creditLetterBank;
	private Double amountCreditLetter;
	private String currencyType;
	private String currencyTypeCode;
	private Double marginRatio;
	private String settlementTerm;
	private String settlementTermCode;
	private String beneficiaryName;
	private String receiveDate;
	
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;
	private String procId;

	// Constructors

	/** default constructor */
	public BaseReceiveCreditLetter() {
	}
	/**
	 *方法: 设置功能模块id.
	 *@param: java.lang.String  功能模块id
	 */
	public BaseReceiveCreditLetter(String receiveCreditLetterId){
		this.receiveCreditLetterId = receiveCreditLetterId;
	}
	/** full constructor */
	public BaseReceiveCreditLetter(String creditLetterId, String auditedById,
			String auditedBy, double amountSettlementAct,
			double amountSettlement, int swfStatus,
			String currencyType, String currencyTypeCode,
			String settlementDate, String paidById, String paidBy,
			String deptName, String deptId, String createdBy,
			String createdById, String createdDate, String modifiedBy,
			String modifiedById, String modifiedDate,
			String receiveCreditLetterDate, String creditSettlementOrderNo,
			int settlementMonth, int settlementYear,
			int creditSettlementIndex, double onWayAmount,
			double onWayAmountF, double realSettlementAmount,
			double realSettlementAmountF, int paymentStatus,
			String controlStatus, String controlStatusCode) {
		this.creditLetterId = creditLetterId;
		this.swfStatus = swfStatus;
		this.currencyType = currencyType;
		this.currencyTypeCode = currencyTypeCode;
		this.deptName = deptName;
		this.deptId = deptId;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
	}

	// Property accessors
	
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "receive_credit_letter_id", unique = true, nullable = false, length = 32)
	public String getReceiveCreditLetterId() {
		return this.receiveCreditLetterId;
	}

	public void setReceiveCreditLetterId(String receiveCreditLetterId) {
		this.receiveCreditLetterId = receiveCreditLetterId;
	}

	@Column(name = "CREDIT_LETTER_ID", length = 32)
	public String getCreditLetterId() {
		return this.creditLetterId;
	}

	public void setCreditLetterId(String creditLetterId) {
		this.creditLetterId = creditLetterId;
	}

	@Column(name = "PROJECT_ID", length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "PROJECT_NAME", length = 128)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "CONTRACT_ID", length = 32)
	public String getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Column(name = "CONTRACT_NAME", length = 128)
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Column(name = "CONTRACT_NO", length = 32)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "CREDIT_LETTER_NO", length = 32)
	public String getCreditLetterNo() {
		return this.creditLetterNo;
	}

	public void setCreditLetterNo(String creditLetterNo) {
		this.creditLetterNo = creditLetterNo;
	}

	@Column(name = "CREDIT_LETTER_BANK", length = 128)
	public String getCreditLetterBank() {
		return this.creditLetterBank;
	}

	public void setCreditLetterBank(String creditLetterBank) {
		this.creditLetterBank = creditLetterBank;
	}

	@Column(name = "AMOUNT_CREDIT_LETTER", precision = 18, scale = 3)
	public Double getAmountCreditLetter() {
		return this.amountCreditLetter;
	}

	public void setAmountCreditLetter(Double amountCreditLetter) {
		this.amountCreditLetter = amountCreditLetter;
	}

	@Column(name = "CURRENCY_TYPE", length = 32)
	public String getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	@Column(name = "CURRENCY_TYPE_CODE", length = 32)
	public String getCurrencyTypeCode() {
		return this.currencyTypeCode;
	}

	public void setCurrencyTypeCode(String currencyTypeCode) {
		this.currencyTypeCode = currencyTypeCode;
	}

	@Column(name = "MARGIN_RATIO", precision = 18, scale = 5)
	public Double getMarginRatio() {
		return this.marginRatio;
	}

	public void setMarginRatio(Double marginRatio) {
		this.marginRatio = marginRatio;
	}

	@Column(name = "SETTLEMENT_TERM", length = 32)
	public String getSettlementTerm() {
		return this.settlementTerm;
	}

	public void setSettlementTerm(String settlementTerm) {
		this.settlementTerm = settlementTerm;
	}

	@Column(name = "SETTLEMENT_TERM_CODE", length = 32)
	public String getSettlementTermCode() {
		return this.settlementTermCode;
	}

	public void setSettlementTermCode(String settlementTermCode) {
		this.settlementTermCode = settlementTermCode;
	}

	@Column(name = "BENEFICIARY_NAME", length = 32)
	public String getBeneficiaryName() {
		return this.beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	
	@Column(name = "RECEIVE_DATE", length = 27)
	public String getReceiveDate() {
		return this.receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	@Column(name = "SWF_STATUS", precision = 2, scale = 0)
	public int getSwfStatus() {
		return this.swfStatus;
	}

	public void setSwfStatus(int swfStatus) {
		this.swfStatus = swfStatus;
	}


	@Column(name = "DEPT_NAME", length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "DEPT_ID", length = 20)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
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


	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
}
