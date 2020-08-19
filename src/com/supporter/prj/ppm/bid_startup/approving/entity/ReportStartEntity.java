/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.approving.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.bid_startup.approving.entity.base.BaseReportStartEntity;
import com.supporter.prj.ppm.bid_startup.preparation.constant.StartContant;

/**
 *<p>Title: 投议标备案及许可-对外提报及获批 实体类扩展类</p>
 *<p>Description: 主要为业务实体类的扩展类，包括一些业务中用到的其他字段类型</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月2日
 */
@Entity
@Table(name="PPM_BIDING_REPORT_START",schema = "")
public class ReportStartEntity extends BaseReportStartEntity{
	
	
	@Transient
	private boolean newBuild; //用于标识是否新建 注：不与数据库做映射
	
	
	/*-----------以下为流程中用到的流程变量-----------------------*/
	@Transient
	public int getDbYear(){
		return 0;
	}
	
	@Transient
	public String getModuleId(){
		return StartContant.MODULE_ID;
	}
	
	@Transient
	public String getDomainObjectId(){
		return StartContant.DOMAIN_OBJECT_ID;
		
	}
	
	@Transient
	public String getEntityId(){
		return this.getId();
	}
	
	@Transient
	public String getId() {
		return this.getReportStartId();
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
	
	/*--------------------以下为声明属性的set、get方法----------------------------------------*/
	/**
	 * @return the newBuild
	 */
	public boolean getNewBuild() {
		return newBuild;
	}

	/**
	 * @param newBuild the newBuild to set
	 */
	public void setNewBuild(boolean newBuild) {
		this.newBuild = newBuild;
	}
	
	
	/*The following is construction method*/

	public ReportStartEntity() {
		
	}
	
	public ReportStartEntity(String reportStartId) {
		super(reportStartId);
		
	}

	
}
