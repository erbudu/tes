package com.supporter.prj.linkworks.oa.abroad.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.abroad.entity.base.BaseAbroadRealDate;


/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @version V1.0   
 *
 */
@Entity
@Table(name="OA_ABROAD_REAL_DATE",schema="")
public class AbroadRealDate extends BaseAbroadRealDate{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 审批状态
	 */
	public static final int DRAFT = 0; //草稿
    public static final int PROCESSING = 1; //审核中 
    public static final int ARCHIVED = 3; //已归档

    //最晚提交日期的时间
    @Transient
    private String maxSubmitedDate;
    
    //前往国家
    @Transient
    private String tgtCountries;
    
  	//部门名称
    @Transient
    private String deptName;
    
    //实际出国时间流程
    @Transient
    private boolean realPrc = true;
    
    @Transient
    private String deptId;
    
    public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getMaxSubmitedDate() {
		return maxSubmitedDate;
	}

	public void setMaxSubmitedDate(String maxSubmitedDate) {
		this.maxSubmitedDate = maxSubmitedDate;
	}

	public int getDbYear(){
	    return 0;
	 }
	
	public String getDomainObjectId(){
	    return "AbroadRealDate";
	}
	
	public String getEntityName() {
		return getClass().getName();
	}

	public String getModuleId() {
		return "OAABROAD";
	}

	public String getModuleBusiType(){
		 return "";
	}

	public String getTgtCountries() {
		return tgtCountries;
	}

	public void setTgtCountries(String tgtCountries) {
		this.tgtCountries = tgtCountries;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public boolean getRealPrc() {
		return realPrc;
	}

	public void setRealPrc(boolean realPrc) {
		this.realPrc = realPrc;
	}
	
}
