package com.supporter.prj.linkworks.oa.signed_report.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseSignedReportMessageBoard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "BOARD_ID", unique = true, nullable = false, length = 32)
	private String boardId;
	
	//
	@Column(name="REPORT_ID" ,length=32, nullable = true)
	private String reportId;
	
	//
	@Column(name="CONTENT" ,length=2000, nullable = true)
	private String content;
	
	//
	@Column(name="DEPT_ID" ,length=32, nullable = true)
	private String deptId;
	
	//
	@Column(name="DEPT_NAME" ,length=128, nullable = true)
	private String deptName;
	
	//
	@Column(name="PERSON_ID" ,length=32, nullable = true)
	private String personId;
	
	//
	@Column(name="PERSON_NAME" ,length=64, nullable = true)
	private String personName;
	
	//
	@Column(name="MESS_DATE" ,length=27, nullable = true)
	private String messDate;

	
	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getMessDate() {
		return messDate;
	}

	public void setMessDate(String messDate) {
		this.messDate = messDate;
	}

	public BaseSignedReportMessageBoard(String boardId, String reportId,
			String content, String deptId, String deptName, String personId,
			String personName, String messDate) {
		super();
		this.boardId = boardId;
		this.reportId = reportId;
		this.content = content;
		this.deptId = deptId;
		this.deptName = deptName;
		this.personId = personId;
		this.personName = personName;
		this.messDate = messDate;
	}

	public BaseSignedReportMessageBoard() {
		super();
	}

	
	
}
