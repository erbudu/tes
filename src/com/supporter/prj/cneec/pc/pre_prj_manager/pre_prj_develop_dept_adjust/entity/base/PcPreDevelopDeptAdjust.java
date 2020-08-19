package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_dept_adjust.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PcPreDevelopDeptAdjust entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public class PcPreDevelopDeptAdjust implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String filingId;
	private String prjNameZh;
	private String prjNameEn;
	private String oldDeptId;
	private String oldDeptName;
	private String newDeptId;
	private String newDeptName;
	private String oldCooperationDeptId;
	private String oldCooperationDeptName;
	private String newCooperationDeptId;
	private String newCooperationDeptName;
	private String oldFollowingManId;
	private String oldFollowingMan;
	private String newFollowingManId;
	private String newFollowingMan;
	private String followingManTel;
	private String changeReason;
	private String createdById;
	private String createdBy;
	private Date createdDate;
	private String linkTel;
	private String deptId;
	private String deptName;
	private String modifiedById;
	private String modifiedBy;
	private Date modifiedDate;
	private Short status;
	private String procId;

	// Constructors

	/** default constructor */
	public PcPreDevelopDeptAdjust() {
	}

	/** minimal constructor */
	public PcPreDevelopDeptAdjust(String id) {
		this.id = id;
	}

	/** full constructor */
	public PcPreDevelopDeptAdjust(String id, String filingId, String prjNameZh, String prjNameEn, String oldDeptId,
			String oldDeptName, String newDeptId, String newDeptName, String oldCooperationDeptId,
			String oldCooperationDeptName, String newCooperationDeptId, String newCooperationDeptName,
			String oldFollowingManId, String oldFollowingMan, String newFollowingManId, String newFollowingMan,
			String followingManTel, String changeReason, String createdById, String createdBy, Date createdDate,
			String linkTel, String deptId, String deptName, String modifiedById, String modifiedBy,
			Date modifiedDate, Short status, String procId) {
		this.id = id;
		this.filingId = filingId;
		this.prjNameZh = prjNameZh;
		this.prjNameEn = prjNameEn;
		this.oldDeptId = oldDeptId;
		this.oldDeptName = oldDeptName;
		this.newDeptId = newDeptId;
		this.newDeptName = newDeptName;
		this.oldCooperationDeptId = oldCooperationDeptId;
		this.oldCooperationDeptName = oldCooperationDeptName;
		this.newCooperationDeptId = newCooperationDeptId;
		this.newCooperationDeptName = newCooperationDeptName;
		this.oldFollowingManId = oldFollowingManId;
		this.oldFollowingMan = oldFollowingMan;
		this.newFollowingManId = newFollowingManId;
		this.newFollowingMan = newFollowingMan;
		this.followingManTel = followingManTel;
		this.changeReason = changeReason;
		this.createdById = createdById;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.linkTel = linkTel;
		this.deptId = deptId;
		this.deptName = deptName;
		this.modifiedById = modifiedById;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.status = status;
		this.procId = procId;
	}

	// Property accessors
	@Id

	@Column(name = "ID", unique = true, nullable = false, length = 32)

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "FILING_ID", length = 32)

	public String getFilingId() {
		return this.filingId;
	}

	public void setFilingId(String filingId) {
		this.filingId = filingId;
	}

	@Column(name = "PRJ_NAME_ZH", length = 128)

	public String getPrjNameZh() {
		return this.prjNameZh;
	}

	public void setPrjNameZh(String prjNameZh) {
		this.prjNameZh = prjNameZh;
	}

	@Column(name = "PRJ_NAME_EN", length = 512)

	public String getPrjNameEn() {
		return this.prjNameEn;
	}

	public void setPrjNameEn(String prjNameEn) {
		this.prjNameEn = prjNameEn;
	}

	@Column(name = "OLD_DEPT_ID", length = 32)

	public String getOldDeptId() {
		return this.oldDeptId;
	}

	public void setOldDeptId(String oldDeptId) {
		this.oldDeptId = oldDeptId;
	}

	@Column(name = "OLD_DEPT_NAME", length = 128)

	public String getOldDeptName() {
		return this.oldDeptName;
	}

	public void setOldDeptName(String oldDeptName) {
		this.oldDeptName = oldDeptName;
	}

	@Column(name = "NEW_DEPT_ID", length = 32)

	public String getNewDeptId() {
		return this.newDeptId;
	}

	public void setNewDeptId(String newDeptId) {
		this.newDeptId = newDeptId;
	}

	@Column(name = "NEW_DEPT_NAME", length = 128)

	public String getNewDeptName() {
		return this.newDeptName;
	}

	public void setNewDeptName(String newDeptName) {
		this.newDeptName = newDeptName;
	}

	@Column(name = "OLD_COOPERATION_DEPT_ID", length = 128)

	public String getOldCooperationDeptId() {
		return this.oldCooperationDeptId;
	}

	public void setOldCooperationDeptId(String oldCooperationDeptId) {
		this.oldCooperationDeptId = oldCooperationDeptId;
	}

	@Column(name = "OLD_COOPERATION_DEPT_NAME", length = 256)

	public String getOldCooperationDeptName() {
		return this.oldCooperationDeptName;
	}

	public void setOldCooperationDeptName(String oldCooperationDeptName) {
		this.oldCooperationDeptName = oldCooperationDeptName;
	}

	@Column(name = "NEW_COOPERATION_DEPT_ID", length = 128)

	public String getNewCooperationDeptId() {
		return this.newCooperationDeptId;
	}

	public void setNewCooperationDeptId(String newCooperationDeptId) {
		this.newCooperationDeptId = newCooperationDeptId;
	}

	@Column(name = "NEW_COOPERATION_DEPT_NAME", length = 256)

	public String getNewCooperationDeptName() {
		return this.newCooperationDeptName;
	}

	public void setNewCooperationDeptName(String newCooperationDeptName) {
		this.newCooperationDeptName = newCooperationDeptName;
	}

	@Column(name = "OLD_FOLLOWING_MAN_ID", length = 32)

	public String getOldFollowingManId() {
		return this.oldFollowingManId;
	}

	public void setOldFollowingManId(String oldFollowingManId) {
		this.oldFollowingManId = oldFollowingManId;
	}

	@Column(name = "OLD_FOLLOWING_MAN", length = 64)

	public String getOldFollowingMan() {
		return this.oldFollowingMan;
	}

	public void setOldFollowingMan(String oldFollowingMan) {
		this.oldFollowingMan = oldFollowingMan;
	}

	@Column(name = "NEW_FOLLOWING_MAN_ID", length = 32)

	public String getNewFollowingManId() {
		return this.newFollowingManId;
	}

	public void setNewFollowingManId(String newFollowingManId) {
		this.newFollowingManId = newFollowingManId;
	}

	@Column(name = "NEW_FOLLOWING_MAN", length = 64)

	public String getNewFollowingMan() {
		return this.newFollowingMan;
	}

	public void setNewFollowingMan(String newFollowingMan) {
		this.newFollowingMan = newFollowingMan;
	}

	@Column(name = "FOLLOWING_MAN_TEL", length = 32)

	public String getFollowingManTel() {
		return this.followingManTel;
	}

	public void setFollowingManTel(String followingManTel) {
		this.followingManTel = followingManTel;
	}

	@Lob
	@Column(name = "CHANGE_REASON")

	public String getChangeReason() {
		return this.changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	@Column(name = "CREATED_BY_ID", length = 32)

	public String getCreatedById() {
		return this.createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	@Column(name = "CREATED_BY", length = 64)

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE", length = 7)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "LINK_TEL", length = 32)

	public String getLinkTel() {
		return this.linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	@Column(name = "DEPT_ID", length = 32)

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "DEPT_NAME", length = 128)

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "MODIFIED_BY_ID", length = 32)

	public String getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	@Column(name = "MODIFIED_BY", length = 128)

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE", length = 7)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "STATUS", precision = 3, scale = 0)

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "PROC_ID", length = 32)

	public String getProcId() {
		return this.procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

}