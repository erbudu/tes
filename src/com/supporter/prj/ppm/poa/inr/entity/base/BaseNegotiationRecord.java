package com.supporter.prj.ppm.poa.inr.entity.base;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
@MappedSuperclass
public   class BaseNegotiationRecord implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String inrId;
	private String prjId;
	private Date inrDate;
	private String inrTitle;
	private String inrAddress;
	private String inrPicId;
	private String inrPicName;
	private String recorderId;
	private String recorderName;
	private String inrOtherUnits;
	private String inrDesc;
	private int status;
	private Date createdDate;
	private String createdBy;
	private String createdById;
	private String createdByDept;
	private String modifiedId;
	private String modifiedName;
	private Date modifiedDate;
	//private String oneId;
	public BaseNegotiationRecord() {
		super();
	}
	
	public BaseNegotiationRecord(String inrId, String prjId, Date inrDate, String inrTitle, String inrAddress,
			String inrPicId, String inrPicName, String recorderId, String recorderName, String inrOtherUnits,
			String inrDesc, int status, Date createdDate, String createdBy, String createdById, String createdByDept,
			String modifiedId, String modifiedName, Date modifiedDate) {
		super();
		this.inrId = inrId;
		this.prjId = prjId;
		this.inrDate = inrDate;
		this.inrTitle = inrTitle;
		this.inrAddress = inrAddress;
		this.inrPicId = inrPicId;
		this.inrPicName = inrPicName;
		this.recorderId = recorderId;
		this.recorderName = recorderName;
		this.inrOtherUnits = inrOtherUnits;
		this.inrDesc = inrDesc;
		this.status = status;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdByDept = createdByDept;
		this.modifiedId = modifiedId;
		this.modifiedName = modifiedName;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(generator = "assigned")
    @GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(name = "INR_ID" , nullable = false, length = 32)
	public String getInrId() {
		return inrId;
	}
	
	public void setInrId(String inrId) {
		this.inrId = inrId;
	}
	@Column(name = "PRJ_ID" , nullable = true, length = 32)
	public String getPrjId() {
		return prjId;
	}
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}
	@Column(name = "INRTITLE" , nullable = true, length = 32)
	public String getInrTitle() {
		return inrTitle;
	}
	public void setInrTitle(String inrTitle) {
		this.inrTitle = inrTitle;
	}
	@Column(name = "INR_DATE" , nullable = true, length = 32)
	public Date getInrDate() {
		return inrDate;
	}
	public void setInrDate(Date inrDate) {
		this.inrDate = inrDate;
	}
	@Column(name = "INR_ADDRESS" , nullable = true, length = 32)
	public String getInrAddress() {
		return inrAddress;
	}
	public void setInrAddress(String inrAddress) {
		this.inrAddress = inrAddress;
	}
	
	@Column(name = "INR_PIC_ID" , nullable = true, length = 32)
	public String getInrPicId() {
		return inrPicId;
	}
	public void setInrPicId(String inrPicId) {
		this.inrPicId = inrPicId;
	}
	@Column(name = "INR_PIC_NAME" , nullable = true, length = 32)
	public String getInrPicName() {
		return inrPicName;
	}
	public void setInrPicName(String inrPicName) {
		this.inrPicName = inrPicName;
	}
	@Column(name = "RECORDER_ID" , nullable = true, length = 128)
	public String getRecorderId() {
		return recorderId;
	}
	public void setRecorderId(String recorderId) {
		this.recorderId = recorderId;
	}
	@Column(name = "RECORDER_NAME" , nullable = true, length = 128)
	public String getRecorderName() {
		return recorderName;
	}
	public void setRecorderName(String recorderName) {
		this.recorderName = recorderName;
	}
	@Column(name = "INR_OTHER_UNITS" , nullable = true, length = 128)
	public String getInrOtherUnits() {
		return inrOtherUnits;
	}
	public void setInrOtherUnits(String inrOtherUnits) {
		this.inrOtherUnits = inrOtherUnits;
	}
	@Column(name = "INR_DESC" , nullable = true, length = 256)
	public String getInrDesc() {
		return inrDesc;
	}
	public void setInrDesc(String inrDesc) {
		this.inrDesc = inrDesc;
	}
	@Column(name = "STATUS" , nullable = true)
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Column(name = "CREATED_DATE" , nullable = true, length = 32)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name = "CREATED_BY_NAME" , nullable = true, length = 32)
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name = "CREATED_BY_ID" , nullable = true, length = 32)
	public String getCreatedById() {
		return createdById;
	}
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	@Column(name = "CREATED_BY_DEPT" , nullable = true, length = 32)
	public String getCreatedByDept() {
		return createdByDept;
	}
	public void setCreatedByDept(String createdByDept) {
		this.createdByDept = createdByDept;
	}
	@Column(name = "MODIFIED_ID" , nullable = true, length = 32)
	public String getModifiedId() {
		return modifiedId;
	}
	public void setModifiedId(String modifiedId) {
		this.modifiedId = modifiedId;
	}
	@Column(name = "MODIFIED_NAME" , nullable = true, length = 32)
	public String getModifiedName() {
		return modifiedName;
	}
	public void setModifiedName(String modifiedName) {
		this.modifiedName = modifiedName;
	}
	@Column(name = "MODIFIED_DATE" , nullable = true, length = 32)
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
	
	
}
