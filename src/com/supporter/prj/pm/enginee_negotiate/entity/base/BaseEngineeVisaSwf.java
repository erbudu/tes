package com.supporter.prj.pm.enginee_negotiate.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: 签证流程
 * @Description: PM_ENGINEE_VISA_SWF, 字段与数据库字段一一对应.
 * @author Administrator
 * @date 2018-07-04 18:08:56
 * @version V1.0   
 *
 */
@MappedSuperclass
public  class BaseEngineeVisaSwf  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *VISA_ID.
	 */
	@Id
	@GeneratedValue(generator = "assigned")
  	@GenericGenerator(name = "assigned",  strategy = "assigned")
	@Column(name = "visa_id", unique = true, nullable = false, length = 32)
	private java.lang.String visaId;
	
	@Column(name = "apply_no", nullable = true, length = 128)
	private java.lang.String applyNo;
	
	@Column(name = "prj_id", nullable = true, length = 32)
	private java.lang.String prjId;
	
	@Column(name = "prj_name", nullable = true, length = 128)
	private java.lang.String prjName;
	
	@Column(name = "oa_exam_status", nullable = false, precision = 10)
	private int oaExamStatus = 0; //默认为草稿 [草稿/审批中/审批完成]
	
	@Column(name = "oa_proc_id", length = 32)
	private java.lang.String oaProcId;
	
	@Column(name = "oa_exam_result", nullable = false, precision = 10)
	private int oaExamResult = 0; //默认为不通过 [通过/不通过]
	
	@Column(name = "oa_exam_opinion", length = 128)
	private java.lang.String oaExamOpinion;
	
	@Column(name = "oa_exam_end_date", nullable = true)
	private java.util.Date oaExamEndDate;
	
	public java.lang.String getVisaId() {
		return this.visaId;
	}
	public void setVisaId(java.lang.String visaId) {
		this.visaId = visaId;
	}
	
	public java.lang.String getApplyNo() {
		return this.applyNo;
	}
	public void setApplyNo(java.lang.String applyNo) {
		this.applyNo = applyNo;
	}
	
	public java.lang.String getPrjId() {
		return prjId;
	}
	public void setPrjId(java.lang.String prjId) {
		this.prjId = prjId;
	}
	
	public java.lang.String getPrjName() {
		return prjName;
	}
	public void setPrjName(java.lang.String prjName) {
		this.prjName = prjName;
	}
	
	public int getOaExamStatus() {
		return this.oaExamStatus;
	}
	public void setOaExamStatus(int oaExamStatus) {
		this.oaExamStatus = oaExamStatus;
	}
	public java.lang.String getOaProcId() {
		return this.oaProcId;
	}
	public void setOaProcId(java.lang.String oaProcId) {
		this.oaProcId = oaProcId;
	}
	public int getOaExamResult() {
		return this.oaExamResult;
	}
	public void setOaExamResult(int oaExamResult) {
		this.oaExamResult = oaExamResult;
	}
	public java.lang.String getOaExamOpinion() {
		return this.oaExamOpinion;
	}
	public void setOaExamOpinion(java.lang.String oaExamOpinion) {
		this.oaExamOpinion = oaExamOpinion;
	}
	public java.util.Date getOaExamEndDate() {
		return this.oaExamEndDate;
	}
	public void setOaExamEndDate(java.util.Date oaExamEndDate) {
		this.oaExamEndDate = oaExamEndDate;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BaseEngineeVisaSwf() {
	
	}
	
	/**
	 * 构造函数.
	 * @param visaId
	 */
	public BaseEngineeVisaSwf(String visaId) {
		this.visaId = visaId;
	}
}
