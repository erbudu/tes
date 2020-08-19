package com.supporter.prj.pm.fund_appropriation.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**   
 * @Title: 资金拨付流程
 * @Description: pm_fund_appropriate_swf, 字段与数据库字段一一对应.
 * @author Administrator
 * @date 2018-07-04 18:08:56
 * @version V1.0   
 *
 */
@MappedSuperclass
public  class BaseFundAppropriationSwf  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *VISA_ID.
	 */
	@Id
	@GeneratedValue(generator = "assigned")
  	@GenericGenerator(name = "assigned",  strategy = "assigned")
	@Column(name = "fund_id", unique = true, nullable = false, length = 32)
	private java.lang.String fundId;
	
	@Column(name = "fund_no", nullable = true, length = 32)
	private java.lang.String fundNo;
	
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
	
	@Column(name = "need_oper_group_exam", nullable = false, length = 1)
	@Type(type = "true_false")
	private boolean needOperGroupExam;
	
	@Column(name = "need_countersign", nullable = false, length = 1)
	@Type(type = "true_false")
	private boolean needCountersign;
	
	public java.lang.String getFundId() {
		return this.fundId;
	}
	public void setFundId(java.lang.String fundId) {
		this.fundId = fundId;
	}
	
	public java.lang.String getFundNo() {
		return this.fundNo;
	}
	public void setFundNo(java.lang.String fundNo) {
		this.fundNo = fundNo;
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
	
	public boolean getNeedOperGroupExam() {
		return this.needOperGroupExam;
	}
	public void setNeedOperGroupExam(boolean needOperGroupExam) {
		this.needOperGroupExam = needOperGroupExam;
	}
	
	public boolean getNeedCountersign() {
		return this.needCountersign;
	}
	public void setNeedCountersign(boolean needCountersign) {
		this.needCountersign = needCountersign;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BaseFundAppropriationSwf() {
	
	}
	
	/**
	 * 构造函数.
	 * @param id
	 */
	public BaseFundAppropriationSwf(String fundId) {
		this.fundId = fundId;
	}
}
