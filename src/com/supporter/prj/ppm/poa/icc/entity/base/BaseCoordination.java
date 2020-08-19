package com.supporter.prj.ppm.poa.icc.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
@MappedSuperclass
public abstract class BaseCoordination implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String iccId;
	private String prjId;
	private String proNo;
	private String prjName;
	private String prjCName;
	private String prjEName;
	private String coTitle;
	private String coTypeId;
	private String coTypeName;
	private String coDesc;
	private String prjSrcDept;
	private String prjTrgDept;
	private String linkmanName;
	private String linkmanTel;
	private int status;
	private Date createdDate;
	private String createdBy;
	private String createdById;
	private String createdByDept;
	private String createDeptId;
	private String modifiedId;
	private String modifiedName;
	//项目原开发负责人工号
	private String prjSrcNo;
	//项目原开发负责人部门编号
	private String prjSrcDeptNo;
	private Date modifiedDate;
	private String procId;
	private String oneId;
	//private String procId;
	
	// Constructors
	/** default constructor */
	public BaseCoordination() {
		
	}

	public BaseCoordination(String iccId) {
		super();
		this.iccId = iccId;
	}
	/** minimal constructor */
	
	
	// Property accessors
	@Id
	@GeneratedValue(generator = "assigned")
    @GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(name = "ICC_ID", nullable = false, length = 32)
	public String getIccId() {
		return iccId;
	}
	public void setIccId(String iccId) {
		this.iccId = iccId;
	}
	@Column(name = "PROJECT_ID", nullable = true, length = 32)
	public String getPrjId() {
		return prjId;
	}
	public void setPrjId(String projectId) {
		this.prjId = projectId;
	}
	@Column(name = "PRJ_NO", nullable = true, length = 32)
	public String getProNo() {
		return proNo;
	}
	public void setProNo(String proNo) {
		this.proNo = proNo;
	}
	
	@Column(name = "PRJ_CNAME", nullable = true, length = 32)
	public String getPrjCName() {
		return prjCName;
	}
	public void setPrjCName(String prjCName) {
		this.prjCName = prjCName;
	}
	@Column(name = "PRJ_ENAME", nullable = true, length = 32)
	public String getPrjEName() {
		return prjEName;
	}
	public void setPrjEName(String prjEName) {
		this.prjEName = prjEName;
	}
	@Column(name = "CO_TITILE", nullable = true, length = 32)
	public String getCoTitle() {
		return coTitle;
	}
	public void setCoTitle(String coTitle) {
		this.coTitle = coTitle;
	}
	@Column(name = "CO_TYPE_ID", nullable = true, length = 32)
	public String getCoTypeId() {
		return coTypeId;
	}
	public void setCoTypeId(String coTypeId) {
		this.coTypeId = coTypeId;
	}	
	@Column(name = "CO_TYPE_NAME", nullable = true, length = 32)
	public String getCoTypeName() {
		return coTypeName;
	}
	public void setCoTypeName(String coTypeName) {
		this.coTypeName = coTypeName;
	}
	@Column(name = "CO_DESC", nullable = true, length = 32)
	public String getCoDesc() {
		return coDesc;
	}
	public void setCoDesc(String coDesc) {
		this.coDesc = coDesc;
	}
	@Column(name = "PRJ_SRC_DEPT", nullable = true, length = 32)
	public String getPrjSrcDept() {
		return prjSrcDept;
	}
	public void setPrjSrcDept(String prjSrcDept) {
		this.prjSrcDept = prjSrcDept;
	}
	@Column(name = "PRJ_TRG_DEPT", nullable = true, length = 32)

	public String getPrjTrgDept() {
		return prjTrgDept;
	}
	public void setPrjTrgDept(String prjTrgDept) {
		this.prjTrgDept = prjTrgDept;
	}
	@Column(name = "LINKMAN_NAME", nullable = true, length = 32)
	public String getLinkmanName() {
		return linkmanName;
	}
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}
	@Column(name = "LINKMAN_TEL", nullable = true, length = 32)
	public String getLinkmanTel() {
		return linkmanTel;
	}
	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel;
	}
	@Column(name = "STATUS", nullable = true, length = 32)
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Column(name = "CREATED_DATE", nullable = true, length = 32)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name = "CREATED_BY_NAME", nullable = true, length = 32)
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name = "CREATED_BY_ID", nullable = true, length = 32)
	public String getCreatedById() {
		return createdById;
	}
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	@Column(name = "CREATED_BY_DEPT", nullable = true, length = 32)
	public String getCreatedByDept() {
		return createdByDept;
	}
	public void setCreatedByDept(String createdByDept) {
		this.createdByDept = createdByDept;
	}
	@Column(name = "CREATED_DEPT_ID", nullable = true, length = 32)
	public String getCreateDeptId() {
		return createDeptId;
	}
	public void setCreateDeptId(String createDeptId) {
		this.createDeptId = createDeptId;
	}
	@Column(name = "MODIFIED_ID", nullable = true, length = 32)
	public String getModifiedId() {
		return modifiedId;
	}
	public void setModifiedId(String modifiedId) {
		this.modifiedId = modifiedId;
	}
	@Column(name = "MODIFIED_NAME", nullable = true, length = 32)
	public String getModifiedName() {
		return modifiedName;
	}
	public void setModifiedName(String modifiedName) {
		this.modifiedName = modifiedName;
	}
	@Column(name = "MODIFIED_DATE", nullable = true, length = 32)
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Column(name = "ONE_ID", nullable = true, length = 32)
	public String getOneId() {
		return oneId;
	}
	public void setOneId(String oneId) {
		this.oneId = oneId;
	}
	@Column(name = "PROC_ID", nullable = true, length = 32)
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	@Column(name = "PRJ_SRC_NO", nullable = true, length = 32)
	public String getPrjSrcNo() {
		return prjSrcNo;
	}
	public void setPrjSrcNo(String prjSrcNo) {
		this.prjSrcNo = prjSrcNo;
	}
	@Column(name = "PRJ_SRC_DEPT_NO", nullable = true, length = 32)
	public String getPrjSrcDeptNo() {
		return prjSrcDeptNo;
	}
	public void setPrjSrcDeptNo(String prjSrcDeptNo) {
		this.prjSrcDeptNo = prjSrcDeptNo;
	}
	

}
