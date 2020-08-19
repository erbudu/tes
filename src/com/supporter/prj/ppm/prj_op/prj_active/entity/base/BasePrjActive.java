package com.supporter.prj.ppm.prj_op.prj_active.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class BasePrjActive implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id; // 主键id
	private String prjId; // 项目id
	private String prjNameZh; // 项目中文名称
	private String prjNameEn; // 项目英文名称
	private String activeReason; // 项目激活原因
	private String explain; // 说明
	private String deptId; // 部门id
	private String deptName; // 部门名称
	private String contactNameId; // 联系人姓名id
	private String contactName; // 联系人姓名
	private String contactNumber; // 联系人电话
	private int status; // 状态
	private Date submitDate; // 提交日期
	private String creator; // 创建人
	private String creatorId; // 创建人id
	private Date creationDate; // 创建日期
	private String modifier; // 修改人
	private String modifierId; // 修改人id
	private Date modificationDate; // 修改日期
	private String procId; // 流程id

	/**
	 * 无参构造
	 */
	public BasePrjActive() {

	}

	/**
	 * 构造函数
	 */
	public BasePrjActive(String id) {
		this.id = id;
	}

	/**
	 * 全参构造
	 */
	public BasePrjActive(String id, String prjId, String prjNameZh, String prjNameEn, String activeReason, String explain, String deptId,
			String deptName, String contactNameId, String contactName, String contactNumber, int status, Date submitDate, String creator,
			String creatorId, Date creationDate, String modifier, String modifierId, Date modificationDate, String procId) {
		this.id = id;
		this.prjId = prjId;
		this.prjNameZh = prjNameZh;
		this.prjNameEn = prjNameEn;
		this.activeReason = activeReason;
		this.explain = explain;
		this.deptId = deptId;
		this.deptName = deptName;
		this.contactNameId = contactNameId;
		this.contactName = contactName;
		this.contactNumber = contactNumber;
		this.status = status;
		this.submitDate = submitDate;
		this.creator = creator;
		this.creatorId = creatorId;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modifierId = modifierId;
		this.modificationDate = modificationDate;
		this.procId = procId;
	}

	@Id
	@Column(name = "id", nullable = false, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "prj_id", nullable = false, length = 32)
	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	@Column(name = "prj_name_zh", nullable = false, length = 64)
	public String getPrjNameZh() {
		return prjNameZh;
	}

	public void setPrjNameZh(String prjNameZh) {
		this.prjNameZh = prjNameZh;
	}

	@Column(name = "prj_name_en", nullable = false, length = 64)
	public String getPrjNameEn() {
		return prjNameEn;
	}

	public void setPrjNameEn(String prjNameEn) {
		this.prjNameEn = prjNameEn;
	}

	@Column(name = "active_reason", nullable = true, length = 512)
	public String getActiveReason() {
		return activeReason;
	}

	public void setActiveReason(String activeReason) {
		this.activeReason = activeReason;
	}

	@Column(name = "explain", nullable = true, length = 512)
	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	@Column(name = "dept_id", nullable = true, length = 32)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "dept_name", nullable = true, length = 64)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "contact_name_id", nullable = true, length = 32)
	public String getContactNameId() {
		return contactNameId;
	}

	public void setContactNameId(String contactNameId) {
		this.contactNameId = contactNameId;
	}

	@Column(name = "contact_name", nullable = true, length = 64)
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Column(name = "contact_number", nullable = true, length = 32)
	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Column(name = "status", nullable = false, precision = 10)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "submit_date", nullable = true, length = 11)
	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	@Column(name = "creator", nullable = true, length = 64)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "creator_id", nullable = true, length = 32)
	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = true, length = 11)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "modifier", nullable = true, length = 64)
	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "modifier_id", nullable = true, length = 32)
	public String getModifierId() {
		return modifierId;
	}

	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modification_date", nullable = true, length = 11)
	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	@Column(name = "proc_id", nullable = true, length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

}
