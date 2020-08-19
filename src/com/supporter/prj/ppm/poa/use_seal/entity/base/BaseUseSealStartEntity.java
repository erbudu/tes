/**
 * 
 */
package com.supporter.prj.ppm.poa.use_seal.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *<p>Title: 启动用印业务表单实体类</p>
 *<p>Description: 字段信息跟数据表一一对应</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月12日
 * 
 */
@MappedSuperclass
public class BaseUseSealStartEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USE_SEAL_ID",length=32)
	private String useSealId;//-------------------------- DB:USE_SEAL_ID	Varchar2(32)	主键
	
	@Column(name="PRJ_ID",length=32)
	private String prjId;//-------------------------------DB:	PRJ_ID	Varchar2(32)	外键(项目主键)
	
	@Column(name="BUSINESS_UUID",length=32)
	private String businessUUID;//----------------------------DB:ENTITY_ID	Varchar2(32)	业务主键
	
	@Column(name="PROC_ID",length=32)
	private String procId;//----------------------------- DB: PROC_ID	Varchar2(32)	流程ID
	
	@Column(name="STATUS")
	private Integer status;//------------------------------DB:STATUS	Varchar2(32)	流程状态
	
	@Column(name="PRJ_NO",length=124)
	private String prjNo;//-------------------------------DB:PRJ_NO 	Varchar2(32)	项目编码
	
	@Column(name="PRJ_NAME_C",length=64)
	private String prjNameC;//----------------------------DB:PRJ_NAME_C	Varchar2(64)	项目中文名称
	
	@Column(name="PRJ_NAME_E",length=64)
	private String prjNameE;//----------------------------DB:PRJ_NAME_E	Varchar2(64)	项目英文名称
	
	@Column(name="USE_SEAL_BUSINESS",length=64)
	private String useSealBusiness;//---------------------DB :USE_SEAL_BUSINESS	Varchar2(64)	用印业务
	
	@Column(name="APPLICATION_DATE",length=128)
	private String applicationDate;//---------------------DB: APPLICATION_DATE	DATE	申请日期
	
	@Column(name="USE_SEAL_DIRCTION",length=1024)
	private String useSealDirction;//---------------------DB:		USE_SEAL_DIRCTION	Varchar2(1024)	用印说明

	@Column(name="USE_SEAL_DEPARTMENT",length=64)
	private String useSealDepartment;//-------------------DB : USE_SEAL_DEPARTMENT	Varchar2(64)	用印部门
	
	@Column(name="USE_SESAL_PERSON",length=64)
	private String useSealPerson;//-----------------------DB: USE_SESAL_PERSON	Varchar2(64)	用印人
	
	@Column(name="LINKMAN_NAME",length=64)
	private String linkmanName;//-------------------------DB: LINKMAN_NAME	Varchar2(64)	联系人姓名
	
	@Column(name="LINKMAN_PHONE",length=64)
	private String linkmanPhone;//-----------------------DB:LINKMAN_PHONE	Varchar2(32)	联系人电话
	
	@Column(name="CREATED_BY",length=64)
	private String createdBy;		//-------------------CREATED_BY	Varchar2(64)	创建人名称
	
	@Column(name="CREATED_BY_ID",length=32)
	private String createdById;		//-------------------CREATED_BY_ID	Varchar2(32)	创建人ID
	
	@Column(name="CREATED_DATE",length=64)
	private Date createdDate;		//-------------------CREATED_DATE	DATE	创建时间
	
	@Column(name="DEPT_NAME",length=200)
	private String deptName;		//--------------------DEPT_NAME	Varchar2(200)	创建人部门名称

	@Column(name="DEPT_ID",length=32)
	private String deptId;			//--------------------DEPT_ID	Varchar2(32)	创建人部门ID

	@Column(name="MODIFIED_BY",length=64)
	private String modifiedBy;		//--------------------MODIFIED_BY	Varchar2(64)	修改人名称

	@Column(name="MODIFIED_BY_ID",length=32)
	private String modifiedById;	//--------------------MODIFIED_BY_ID	Varchar2(32)	修改人ID

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;		//--------------------MODIFIED_DATE	DATE	修改时间

	@Column(name="BACK_PATH",length= 128)
	private String backPath;

	/**
	 * This method is constructor
	 */
	public BaseUseSealStartEntity() {
		
	}
	
	/**
	 * This method is constructor
	 */
	public BaseUseSealStartEntity(String useSealId) {
		this.useSealId = useSealId;
	}

	/**
	 * @return the useSealId
	 */
	public String getUseSealId() {
		return useSealId;
	}
	
	/*		The following is set and get methods	↓		*/

	/**
	 * @param useSealId the useSealId to set
	 */
	public void setUseSealId(String useSealId) {
		this.useSealId = useSealId;
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
	 * @return the businessUUID
	 */
	public String getBusinessUUID() {
		return businessUUID;
	}

	/**
	 * @param businessUUID the businessUUID to set
	 */
	public void setBusinessUUID(String businessUUID) {
		this.businessUUID = businessUUID;
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
	 * @return the useSealBusiness
	 */
	public String getUseSealBusiness() {
		return useSealBusiness;
	}

	/**
	 * @param useSealBusiness the useSealBusiness to set
	 */
	public void setUseSealBusiness(String useSealBusiness) {
		this.useSealBusiness = useSealBusiness;
	}

	/**
	 * @return the applicationDate
	 */
	public String getApplicationDate() {
		return applicationDate;
	}

	/**
	 * @param applicationDate the applicationDate to set
	 */
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	/**
	 * @return the useSealDirction
	 */
	public String getUseSealDirction() {
		return useSealDirction;
	}

	/**
	 * @param useSealDirction the useSealDirction to set
	 */
	public void setUseSealDirction(String useSealDirction) {
		this.useSealDirction = useSealDirction;
	}

	/**
	 * @return the useSealDepartment
	 */
	public String getUseSealDepartment() {
		return useSealDepartment;
	}

	/**
	 * @param useSealDepartment the useSealDepartment to set
	 */
	public void setUseSealDepartment(String useSealDepartment) {
		this.useSealDepartment = useSealDepartment;
	}

	/**
	 * @return the useSealPerson
	 */
	public String getUseSealPerson() {
		return useSealPerson;
	}

	/**
	 * @param useSealPerson the useSealPerson to set
	 */
	public void setUseSealPerson(String useSealPerson) {
		this.useSealPerson = useSealPerson;
	}

	/**
	 * @return the linkmanName
	 */
	public String getLinkmanName() {
		return linkmanName;
	}

	/**
	 * @param linkmanName the linkmanName to set
	 */
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	/**
	 * @return the linkmanPhone
	 */
	public String getLinkmanPhone() {
		return linkmanPhone;
	}

	/**
	 * @param linkmanPhone the linkmanPhone to set
	 */
	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
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
	 * @return the backPath
	 */
	public String getBackPath() {
		return backPath;
	}

	/**
	 * @param backPath the backPath to set
	 */
	public void setBackPath(String backPath) {
		this.backPath = backPath;
	}
	
}
