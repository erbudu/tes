package com.supporter.prj.linkworks.oa.signed_report.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author qiyuanbiin
 * 
 */
@MappedSuperclass
public class BaseSignedReport implements Serializable {
	private static final long serialVersionUID = 1L;
	// 功能模块id.
	@Id
	@Column(name = "SIGNED_REPORT_ID", unique = true, nullable = false, length = 32)
	private String signedReportId;
	@Column(name = "DEPT_ID", length = 32)
	private String deptId;
	@Column(name = "DEPT_NAME", length = 128)
	private String deptName;
	@Column(name = "DEPT_TYPE_CODE", length = 32)
	private String deptTypeCode;
	@Column(name = "DEPT_TYPE", length = 32)
	private String deptType;
	@Column(name = "REASON", length = 1024)
	private String reason;
	@Column(name = "SIGN_NO", length = 32)
	private String signNo;
	@Column(name = "OPERATOR_ID", length = 32)
	private String operatorId;
	@Column(name = "OPERATOR_NAME", length = 32)
	private String operatorName;
	@Column(name = "OPERATOR_TEL", length = 32)
	private String operatorTel;
	@Column(name = "OPERATOR_DATE", length = 32)
	private String operatorDate;
	@Column(name = "DEPT_SIGNER_IDS", length = 1024)
	private String deptSignerIds;
	@Column(name = "DEPT_SIGNER_NAMES", length = 1024)
	private String deptSignerNames;
	@Column(name = "LEADERS_ID", length = 512)
	private String leadersId;
	@Column(name = "LEADERS_NAME", length = 512)
	private String leadersName;
	@Column(name = "LEADER_SIGNER_IDS", length = 1024)
	private String leaderSignerIds;
	@Column(name = "LEADER_SIGNER_NAMES", length = 1024)
	private String leaderSignerNames;
	@Column(name = "OTHER_FILE", length = 2000)
	private String otherFile;
	@Column(name = "SWF_STATUS", precision = 22, scale = 0)
	private int swfStatus;
	@Column(name = "CREATED_BY", length = 32)
	private String createdBy;
	@Column(name = "CREATED_BY_ID", length = 32)
	private String createdById;
	@Column(name = "CREATED_DATE", length = 32)
	private String createdDate;
	@Column(name = "MODIFIED_BY", length = 32)
	private String modifiedBy;
	@Column(name = "MODIFIED_BY_ID", length = 32)
	private String modifiedById;
	@Column(name = "MODIFIED_DATE", length = 32)
	private String modifiedDate;
	@Column(name = "CONFIRM_DATE", length = 32)
	private String confirmDate;
	@Column(name = "SECRATRY_ID", length = 32)
	private String secratryId;
	@Column(name = "SECRATRY_NAME", length = 32)
	private String secratryName;
	@Column(name = "DEPT_LEADER_ID", length = 32)
	private String deptLeaderId;
	@Column(name = "DEPT_LEADER_NAME", length = 32)
	private String deptLeaderName;
	@Column(name = "ACTRESULT", precision = 22, scale = 0)
	private int actresult;
	@Column(name = "ACTRESULTNAME", length = 256)
	private String actresultname;
	@Column(name = "SIGNED_DEPT_IDS", length = 256)
	private String signedDeptIds;
	@Column(name = "SIGNED_DEPT_NAMES", length = 1024)
	private String signedDeptNames;
	@Column(name = "REPORT_FILE", length = 256)
	private String reportFile;
	@Column(name = "REPORT_CONTENT_FILE", length = 256)
	private String reportContentFile;
	@Column(name = "SIGNED_DATE", length = 32)
	private String signedDate;
	@Column(name = "ARCHIVE_DATE", length = 32)
	private String archiveDate;
	@Column(name = "DEPT_LEADER_SIGNE_ID", length = 32)
	private String deptLeaderSigneId;
	@Column(name = "DEPT_LEADER_SIGNE_DATE", length = 32)
	private String deptLeaderSigneDate;
	@Column(name = "PRESIDENT_SECRATRY_ADD_ID", length = 128)
	private String presidentSecratryAddId;
	@Column(name = "PRESIDENT_SECRATRY_ADD", length = 128)
	private String presidentSecratryAdd;
	@Column(name = "PROC_ID", length = 32)
	private String procId;
	@Column(name = "PRJ_ID", length = 32)
	private String prjId;
	@Column(name = "PRJ_NAME", length = 128)
	private String prjName;
	@Column(name = "TYPE", length = 128)
	private String type;
	@Column(name = "IS_HISTORY")
	@org.hibernate.annotations.Type(type="true_false")
 	private boolean history;
	@Column(name = "ADD_DEPT_SIGNER_IDS", length = 1024)
	private String addDeptSignerIds;
	@Column(name = "ADD_DEPT_SIGNER_NAMES", length = 1024)
	private String addDeptSignerNames;
	
	@Column(name = "IS_AGREEMENT", precision = 22, scale = 0)
	private Integer isAgreement;//是否为协议类（佣金代理）
	
	@Column(name = "IS_NEED_AGREEMENT_NO")
	@org.hibernate.annotations.Type(type="true_false")
	private boolean isNeedAgreementNo;//是否需要协议类（佣金代理）编号
	@Column(name = "NEW_AGREEMENT_TYPT", precision = 22, scale = 0)
	private Integer newAgreementType;//新协议类（佣金代理）类型
	@Column(name = "AGREEMENT_NO", length = 32)
	private String agreementNo;//协议类（佣金代理）编号
	@Column(name = "FIRST_AGREEMENT_ID", length = 32)
	private String firstAgreementId;//第一个协议类（佣金代理）编号的签报ID
	@Column(name = "AGREEMENT_NO_CH_SOURCE", length = 32)
	private String agreementNoChSource;//变更来源编号
	@Column(name = "PRJ_TYPE", length = 32)
	private String prjType;//项目类型
	
	public Integer getIsAgreement() {
		return isAgreement;
	}

	public void setIsAgreement(Integer isAgreement) {
		this.isAgreement = isAgreement;
	}

	public String getAddDeptSignerIds() {
		return addDeptSignerIds;
	}

	public void setAddDeptSignerIds(String addDeptSignerIds) {
		this.addDeptSignerIds = addDeptSignerIds;
	}

	public String getAddDeptSignerNames() {
		return addDeptSignerNames;
	}

	public void setAddDeptSignerNames(String addDeptSignerNames) {
		this.addDeptSignerNames = addDeptSignerNames;
	}

	public boolean getHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

	/**
	 * 无参构造函数.
	 */
	public BaseSignedReport() {

	}

	/**
	 * 构造函数.
	 * 
	 * @param contractId
	 */
	public BaseSignedReport(String id) {
		this.signedReportId = id;
	}

	
	
	
	public BaseSignedReport(String signedReportId, String deptId,
			String deptName, String deptTypeCode, String deptType,
			String reason, String signNo, String operatorId,
			String operatorName, String operatorTel, String operatorDate,
			String deptSignerIds, String deptSignerNames, String leadersId,
			String leadersName, String leaderSignerIds,
			String leaderSignerNames, String otherFile, int swfStatus,
			String createdBy, String createdById, String createdDate,
			String modifiedBy, String modifiedById, String modifiedDate,
			String confirmDate, String secratryId, String secratryName,
			String deptLeaderId, String deptLeaderName, int actresult,
			String actresultname, String signedDeptIds, String signedDeptNames,
			String reportFile, String reportContentFile, String signedDate,
			String archiveDate, String deptLeaderSigneId,
			String deptLeaderSigneDate, String presidentSecratryAddId,
			String presidentSecratryAdd, String procId,String prjName,
			String prjId,String type, String addDeptSignerIds, 
			String addDeptSignerNames, Integer isAgreement, boolean isNeedAgreementNo,
			Integer newAgreementType, String agreementNo, String firstAgreementId,
			String agreementNoChSource, String prjType) {
		super();
		this.signedReportId = signedReportId;
		this.deptId = deptId;
		this.deptName = deptName;
		this.deptTypeCode = deptTypeCode;
		this.deptType = deptType;
		this.reason = reason;
		this.signNo = signNo;
		this.operatorId = operatorId;
		this.operatorName = operatorName;
		this.operatorTel = operatorTel;
		this.operatorDate = operatorDate;
		this.deptSignerIds = deptSignerIds;
		this.deptSignerNames = deptSignerNames;
		this.leadersId = leadersId;
		this.leadersName = leadersName;
		this.leaderSignerIds = leaderSignerIds;
		this.leaderSignerNames = leaderSignerNames;
		this.otherFile = otherFile;
		this.swfStatus = swfStatus;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedById = modifiedById;
		this.modifiedDate = modifiedDate;
		this.confirmDate = confirmDate;
		this.secratryId = secratryId;
		this.secratryName = secratryName;
		this.deptLeaderId = deptLeaderId;
		this.deptLeaderName = deptLeaderName;
		this.actresult = actresult;
		this.actresultname = actresultname;
		this.signedDeptIds = signedDeptIds;
		this.signedDeptNames = signedDeptNames;
		this.reportFile = reportFile;
		this.reportContentFile = reportContentFile;
		this.signedDate = signedDate;
		this.archiveDate = archiveDate;
		this.deptLeaderSigneId = deptLeaderSigneId;
		this.deptLeaderSigneDate = deptLeaderSigneDate;
		this.presidentSecratryAddId = presidentSecratryAddId;
		this.presidentSecratryAdd = presidentSecratryAdd;
		this.procId = procId;
		this.prjId = prjId;
	    this.prjName = prjName;
	    this.type = type;
	    this.addDeptSignerIds=addDeptSignerIds;
	    this.addDeptSignerNames=addDeptSignerNames;
	    this.isAgreement=isAgreement;
	    this.isNeedAgreementNo=isNeedAgreementNo;
	    this.newAgreementType=newAgreementType;
	    this.agreementNo=agreementNo;
	    this.firstAgreementId=firstAgreementId;
	    this.agreementNoChSource=agreementNoChSource;
	    this.prjType=prjType;
	}

	public String getSignedReportId() {
		return signedReportId;
	}

	public void setSignedReportId(String signedReportId) {
		this.signedReportId = signedReportId;
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

	public String getDeptTypeCode() {
		return deptTypeCode;
	}

	public void setDeptTypeCode(String deptTypeCode) {
		this.deptTypeCode = deptTypeCode;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSignNo() {
		return signNo;
	}

	public void setSignNo(String signNo) {
		this.signNo = signNo;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorTel() {
		return operatorTel;
	}

	public void setOperatorTel(String operatorTel) {
		this.operatorTel = operatorTel;
	}

	public String getOperatorDate() {
		return operatorDate;
	}

	public void setOperatorDate(String operatorDate) {
		this.operatorDate = operatorDate;
	}

	public String getDeptSignerIds() {
		return deptSignerIds;
	}

	public void setDeptSignerIds(String deptSignerIds) {
		this.deptSignerIds = deptSignerIds;
	}

	public String getDeptSignerNames() {
		return deptSignerNames;
	}

	public void setDeptSignerNames(String deptSignerNames) {
		this.deptSignerNames = deptSignerNames;
	}

	public String getLeadersId() {
		return leadersId;
	}

	public void setLeadersId(String leadersId) {
		this.leadersId = leadersId;
	}

	public String getLeadersName() {
		return leadersName;
	}

	public void setLeadersName(String leadersName) {
		this.leadersName = leadersName;
	}

	public String getLeaderSignerIds() {
		return leaderSignerIds;
	}

	public void setLeaderSignerIds(String leaderSignerIds) {
		this.leaderSignerIds = leaderSignerIds;
	}

	public String getLeaderSignerNames() {
		return leaderSignerNames;
	}

	public void setLeaderSignerNames(String leaderSignerNames) {
		this.leaderSignerNames = leaderSignerNames;
	}

	public String getOtherFile() {
		return otherFile;
	}

	public void setOtherFile(String otherFile) {
		this.otherFile = otherFile;
	}

	public Integer getSwfStatus() {
		return swfStatus;
	}


	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getSecratryId() {
		return secratryId;
	}

	public void setSecratryId(String secratryId) {
		this.secratryId = secratryId;
	}

	public String getSecratryName() {
		return secratryName;
	}

	public void setSecratryName(String secratryName) {
		this.secratryName = secratryName;
	}

	public String getDeptLeaderId() {
		return deptLeaderId;
	}

	public void setDeptLeaderId(String deptLeaderId) {
		this.deptLeaderId = deptLeaderId;
	}

	public String getDeptLeaderName() {
		return deptLeaderName;
	}

	public void setDeptLeaderName(String deptLeaderName) {
		this.deptLeaderName = deptLeaderName;
	}

	public int getActresult() {
		return actresult;
	}

	public void setActresult(int actresult) {
		this.actresult = actresult;
	}

	public String getactresultname() {
		return actresultname;
	}

	public void setactresultname(String actresultname) {
		this.actresultname = actresultname;
	}

	public String getSignedDeptIds() {
		return signedDeptIds;
	}

	public void setSignedDeptIds(String signedDeptIds) {
		this.signedDeptIds = signedDeptIds;
	}

	public String getSignedDeptNames() {
		return signedDeptNames;
	}

	public void setSignedDeptNames(String signedDeptNames) {
		this.signedDeptNames = signedDeptNames;
	}

	public String getReportFile() {
		return reportFile;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	public String getReportContentFile() {
		return reportContentFile;
	}

	public void setReportContentFile(String reportContentFile) {
		this.reportContentFile = reportContentFile;
	}

	public String getSignedDate() {
		return signedDate;
	}

	public void setSignedDate(String signedDate) {
		this.signedDate = signedDate;
	}

	public String getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(String archiveDate) {
		this.archiveDate = archiveDate;
	}

	public String getDeptLeaderSigneId() {
		return deptLeaderSigneId;
	}

	public void setDeptLeaderSigneId(String deptLeaderSigneId) {
		this.deptLeaderSigneId = deptLeaderSigneId;
	}

	public String getDeptLeaderSigneDate() {
		return deptLeaderSigneDate;
	}

	public void setDeptLeaderSigneDate(String deptLeaderSigneDate) {
		this.deptLeaderSigneDate = deptLeaderSigneDate;
	}

	public String getPresidentSecratryAddId() {
		return presidentSecratryAddId;
	}

	public void setPresidentSecratryAddId(String presidentSecratryAddId) {
		this.presidentSecratryAddId = presidentSecratryAddId;
	}

	public String getPresidentSecratryAdd() {
		return presidentSecratryAdd;
	}

	public void setPresidentSecratryAdd(String presidentSecratryAdd) {
		this.presidentSecratryAdd = presidentSecratryAdd;
	}

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public void setSwfStatus(int swfStatus) {
		this.swfStatus = swfStatus;
	}

	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean getIsNeedAgreementNo() {
		return isNeedAgreementNo;
	}

	public void setIsNeedAgreementNo(boolean isNeedAgreementNo) {
		this.isNeedAgreementNo = isNeedAgreementNo;
	}

	public Integer getNewAgreementType() {
		return newAgreementType;
	}

	public void setNewAgreementType(Integer newAgreementType) {
		this.newAgreementType = newAgreementType;
	}

	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	public String getFirstAgreementId() {
		return firstAgreementId;
	}

	public void setFirstAgreementId(String firstAgreementId) {
		this.firstAgreementId = firstAgreementId;
	}

	public String getAgreementNoChSource() {
		return agreementNoChSource;
	}

	public void setAgreementNoChSource(String agreementNoChSource) {
		this.agreementNoChSource = agreementNoChSource;
	}

	public String getPrjType() {
		return prjType;
	}

	public void setPrjType(String prjType) {
		this.prjType = prjType;
	}

}
