package com.supporter.prj.cneec.tpc.use_seal.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

/**   
 * @Title: Entity
 * @Description: TPC_USE_SEAL_DETAIL,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2017-10-23 14:30:25
 * @version V1.0   
 *
 */
@MappedSuperclass
public class BaseUseSealDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	private String detailId;
	private String sealId;
	private String serialNumber;
	private String useSealBusiness;
	private String signId;
	private String reviewNo;
	private String inforId;
	private String contractParty;
	private String contractName;
	private boolean isFiling;
	private String businessNo;
	private int contractCopies;

	/**
	 * 无参构造函数.
	 */
	public BaseUseSealDetail() {
	}

	/**
	 * 构造函数.
	 *
	 * @param detailId
	 */
	public BaseUseSealDetail(String detailId) {
		this.detailId = detailId;
	}

	/**
	 * GET方法: 取得DETAIL_ID.
	 *
	 * @return: String  DETAIL_ID
	 */
	@Id
	@Column(name = "DETAIL_ID", nullable = false, length = 32)
	public String getDetailId() {
		return this.detailId;
	}

	/**
	 * SET方法: 设置DETAIL_ID.
	 *
	 * @param: String  DETAIL_ID
	 */
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	/**
	 * GET方法: 取得SEAL_ID.
	 *
	 * @return: String  SEAL_ID
	 */
	@Column(name = "SEAL_ID", nullable = true, length = 32)
	public String getSealId() {
		return this.sealId;
	}

	/**
	 * SET方法: 设置SEAL_ID.
	 *
	 * @param: String  SEAL_ID
	 */
	public void setSealId(String sealId) {
		this.sealId = sealId;
	}

	/**
	 * GET方法: 取得SERIAL_NUMBER.
	 *
	 * @return: String  SERIAL_NUMBER
	 */
	@Column(name = "SERIAL_NUMBER", nullable = true, length = 32)
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * SET方法: 设置SERIAL_NUMBER.
	 *
	 * @param: String  SERIAL_NUMBER
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * GET方法: 取得USE_SEAL_BUSINESS.
	 *
	 * @return: String  USE_SEAL_BUSINESS
	 */
	@Column(name = "USE_SEAL_BUSINESS", nullable = true, length = 64)
	public String getUseSealBusiness() {
		return useSealBusiness;
	}

	/**
	 * SET方法: 设置USE_SEAL_BUSINESS.
	 *
	 * @param: String  USE_SEAL_BUSINESS
	 */
	public void setUseSealBusiness(String useSealBusiness) {
		this.useSealBusiness = useSealBusiness;
	}

	/**
	 * GET方法: 取得SIGN_ID.
	 *
	 * @return: String  SIGN_ID
	 */
	@Column(name = "SIGN_ID", nullable = true, length = 32)
	public String getSignId() {
		return this.signId;
	}

	/**
	 * SET方法: 设置SIGN_ID.
	 *
	 * @param: String  SIGN_ID
	 */
	public void setSignId(String signId) {
		this.signId = signId;
	}

	/**
	 * GET方法: 取得REVIEW_NO.
	 *
	 * @return: String  REVIEW_NO
	 */
	@Column(name = "REVIEW_NO", nullable = true, length = 32)
	public String getReviewNo() {
		return this.reviewNo;
	}

	/**
	 * SET方法: 设置REVIEW_NO.
	 *
	 * @param: String  REVIEW_NO
	 */
	public void setReviewNo(String reviewNo) {
		this.reviewNo = reviewNo;
	}

	@Column(name = "INFOR_ID", nullable = true, length = 32)
	public String getInforId() {
		return inforId;
	}

	public void setInforId(String inforId) {
		this.inforId = inforId;
	}

	@Column(name = "CONTRACT_PARTY", nullable = true, length = 512)
	public String getContractParty() {
		return this.contractParty;
	}

	public void setContractParty(String contractParty) {
		this.contractParty = contractParty;
	}

	@Column(name = "CONTRACT_NAME", nullable = true, length = 128)
	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	/**
	 * GET方法: 取得IS_FILING.
	 *
	 * @return: String  IS_FILING
	 */
	@Type(type = "true_false")
	@Column(name = "IS_FILING", nullable = true, length = 1)
	public boolean isFiling() {
		return this.isFiling;
	}

	/**
	 * SET方法: 设置IS_FILING.
	 *
	 * @param: String  IS_FILING
	 */
	public void setFiling(boolean isFiling) {
		this.isFiling = isFiling;
	}

	/**
	 * GET方法: 取得BUSINESS_NO.
	 *
	 * @return: String  BUSINESS_NO
	 */
	@Column(name = "BUSINESS_NO", nullable = true, length = 32)
	public String getBusinessNo() {
		return this.businessNo;
	}

	/**
	 * SET方法: 设置BUSINESS_NO.
	 *
	 * @param: String  BUSINESS_NO
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	/**
	 * GET方法: 取得CONTRACT_COPIES.
	 *
	 * @return: int  CONTRACT_COPIES
	 */
	@Column(name = "CONTRACT_COPIES", nullable = true, precision = 10)
	public int getContractCopies() {
		return this.contractCopies;
	}

	/**
	 * SET方法: 设置CONTRACT_COPIES.
	 *
	 * @param: int  CONTRACT_COPIES
	 */
	public void setContractCopies(int contractCopies) {
		this.contractCopies = contractCopies;
	}

}
