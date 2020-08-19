package com.supporter.prj.linkworks.oa.emp_meal_apply.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * OaNonEmpMealRec entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseNonEmpMealRec implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String recId;
	private String applyId;
	private String empId;
	private String empName;
	private String empNo;
	private Long empType;
	private Double mealAmount;
	private Double mealOutAmount;
	private Double mealInAmount;
	private Long mealType;
	private String mealDesc;
	private Long displayOrder;

	// Constructors

	/** default constructor */
	public BaseNonEmpMealRec() {
	}

	/** minimal constructor */
	public BaseNonEmpMealRec(String recId) {
		this.recId = recId;
	}

	/** full constructor */
	public BaseNonEmpMealRec(String recId, String applyId, String empId,
			String empName, String empNo, Long empType, Double mealAmount,
			Double mealOutAmount, Double mealInAmount, Long mealType,
			String mealDesc, Long displayOrder) {
		this.recId = recId;
		this.applyId = applyId;
		this.empId = empId;
		this.empName = empName;
		this.empNo = empNo;
		this.empType = empType;
		this.mealAmount = mealAmount;
		this.mealOutAmount = mealOutAmount;
		this.mealInAmount = mealInAmount;
		this.mealType = mealType;
		this.mealDesc = mealDesc;
		this.displayOrder = displayOrder;
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

	@Column(name = "EMP_ID", length = 32)
	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Column(name = "EMP_NAME", length = 64)
	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name = "EMP_NO", length = 64)
	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	@Column(name = "EMP_TYPE", precision = 22, scale = 0)
	public Long getEmpType() {
		return this.empType;
	}

	public void setEmpType(Long empType) {
		this.empType = empType;
	}

	@Column(name = "MEAL_AMOUNT", precision = 18, scale = 3)
	public Double getMealAmount() {
		return this.mealAmount;
	}

	public void setMealAmount(Double mealAmount) {
		this.mealAmount = mealAmount;
	}

	@Column(name = "MEAL_OUT_AMOUNT", precision = 18, scale = 3)
	public Double getMealOutAmount() {
		return this.mealOutAmount;
	}

	public void setMealOutAmount(Double mealOutAmount) {
		this.mealOutAmount = mealOutAmount;
	}

	@Column(name = "MEAL_IN_AMOUNT", precision = 18, scale = 3)
	public Double getMealInAmount() {
		return this.mealInAmount;
	}

	public void setMealInAmount(Double mealInAmount) {
		this.mealInAmount = mealInAmount;
	}

	@Column(name = "MEAL_TYPE", precision = 22, scale = 0)
	public Long getMealType() {
		return this.mealType;
	}

	public void setMealType(Long mealType) {
		this.mealType = mealType;
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

}