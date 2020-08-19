package com.supporter.prj.cneec.tpc.credit_letter_collecting.entity.base;
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
public  class BaseCreditLetterCollecting  implements Serializable {
	private static final long serialVersionUID = 1L;
	// Fields

	private String creditLetterCollectingId;
	private String creditLetterId;
	private String projectId;
	private String projectName;
	private String contractId;
	private String contractName;
	private String contractNo;
	private String creditLetterNo;
	private Double amountCollecting;
	private Double amountCollectingAct;
	
	private int swfStatus;
	private String currencyType;
	private String currencyTypeCode;
	private String collectingDate;
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;
	private String creditCollectingOrderNo;
	private int creditCollectingIndex;
	private String procId;

	// Constructors

	/** default constructor */
	public BaseCreditLetterCollecting() {
	}
	/**
	 *方法: 设置功能模块id.
	 *@param: java.lang.String  功能模块id
	 */
	public BaseCreditLetterCollecting(String creditLetterCollectingId){
		this.creditLetterCollectingId = creditLetterCollectingId;
	}
	/** full constructor */
	public BaseCreditLetterCollecting(String creditLetterId, String auditedById,
			String auditedBy, double amountCollectingAct,
			double amountCollecting, int swfStatus,
			String currencyType, String currencyTypeCode,
			String collectingDate, String paidById, String paidBy,
			String deptName, String deptId, String createdBy,
			String createdById, String createdDate, String modifiedBy,
			String modifiedById, String modifiedDate,
			String creditCollectingOrderNo,
			int settlementMonth, int settlementYear,
			int creditCollectingIndex) {
		this.creditLetterId = creditLetterId;
		this.swfStatus = swfStatus;
		this.currencyType = currencyType;
		this.currencyTypeCode = currencyTypeCode;
		this.collectingDate = collectingDate;
		this.deptName = deptName;
		this.deptId = deptId;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
		this.creditCollectingOrderNo = creditCollectingOrderNo;
		this.creditCollectingIndex = creditCollectingIndex;
	}

	// Property accessors
	
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "CREDIT_LETTER_COLLECTING_ID", unique = true, nullable = false, length = 32)
	public String getCreditLetterCollectingId() {
		return this.creditLetterCollectingId;
	}

	public void setCreditLetterCollectingId(String creditLetterCollectingId) {
		this.creditLetterCollectingId = creditLetterCollectingId;
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
	
	@Column(name = "SWF_STATUS", precision = 2, scale = 0)
	public int getSwfStatus() {
		return this.swfStatus;
	}

	public void setSwfStatus(int swfStatus) {
		this.swfStatus = swfStatus;
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

	@Column(name = "CREDIT_COLLECTING_ORDER_NO", length = 64)
	public String getCreditCollectingOrderNo() {
		return this.creditCollectingOrderNo;
	}

	public void setCreditCollectingOrderNo(String creditCollectingOrderNo) {
		this.creditCollectingOrderNo = creditCollectingOrderNo;
	}


	@Column(name = "CREDIT_COLLECTING_INDEX", precision = 22, scale = 0)
	public int getCreditCollectingIndex() {
		return this.creditCollectingIndex;
	}

	public void setCreditCollectingIndex(int creditCollectingIndex) {
		this.creditCollectingIndex = creditCollectingIndex;
	}
	
	@Column(name = "AMOUNT_COLLECTING", precision = 18, scale = 3)
	public Double getAmountCollecting() {
		return this.amountCollecting;
	}

	public void setAmountCollecting(Double amountCollecting) {
		this.amountCollecting = amountCollecting;
	}
	
	@Column(name = "AMOUNT_COLLECTING_ACT", precision = 18, scale = 3)
	public Double getAmountCollectingAct() {
		return this.amountCollectingAct;
	}

	public void setAmountCollectingAct(Double amountCollectingAct) {
		this.amountCollectingAct = amountCollectingAct;
	}

	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
}
