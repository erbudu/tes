package com.supporter.prj.cneec.tpc.benefit_note.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: TPC_V_BENEFIT_NOTE,字段与数据库字段一一对应.
 * @author Yanweichao
 * @date 2018-06-01
 * @version V1.0
 */
@MappedSuperclass
public class BaseVBenefitNote implements Serializable {

	private static final long serialVersionUID = 1L;
	private String noteId;
	private int versions;
	private String versionsType;
	private String parentNoteId;
	private String signId;
	private String inforId;
	private String benefitNo;
	private String projectId;
	private String projectName;
	private String contractId;
	private String contractNo;
	private String contractName;
	private int currencyNum;
	private Integer swfStatus;
	private String deptName;
	private String deptId;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedBy;
	private String modifiedById;
	private String modifiedDate;

	/**
	 * 无参构造函数.
	 */
	public BaseVBenefitNote() {
	}

	/**
	 * 构造函数.
	 *
	 * @param noteId
	 */
	public BaseVBenefitNote(String noteId) {
		this.noteId = noteId;
	}

	@Id
	@GeneratedValue(generator = "systemUUID")
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@Column(name = "NOTE_ID", nullable = false, length = 32)
	public String getNoteId() {
		return this.noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	@Column(name = "VERSIONS", nullable = true, precision = 10)
	public int getVersions() {
		return this.versions;
	}

	public void setVersions(int versions) {
		this.versions = versions;
	}

	@Column(name = "VERSIONS_TYPE", nullable = true, length = 32)
	public String getVersionsType() {
		return this.versionsType;
	}

	public void setVersionsType(String versionsType) {
		this.versionsType = versionsType;
	}

	@Column(name = "PARENT_NOTE_ID", nullable = true, length = 32)
	public String getParentNoteId() {
		return this.parentNoteId;
	}

	public void setParentNoteId(String parentNoteId) {
		this.parentNoteId = parentNoteId;
	}

	@Column(name = "SIGN_ID", nullable = true, length = 32)
	public String getSignId() {
		return this.signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	@Column(name = "INFOR_ID", nullable = true, length = 32)
	public String getInforId() {
		return this.inforId;
	}

	public void setInforId(String inforId) {
		this.inforId = inforId;
	}

	@Column(name = "BENEFIT_NO", nullable = true, length = 32)
	public String getBenefitNo() {
		return this.benefitNo;
	}

	public void setBenefitNo(String benefitNo) {
		this.benefitNo = benefitNo;
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

	@Column(name = "CONTRACT_ID", nullable = true, length = 512)
	public String getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Column(name = "CONTRACT_NO", nullable = true, length = 1024)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "CONTRACT_NAME", nullable = true, length = 1024)
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Column(name = "CURRENCY_NUM", nullable = true, precision = 10)
	public int getCurrencyNum() {
		return this.currencyNum;
	}

	public void setCurrencyNum(int currencyNum) {
		this.currencyNum = currencyNum;
	}

	@Column(name = "SWF_STATUS", nullable = true, precision = 10)
	public Integer getSwfStatus() {
		return swfStatus;
	}

	public void setSwfStatus(Integer swfStatus) {
		this.swfStatus = swfStatus;
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

	@Column(name = "MODIFIED_BY", nullable = true, length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_BY_ID", nullable = true, length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "MODIFIED_DATE", nullable = true, length = 27)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
