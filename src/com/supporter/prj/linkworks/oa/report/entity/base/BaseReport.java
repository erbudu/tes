package com.supporter.prj.linkworks.oa.report.entity.base;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.Type;

/**   
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author liyinfeng
 * @date 2017-03-15 16:25:15
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseReport  implements Serializable {
	private static final long serialVersionUID = 1L;
	// Fields

	private String reportId;
	private String reportTitle;
	private int reportStatus;
	private String deptId;
	private String deptName;
	private String empId;
	private String empName;
	private String reportDate;
	private String examIds;
	private String examNames;
	private String createdBy;
	private String creatorName;
	private String createdDate;
	private String modifiedBy;
	private String modifiedDate;
	private String fileName;
	private String sequentialExam;
	private String procId;
	private String notifierIds;
	private String notifierNames;
	//标识历史数据
	
	private boolean history;
	@Column(name = "IS_HISTORY")
	@org.hibernate.annotations.Type(type="true_false")
	public boolean getHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

	// Constructors

	/** default constructor */
	public BaseReport() {
	}
	/**
	 *方法: 设置功能模块id.
	 *@param: java.lang.String  功能模块id
	 */
	public BaseReport(String reportId){
		this.reportId = reportId;
	}
	/** full constructor */
	public BaseReport(String reportTitle, int reportStatus, String deptId,
			String deptName, String empId, String empName, String reportDate,
			String examIds, String examNames, String createdBy,
			String creatorName, String createdDate, String modifiedBy,
			String modifiedDate, String fileName, String sequentialExam,boolean history,String notifierIds,
			String notifierNames) {
		this.reportTitle = reportTitle;
		this.reportStatus = reportStatus;
		this.deptId = deptId;
		this.deptName = deptName;
		this.empId = empId;
		this.empName = empName;
		this.reportDate = reportDate;
		this.examIds = examIds;
		this.examNames = examNames;
		this.createdBy = createdBy;
		this.creatorName = creatorName;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.fileName = fileName;
		this.sequentialExam = sequentialExam;
		this.history=history;
		this.notifierIds=notifierIds;
		this.notifierNames =notifierNames;
	}

	// Property accessors
	
	@Id
	//@GeneratedValue(generator = "systemUUID")
	//@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "REPORT_ID", unique = true, nullable = false, length = 32)
	public String getReportId() {
		return this.reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	@Column(name = "REPORT_TITLE")
	public String getReportTitle() {
		return this.reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	@Column(name = "REPORT_STATUS", precision = 22, scale = 0)
	public int getReportStatus() {
		return this.reportStatus;
	}

	public void setReportStatus(int reportStatus) {
		this.reportStatus = reportStatus;
	}

	@Column(name = "DEPT_ID", length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DEPT_NAME", length = 64)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "EMP_ID", length = 20)
	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Column(name = "EMP_NAME", length = 20)
	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name = "REPORT_DATE", length = 27)
	public String getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	@Column(name = "EXAM_IDS", length = 2000)
	public String getExamIds() {
		return this.examIds;
	}

	public void setExamIds(String examIds) {
		this.examIds = examIds;
	}

	@Column(name = "EXAM_NAMES", length = 2000)
	public String getExamNames() {
		return this.examNames;
	}

	public void setExamNames(String examNames) {
		this.examNames = examNames;
	}

	@Column(name = "CREATED_BY", length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATOR_NAME", length = 32)
	public String getCreatorName() {
		return this.creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Column(name = "CREATED_DATE", length = 27)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE", length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "FILE_NAME", length = 512)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "SEQUENTIAL_EXAM", length = 1)
	public String getSequentialExam() {
		return this.sequentialExam;
	}

	public void setSequentialExam(String sequentialExam) {
		this.sequentialExam = sequentialExam;
	}
	
	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	@Column(name = "notifier_Ids", length = 2000)
	public String getNotifierIds() {
		return notifierIds;
	}

	public void setNotifierIds(String notifierIds) {
		this.notifierIds = notifierIds;
	}
	@Column(name = "notifier_Names", length = 2000)
	public String getNotifierNames() {
		return notifierNames;
	}

	public void setNotifierNames(String notifierNames) {
		this.notifierNames = notifierNames;
	}
}
