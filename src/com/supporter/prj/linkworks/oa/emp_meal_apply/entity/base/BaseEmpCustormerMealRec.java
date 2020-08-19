package com.supporter.prj.linkworks.oa.emp_meal_apply.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * OaEmpCustormerMealRec entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseEmpCustormerMealRec implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String recId;
	private String applyId;
	private String custormerName;
	private String cardNo;
	private String empType;
	private Double mealAmount;
	private String mealDesc;
	private Long displayOrder;
	private String custormerId;

	// Constructors

	/** default constructor */
	public BaseEmpCustormerMealRec() {
	}

	/** minimal constructor */
	public BaseEmpCustormerMealRec(String recId) {
		this.recId = recId;
	}

	/** full constructor */
	public BaseEmpCustormerMealRec(String recId, String applyId,
			String custormerName, String cardNo, String empType,
			Double mealAmount, String mealDesc, Long displayOrder,
			String custormerId) {
		this.recId = recId;
		this.applyId = applyId;
		this.custormerName = custormerName;
		this.cardNo = cardNo;
		this.empType = empType;
		this.mealAmount = mealAmount;
		this.mealDesc = mealDesc;
		this.displayOrder = displayOrder;
		this.custormerId = custormerId;
	}

	// Property accessors
	@Id
	@Column(name = "REC_ID", unique = true, nullable = false, length = 32)
	public String getRecId() {
		return this.recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	@Column(name = "APPLY_ID", length = 32)
	public String getApplyId() {
		return this.applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	@Column(name = "CUSTORMER_NAME", length = 128)
	public String getCustormerName() {
		return this.custormerName;
	}

	public void setCustormerName(String custormerName) {
		this.custormerName = custormerName;
	}

	@Column(name = "CARD_NO", length = 64)
	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Column(name = "EMP_TYPE", length = 1)
	public String getEmpType() {
		return this.empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	@Column(name = "MEAL_AMOUNT", precision = 18, scale = 3)
	public Double getMealAmount() {
		return this.mealAmount;
	}

	public void setMealAmount(Double mealAmount) {
		this.mealAmount = mealAmount;
	}

	@Column(name = "MEAL_DESC", length = 512)
	public String getMealDesc() {
		return this.mealDesc;
	}

	public void setMealDesc(String mealDesc) {
		this.mealDesc = mealDesc;
	}

	@Column(name = "DISPLAY_ORDER", precision = 22, scale = 0)
	public Long getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Column(name = "CUSTORMER_ID", length = 128)
	public String getCustormerId() {
		return this.custormerId;
	}

	public void setCustormerId(String custormerId) {
		this.custormerId = custormerId;
	}

}