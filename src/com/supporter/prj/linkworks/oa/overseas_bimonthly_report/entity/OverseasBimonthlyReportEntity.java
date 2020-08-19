/**
 * 
 */
package com.supporter.prj.linkworks.oa.overseas_bimonthly_report.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.constant.OverseasBimonthlyReportConstant;
import com.supporter.prj.linkworks.oa.overseas_bimonthly_report.entity.base.BaseOverseasBimonthlyReportEntity;

/**
 *<p>Title: OverseasbimonthlyreportEntity</p>
 *<p>Description: 该类为 境外机构双月报审批  实体类扩展类</p>
 *<p>Company: 东华后盾</p>
 * @author YUEYUN
 * @date 2019年12月23日
 * 
 */

@Entity
@Table(name="OA_OVERSEAS_BIMONTHLY_REPORT",schema = "")
public  class OverseasBimonthlyReportEntity extends BaseOverseasBimonthlyReportEntity{

	
	private static final long serialVersionUID = 1L;
	
	/*以下两个字段用于高级查询*/
	private String startDate;
	private String endDate;
	
	
	/**
	 * get 开始时间
	 * @return the startDate
	 */
	@Transient
	public String getStartDate() {
		return startDate;
	}

	/**
	 * set 开始时间
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * set 结束时间
	 * @return the endDate
	 */
	@Transient
	public String getEndDate() {
		return endDate;
	}

	
	/**
	 * set 结束时间
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Transient
	public String getStatusDesc() {//审批状态显示名称
		return OverseasBimonthlyReportConstant.stutsDesc(this.getStatus());
	}
	
	/** This method is constructor*/
	public OverseasBimonthlyReportEntity() {
		super();
	}

	/* 以下为流程中用到的字段信息 */
	@Transient
	public int getDbYear(){//年度
		return 0;
	}
	
	@Transient
	public String getModuleId(){//应用编号
		return OverseasBimonthlyReportConstant.MODULE_ID;
	}
	
	@Transient
	public String getDomainObjectId(){//业务对象编号
		return OverseasBimonthlyReportConstant.DOMAIN_OBJECT_ID;
		
	}
	
	@Transient
	public String getEntityId(){//实体类业务单主键
		return this.getId();
	}
	
	@Transient
	public String getId() {
		return this.getReportId();
	}
	
	@Transient
	public String getCompanyNo(){//公司名称
		
		return "";
	}
	
	@Transient
	public String getModuleBusiType(){
		return "";
	}
	
	@Transient
	public String getEntityName(){
		return this.getClass().getName();
	}
	

}
