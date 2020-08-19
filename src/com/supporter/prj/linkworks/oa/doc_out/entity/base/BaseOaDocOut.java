package com.supporter.prj.linkworks.oa.doc_out.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * OaDocOut entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class BaseOaDocOut implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String docId;
	private String docNo;
	private String recordNo;
	private String docTitle;
	private String emergencyType;
	private String docType;
	private Integer docStatus;
	private String deptId;
	private String deptName;
	private String publisherId;
	private String publisherName;
	private String publishDate;
	private String draftsmanId;
	private String draftsmanName;
	private Long confidentialRank;
	private String keyWords;
	private Long insideOnly;
	private Long copyCount;
	private String toDeptId;
	private String toDeptName;
	private String ctoDeptIds;
	private String ctoDeptNames;
	private String topLeaderIds;
	private String topLeaderNames;
	private String deptLeaderIds;
	private String deptLeaderNames;
	private String presidentId;
	private String presidentName;
	private String deptOpinion;
	private String officeOpinion1;
	private String editorId;
	private String editorName;
	private String pressCorrectorId;
	private String pressCorrector;
	private String officeOpinion2;
	private String receiverIds;
	private String receiverNames;
	private String readerIds;
	private String readerNames;
	private String handlerIds;
	private String handlerNames;
	private String handlingCheckerIds;
	private String handlingCheckerNames;
	private String handlingInstructorId;
	private String handlingInstructorName;
	private String archivesId;
	private String archivesName;
	private String createdBy;
	private String createdById;
	private String createdDate;
	private String modifiedById;
	private String modifiedBy;
	private String modifiedDate;
	private String fileName;
	private String fileExt;
	private String examId;
	private String examName;
	private String needReceivers;
	private String officeLeaderId;
	private String officeLeader;
	private String fileNames;
	private String extendTopLeaderIds;
	private String extendTopLeaderNames;
	private String extendDeptLeaderIds;
	private String extendDeptLeaderNames;
	private String procId;
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
	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	/** default constructor */
	public BaseOaDocOut() {
	}

	/** minimal constructor */
	public BaseOaDocOut(String docId) {
		this.docId = docId;
	}

	/** full constructor */
	public BaseOaDocOut(String docId, String docNo, String recordNo,
			String docTitle, String emergencyType, String docType,
			Integer docStatus, String deptId, String deptName,
			String publisherId, String publisherName, String publishDate,
			String draftsmanId, String draftsmanName, Long confidentialRank,
			String keyWords, Long insideOnly, Long copyCount, String toDeptId,
			String toDeptName, String ctoDeptIds, String ctoDeptNames,
			String topLeaderIds, String topLeaderNames, String deptLeaderIds,
			String deptLeaderNames, String presidentId, String presidentName,
			String deptOpinion, String officeOpinion1, String editorId,
			String editorName, String pressCorrectorId, String pressCorrector,
			String officeOpinion2, String receiverIds, String receiverNames,
			String readerIds, String readerNames, String handlerIds,
			String handlerNames, String handlingCheckerIds,
			String handlingCheckerNames, String handlingInstructorId,
			String handlingInstructorName, String archivesId,
			String archivesName, String createdBy, String createdById,
			String createdDate, String modifiedById, String modifiedBy,
			String modifiedDate, String fileName, String fileExt,
			String examId, String examName, String needReceivers,
			String officeLeaderId, String officeLeader, String fileNames,
			String extendTopLeaderIds, String extendTopLeaderNames,
			String extendDeptLeaderIds, String extendDeptLeaderNames,
			String procId) {
		this.docId = docId;
		this.docNo = docNo;
		this.recordNo = recordNo;
		this.docTitle = docTitle;
		this.emergencyType = emergencyType;
		this.docType = docType;
		this.docStatus = docStatus;
		this.deptId = deptId;
		this.deptName = deptName;
		this.publisherId = publisherId;
		this.publisherName = publisherName;
		this.publishDate = publishDate;
		this.draftsmanId = draftsmanId;
		this.draftsmanName = draftsmanName;
		this.confidentialRank = confidentialRank;
		this.keyWords = keyWords;
		this.insideOnly = insideOnly;
		this.copyCount = copyCount;
		this.toDeptId = toDeptId;
		this.toDeptName = toDeptName;
		this.ctoDeptIds = ctoDeptIds;
		this.ctoDeptNames = ctoDeptNames;
		this.topLeaderIds = topLeaderIds;
		this.topLeaderNames = topLeaderNames;
		this.deptLeaderIds = deptLeaderIds;
		this.deptLeaderNames = deptLeaderNames;
		this.presidentId = presidentId;
		this.presidentName = presidentName;
		this.deptOpinion = deptOpinion;
		this.officeOpinion1 = officeOpinion1;
		this.editorId = editorId;
		this.editorName = editorName;
		this.pressCorrectorId = pressCorrectorId;
		this.pressCorrector = pressCorrector;
		this.officeOpinion2 = officeOpinion2;
		this.receiverIds = receiverIds;
		this.receiverNames = receiverNames;
		this.readerIds = readerIds;
		this.readerNames = readerNames;
		this.handlerIds = handlerIds;
		this.handlerNames = handlerNames;
		this.handlingCheckerIds = handlingCheckerIds;
		this.handlingCheckerNames = handlingCheckerNames;
		this.handlingInstructorId = handlingInstructorId;
		this.handlingInstructorName = handlingInstructorName;
		this.archivesId = archivesId;
		this.archivesName = archivesName;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.modifiedById = modifiedById;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.fileName = fileName;
		this.fileExt = fileExt;
		this.examId = examId;
		this.examName = examName;
		this.needReceivers = needReceivers;
		this.officeLeaderId = officeLeaderId;
		this.officeLeader = officeLeader;
		this.fileNames = fileNames;
		this.extendTopLeaderIds = extendTopLeaderIds;
		this.extendTopLeaderNames = extendTopLeaderNames;
		this.extendDeptLeaderIds = extendDeptLeaderIds;
		this.extendDeptLeaderNames = extendDeptLeaderNames;
		this.procId = procId;
	}

	// Property accessors
	@Id
	@Column(name = "DOC_ID", unique = true, nullable = false, length = 32)
	public String getDocId() {
		return this.docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	@Column(name = "DOC_NO", length = 64)
	public String getDocNo() {
		return this.docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	@Column(name = "RECORD_NO", length = 32)
	public String getRecordNo() {
		return this.recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	@Column(name = "DOC_TITLE")
	public String getDocTitle() {
		return this.docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	@Column(name = "EMERGENCY_TYPE", length = 1)
	public String getEmergencyType() {
		return this.emergencyType;
	}

	public void setEmergencyType(String emergencyType) {
		this.emergencyType = emergencyType;
	}

	@Column(name = "DOC_TYPE", length = 32)
	public String getDocType() {
		return this.docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	@Column(name = "DOC_STATUS", precision = 22, scale = 0)
	public Integer getDocStatus() {
		return this.docStatus;
	}

	public void setDocStatus(Integer docStatus) {
		this.docStatus = docStatus;
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

	@Column(name = "PUBLISHER_ID", length = 32)
	public String getPublisherId() {
		return this.publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	@Column(name = "PUBLISHER_NAME", length = 32)
	public String getPublisherName() {
		return this.publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	@Column(name = "PUBLISH_DATE", length = 64)
	public String getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	@Column(name = "DRAFTSMAN_ID", length = 32)
	public String getDraftsmanId() {
		return this.draftsmanId;
	}

	public void setDraftsmanId(String draftsmanId) {
		this.draftsmanId = draftsmanId;
	}

	@Column(name = "DRAFTSMAN_NAME", length = 32)
	public String getDraftsmanName() {
		return this.draftsmanName;
	}

	public void setDraftsmanName(String draftsmanName) {
		this.draftsmanName = draftsmanName;
	}

	@Column(name = "CONFIDENTIAL_RANK", precision = 22, scale = 0)
	public Long getConfidentialRank() {
		return this.confidentialRank;
	}

	public void setConfidentialRank(Long confidentialRank) {
		this.confidentialRank = confidentialRank;
	}

	@Column(name = "KEY_WORDS", length = 128)
	public String getKeyWords() {
		return this.keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	@Column(name = "INSIDE_ONLY", precision = 22, scale = 0)
	public Long getInsideOnly() {
		return this.insideOnly;
	}

	public void setInsideOnly(Long insideOnly) {
		this.insideOnly = insideOnly;
	}

	@Column(name = "COPY_COUNT", precision = 22, scale = 0)
	public Long getCopyCount() {
		return this.copyCount;
	}

	public void setCopyCount(Long copyCount) {
		this.copyCount = copyCount;
	}

	@Column(name = "TO_DEPT_ID", length = 32)
	public String getToDeptId() {
		return this.toDeptId;
	}

	public void setToDeptId(String toDeptId) {
		this.toDeptId = toDeptId;
	}

	@Column(name = "TO_DEPT_NAME")
	public String getToDeptName() {
		return this.toDeptName;
	}

	public void setToDeptName(String toDeptName) {
		this.toDeptName = toDeptName;
	}

	@Column(name = "C_TO_DEPT_IDS")
	public String getCtoDeptIds() {
		return this.ctoDeptIds;
	}

	public void setCtoDeptIds(String CToDeptIds) {
		this.ctoDeptIds = CToDeptIds;
	}

	@Column(name = "C_TO_DEPT_NAMES")
	public String getCtoDeptNames() {
		return this.ctoDeptNames;
	}

	public void setCtoDeptNames(String ctoDeptNames) {
		this.ctoDeptNames = ctoDeptNames;
	}

	@Column(name = "TOP_LEADER_IDS")
	public String getTopLeaderIds() {
		return this.topLeaderIds;
	}

	public void setTopLeaderIds(String topLeaderIds) {
		this.topLeaderIds = topLeaderIds;
	}

	@Column(name = "TOP_LEADER_NAMES", length = 1024)
	public String getTopLeaderNames() {
		return this.topLeaderNames;
	}

	public void setTopLeaderNames(String topLeaderNames) {
		this.topLeaderNames = topLeaderNames;
	}

	@Column(name = "DEPT_LEADER_IDS")
	public String getDeptLeaderIds() {
		return this.deptLeaderIds;
	}

	public void setDeptLeaderIds(String deptLeaderIds) {
		this.deptLeaderIds = deptLeaderIds;
	}

	@Column(name = "DEPT_LEADER_NAMES", length = 1024)
	public String getDeptLeaderNames() {
		return this.deptLeaderNames;
	}

	public void setDeptLeaderNames(String deptLeaderNames) {
		this.deptLeaderNames = deptLeaderNames;
	}

	@Column(name = "PRESIDENT_ID", length = 32)
	public String getPresidentId() {
		return this.presidentId;
	}

	public void setPresidentId(String presidentId) {
		this.presidentId = presidentId;
	}

	@Column(name = "PRESIDENT_NAME", length = 32)
	public String getPresidentName() {
		return this.presidentName;
	}

	public void setPresidentName(String presidentName) {
		this.presidentName = presidentName;
	}

	@Column(name = "DEPT_OPINION")
	public String getDeptOpinion() {
		return this.deptOpinion;
	}

	public void setDeptOpinion(String deptOpinion) {
		this.deptOpinion = deptOpinion;
	}

	@Column(name = "OFFICE_OPINION_1")
	public String getOfficeOpinion1() {
		return this.officeOpinion1;
	}

	public void setOfficeOpinion1(String officeOpinion1) {
		this.officeOpinion1 = officeOpinion1;
	}

	@Column(name = "EDITOR_ID", length = 32)
	public String getEditorId() {
		return this.editorId;
	}

	public void setEditorId(String editorId) {
		this.editorId = editorId;
	}

	@Column(name = "EDITOR_NAME", length = 32)
	public String getEditorName() {
		return this.editorName;
	}

	public void setEditorName(String editorName) {
		this.editorName = editorName;
	}

	@Column(name = "PRESS_CORRECTOR_ID", length = 32)
	public String getPressCorrectorId() {
		return this.pressCorrectorId;
	}

	public void setPressCorrectorId(String pressCorrectorId) {
		this.pressCorrectorId = pressCorrectorId;
	}

	@Column(name = "PRESS_CORRECTOR", length = 32)
	public String getPressCorrector() {
		return this.pressCorrector;
	}

	public void setPressCorrector(String pressCorrector) {
		this.pressCorrector = pressCorrector;
	}

	@Column(name = "OFFICE_OPINION_2")
	public String getOfficeOpinion2() {
		return this.officeOpinion2;
	}

	public void setOfficeOpinion2(String officeOpinion2) {
		this.officeOpinion2 = officeOpinion2;
	}

	@Column(name = "RECEIVER_IDS", length = 4000)
	public String getReceiverIds() {
		return this.receiverIds;
	}

	public void setReceiverIds(String receiverIds) {
		this.receiverIds = receiverIds;
	}

	@Column(name = "RECEIVER_NAMES", length = 4000)
	public String getReceiverNames() {
		return this.receiverNames;
	}

	public void setReceiverNames(String receiverNames) {
		this.receiverNames = receiverNames;
	}

	@Column(name = "READER_IDS", length = 512)
	public String getReaderIds() {
		return this.readerIds;
	}

	public void setReaderIds(String readerIds) {
		this.readerIds = readerIds;
	}

	@Column(name = "READER_NAMES", length = 512)
	public String getReaderNames() {
		return this.readerNames;
	}

	public void setReaderNames(String readerNames) {
		this.readerNames = readerNames;
	}

	@Column(name = "HANDLER_IDS", length = 128)
	public String getHandlerIds() {
		return this.handlerIds;
	}

	public void setHandlerIds(String handlerIds) {
		this.handlerIds = handlerIds;
	}

	@Column(name = "HANDLER_NAMES", length = 128)
	public String getHandlerNames() {
		return this.handlerNames;
	}

	public void setHandlerNames(String handlerNames) {
		this.handlerNames = handlerNames;
	}

	@Column(name = "HANDLING_CHECKER_IDS", length = 64)
	public String getHandlingCheckerIds() {
		return this.handlingCheckerIds;
	}

	public void setHandlingCheckerIds(String handlingCheckerIds) {
		this.handlingCheckerIds = handlingCheckerIds;
	}

	@Column(name = "HANDLING_CHECKER_NAMES", length = 64)
	public String getHandlingCheckerNames() {
		return this.handlingCheckerNames;
	}

	public void setHandlingCheckerNames(String handlingCheckerNames) {
		this.handlingCheckerNames = handlingCheckerNames;
	}

	@Column(name = "HANDLING_INSTRUCTOR_ID", length = 32)
	public String getHandlingInstructorId() {
		return this.handlingInstructorId;
	}

	public void setHandlingInstructorId(String handlingInstructorId) {
		this.handlingInstructorId = handlingInstructorId;
	}

	@Column(name = "HANDLING_INSTRUCTOR_NAME", length = 32)
	public String getHandlingInstructorName() {
		return this.handlingInstructorName;
	}

	public void setHandlingInstructorName(String handlingInstructorName) {
		this.handlingInstructorName = handlingInstructorName;
	}

	@Column(name = "ARCHIVES_ID", length = 32)
	public String getArchivesId() {
		return this.archivesId;
	}

	public void setArchivesId(String archivesId) {
		this.archivesId = archivesId;
	}

	@Column(name = "ARCHIVES_NAME", length = 64)
	public String getArchivesName() {
		return this.archivesName;
	}

	public void setArchivesName(String archivesName) {
		this.archivesName = archivesName;
	}

	@Column(name = "CREATED_BY", length = 32)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_BY_ID", length = 32)
	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "CREATED_DATE", length = 64)
	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY_ID", length = 32)
	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "MODIFIED_BY", length = 32)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE", length = 64)
	public String getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "FILE_NAME", length = 64)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "FILE_EXT", length = 16)
	public String getFileExt() {
		return this.fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	@Column(name = "EXAM_ID", length = 32)
	public String getExamId() {
		return this.examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	@Column(name = "EXAM_NAME", length = 64)
	public String getExamName() {
		return this.examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	@Column(name = "NEED_RECEIVERS", length = 10)
	public String getNeedReceivers() {
		return this.needReceivers;
	}

	public void setNeedReceivers(String needReceivers) {
		this.needReceivers = needReceivers;
	}

	@Column(name = "OFFICE_LEADER_ID", length = 32)
	public String getOfficeLeaderId() {
		return this.officeLeaderId;
	}

	public void setOfficeLeaderId(String officeLeaderId) {
		this.officeLeaderId = officeLeaderId;
	}

	@Column(name = "OFFICE_LEADER", length = 32)
	public String getOfficeLeader() {
		return this.officeLeader;
	}

	public void setOfficeLeader(String officeLeader) {
		this.officeLeader = officeLeader;
	}

	@Column(name = "FILE_NAMES", length = 512)
	public String getFileNames() {
		return this.fileNames;
	}

	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}

	@Column(name = "EXTEND_TOP_LEADER_IDS")
	public String getExtendTopLeaderIds() {
		return this.extendTopLeaderIds;
	}

	public void setExtendTopLeaderIds(String extendTopLeaderIds) {
		this.extendTopLeaderIds = extendTopLeaderIds;
	}

	@Column(name = "EXTEND_TOP_LEADER_NAMES", length = 1024)
	public String getExtendTopLeaderNames() {
		return this.extendTopLeaderNames;
	}

	public void setExtendTopLeaderNames(String extendTopLeaderNames) {
		this.extendTopLeaderNames = extendTopLeaderNames;
	}

	@Column(name = "EXTEND_DEPT_LEADER_IDS")
	public String getExtendDeptLeaderIds() {
		return this.extendDeptLeaderIds;
	}

	public void setExtendDeptLeaderIds(String extendDeptLeaderIds) {
		this.extendDeptLeaderIds = extendDeptLeaderIds;
	}

	@Column(name = "EXTEND_DEPT_LEADER_NAMES", length = 1024)
	public String getExtendDeptLeaderNames() {
		return this.extendDeptLeaderNames;
	}

	public void setExtendDeptLeaderNames(String extendDeptLeaderNames) {
		this.extendDeptLeaderNames = extendDeptLeaderNames;
	}

}