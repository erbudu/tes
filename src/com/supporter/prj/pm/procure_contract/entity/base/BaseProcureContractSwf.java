package com.supporter.prj.pm.procure_contract.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: 采购合同
 * @Description: pm_payment_onsite_swf, 字段与数据库字段一一对应.
 * @author Administrator
 * @date 2018-07-04 18:08:56
 * @version V1.0   
 *
 */
/**
 * @Title: BaseProcureContractSwf
 * @Description: 
 * @author Yanweichao
 * @date 2018年12月12日
 * @version: V1.0
 */
@MappedSuperclass
public  class BaseProcureContractSwf  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *VISA_ID.
	 */
	@Id
	@GeneratedValue(generator = "assigned")
  	@GenericGenerator(name = "assigned",  strategy = "assigned")
	@Column(name = "CONTRACT_ID", nullable = false, length = 32)
	private java.lang.String contractId;
	
	@Column(name = "CONTRACT_NO", nullable = true, length = 64)
	private java.lang.String contractNo;

	@Column(name = "CONTRACT_NAME", nullable = true, length = 32)
	private java.lang.String contractName;
	
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
	
	public java.lang.String getContractId() {
		return this.contractId;
	}
	public void setContractId(java.lang.String contractId) {
		this.contractId = contractId;
	}
	
	public java.lang.String getContractNo() {
		return this.contractNo;
	}
	public void setContractNo(java.lang.String contractNo) {
		this.contractNo = contractNo;
	}
	public java.lang.String getContractName() {
		return contractName;
	}
	public void setContractName(java.lang.String contractName) {
		this.contractName = contractName;
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
	public BaseProcureContractSwf() {
	
	}
	
	/**
	 * 构造函数.
	 * @param id
	 */
	public BaseProcureContractSwf(String contractId) {
		this.contractId = contractId;
	}
}
