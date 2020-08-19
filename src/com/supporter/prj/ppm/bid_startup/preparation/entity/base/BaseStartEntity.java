/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *<p>Title: 投议标备案及许可->申报准备->启动申报实体类</p>
 *<p>Description: 启动申报实体类，字段数据与数据库一一对应</p>
 *<p>Company: 东华后盾</p>
 * @author YUEYUN
 * @date 2019年8月13日
 * 
 */
@MappedSuperclass
public class BaseStartEntity implements Serializable{
	

	private static final long serialVersionUID = 1L;

	@Id  
	@Column(name="BIDINGUP_ID", nullable=false, length=32)
	private  String bidIngUpId;        //BIDINGUP_ID	Varchar2(32) 主键
	
	@Column(name="PRJ_ID",length=32)
	private  String  prjId;			//PRJ_ID	Varchar2(32)	外键(项目主键)
	
	@Column(name="PRJ_NO",length=32)
	private String prjNo;			//PRJ_NO	Varchar2(32)	项目编码
	
	@Column(name="PRJNAME_C",length=64)
	private String prjNameC;		//PRJNAME_C	Varchar2(64)	项目中文名称
	
	@Column(name="PRJNAME_E",length=64)
	private String prjNameE;		//PRJ_NAME_E	Varchar2(64)	项目英文名称
	
	@Column(name="STARTUP_DATE")
	private String startUpDate;		//STARTUP_DATE	DATE	申报日期
	
	@Column(name="LINKMAN_NAME",length=128)
	private String linkManName;		//LINKMAN_NAME	Varchar2(128)	申报部门联系人姓名
	
	@Column(name="LINKMAN_NAME_TEL",length=128)
	private String linkManNameTel;	//LINKMAN_NAME _TEL	Varchar2(128)	申报部门联系人电话
	
	@Column(name="LINKMAN_NAME_MOB",length=128)
	private String linkManNameMob;	//LINKMAN_NAME _MOB	Varchar2(128)	申报部门联系人手机
	
	@Column(name="LINKMAN_NAME_FAX",length=128)
	private String linkManNameFax;	//LINKMAN_NAME _FAX	Varchar2(128)	申报部门联系人传真
	
	@Column(name="IS_SEND_E_LETTERS")
	private Integer isSendELetters;		//IS_SEND_E_LETTERS	NUMBER(1)	是否给大使馆发函

	@Column(name="E_LETTERS_TYPE_ID",length=32)
	private String eLettersTypeId;	//E_LETTERS_TYPE_ID	Varchar2(32)	函件类型ID

	@Column(name="E_LETTERS_TYPE_NAME",length=128)
	private String eLettersTypeName;//E_LETTERS_TYPE_NAME	Varchar2(128)	函件类型名称

	@Column(name="TEMP_ID",length=32)
	private String tempId;			//TEMP_ID	Varchar2(32)	模板ID

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

	@Column(name="PROC_ID",length=32)
	private String procId;			//PROC_ID	Varchar2(32)	流程ID

	@Column(name="STATUS")
	private Integer status;			//STATUS	NUMBER(1)	流程状态 0草稿1审批中2审批完成3已启动用印
	
	@Column(name="OTHER")
	private String otherContent;//其他
	
	
	/**
	 * 描述：无参构造方法
	 */
	public BaseStartEntity() {
	}
	
	/**
	 * 描述：带参构造方法
	 * @param bidIngUpId 主键
	 */
	public BaseStartEntity(String bidIngUpId) {
		this.bidIngUpId = bidIngUpId;
	}

	/**
	 * @return the bidIngUpId
	 */
	public String getBidIngUpId() {
		return bidIngUpId;
	}

	/**
	 * @param bidIngUpId the bidIngUpId to set
	 */
	public void setBidIngUpId(String bidIngUpId) {
		this.bidIngUpId = bidIngUpId;
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
	 * @return the startUpDate
	 */
	public String getStartUpDate() {
		return startUpDate;
	}

	/**
	 * @param startUpDate the startUpDate to set
	 */
	public void setStartUpDate(String startUpDate) {
		this.startUpDate = startUpDate;
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
	 *
	 * @param linkManNameFax the linkManNameFax to set
	 */
	public void setLinkManNameFax(String linkManNameFax) {
		this.linkManNameFax = linkManNameFax;
	}
	public String getLinkManNameFax() {
		return linkManNameFax;
	}


	/**
	 * @return the isSendELetters
	 */
	public Integer getIsSendELetters() {
		return isSendELetters;
	}

	/**
	 * @param isSendELetters the isSendELetters to set
	 */
	public void setIsSendELetters(Integer isSendELetters) {
		this.isSendELetters = isSendELetters;
	}
	

	/**
	 * @return the eLettersTypeId
	 */
	public String geteLettersTypeId() {
		return eLettersTypeId;
	}

	/**
	 * @param eLettersTypeId the eLettersTypeId to set
	 */
	public void seteLettersTypeId(String eLettersTypeId) {
		this.eLettersTypeId = eLettersTypeId;
	}

	/**
	 * @return the eLettersTypeName
	 */
	public String geteLettersTypeName() {
		return eLettersTypeName;
	}

	/**
	 * @param eLettersTypeName the eLettersTypeName to set
	 */
	public void seteLettersTypeName(String eLettersTypeName) {
		this.eLettersTypeName = eLettersTypeName;
	}

	/**
	 * @return the tempId
	 */
	public String getTempId() {
		return tempId;
	}

	/**
	 * @param tempId the tempId to set
	 */
	public void setTempId(String tempId) {
		this.tempId = tempId;
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

	/**
	 * @return the otherContent
	 */
	public String getOtherContent() {
		return otherContent;
	}

	/**
	 * @param otherContent the otherContent to set
	 */
	public void setOtherContent(String otherContent) {
		this.otherContent = otherContent;
	}

	

	

	
}
