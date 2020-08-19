package com.supporter.prj.ppm.poa.bor.entity.base;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
@MappedSuperclass
public   class BaseBidOpenResult implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String borId;
	private String prjId;
	private String prjNo;
	private Date bidEndTime;
	private int winBiddingOrNo;
	private String prjName;
	//private String tenderPrice;
	private int status;
	private Date createdDate;
	private String createdBy;
	private String createdById;
	private String createdByDept;
	private String modifiedId;
	private String modifiedName;
	private Date modifiedDate;
	private String createdDeptId;
	@Id
	@GeneratedValue(generator = "assigned")
    @GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(name = "BOR_ID" , nullable = false, length = 32)
	public String getBorId() {
		return borId;
	}
	public void setBorId(String borId) {
		this.borId = borId;
	}
	@Column(name = "PRJ_ID" , nullable = true, length = 32)
	public String getPrjId() {
		return prjId;
	}
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}
	@Column(name = "PRJ_NO" , nullable = true, length = 32)
	public String getPrjNo() {
		return prjNo;
	}
	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}
	@Column(name = "PRJ_NAME" , nullable = true, length = 32)
	public String getPrjName() {
		return prjName;
	}
	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}
	@Column(name = "BID_END_TIME" , nullable = true, length = 32)
	public Date getBidEndTime() {
		return bidEndTime;
	}
	public void setBidEndTime(Date bidEndTime) {
		this.bidEndTime = bidEndTime;
	}
	@Column(name = "WIN_BIDDING_OR_NO" , nullable = true, length = 32)
	public int getWinBiddingOrNo() {
		return winBiddingOrNo;
	}
	public void setWinBiddingOrNo(int winBiddingOrNo) {
		this.winBiddingOrNo = winBiddingOrNo;
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
	@Column(name = "CREATED_DEPT_ID" , nullable = true, length = 32)
	public String getCreatedDeptId() {
		return createdDeptId;
	}
	public void setCreatedDeptId(String createdDeptId) {
		this.createdDeptId = createdDeptId;
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
	@Override
	public String toString() {
		return "BaseBidOpenResult [borId=" + borId + ", prjId=" + prjId + ", prjNo=" + prjNo + ", bidEndTime="
				+ bidEndTime + ", winBiddingOrNo=" + winBiddingOrNo + ", prjName=" + prjName + ", status=" + status
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", createdById=" + createdById
				+ ", createdByDept=" + createdByDept + ", modifiedId=" + modifiedId + ", modifiedName=" + modifiedName
				+ ", modifiedDate=" + modifiedDate + ", createdDeptId=" + createdDeptId + "]";
	}
	
	
	
	
}
