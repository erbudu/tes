/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.approving.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *<p>Title: 对外提报及获批-批复结果 业务单实体</p>
 *<p>Description: 跟数据库字段一一对应</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月2日
 * 
 */
@MappedSuperclass
public class BaseReportResultEntity {
	
	@Id  
	@Column(name="REPORT_RESULT_ID", nullable=false, length=32)
	private String reportResultId;//主键
	
	@Column(name="REPORT_START_ID" ,length=32)
	private String reportStartId;//外键 （启动对外提报主键）
	
	/*The following is project information*/
	@Column(name="PRJ_ID",length=32)
	private String prjId;
	
	@Column(name="PRJ_NO",length=32)
	private String prjNo;//项目编号 Varchar2(32)
	
	@Column(name="PRJNAME_C",length=64)
	private String prjNameC;//项目中文名称 Varchar2(64)
	
	@Column(name="PRJNAME_E",length=64)
	private String prjNameE;//项目英文名称Varchar2(64)
	
	/*The following is approving information*/
	
	@Column(name="ACTUAL_START_DATE",length=64)
	private String actualStartDate;//实际报审开始日期 Varchar2(64)
	
	@Column(name="ACTUAL_END_DATE",length=64)
	private String actualEndDate;//实际报审结束日期  Varchar2(64)
	
	/*The following is result information*/
	
	@Column(name="RESULT_TYPE_ID")
	private Integer resultTypeID;//报审结果类型ID
	
	@Column(name="RESULT_TYPE_NAME")
	private String resultTypeName;//报审结果显示名称

	
	/*The following is construction method*/
	
	public BaseReportResultEntity() {
		
	}
	
	public BaseReportResultEntity(String reportResultId) {
		this.reportResultId=reportResultId;
	}
	/**
	 * @return the reportResultId
	 */
	public String getReportResultId() {
		return reportResultId;
	}

	/**
	 * @param reportResultId the reportResultId to set
	 */
	public void setReportResultId(String reportResultId) {
		this.reportResultId = reportResultId;
	}

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
	 * @return the actualStartDate
	 */
	public String getActualStartDate() {
		return actualStartDate;
	}

	/**
	 * @param actualStartDate the actualStartDate to set
	 */
	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	/**
	 * @return the actualEndDate
	 */
	public String getActualEndDate() {
		return actualEndDate;
	}

	/**
	 * @param actualEndDate the actualEndDate to set
	 */
	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
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
	 * @return the resultTypeID
	 */
	public Integer getResultTypeID() {
		return resultTypeID;
	}

	/**
	 * @param resultTypeID the resultTypeID to set
	 */
	public void setResultTypeID(Integer resultTypeID) {
		this.resultTypeID = resultTypeID;
	}

	/**
	 * @return the resultTypeName
	 */
	public String getResultTypeName() {
		return resultTypeName;
	}

	/**
	 * @param resultTypeName the resultTypeName to set
	 */
	public void setResultTypeName(String resultTypeName) {
		this.resultTypeName = resultTypeName;
	}
	
	

}
