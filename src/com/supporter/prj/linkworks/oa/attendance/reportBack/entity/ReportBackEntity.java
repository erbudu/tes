/**
 * 
 */
package com.supporter.prj.linkworks.oa.attendance.reportBack.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.attendance.reportBack.entity.base.ReportBackBaseEntity;

/**
 * @title 销假单数据模型操作类
 * @author YUEYUN
 * @date 2019年7月19日
 * 
 */
@Entity
@Table(name="OA_REPORT_BACK", schema = "SUPP_APP")
public class ReportBackEntity extends ReportBackBaseEntity implements java.io.Serializable{

	
	private static final long serialVersionUID = 1L;
	
	//此常量为应用编号 
	public static final String MODULE_ID = "REPORTBACK";
	
	//此常量为应用中业务对象编号
	public static final String DOMAIN_OBJECT_ID = "ReportBack";
	
	public ReportBackEntity() {};
	
	public ReportBackEntity(String reportBackId) {super(reportBackId);}
	
	@Column(name="EMP_LEVEL")
	private String empLevel;//此变量为职位等级 流程中条件判断用到的变量
	
	@Column(name="DEPT_ID",length=32)
	private String deptId;//此变量为部门id 流程中用到的变量						
	
	@Column(name="DEPT_NAME",length=128)
	private String deptName;//此变量为部门名称 流程中用到的变量
	
	//*****申明流程所用属性开始**********************************************
	
	@Transient
	public int getDbYear(){
		return 0;
	}
	
	@Transient
	public String getModuleId(){
		return MODULE_ID;
	}
	
	@Transient
	public String getDomainObjectId(){
		return DOMAIN_OBJECT_ID;
		
	}
	
	@Transient
	public String getEntityId(){
		return this.getId();
	}
	
	@Transient
	public String getId() {
		return this.getReportBackId();
	}
	
	@Transient
	public String getCompanyNo(){
		
		return this.getDeptId();
	}
	
	@Transient
	public String getModuleBusiType(){
		return "";
	}
	
	@Transient
	public String getEntityName(){
		return this.getClass().getName();
	}

	//*****申明流程所用属性结束**********************************************
	
	public String getEmpLevel() {
		return empLevel;
	}

	public void setEmpLevel(String empLevel) {
		this.empLevel = empLevel;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Transient
	public String getKeyProps() {
		return "report_back_id";
	}

	@Transient
	public String getKeyValues() {
		return this.getReportBackId();
	}

}
