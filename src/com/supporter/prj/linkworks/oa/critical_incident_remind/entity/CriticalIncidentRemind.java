package com.supporter.prj.linkworks.oa.critical_incident_remind.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.util.CodeTable;
import com.supporter.prj.linkworks.oa.critical_incident_remind.entity.base.BaseCriticalIncidentRemind;

@Entity
@Table(name="OA_CRITICAL_INCIDENT_REMIND"
    ,schema=""
)

public class CriticalIncidentRemind extends BaseCriticalIncidentRemind{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int DRAFT = 0; //草稿
    public static final int EFFECTIVE = 1; //生效（可以撤销生效） 
//    public static final int EFFECTIVEANDREMINDING = 2; //生效并提醒中（可以撤销失效的同时撤回待办）
//    public static final int EFFECTIVEANDFINISH = 3; //生效并办结（不能失效、不能删除）
    
    
    /**
	 * 获取重要事件提醒的状态码表.
	 * @return
	 */
	public static CodeTable getStatusCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT, "草稿");
		lcdtbl_Return.insertItem(EFFECTIVE, "生效");
//		lcdtbl_Return.insertItem(EFFECTIVEANDREMINDING, "生效并提醒中");
//		lcdtbl_Return.insertItem(EFFECTIVEANDFINISH, "生效并办结");
		return lcdtbl_Return;
	}
	
	public static final int NOTREMINDED = 0; //未提醒
    public static final int FINISH = 1; //已发送 
//    public static final int REMINDING_02 = 2; //提醒中（待办已处理） 
//    public static final int FINISH = 3; //提醒结束（已办结）
	
	  /**
	 * 获取重要事件提醒的提醒状态码表.
	 * @return
	 */
	public static CodeTable getReminderStatusCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(NOTREMINDED, "未提醒");
		lcdtbl_Return.insertItem(FINISH, "已发送");
//		lcdtbl_Return.insertItem(REMINDING_02, "提醒中（待办已处理）");
//		lcdtbl_Return.insertItem(FINISH, "已办结");
		return lcdtbl_Return;
	}
	
	
	  /**
	 * 获取重要事件提醒是否重复码表.
	 * @return
	 */
	public static CodeTable getIsRepeatRemindCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem("1", "不重复");
		lcdtbl_Return.insertItem("2", "重复");
		return lcdtbl_Return;
	}
       
	public static Map<String, String> getIsRepeatRemindMap(){
    	Map<String, String> map = new LinkedHashMap<String, String>();
    	map.put("1", "不重复");
    	map.put("2", "重复");
    	
		return map;
	}
    
    
    private boolean add;

    @Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}
	
	public String statusDesc;

	@Transient
	public String getStatusDesc() {
		return getStatusCodeTable().getDisplay(this.getStatus());
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
	
	public String reminderStatusDesc;

	@Transient
	public String getReminderStatusDesc() {
		return getReminderStatusCodeTable().getDisplay(this.getReminderStatus());
//		return reminderStatusDesc;
	}

	public void setReminderStatusDesc(String reminderStatusDesc) {
		this.reminderStatusDesc = reminderStatusDesc;
	}
	
	public String isRepeatRemindDesc;//格式化是否重复
	@Transient
	public String getIsRepeatRemindDesc() {
		return getIsRepeatRemindCodeTable().getDisplay(this.getIsRepeatRemind());
	}

	public void setIsRepeatRemindDesc(String isRepeatRemindDesc) {
		this.isRepeatRemindDesc = isRepeatRemindDesc;
	}
	
	//附件查看能用到
	private String files;

	@Transient
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}



}
