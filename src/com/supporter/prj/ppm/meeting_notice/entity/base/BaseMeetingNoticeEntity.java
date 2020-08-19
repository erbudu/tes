package com.supporter.prj.ppm.meeting_notice.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *<p>Title: BaseMeetingNoticeEntity</p>
 *<p>Description: 会议通知</p>
 * @author CHENHAO
 * @date 2019年11月25日
 */

@MappedSuperclass
public class BaseMeetingNoticeEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="NOTICE_ID", nullable=false, length=32)
	private String noticeId;       //NOTICE_ID   VARCHAR2(32)     主键
	
	@Column(name="PRJ_ID", length=32)
	private String prjId;	
	
	private String isSue;          //ISSUE			VARCHAR2(256)   议题
	
	@Column(name="START_DATE")
	private String startDate;			//START_DATE	DATE			开始时间
	
	@Column(name="END_DATE")
	private String endDate;			//END_DATE		DATE			结束时间
	
	
	private String host;			//HOST			Varchar2(128)	主持人
	
	@Column(name="HOST_ID",length=32)
	private String hostId;			//HOST_ID      VARCHAR2(32)		主持人ID

	private String place;			//PLACE			Varchar2(128)	会议地点
	
	@Column(name="PERSON_IDS",length=1024)
	private String personIds;		//PERSON_IDS 	Varchar2(1024)  参会人员id
	
	@Column(name="PERSON_NAMES",length=1024)
	private String personNames;		//PERSON_NAMES   Varchar2(1024) 参会人员
		
	private String demand;			//DEMAND		Varchar2(1024)	其他要求	
	
	private Integer status;			//STATUS		Number(2)		状态(0：草稿 1：已通知)
	
	@Column(name="LOOD_URL",length=512)
	private String loodUrl;			//LOOD_URL		Varchar2(512)	各模块用于被查看的URL
	
	@Column(name="BUSINES_PK_NAME",length=128)
	private String businesPkName;	//BUSINES_PK_NAME Varchar2(128)	各业务单的主键名称
	
	@Column(name="BUSINES_PK_VALUE",length=128)
	private String businesPkValue;	//BUSINES_PK_VALUE Varchar2(128) 各业务单的主键值
	
	@Column(name="PROC_ID",length=32)
	private String procId;			//PROC_ID			Varchar2(32)  发送代办的proc_id   备注：页面传参
	
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

	/**
	 * 描述：无参构造方法
	 */
	public BaseMeetingNoticeEntity() {}

	
	/**
	 * 描述：带参构造方法
	 * @param noticeId 主键
	 */
	public BaseMeetingNoticeEntity(String noticeId) {
		this.noticeId = noticeId;
	}
	
	
	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	
	
	public String getPrjId() {
		return prjId;
	}


	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}


	public String getHostId() {
		return hostId;
	}


	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	@Column(name="ISSUE", length=256)
	public String getIsSue() {
		return isSue;
	}

	public void setIsSue(String isSue) {
		this.isSue = isSue;
	}

	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Column(name="HOST",length=128)
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Column(name="PLACE",length=128)
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}


	public String getPersonIds() {
		return personIds;
	}

	public void setPersonIds(String personIds) {
		this.personIds = personIds;
	}

	
	public String getPersonNames() {
		return personNames;
	}

	public void setPersonNames(String personNames) {
		this.personNames = personNames;
	}

	@Column(name="DEMAND",length=1024)
	public String getDemand() {
		return demand;
	}

	public void setDemand(String demand) {
		this.demand = demand;
	}

	@Column(name="STATUS")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public String getLoodUrl() {
		return loodUrl;
	}

	public void setLoodUrl(String loodUrl) {
		this.loodUrl = loodUrl;
	}

	
	public String getBusinesPkName() {
		return businesPkName;
	}

	public void setBusinesPkName(String businesPkName) {
		this.businesPkName = businesPkName;
	}

	
	public String getBusinesPkValue() {
		return businesPkValue;
	}

	public void setBusinesPkValue(String businesPkValue) {
		this.businesPkValue = businesPkValue;
	}

	
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
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

	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
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

	
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	
	
}
