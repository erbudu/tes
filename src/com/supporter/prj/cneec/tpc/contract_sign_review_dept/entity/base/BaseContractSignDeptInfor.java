package com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

/**
 * @Title: Entity
 * @Description: TPC_CONTRACT_SIGN_INFOR,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2018-03-21
 * @version V1.0
 * 
 */
@MappedSuperclass
public class BaseContractSignDeptInfor implements Serializable {

	private static final long serialVersionUID = 1L;
	private String inforId;
	private String signId;
	private int inforType;// 合同类型（销售/采购）
	private String recordId;// 客户采购需求单中客户包ID
	private String contractParty;// 客户名称/供应商名称
	private String customerId;// 客户名称Id/供应商名称Id
	private String reviewNo;// 合同评审单号
	private double totalRmbAmount;
	private String reviewBasis;
	private String reviewConclusion;
	private String contractName;
	private String noteId;
	private String benefitNo;
	private boolean isChangeBenefit;
	private String purchaseTypeCode;//采购类型code
	private String purchaseType;//采购类型

	/**
	 * 无参构造函数.
	 */
	public BaseContractSignDeptInfor() {
	}

	/**
	 * 构造函数.
	 * 
	 * @param inforId
	 */
	public BaseContractSignDeptInfor(String inforId) {
		this.inforId = inforId;
	}

	/**
	 * GET方法: 取得INFOR_ID.
	 * 
	 * @return: String INFOR_ID
	 */
	@Id
	@Column(name = "INFOR_ID", nullable = false, length = 32)
	public String getInforId() {
		return this.inforId;
	}

	/**
	 * SET方法: 设置INFOR_ID.
	 * 
	 * @param: String INFOR_ID
	 */
	public void setInforId(String inforId) {
		this.inforId = inforId;
	}

	/**
	 * GET方法: 取得SIGN_ID.
	 * 
	 * @return: String SIGN_ID
	 */
	@Column(name = "SIGN_ID", nullable = true, length = 32)
	public String getSignId() {
		return this.signId;
	}

	/**
	 * SET方法: 设置SIGN_ID.
	 * 
	 * @param: String SIGN_ID
	 */
	public void setSignId(String signId) {
		this.signId = signId;
	}

	/**
	 * GET方法: 取得INFOR_TYPE.
	 *
	 * @return: int  INFOR_TYPE
	 */
	@Column(name = "INFOR_TYPE", nullable = true, precision = 10)
	public int getInforType() {
		return this.inforType;
	}

	/**
	 * SET方法: 设置INFOR_TYPE.
	 *
	 * @param: int  INFOR_TYPE
	 */
	public void setInforType(int inforType) {
		this.inforType = inforType;
	}

	@Column(name = "RECORD_ID", nullable = true, length = 512)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * GET方法: 取得CONTRACT_PARTY.
	 * 
	 * @return: String CONTRACT_PARTY
	 */
	@Column(name = "CONTRACT_PARTY", nullable = true, length = 512)
	public String getContractParty() {
		return this.contractParty;
	}

	/**
	 * SET方法: 设置CONTRACT_PARTY.
	 * 
	 * @param: String CONTRACT_PARTY
	 */
	public void setContractParty(String contractParty) {
		this.contractParty = contractParty;
	}

	/**
	 * GET方法: 取得CUSTOMER_ID.
	 * 销售合同中是客户采购需求单中客户ID，采购合同是合同对方（即供应商）ID
	 * @return: String CUSTOMER_ID
	 */
	@Column(name = "CUSTOMER_ID", nullable = true, length = 32)
	public String getCustomerId() {
		return this.customerId;
	}

	/**
	 * SET方法: 设置CUSTOMER_ID.
	 *  销售合同中是客户采购需求单中客户ID，采购合同是合同对方（即供应商）ID
	 * @param: String CUSTOMER_ID
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * GET方法: 取得REVIEW_NO.
	 * 
	 * @return: String REVIEW_NO
	 */
	@Column(name = "REVIEW_NO", nullable = true, length = 32)
	public String getReviewNo() {
		return this.reviewNo;
	}

	/**
	 * SET方法: 设置REVIEW_NO.
	 * 
	 * @param: String REVIEW_NO
	 */
	public void setReviewNo(String reviewNo) {
		this.reviewNo = reviewNo;
	}

	/**
	 * GET方法: 取得TOTAL_RMB_AMOUNT.
	 * 
	 * @return: double TOTAL_RMB_AMOUNT
	 */
	@Column(name = "TOTAL_RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getTotalRmbAmount() {
		return this.totalRmbAmount;
	}

	/**
	 * SET方法: 设置TOTAL_RMB_AMOUNT.
	 * 
	 * @param: double TOTAL_RMB_AMOUNT
	 */
	public void setTotalRmbAmount(double totalRmbAmount) {
		this.totalRmbAmount = totalRmbAmount;
	}

	/**
	 * GET方法: 取得REVIEW_BASIS.
	 * 
	 * @return: String REVIEW_BASIS
	 */
	@Column(name = "REVIEW_BASIS", nullable = true, length = 512)
	public String getReviewBasis() {
		return this.reviewBasis;
	}

	/**
	 * SET方法: 设置REVIEW_BASIS.
	 * 
	 * @param: String REVIEW_BASIS
	 */
	public void setReviewBasis(String reviewBasis) {
		this.reviewBasis = reviewBasis;
	}

	/**
	 * GET方法: 取得REVIEW_CONCLUSION.
	 * 
	 * @return: String REVIEW_CONCLUSION
	 */
	@Column(name = "REVIEW_CONCLUSION", nullable = true, length = 32)
	public String getReviewConclusion() {
		return this.reviewConclusion;
	}

	/**
	 * SET方法: 设置REVIEW_CONCLUSION.
	 * 
	 * @param: String REVIEW_CONCLUSION
	 */
	public void setReviewConclusion(String reviewConclusion) {
		this.reviewConclusion = reviewConclusion;
	}

	@Column(name = "CONTRACT_NAME", nullable = true, length = 128)
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Column(name = "NOTE_ID", nullable = true, length = 32)
	public String getNoteId() {
		return this.noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	@Column(name = "BENEFIT_NO", nullable = true, length = 32)
	public String getBenefitNo() {
		return this.benefitNo;
	}

	public void setBenefitNo(String benefitNo) {
		this.benefitNo = benefitNo;
	}

	@Type(type = "true_false")
	@Column(name = "IS_CHANGE_BENEFIT", nullable = true, length = 1)
	public boolean isChangeBenefit() {
		return isChangeBenefit;
	}

	public void setChangeBenefit(boolean isChangeBenefit) {
		this.isChangeBenefit = isChangeBenefit;
	}
	
	@Column(name = "PURCHASE_TYPE_CODE", nullable = true, length = 64)
	public String getPurchaseTypeCode() {
		return this.purchaseTypeCode;
	}

	public void setPurchaseTypeCode(String purchaseTypeCode) {
		this.purchaseTypeCode = purchaseTypeCode;
	}
	
	@Column(name = "PURCHASE_TYPE", nullable = true, length = 200)
	public String getPurchaseType() {
		return this.purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

}
