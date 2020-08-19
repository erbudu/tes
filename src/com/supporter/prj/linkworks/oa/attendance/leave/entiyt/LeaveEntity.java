/**
 * 
 */
package com.supporter.prj.linkworks.oa.attendance.leave.entiyt;


import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.attendance.leave.entiyt.base.LeaveBaseEntity;

@Entity
@Table(name="OA_LEAVE", schema = "SUPP_APP")
public class LeaveEntity extends LeaveBaseEntity implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final String MODULE_ID = "LEAVE";//应用编号
	public static final String CODETABLE_ID = "LEAVE_TYPE";//码表编号
	public static final String DOMAIN_OBJECT_ID = "LeaveEntity";//业务对象编号

	@Column(name="EMP_LEVEL")
	private String empLevel;
	
	public LeaveEntity(String leaveId) {
		super(leaveId);
	}

	public LeaveEntity() { }
	
	@Transient
	private String condition;
	
	@Transient
	private String statusDesc;
	
	/*以下为流程状态对应的常量值*/
	public static final int DEFAULT = 0; //草稿
	public static final int PROCESSING = 1; //审批中
	public static final int COMPLETED = 2; 	//审批完成
	
	
	/**
	 * 流程码表
	 * 作用：封装流程状态int类型对应的字符串类型
	 * @return 流程Map
	 */
	public static Map<Integer,String> getStatusMap(){
		HashMap<Integer,String> map = new HashMap<Integer, String>();
		map.put(DEFAULT, "草稿");
		map.put(PROCESSING,"审批中");
		map.put(COMPLETED, "审批完成");
		return map;
		
	}
	
	/**
	 * 描述：获取流程状态
	 * 作用：获取流程Map中流程状态int值对应的字符串
	 * @return 流程状态对应的字符串
	 */
	public String getStatusDesc() {
		return getStatusMap().get(this.getStatus());
	}
	
	@Transient
	private String isSellingLeaveStatus;

	/*以下为是否销假字段对应的int常量值*/
	public static final int SELLING_NO = 0; //未销假
	public static final int SELLING_YES = 1; //已销假
	
	/**
	 * 
	 * 是否销假状态Map<Integer,String>  integer：是否销假字段值     String： 是否销假字段值对应的字符串
	 * 作用：用于封装是否销假状态字段值和对应字符串的对应关系
	 * @param <V>
	 * @return map 封装集合
	 */
	public static  Map<Integer, String> getIsSellingLeaveStatusMap() {
		HashMap<Integer,String> map = new HashMap<Integer,String>();
		map.put(SELLING_NO, "否");
		map.put(SELLING_YES, "是");
		return map;
		
	}
	
	/**
	 * 描述：次方法用于获取是否销假字段值在映射中对应的字符串值
	 * @return
	 */
	public String getIsSellingLeaveStatus() {
		return getIsSellingLeaveStatusMap().get(this.getIsSellingOff());
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
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
		return this.getLeaveId();
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

	@Transient
	public String getKeyProps() {
		return "leave_id";
	}

	@Transient
	public String getKeyValues() {
		return this.getLeaveId();
	}
	
	
}
