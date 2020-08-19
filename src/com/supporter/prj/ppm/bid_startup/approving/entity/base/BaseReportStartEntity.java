/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.approving.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *<p>Title: 对外提报及获批</p>
 *<p>Description: 投议标备案及许可 - 对外提报及获批  业务实体类，跟数据库字段一一对应</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月2日
 */
@MappedSuperclass//标注为@MappedSuperclass的类将不是一个完整的实体类，他将不会映射到数据库表，但是他的属性都将映射到其子类的数据库字段中
public class BaseReportStartEntity {
	
	@Id  
	@Column(name="REPORT_START_ID", nullable=false, length=32)
	private String reportStartId;//主键id Varchar2(32)
	
	/*The following is project information*/
	
	@Column(name="PRJ_ID",length=32)  
	private  String  prjId;			//PRJ_ID	Varchar2(32)	外键(项目主键)
	
	@Column(name="PRJ_NO",length=32)
	private String prjNo;//项目编号 Varchar2(32)
	
	@Column(name="PRJNAME_C",length=64)
	private String prjNameC;//项目中文名称 Varchar2(64)
	
	@Column(name="PRJNAME_E",length=64)
	private String prjNameE;//项目英文名称Varchar2(64)
	
	/*The following is approving information*/
	
	@Column(name="START_DATE",length=64)
	private String startDate;//报审开始日期 Varchar2(64)
	
	@Column(name="END_DATE",length=64)
	private String endDate;//报审结束日期  Varchar2(64)
	
	@Column(name="LINKMAN_NAME",length=128)
	private String linkManName;		//LINKMAN_NAME	Varchar2(128)	申报部门联系人姓名
	
	@Column(name="LINKMAN_NAME_TEL",length=128)
	private String linkManNameTel;	//LINKMAN_NAME_TEL	Varchar2(128)	申报部门联系人电话
	
	@Column(name="LINKMAN_NAME_MOB",length=128)
	private String linkManNameMob;	//LINKMAN_NAME_MOB	Varchar2(128)	申报部门联系人手机
	
	@Column(name="LINKMAN_NAME_FAX",length=128)
	private String linkManNameFax;	//LINKMAN_NAME_FAX	Varchar2(128)	申报部门联系人传真
	
	/*The following is creator and modifier information*/
	
	@Column(name="CREATED_BY",length=64)
	private String createdBy;		//CREATED_BY	Varchar2(64)	创建人名称

	@Column(name="CREATED_BY_ID",length=32)
	private String createdById;		//CREATED_BY_ID	Varchar2(32)	创建人ID
	
	@Column(name="CREATED_DATE")
	private Date createdDate;		//CREATED_DATE	DATE	创建时间
	
	@Column(name="MODIFIED_BY",length=64)
	private String modifiedBy;		//MODIFIED_BY	Varchar2(64)	修改人名称

	@Column(name="MODIFIED_BY_ID",length=32)
	private String modifiedById;	//MODIFIED_BY_ID	Varchar2(32)	修改人ID

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;		//MODIFIED_DATE	DATE	修改时间
	
	@Column(name="DEPT_NAME",length=200)
	private String deptName;		//DEPT_NAME	Varchar2(200)	创建人部门名称

	@Column(name="DEPT_ID",length=32)
	private String deptId;			//DEPT_ID	Varchar2(32)	创建人部门ID
	
	/*The following is workflow information*/
	
	@Column(name="PROC_ID",length=32)
	private String procId;			//PROC_ID	Varchar2(32)	流程ID

	@Column(name="STATUS",length=32)
	private Integer status;			//STATUS	NUMBER(1)	流程状态   0草稿1已生效2已通知
	
	/*The following is construction method*/
	
	public BaseReportStartEntity() {
		
	}
	
	public BaseReportStartEntity(String reportStartId) {
		this.reportStartId=reportStartId;
	}
	
	/*The following is set and get method*/

	/**
	 * @return the reportStartId
	 */
	public String getReportStartId() {
		return reportStartId;
	}

	/**
	 * @param reportStartId the reportStartId to set
	 */
	public void setReportStartId(String reportStartId) {
		this.reportStartId = reportStartId;
	}

	/**
	 * @return the prjId
	 */
	public String getPrjId() {
		return prjId;
	}

	/**
	 * @param prjId the prjId to set
	 */
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	/**
	 * @return the prjNo
	 */
	public String getPrjNo() {
		return prjNo;
	}

	/**
	 * @param prjNo the prjNo to set
	 */
	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}

	/**
	 * @return the prjNameC
	 */
	public String getPrjNameC() {
		return prjNameC;
	}

	/**
	 * @param prjNameC the prjNameC to set
	 */
	public void setPrjNameC(String prjNameC) {
		this.prjNameC = prjNameC;
	}

	/**
	 * @return the prjNameE
	 */
	public String getPrjNameE() {
		return prjNameE;
	}

	/**
	 * @param prjNameE the prjNameE to set
	 */
	public void setPrjNameE(String prjNameE) {
		this.prjNameE = prjNameE;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the linkManName
	 */
	public String getLinkManName() {
		return linkManName;
	}

	/**
	 * @param linkManName the linkManName to set
	 */
	public void setLinkManName(String linkManName) {
		this.linkManName = linkManName;
	}

	/**
	 * @return the linkManNameTel
	 */
	public String getLinkManNameTel() {
		return linkManNameTel;
	}

	/**
	 * @param linkManNameTel the linkManNameTel to set
	 */
	public void setLinkManNameTel(String linkManNameTel) {
		this.linkManNameTel = linkManNameTel;
	}

	/**
	 * @return the linkManNameMob
	 */
	public String getLinkManNameMob() {
		return linkManNameMob;
	}

	/**
	 * @param linkManNameMob the linkManNameMob to set
	 */
	public void setLinkManNameMob(String linkManNameMob) {
		this.linkManNameMob = linkManNameMob;
	}

	/**
	 * @return the linkManNameFax
	 */
	public String getLinkManNameFax() {
		return linkManNameFax;
	}

	/**
	 * @param linkManNameFax the linkManNameFax to set
	 */
	public void setLinkManNameFax(String linkManNameFax) {
		this.linkManNameFax = linkManNameFax;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdById
	 */
	public String getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the modifiedById
	 */
	public String getModifiedById() {
		return modifiedById;
	}

	/**
	 * @param modifiedById the modifiedById to set
	 */
	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the deptId
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return the procId
	 */
	public String getProcId() {
		return procId;
	}

	/**
	 * @param procId the procId to set
	 */
	public void setProcId(String procId) {
		this.procId = procId;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	
	
	

}
