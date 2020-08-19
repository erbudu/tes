package com.supporter.prj.linkworks.oa.history_exam_record.entity.base;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.supporter.prj.linkworks.oa.history_exam_record.entity.VCneecExam;

/**   
 * @Title: Entity
 * @Description: CNEEC_V_SWF_EXAM,字段与数据库字段一一对应.
 * @author T
 * @date 2017-09-30 10:27:58
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseVCneecExam  implements Serializable {
	private static final long serialVersionUID = 1L;
	//EXAM_ID.
	@Id
	@GeneratedValue(generator = "assigned")
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@Column(name = "EXAM_ID",nullable = false,precision = 10)
	private long examId;
	//TABLE_NAME.
	@Column(name = "TABLE_NAME",nullable = true,length = 64)
	private java.lang.String tableName;
	//ID_FIELD_NAME.
	@Column(name = "ID_FIELD_NAME",nullable = true,length = 64)
	private java.lang.String idFieldName;
	//ID_FIELD_VALUE.
	@Column(name = "ID_FIELD_VALUE",nullable = true,precision = 10)
	private int idFieldValue;
	//EMP_ID.
	@Column(name = "EMP_ID",nullable = false,precision = 10)
	private int empId;
	//EMP_NAME.
	@Column(name = "EMP_NAME",nullable = true,length = 32)
	private java.lang.String empName;
	//OPINION_DESC.
	@Column(name = "OPINION_DESC",nullable = true,length = 4000)
	private java.lang.String opinionDesc;
	//EXAM_TYPE.
	@Column(name = "EXAM_TYPE",nullable = true,length = 255)
	private java.lang.String examType;
	//NOTES.
	@Column(name = "NOTES",nullable = true,length = 255)
	private java.lang.String notes;
	//CREATED_DATE.
	@Column(name = "CREATED_DATE",nullable = true,length = 27)
	private java.util.Date createdDate;
	//CREATED_BY.
	@Column(name = "CREATED_BY",nullable = true,precision = 10)
	private int createdBy;
	//MODIFIED_DATE.
	@Column(name = "MODIFIED_DATE",nullable = true,length = 27)
	private java.util.Date modifiedDate;
	//MODIFIED_BY.
	@Column(name = "MODIFIED_BY",nullable = true,precision = 10)
	private int modifiedBy;
	//EXAM_DATE.
	@Column(name = "EXAM_DATE",nullable = true,length = 27)
	private java.util.Date examDate;
	//EXAM_RESULT.
	@Column(name = "EXAM_RESULT",nullable = true,precision = 10)
	private int examResult;
	//EXAM_RESULT_DESC.
	@Column(name = "EXAM_RESULT_DESC",nullable = true,length = 255)
	private java.lang.String examResultDesc;
	//WF_PROC_ID.
	@Column(name = "WF_PROC_ID",nullable = true,precision = 10)
	private int wfProcId;
	//WF_ACT_ID.
	@Column(name = "WF_ACT_ID",nullable = true,precision = 10)
	private int wfActId;
	//WF_ACT_INDEX.
	@Column(name = "WF_ACT_INDEX",nullable = true,precision = 10)
	private int wfActIndex;
	//WF_ACT_NAME.
	@Column(name = "WF_ACT_NAME",nullable = true,length = 64)
	private java.lang.String wfActName;
	//FLAG.
	@Column(name = "FLAG",nullable = true,length = 2)
	private java.lang.String flag;
	
	/**
	 *方法: 取得EXAM_ID.
	 *@return: int  EXAM_ID
	 */
	public long getExamId(){
		return this.examId;
	}

	/**
	 *方法: 设置EXAM_ID.
	 *@param: int  EXAM_ID
	 */
	public void setExamId(int examId){
		this.examId = examId;
	}
	/**
	 *方法: 取得TABLE_NAME.
	 *@return: java.lang.String  TABLE_NAME
	 */
	public java.lang.String getTableName(){
		return this.tableName;
	}

	/**
	 *方法: 设置TABLE_NAME.
	 *@param: java.lang.String  TABLE_NAME
	 */
	public void setTableName(java.lang.String tableName){
		this.tableName = tableName;
	}
	/**
	 *方法: 取得ID_FIELD_NAME.
	 *@return: java.lang.String  ID_FIELD_NAME
	 */
	public java.lang.String getIdFieldName(){
		return this.idFieldName;
	}

	/**
	 *方法: 设置ID_FIELD_NAME.
	 *@param: java.lang.String  ID_FIELD_NAME
	 */
	public void setIdFieldName(java.lang.String idFieldName){
		this.idFieldName = idFieldName;
	}
	/**
	 *方法: 取得ID_FIELD_VALUE.
	 *@return: int  ID_FIELD_VALUE
	 */
	public int getIdFieldValue(){
		return this.idFieldValue;
	}

	/**
	 *方法: 设置ID_FIELD_VALUE.
	 *@param: int  ID_FIELD_VALUE
	 */
	public void setIdFieldValue(int idFieldValue){
		this.idFieldValue = idFieldValue;
	}
	/**
	 *方法: 取得EMP_ID.
	 *@return: int  EMP_ID
	 */
	public int getEmpId(){
		return this.empId;
	}

	/**
	 *方法: 设置EMP_ID.
	 *@param: int  EMP_ID
	 */
	public void setEmpId(int empId){
		this.empId = empId;
	}
	/**
	 *方法: 取得EMP_NAME.
	 *@return: java.lang.String  EMP_NAME
	 */
	public java.lang.String getEmpName(){
		return this.empName;
	}

	/**
	 *方法: 设置EMP_NAME.
	 *@param: java.lang.String  EMP_NAME
	 */
	public void setEmpName(java.lang.String empName){
		this.empName = empName;
	}
	/**
	 *方法: 取得OPINION_DESC.
	 *@return: java.lang.String  OPINION_DESC
	 */
	public java.lang.String getOpinionDesc(){
		return this.opinionDesc;
	}

	/**
	 *方法: 设置OPINION_DESC.
	 *@param: java.lang.String  OPINION_DESC
	 */
	public void setOpinionDesc(java.lang.String opinionDesc){
		this.opinionDesc = opinionDesc;
	}
	/**
	 *方法: 取得EXAM_TYPE.
	 *@return: java.lang.String  EXAM_TYPE
	 */
	public java.lang.String getExamType(){
		return this.examType;
	}

	/**
	 *方法: 设置EXAM_TYPE.
	 *@param: java.lang.String  EXAM_TYPE
	 */
	public void setExamType(java.lang.String examType){
		this.examType = examType;
	}
	/**
	 *方法: 取得NOTES.
	 *@return: java.lang.String  NOTES
	 */
	public java.lang.String getNotes(){
		return this.notes;
	}

	/**
	 *方法: 设置NOTES.
	 *@param: java.lang.String  NOTES
	 */
	public void setNotes(java.lang.String notes){
		this.notes = notes;
	}
	/**
	 *方法: 取得CREATED_DATE.
	 *@return: java.lang.String  CREATED_DATE
	 */
	public java.util.Date getCreatedDate(){
		return this.createdDate;
	}

	/**
	 *方法: 设置CREATED_DATE.
	 *@param: java.lang.String  CREATED_DATE
	 */
	public void setCreatedDate(java.util.Date createdDate){
		this.createdDate = createdDate;
	}
	/**
	 *方法: 取得CREATED_BY.
	 *@return: int  CREATED_BY
	 */
	public int getCreatedBy(){
		return this.createdBy;
	}

	/**
	 *方法: 设置CREATED_BY.
	 *@param: int  CREATED_BY
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}
	/**
	 *方法: 取得MODIFIED_DATE.
	 *@return: java.lang.String  MODIFIED_DATE
	 */
	public java.util.Date getModifiedDate(){
		return this.modifiedDate;
	}

	/**
	 *方法: 设置MODIFIED_DATE.
	 *@param: java.lang.String  MODIFIED_DATE
	 */
	public void setModifiedDate(java.util.Date modifiedDate){
		this.modifiedDate = modifiedDate;
	}
	/**
	 *方法: 取得MODIFIED_BY.
	 *@return: int  MODIFIED_BY
	 */
	public int getModifiedBy(){
		return this.modifiedBy;
	}

	/**
	 *方法: 设置MODIFIED_BY.
	 *@param: int  MODIFIED_BY
	 */
	public void setModifiedBy(int modifiedBy){
		this.modifiedBy = modifiedBy;
	}
	/**
	 *方法: 取得EXAM_DATE.
	 *@return: java.lang.String  EXAM_DATE
	 */
	public java.util.Date getExamDate(){
		return this.examDate;
	}

	/**
	 *方法: 设置EXAM_DATE.
	 *@param: java.lang.String  EXAM_DATE
	 */
	public void setExamDate(java.util.Date examDate){
		this.examDate = examDate;
	}
	/**
	 *方法: 取得EXAM_RESULT.
	 *@return: int  EXAM_RESULT
	 */
	public int getExamResult(){
		return this.examResult;
	}

	/**
	 *方法: 设置EXAM_RESULT.
	 *@param: int  EXAM_RESULT
	 */
	public void setExamResult(int examResult){
		this.examResult = examResult;
	}
	/**
	 *方法: 取得EXAM_RESULT_DESC.
	 *@return: java.lang.String  EXAM_RESULT_DESC
	 */
	public java.lang.String getExamResultDesc(){
		return this.examResultDesc;
	}

	/**
	 *方法: 设置EXAM_RESULT_DESC.
	 *@param: java.lang.String  EXAM_RESULT_DESC
	 */
	public void setExamResultDesc(java.lang.String examResultDesc){
		this.examResultDesc = examResultDesc;
	}
	/**
	 *方法: 取得WF_PROC_ID.
	 *@return: int  WF_PROC_ID
	 */
	public int getWfProcId(){
		return this.wfProcId;
	}

	/**
	 *方法: 设置WF_PROC_ID.
	 *@param: int  WF_PROC_ID
	 */
	public void setWfProcId(int wfProcId){
		this.wfProcId = wfProcId;
	}
	/**
	 *方法: 取得WF_ACT_ID.
	 *@return: int  WF_ACT_ID
	 */
	public int getWfActId(){
		return this.wfActId;
	}

	/**
	 *方法: 设置WF_ACT_ID.
	 *@param: int  WF_ACT_ID
	 */
	public void setWfActId(int wfActId){
		this.wfActId = wfActId;
	}
	/**
	 *方法: 取得WF_ACT_INDEX.
	 *@return: int  WF_ACT_INDEX
	 */
	public int getWfActIndex(){
		return this.wfActIndex;
	}

	/**
	 *方法: 设置WF_ACT_INDEX.
	 *@param: int  WF_ACT_INDEX
	 */
	public void setWfActIndex(int wfActIndex){
		this.wfActIndex = wfActIndex;
	}
	/**
	 *方法: 取得WF_ACT_NAME.
	 *@return: java.lang.String  WF_ACT_NAME
	 */
	public java.lang.String getWfActName(){
		return this.wfActName;
	}

	/**
	 *方法: 设置WF_ACT_NAME.
	 *@param: java.lang.String  WF_ACT_NAME
	 */
	public void setWfActName(java.lang.String wfActName){
		this.wfActName = wfActName;
	}
	/**
	 *方法: 取得FLAG.
	 *@return: java.lang.String  FLAG
	 */
	public java.lang.String getFlag(){
		return this.flag;
	}

	/**
	 *方法: 设置FLAG.
	 *@param: java.lang.String  FLAG
	 */
	public void setFlag(java.lang.String flag){
		this.flag = flag;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BaseVCneecExam(){
	
	}
	
	/**
	 * 构造函数.
	 * @param examId
	 */
	public BaseVCneecExam(long examId){
		this.examId = examId;
	}
	
	public boolean equals(Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof VCneecExam)) return false;
		else {
			VCneecExam objInst = (VCneecExam) obj;
			if (this.getExamId() <= 0) return false;
			else return (this.getExamId() == objInst.getExamId());
		}
	}

	@Transient
	private int hashCode = Integer.MIN_VALUE;
	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (this.getExamId() <= 0) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + Long.toString(this.getExamId()).hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
}
