package com.supporter.prj.ppm.template_register.entity.base;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**   
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author liyinfeng
 * @date 2017-03-15 16:25:15
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseTemplateKeyPointsExam  implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	// Fields
	private String recordId;
	private String procId;
	private String empId;
	private String empName;
	private String detailId;
	private String templateId;
	private String dataId;
	
	private Date createdDate;
	private Date examDate;
	private String examResult;
	private String opinionDesc;  
	private String textDisplay;


	  
	 

	// Constructors

	/** default constructor */
	public BaseTemplateKeyPointsExam() {
	}
	
	public BaseTemplateKeyPointsExam(String detailId) {
		this.detailId = detailId;
	}

	// Property accessors
	@Id
	@Column(name = "RECORD_ID", unique = true, nullable = false, length = 32)
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	@Column(name = "DETAIL_ID", length = 32)
	public String getDetailId() {
		return this.detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	@Column(name = "TEMPLATE_ID", length = 32)
	public String getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", length = 11)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "DATA_ID", length = 1)
	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}
	
	@Column(name="EMP_ID", nullable=true, length=32)
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Column(name="EMP_NAME", nullable=true, length=32)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="EXAM_DATE", nullable=true)
	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	
	@Column(name="EXAM_RESULT", nullable=true, precision=10)
	public String getExamResult() {
		return examResult;
	}

	public void setExamResult(String examResult) {
		this.examResult = examResult;
	}
	
	@Column(name="OPINION_DESC", nullable=true, length=1000)
	public String getOpinionDesc() {
		return opinionDesc;
	}

	public void setOpinionDesc(String opinionDesc) {
		this.opinionDesc = opinionDesc;
	}

	@Column(name="TEXT_DISPLAY", nullable=true, length=1000)
	public String getTextDisplay() {
		return textDisplay;
	}

	public void setTextDisplay(String textDisplay) {
		this.textDisplay = textDisplay;
	}
	 
}
