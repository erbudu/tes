package com.supporter.prj.cneec.tpc.collection_confirmation.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

/**
 * @Title: Entity
 * @Description: TPC_COLLECTION_CONFIRMATION,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-11-17
 * @version V1.0
 */
/**
 * @Title: BaseCollectionConfirmation
 * @Description: 
 * @author: yanweichao
 * @date: 2018-7-10
 * @version: V1.0
 */
@MappedSuperclass
public class BaseCollectionConfirmation implements Serializable {

	private static final long serialVersionUID = 1L;
	private String collectionId;
	private String collectionNo;
	private String projectId;
	private String projectName;
	private String collectionBusinessCode;
	private String collectionBusiness;
	private String businessPreposeId;
	private String businessPreposeRecord;
	private double collectionTotalAmount;
	private String prjManagerId;
	private String prjManager;
	private Integer swfStatus;
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;
	private String payaccountId;
	private String companyName;
	private String bank;
	private String bankAccount;
	private boolean isOtherPay;
	private String bankCode;
	private String remitProvince;
	private String remitProvinceId;
	private String remitCity;
	private String remitCityId;
	private int refundStatus;// 退款状态

	/**
	 * 无参构造函数.
	 */
	public BaseCollectionConfirmation() {
	}

	/**
	 * 构造函数.
	 *
	 * @param collectionId
	 */
	public BaseCollectionConfirmation(String collectionId) {
		this.collectionId = collectionId;
	}

	/**
	 * GET方法: 取得COLLECTION_ID.
	 *
	 * @return: String  COLLECTION_ID
	 */
	@Id
	@Column(name = "COLLECTION_ID", nullable = false, length = 32)
	public String getCollectionId() {
		return this.collectionId;
	}

	/**
	 * SET方法: 设置COLLECTION_ID.
	 *
	 * @param: String  COLLECTION_ID
	 */
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

	/**
	 * GET方法: 取得COLLECTION_NO.
	 *
	 * @return: String  COLLECTION_NO
	 */
	@Column(name = "COLLECTION_NO", nullable = true, length = 32)
	public String getCollectionNo() {
		return this.collectionNo;
	}

	/**
	 * SET方法: 设置COLLECTION_NO.
	 *
	 * @param: String  COLLECTION_NO
	 */
	public void setCollectionNo(String collectionNo) {
		this.collectionNo = collectionNo;
	}

	/**
	 * GET方法: 取得PROJECT_ID.
	 *
	 * @return: String  PROJECT_ID
	 */
	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	/**
	 * SET方法: 设置PROJECT_ID.
	 *
	 * @param: String  PROJECT_ID
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * GET方法: 取得PROJECT_NAME.
	 *
	 * @return: String  PROJECT_NAME
	 */
	@Column(name = "PROJECT_NAME", nullable = true, length = 128)
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * SET方法: 设置PROJECT_NAME.
	 *
	 * @param: String  PROJECT_NAME
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * GET方法: 取得COLLECTION_BUSINESS_CODE.
	 *
	 * @return: String  COLLECTION_BUSINESS_CODE
	 */
	@Column(name = "COLLECTION_BUSINESS_CODE", nullable = true, length = 32)
	public String getCollectionBusinessCode() {
		return this.collectionBusinessCode;
	}

	/**
	 * SET方法: 设置COLLECTION_BUSINESS_CODE.
	 *
	 * @param: String  COLLECTION_BUSINESS_CODE
	 */
	public void setCollectionBusinessCode(String collectionBusinessCode) {
		this.collectionBusinessCode = collectionBusinessCode;
	}

	/**
	 * GET方法: 取得COLLECTION_BUSINESS.
	 *
	 * @return: String  COLLECTION_BUSINESS
	 */
	@Column(name = "COLLECTION_BUSINESS", nullable = true, length = 32)
	public String getCollectionBusiness() {
		return this.collectionBusiness;
	}

	/**
	 * SET方法: 设置COLLECTION_BUSINESS.
	 *
	 * @param: String  COLLECTION_BUSINESS
	 */
	public void setCollectionBusiness(String collectionBusiness) {
		this.collectionBusiness = collectionBusiness;
	}

	/**
	 * GET方法: 取得BUSINESS_PREPOSE_ID.
	 *
	 * @return: String  BUSINESS_PREPOSE_ID
	 */
	@Column(name = "BUSINESS_PREPOSE_ID", nullable = true, length = 32)
	public String getBusinessPreposeId() {
		return this.businessPreposeId;
	}

	/**
	 * SET方法: 设置BUSINESS_PREPOSE_ID.
	 *
	 * @param: String  BUSINESS_PREPOSE_ID
	 */
	public void setBusinessPreposeId(String businessPreposeId) {
		this.businessPreposeId = businessPreposeId;
	}

	/**
	 * GET方法: 取得BUSINESS_PREPOSE_RECORD.
	 *
	 * @return: String  BUSINESS_PREPOSE_RECORD
	 */
	@Column(name = "BUSINESS_PREPOSE_RECORD", nullable = true, length = 200)
	public String getBusinessPreposeRecord() {
		return this.businessPreposeRecord;
	}

	/**
	 * SET方法: 设置BUSINESS_PREPOSE_RECORD.
	 *
	 * @param: String  BUSINESS_PREPOSE_RECORD
	 */
	public void setBusinessPreposeRecord(String businessPreposeRecord) {
		this.businessPreposeRecord = businessPreposeRecord;
	}


	/**
	 * GET方法: 取得COLLECTION_TOTAL_AMOUNT.
	 *
	 * @return: double  COLLECTION_TOTAL_AMOUNT
	 */
	@Column(name = "COLLECTION_TOTAL_AMOUNT", nullable = true, precision = 10, scale = 3)
	public double getCollectionTotalAmount() {
		return this.collectionTotalAmount;
	}

	/**
	 * SET方法: 设置COLLECTION_TOTAL_AMOUNT.
	 *
	 * @param: double  COLLECTION_TOTAL_AMOUNT
	 */
	public void setCollectionTotalAmount(double collectionTotalAmount) {
		this.collectionTotalAmount = collectionTotalAmount;
	}

	@Column(name = "PRJ_MANAGER_ID", nullable = true, length = 32)
	public String getPrjManagerId() {
		return this.prjManagerId;
	}

	public void setPrjManagerId(String prjManagerId) {
		this.prjManagerId = prjManagerId;
	}

	@Column(name = "PRJ_MANAGER", nullable = true, length = 32)
	public String getPrjManager() {
		return this.prjManager;
	}

	public void setPrjManager(String prjManager) {
		this.prjManager = prjManager;
	}

	/**
	 * GET方法: 取得SWF_STATUS.
	 *
	 * @return: Integer  SWF_STATUS
	 */
	@Column(name = "SWF_STATUS", nullable = true, precision = 10)
	public Integer getSwfStatus() {
		return this.swfStatus;
	}

	/**
	 * SET方法: 设置SWF_STATUS.
	 *
	 * @param: Integer  SWF_STATUS
	 */
	public void setSwfStatus(Integer swfStatus) {
		this.swfStatus = swfStatus;
	}

	/**
	 * GET方法: 取得DEPT_NAME.
	 *
	 * @return: String  DEPT_NAME
	 */
	@Column(name = "DEPT_NAME", nullable = true, length = 128)
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * SET方法: 设置DEPT_NAME.
	 *
	 * @param: String  DEPT_NAME
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * GET方法: 取得DEPT_ID.
	 *
	 * @return: String  DEPT_ID
	 */
	@Column(name = "DEPT_ID", nullable = true, length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	/**
	 * SET方法: 设置DEPT_ID.
	 *
	 * @param: String  DEPT_ID
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * GET方法: 取得CREATED_BY.
	 *
	 * @return: String  CREATED_BY
	 */
	@Column(name = "CREATED_BY", nullable = true, length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * SET方法: 设置CREATED_BY.
	 *
	 * @param: String  CREATED_BY
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * GET方法: 取得CREATED_BY_ID.
	 *
	 * @return: String  CREATED_BY_ID
	 */
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	/**
	 * SET方法: 设置CREATED_BY_ID.
	 *
	 * @param: String  CREATED_BY_ID
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	/**
	 * GET方法: 取得CREATED_DATE.
	 *
	 * @return: String  CREATED_DATE
	 */
	@Column(name = "CREATED_DATE", nullable = true, length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * SET方法: 设置CREATED_DATE.
	 *
	 * @param: String  CREATED_DATE
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * GET方法: 取得MODIFIED_BY.
	 *
	 * @return: String  MODIFIED_BY
	 */
	@Column(name = "MODIFIED_BY", nullable = true, length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	/**
	 * SET方法: 设置MODIFIED_BY.
	 *
	 * @param: String  MODIFIED_BY
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * GET方法: 取得MODIFIED_BY_ID.
	 *
	 * @return: String  MODIFIED_BY_ID
	 */
	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	/**
	 * SET方法: 设置MODIFIED_BY_ID.
	 *
	 * @param: String  MODIFIED_BY_ID
	 */
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	/**
	 * GET方法: 取得MODIFIED_DATE.
	 *
	 * @return: String  MODIFIED_DATE
	 */
	@Column(name = "MODIFIED_DATE", nullable = true, length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	/**
	 * SET方法: 设置MODIFIED_DATE.
	 *
	 * @param: String  MODIFIED_DATE
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * GET方法: 取得PAYACCOUNT_ID.
	 * 
	 * @return: String PAYACCOUNT_ID
	 */
	@Column(name = "PAYACCOUNT_ID", nullable = true, length = 32)
	public String getPayaccountId() {
		return this.payaccountId;
	}

	/**
	 * SET方法: 设置PAYACCOUNT_ID.
	 * 
	 * @param: String PAYACCOUNT_ID
	 */
	public void setPayaccountId(String PayaccountId) {
		this.payaccountId = PayaccountId;
	}

	/**
	 * GET方法: 取得COMPANY_NAME.
	 * 
	 * @return: String COMPANY_NAME
	 */
	@Column(name = "COMPANY_NAME", nullable = true, length = 128)
	public String getCompanyName() {
		return this.companyName;
	}

	/**
	 * SET方法: 设置COMPANY_NAME.
	 * 
	 * @param: String COMPANY_NAME
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * GET方法: 取得BANK.
	 * 
	 * @return: String BANK
	 */
	@Column(name = "BANK", nullable = true, length = 32)
	public String getBank() {
		return this.bank;
	}

	/**
	 * SET方法: 设置BANK.
	 * 
	 * @param: String BANK
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * GET方法: 取得BANK_ACCOUNT.
	 * 
	 * @return: String BANK_ACCOUNT
	 */
	@Column(name = "BANK_ACCOUNT", nullable = true, length = 128)
	public String getBankAccount() {
		return this.bankAccount;
	}

	/**
	 * SET方法: 设置BANK_ACCOUNT.
	 * 
	 * @param: String BANK_ACCOUNT
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * GET方法: 取得IS_OTHER_PAY.
	 * 
	 * @return: String IS_OTHER_PAY
	 */
	@Column(name = "IS_OTHER_PAY", nullable = true, length = 32)
	@Type(type = "true_false")
	public boolean getIsOtherPay() {
		return this.isOtherPay;
	}

	/**
	 * SET方法: 设置IS_OTHER_PAY.
	 * 
	 * @param: boolean IS_OTHER_PAY
	 */
	public void setIsOtherPay(boolean isOtherPay) {
		this.isOtherPay = isOtherPay;
	}

	@Column(name = "BANK_CODE", nullable = true, length = 32)
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * GET方法: 取得REMIT_PROVINCE.
	 *
	 * @return: String  REMIT_PROVINCE
	 */
	@Column(name = "REMIT_PROVINCE", nullable = true, length = 64)
	public String getRemitProvince() {
		return this.remitProvince;
	}

	/**
	 * SET方法: 设置REMIT_PROVINCE.
	 *
	 * @param: String  REMIT_PROVINCE
	 */
	public void setRemitProvince(String remitProvince) {
		this.remitProvince = remitProvince;
	}

	/**
	 * GET方法: 取得REMIT_PROVINCE_ID.
	 *
	 * @return: String  REMIT_PROVINCE_ID
	 */
	@Column(name = "REMIT_PROVINCE_ID", nullable = true, length = 32)
	public String getRemitProvinceId() {
		return this.remitProvinceId;
	}

	/**
	 * SET方法: 设置REMIT_PROVINCE_ID.
	 *
	 * @param: String  REMIT_PROVINCE_ID
	 */
	public void setRemitProvinceId(String remitProvinceId) {
		this.remitProvinceId = remitProvinceId;
	}

	/**
	 * GET方法: 取得REMIT_CITY.
	 *
	 * @return: String  REMIT_CITY
	 */
	@Column(name = "REMIT_CITY", nullable = true, length = 64)
	public String getRemitCity() {
		return this.remitCity;
	}

	/**
	 * SET方法: 设置REMIT_CITY.
	 *
	 * @param: String  REMIT_CITY
	 */
	public void setRemitCity(String remitCity) {
		this.remitCity = remitCity;
	}

	/**
	 * GET方法: 取得REMIT_CITY_ID.
	 *
	 * @return: String  REMIT_CITY_ID
	 */
	@Column(name = "REMIT_CITY_ID", nullable = true, length = 32)
	public String getRemitCityId() {
		return this.remitCityId;
	}

	/**
	 * SET方法: 设置REMIT_CITY_ID.
	 *
	 * @param: String  REMIT_CITY_ID
	 */
	public void setRemitCityId(String remitCityId) {
		this.remitCityId = remitCityId;
	}

	@Column(name = "REFUND_STATUS", nullable = true, precision = 10)
	public int getRefundStatus() {
		return this.refundStatus;
	}

	public void setRefundStatus(int refundStatus) {
		this.refundStatus = refundStatus;
	}

}
