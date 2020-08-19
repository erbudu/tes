package com.supporter.prj.cneec.todo.entity.base;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**   
 * @Title: Entity
 * @date 2017-08-15 09:04:58
 * @version V1.0   
 *
 */
@MappedSuperclass
public  class BaseCneecHistTodo  implements Serializable {
	private static final long serialVersionUID = 1L;
	//ID.
	@Id
	@Column(name = "TODO_ID",nullable = false,length = 32)
	private java.lang.String id;
	//MSG_TITLE.
	@Column(name = "MSG_TITLE",nullable = true,length = 300)
	private java.lang.String msgTitle;
	//PROC_ID.
	@Column(name = "PROC_ID",nullable = true,length = 32)
	private java.lang.String procId;
	//TASK_INSTANCE_ID.
	@Column(name = "TASK_INSTANCE_ID",nullable = true,length = 32)
	private java.lang.String taskInstanceId;
	//CREATED_DATE.
	@Column(name = "CREATED_DATE",nullable = true)
	private java.util.Date createdDate;
	//MSG_TYPE.
	@Column(name = "MSG_TYPE",nullable = true,length = 32)
	private java.lang.String msgType;
	//PERSON_ID.
	@Column(name = "PERSON_ID",nullable = true,length = 32)
	private java.lang.String personId;
	//CONSIGNER_NAME.
	@Column(name = "CONSIGNER_NAME",nullable = true,length = 64)
	private java.lang.String consignerName;
	//ASSIGNER_NAME.
	@Column(name = "ASSIGNER_NAME",nullable = true,length = 64)
	private java.lang.String assignerName;
	//WEBAPP_NAME.
	@Column(name = "WEBAPP_NAME",nullable = true,length = 255)
	private java.lang.String webappName;
	//MODULE_ID.
	@Column(name = "MODULE_ID",nullable = true,length = 12)
	private java.lang.String moduleId;
	//NODE_NAME.
	@Column(name = "NODE_NAME",nullable = true,length = 64)
	private java.lang.String nodeName;
	//PROC_DEF_NAME.
	@Column(name = "PROC_DEF_NAME",nullable = true,length = 64)
	private java.lang.String procDefName;
	//DATA_VIEWER_URL.
	@Column(name = "DATA_VIEWER_URL",nullable = true,length = 255)
	private java.lang.String dataViewerUrl;
	

	
	
	/**
	 *方法: 取得ID.
	 *@return: java.lang.String  ID
	 */
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置ID.
	 *@param: java.lang.String  ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得MSG_TITLE.
	 *@return: java.lang.String  MSG_TITLE
	 */
	public java.lang.String getMsgTitle(){
		return this.msgTitle;
	}

	/**
	 *方法: 设置MSG_TITLE.
	 *@param: java.lang.String  MSG_TITLE
	 */
	public void setMsgTitle(java.lang.String msgTitle){
		this.msgTitle = msgTitle;
	}
	/**
	 *方法: 取得PROC_ID.
	 *@return: java.lang.String  PROC_ID
	 */
	public java.lang.String getProcId(){
		return this.procId;
	}

	/**
	 *方法: 设置PROC_ID.
	 *@param: java.lang.String  PROC_ID
	 */
	public void setProcId(java.lang.String procId){
		this.procId = procId;
	}
	/**
	 *方法: 取得TASK_INSTANCE_ID.
	 *@return: java.lang.String  TASK_INSTANCE_ID
	 */
	public java.lang.String getTaskInstanceId(){
		return this.taskInstanceId;
	}

	/**
	 *方法: 设置TASK_INSTANCE_ID.
	 *@param: java.lang.String  TASK_INSTANCE_ID
	 */
	public void setTaskInstanceId(java.lang.String taskInstanceId){
		this.taskInstanceId = taskInstanceId;
	}
	/**
	 *方法: 取得CREATED_DATE.
	 *@return: java.util.Date  CREATED_DATE
	 */
	public java.util.Date getCreatedDate(){
		return this.createdDate;
	}

	/**
	 *方法: 设置CREATED_DATE.
	 *@param: java.util.Date  CREATED_DATE
	 */
	public void setCreatedDate(java.util.Date createdDate){
		this.createdDate = createdDate;
	}
	/**
	 *方法: 取得MSG_TYPE.
	 *@return: java.lang.String  MSG_TYPE
	 */
	public java.lang.String getMsgType(){
		return this.msgType;
	}

	/**
	 *方法: 设置MSG_TYPE.
	 *@param: java.lang.String  MSG_TYPE
	 */
	public void setMsgType(java.lang.String msgType){
		this.msgType = msgType;
	}
	 
	public java.lang.String getPersonId(){
		return this.personId;
	} 
	public void setPersonId(java.lang.String paramVal){
		this.personId = paramVal;
	}

	/**
	 *方法: 取得CONSIGNER_NAME.
	 *@return: java.lang.String  CONSIGNER_NAME
	 */
	public java.lang.String getConsignerName(){
		return this.consignerName;
	}

	/**
	 *方法: 设置CONSIGNER_NAME.
	 *@param: java.lang.String  CONSIGNER_NAME
	 */
	public void setConsignerName(java.lang.String consignerName){
		this.consignerName = consignerName;
	}

	/**
	 *方法: 取得ASSIGNER_NAME.
	 *@return: java.lang.String  ASSIGNER_NAME
	 */
	public java.lang.String getAssignerName(){
		return this.assignerName;
	}

	/**
	 *方法: 设置ASSIGNER_NAME.
	 *@param: java.lang.String  ASSIGNER_NAME
	 */
	public void setAssignerName(java.lang.String assignerName){
		this.assignerName = assignerName;
	}
	/**
	 *方法: 取得WEBAPP_NAME.
	 *@return: java.lang.String  WEBAPP_NAME
	 */
	public java.lang.String getWebappName(){
		return this.webappName;
	}

	/**
	 *方法: 设置WEBAPP_NAME.
	 *@param: java.lang.String  WEBAPP_NAME
	 */
	public void setWebappName(java.lang.String webappName){
		this.webappName = webappName;
	}
	/**
	 *方法: 取得MODULE_ID.
	 *@return: java.lang.String  MODULE_ID
	 */
	public java.lang.String getModuleId(){
		return this.moduleId;
	}

	/**
	 *方法: 设置MODULE_ID.
	 *@param: java.lang.String  MODULE_ID
	 */
	public void setModuleId(java.lang.String moduleId){
		this.moduleId = moduleId;
	}
	/**
	 *方法: 取得NODE_NAME.
	 *@return: java.lang.String  NODE_NAME
	 */
	public java.lang.String getNodeName(){
		return this.nodeName;
	}

	/**
	 *方法: 设置NODE_NAME.
	 *@param: java.lang.String  NODE_NAME
	 */
	public void setNodeName(java.lang.String nodeName){
		this.nodeName = nodeName;
	}
	/**
	 *方法: 取得PROC_DEF_NAME.
	 *@return: java.lang.String  PROC_DEF_NAME
	 */
	public java.lang.String getProcDefName(){
		return this.procDefName;
	}

	/**
	 *方法: 设置PROC_DEF_NAME.
	 *@param: java.lang.String  PROC_DEF_NAME
	 */
	public void setProcDefName(java.lang.String procDefName){
		this.procDefName = procDefName;
	}
	/**
	 *方法: 取得DATA_VIEWER_URL.
	 *@return: java.lang.String  DATA_VIEWER_URL
	 */
	public java.lang.String getDataViewerUrl(){
		return this.dataViewerUrl;
	}

	/**
	 *方法: 设置DATA_VIEWER_URL.
	 *@param: java.lang.String  DATA_VIEWER_URL
	 */
	public void setDataViewerUrl(java.lang.String dataViewerUrl){
		this.dataViewerUrl = dataViewerUrl;
	}
	 
	
	
	/**
	 * 无参构造函数.
	 */
	public BaseCneecHistTodo(){
	
	}
	
	/**
	 * 构造函数.
	 * @param 
	 */
	public BaseCneecHistTodo(String id){
		this.id = id;
	}
	
}
