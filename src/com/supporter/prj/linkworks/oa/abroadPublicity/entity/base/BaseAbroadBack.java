package com.supporter.prj.linkworks.oa.abroadPublicity.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @Title: Entity
 * @Description: 功能模块,字段与数据库字段一一对应.
 * @author linxiaosong
 * @version V1.0   
 */
@MappedSuperclass
public class BaseAbroadBack implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//id
	@Id
	@Column(name = "BACK_ID", unique = true, nullable = false, precision = 32, scale = 0)
	private String backId;
	
	//公示id
	@Column(name="PUB_ID" ,length=32, nullable = true)
	private String pubId;
	
	//报告状态（0草稿，1审核中，2审批完成）
    @Column(name = "SWF_STATUS", precision = 2, scale = 0, nullable = true)
	private Integer swfStatus = 0;
    
    //回国日期
	@Column(name="BACK_DATE" ,length=27, nullable = true)
	private String backDate;
	
	//实际日程安排
	@Column(name="REAL_SCHEDULE" ,length=2000, nullable = true)
	private String realSchedule;
	
	//是否顺利完成本次出国任务
	@Column(name="IF_COMPLETED" ,length=4000, nullable = true)
	private String ifCompleted;
	
	//最晚提交报告日期
	@Column(name="SUBMIT_END_DATE" ,length=27, nullable = true)
	private String submitEndDate;
	
	//是否按时出国(1未按时，0按时)
	@Column(name="CANCELED" , precision = 2, scale = 0, nullable = true)
	private Integer canceled;
	
	//出国行程取消原因
	@Column(name="CANCELED_REASON" ,length=512, nullable = true)
	private String canceledReason;
	
	//实际出国日期
	@Column(name="REAL_LEAVE_DATE" ,length=27, nullable = true)
	private String realLeaveDate;
	
	//流程id
	@Column(name="PROC_ID" ,length=32, nullable = true)
	private String procId;

	//标识历史数据
	@Column(name = "IS_HISTORY")
	@org.hibernate.annotations.Type(type="true_false")
    private boolean history;

	public boolean getHistory() {
		return history;
	}
	public BaseAbroadBack() {
		super();
	}

	public BaseAbroadBack(String backId, String pubId, Integer swfStatus,
			String backDate, String realSchedule, String ifCompleted,
			String submitEndDate, Integer canceled, String canceledReason,
			String realLeaveDate,String procId) {
		super();
		this.backId = backId;
		this.pubId = pubId;
		this.swfStatus = swfStatus;
		this.backDate = backDate;
		this.realSchedule = realSchedule;
		this.ifCompleted = ifCompleted;
		this.submitEndDate = submitEndDate;
		this.canceled = canceled;
		this.canceledReason = canceledReason;
		this.realLeaveDate = realLeaveDate;
		this.procId = procId;
	}

	
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public String getBackId() {
		return backId;
	}

	public void setBackId(String backId) {
		this.backId = backId;
	}

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public Integer getSwfStatus() {
		return swfStatus;
	}

	public void setSwfStatus(Integer swfStatus) {
		this.swfStatus = swfStatus;
	}

	public String getBackDate() {
		return backDate;
	}

	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}

	public String getRealSchedule() {
		return realSchedule;
	}

	public void setRealSchedule(String realSchedule) {
		this.realSchedule = realSchedule;
	}

	public String getIfCompleted() {
		return ifCompleted;
	}

	public void setIfCompleted(String ifCompleted) {
		this.ifCompleted = ifCompleted;
	}


	public String getSubmitEndDate() {
		return submitEndDate;
	}

	public void setSubmitEndDate(String submitEndDate) {
		this.submitEndDate = submitEndDate;
	}


	public Integer getCanceled() {
		return canceled;
	}

	public void setCanceled(Integer canceled) {
		this.canceled = canceled;
	}

	public String getCanceledReason() {
		return canceledReason;
	}

	public void setCanceledReason(String canceledReason) {
		this.canceledReason = canceledReason;
	}

	public String getRealLeaveDate() {
		return realLeaveDate;
	}

	public void setRealLeaveDate(String realLeaveDate) {
		this.realLeaveDate = realLeaveDate;
	}
	
	
}
