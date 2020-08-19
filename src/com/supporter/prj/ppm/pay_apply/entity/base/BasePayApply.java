package com.supporter.prj.ppm.pay_apply.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**   
 * @Title: Entity
 * @Description: ppm_pay_apply,字段与数据库字段一一对应.
 * @author: zmq
 * @date: 2019-9-3
 * @version V1.0   
 *
 */
@MappedSuperclass
public class BasePayApply implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id; //主键
	private String prjId; // 项目id
	private String prjNo;		 //项目编码
	private String prjNameC; 	 //项目中文名称
	private String prjNameE; 	//项目英文名称
	private String payNo; 		//付款单号
	private String applyType;   //申请类型
	private String fundUseId;     //资金用途id
	private String fundUse;     //资金用途
	private String agreConId;   //合同及协议id
	private String agreConNo; // 合同及协议编号
	private String agreConName;   //合同及协议名称
	private String payType;     //支付类型
	private double payOriCur;    //支付原币金额  数字
	private String payCurrencyId;   //支付币别id
	private String payCurrency;   //支付币别
	private double rmbAmount;    //折合人民币金额    数字
	private double exchangeRate;         //汇率   数字
	private String payReasons ;     //付款理由
	private String receivingUnit;         // 收款单位
	private String ruOpenB;         //开户行
	private String ruBankAccount;       //开户账号
	private int status;  
	private String procId; // 流程id
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private Date createdDate;
	private String modifiedBy;
	private String modifiedById;
	private Date modifiedDate;
	private String submitter; // 提交人
	private String submitterId; // 提交人ID
	private Date submissionDate; // 提交日期

	/**
	 * 无参构造函数.
	 */
	public BasePayApply() {
	}

	/**
	 * 构造函数.
	 *
	 * @param id
	 */
	public BasePayApply(String id) {
		this.id = id;
	}

	/**
	 * 全参构造
	 */
	public BasePayApply(String id, String prjId, String prjNo, String prjNameC, String prjNameE, String payNo, String applyType, String fundUseId,
			String fundUse, String agreConId, String agreConNo, String agreConName, String payType, double payOriCur, String payCurrencyId,
			String payCurrency, double rmbAmount, double exchangeRate, String payReasons, String receivingUnit, String ruOpenB, String ruBankAccount,
			int status, String procId, String deptName, String deptId, String createdBy, String createdById, Date createdDate, String modifiedBy,
			String modifiedById, Date modifiedDate, String submitter, String submitterId, Date submissionDate) {
		this.id = id;
		this.prjId = prjId;
		this.prjNo = prjNo;
		this.prjNameC = prjNameC;
		this.prjNameE = prjNameE;
		this.payNo = payNo;
		this.applyType = applyType;
		this.fundUseId = fundUseId;
		this.fundUse = fundUse;
		this.agreConId = agreConId;
		this.agreConNo = agreConNo;
		this.agreConName = agreConName;
		this.payType = payType;
		this.payOriCur = payOriCur;
		this.payCurrencyId = payCurrencyId;
		this.payCurrency = payCurrency;
		this.rmbAmount = rmbAmount;
		this.exchangeRate = exchangeRate;
		this.payReasons = payReasons;
		this.receivingUnit = receivingUnit;
		this.ruOpenB = ruOpenB;
		this.ruBankAccount = ruBankAccount;
		this.status = status;
		this.procId = procId;
		this.deptName = deptName;
		this.deptId = deptId;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
		this.submitter = submitter;
		this.submitterId = submitterId;
		this.submissionDate = submissionDate;
	}

//	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
//	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "prj_no", nullable = true, length = 128)
	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}
	@Column(name = "prj_name_C", nullable = true, length = 128)
	public String getPrjNameC() {
		return prjNameC;
	}

	public void setPrjNameC(String prjNameC) {
		this.prjNameC = prjNameC;
	}
	@Column(name = "prj_name_E", nullable = true, length = 128)
	public String getPrjNameE() {
		return prjNameE;
	}

	public void setPrjNameE(String prjNameE) {
		this.prjNameE = prjNameE;
	}
	@Column(name = "pay_no", nullable = true, length = 128)
	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	@Column(name = "apply_type", nullable = true, length = 32)
	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	@Column(name = "fund_use_id", nullable = true, length = 32)
	public String getFundUseId() {
		return fundUseId;
	}

	public void setFundUseId(String fundUseId) {
		this.fundUseId = fundUseId;
	}
	@Column(name = "fund_use", nullable = true, length = 32)
	public String getFundUse() {
		return fundUse;
	}

	public void setFundUse(String fundUse) {
		this.fundUse = fundUse;
	}
	@Column(name = "pay_type", nullable = true, length = 32)
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	@Column(name = "pay_ori_cur", length = 18)
	public double getPayOriCur() {
		return payOriCur;
	}

	public void setPayOriCur(double payOriCur) {
		this.payOriCur = payOriCur;
	}
	@Column(name = "pay_currency_id", nullable = true, length = 32)
	public String getPayCurrencyId() {
		return payCurrencyId;
	}

	public void setPayCurrencyId(String payCurrencyId) {
		this.payCurrencyId = payCurrencyId;
	}
	@Column(name = "pay_currency", nullable = true, length = 32)
	public String getPayCurrency() {
		return payCurrency;
	}

	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}
	@Column(name = "rmb_amount", length = 18)
	public double getRmbAmount() {
		return rmbAmount;
	}

	public void setRmbAmount(double rmbAmount) {
		this.rmbAmount = rmbAmount;
	}
	@Column(name = "exchange_rate", length = 18)
	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	@Column(name = "pay_reasons", nullable = true, length = 512)
	public String getPayReasons() {
		return payReasons;
	}

	public void setPayReasons(String payReasons) {
		this.payReasons = payReasons;
	}
	@Column(name = "receiving_unit", nullable = true, length = 256)
	public String getReceivingUnit() {
		return receivingUnit;
	}

	public void setReceivingUnit(String receivingUnit) {
		this.receivingUnit = receivingUnit;
	}
	@Column(name = "ru_open_B", nullable = true, length = 128)
	public String getRuOpenB() {
		return ruOpenB;
	}

	public void setRuOpenB(String ruOpenB) {
		this.ruOpenB = ruOpenB;
	}
	@Column(name = "ru_bank_account", nullable = true, length = 128)
	public String getRuBankAccount() {
		return ruBankAccount;
	}

	public void setRuBankAccount(String ruBankAccount) {
		this.ruBankAccount = ruBankAccount;
	}
	@Column(name = "dept_name", nullable = true, length = 128)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Column(name = "dept_id", nullable = true, length = 128)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name = "created_by", nullable = true, length = 128)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name = "created_by_id", nullable = true, length = 128)
	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = true, length = 32)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name = "modified_by", nullable = true, length = 128)
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@Column(name = "modified_by_id", nullable = true, length = 128)
	public String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date", nullable = true, length = 32)
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "status", nullable = true, precision = 10)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	@Column(name = "proc_id", length = 32)
	public java.lang.String getProcId() {
		return procId;
	}

	public void setProcId(java.lang.String procId) {
		this.procId = procId;
	}
	@Column(name = "prj_id", nullable = false, length = 32)
	public String getPrjId() {
		return prjId;
	}
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	@Column(name = "agre_con_id", nullable = true, length = 32)
	public String getAgreConId() {
		return agreConId;
	}
	
	public void setAgreConId(String agreConId) {
		this.agreConId = agreConId;
	}

	@Column(name = "agre_con_no", nullable = true, length = 32)
	public String getAgreConNo() {
		return agreConNo;
	}

	public void setAgreConNo(String agreConNo) {
		this.agreConNo = agreConNo;
	}

	@Column(name = "agre_con_name", nullable = true, length = 128)
	public String getAgreConName() {
		return agreConName;
	}

	public void setAgreConName(String agreConName) {
		this.agreConName = agreConName;
	}

	@Column(name = "submitter", nullable = true, length = 64)
	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	@Column(name = "submitter_id", nullable = true, length = 32)
	public String getSubmitterId() {
		return submitterId;
	}

	public void setSubmitterId(String submitterId) {
		this.submitterId = submitterId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "submission_date")
	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
	
}	