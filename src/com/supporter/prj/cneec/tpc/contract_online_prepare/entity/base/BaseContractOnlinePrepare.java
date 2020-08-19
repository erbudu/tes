package com.supporter.prj.cneec.tpc.contract_online_prepare.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: TPC_CONTRACT_ONLINE_PREPARE,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2018-05-15
 * @version V1.0
 */
@MappedSuperclass
public class BaseContractOnlinePrepare implements Serializable {

	private static final long serialVersionUID = 1L;
	private String prepareId;
	private String projectId;
	private String projectName;
	private String stemFrom;
	private String signId;
	private String signReviewNo;
	private String inforId;
	private int inforType;
	private String contractParty;
	private String customerId;
	private String reviewNo;
	private String contractName;
	private double totalRmbAmount;
	private String reviewClassification;
	private Integer sealStatus;
	private Integer filingStatus;
	private Integer onlineStatus;
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String purchaseTypeCode;//采购类型code
	private String purchaseType;//采购类型

	/**
	 * 无参构造函数.
	 */
	public BaseContractOnlinePrepare() {
	}

	/**
	 * 构造函数.
	 *
	 * @param prepareId
	 */
	public BaseContractOnlinePrepare(String prepareId) {
		this.prepareId = prepareId;
	}

	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "PREPARE_ID", nullable = false, length = 32)
	public String getPrepareId() {
		return this.prepareId;
	}

	public void setPrepareId(String prepareId) {
		this.prepareId = prepareId;
	}

	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "PROJECT_NAME", nullable = true, length = 128)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "STEM_FROM", nullable = true, length = 32)
	public String getStemFrom() {
		return this.stemFrom;
	}

	public void setStemFrom(String stemFrom) {
		this.stemFrom = stemFrom;
	}

	@Column(name = "SIGN_ID", nullable = true, length = 32)
	public String getSignId() {
		return this.signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	@Column(name = "SIGN_REVIEW_NO", nullable = true, length = 32)
	public String getSignReviewNo() {
		return this.signReviewNo;
	}

	public void setSignReviewNo(String signReviewNo) {
		this.signReviewNo = signReviewNo;
	}

	@Column(name = "INFOR_ID", nullable = true, length = 32)
	public String getInforId() {
		return this.inforId;
	}

	public void setInforId(String inforId) {
		this.inforId = inforId;
	}

	@Column(name = "INFOR_TYPE", nullable = true, precision = 10)
	public int getInforType() {
		return this.inforType;
	}

	public void setInforType(int inforType) {
		this.inforType = inforType;
	}

	@Column(name = "CONTRACT_PARTY", nullable = true, length = 512)
	public String getContractParty() {
		return this.contractParty;
	}

	public void setContractParty(String contractParty) {
		this.contractParty = contractParty;
	}

	@Column(name = "CUSTOMER_ID", nullable = true, length = 64)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "REVIEW_NO", nullable = true, length = 64)
	public String getReviewNo() {
		return this.reviewNo;
	}

	public void setReviewNo(String reviewNo) {
		this.reviewNo = reviewNo;
	}

	@Column(name = "CONTRACT_NAME", nullable = true, length = 128)
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Column(name = "TOTAL_RMB_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getTotalRmbAmount() {
		return this.totalRmbAmount;
	}

	public void setTotalRmbAmount(double totalRmbAmount) {
		this.totalRmbAmount = totalRmbAmount;
	}

	@Column(name = "REVIEW_CLASSIFICATION", nullable = true, length = 32)
	public String getReviewClassification() {
		return this.reviewClassification;
	}

	public void setReviewClassification(String reviewClassification) {
		this.reviewClassification = reviewClassification;
	}

	@Column(name = "SEAL_STATUS", nullable = true, precision = 10)
	public Integer getSealStatus() {
		return this.sealStatus;
	}

	public void setSealStatus(Integer sealStatus) {
		this.sealStatus = sealStatus;
	}

	@Column(name = "FILING_STATUS", nullable = true, precision = 10)
	public Integer getFilingStatus() {
		return this.filingStatus;
	}

	public void setFilingStatus(Integer filingStatus) {
		this.filingStatus = filingStatus;
	}

	@Column(name = "ONLINE_STATUS", nullable = true, precision = 10)
	public Integer getOnlineStatus() {
		return this.onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	@Column(name = "DEPT_NAME", nullable = true, length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "DEPT_ID", nullable = true, length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "CREATED_BY", nullable = true, length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "CREATED_DATE", nullable = true, length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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
