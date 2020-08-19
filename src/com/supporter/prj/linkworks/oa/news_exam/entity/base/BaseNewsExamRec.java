package com.supporter.prj.linkworks.oa.news_exam.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.supporter.prj.linkworks.oa.news_exam.entity.NewsExamRec;

/**   
 * @Title: Entity
 * @Description: OA_NEWS_EXAM_REC,字段与数据库字段一一对应
 * @author linda
 * @date 2017-11-14 13:46:38
 * @version V1.0   
 *
 */
@MappedSuperclass
public  class BaseNewsExamRec  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**REC_ID*/
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name ="REC_ID",nullable=false,length=32)
	private java.lang.String recId;
	
	/**DEPT_ID*/
	@Column(name ="DEPT_ID",nullable=true,length=32)
	private java.lang.String deptId;
	
	/**DEPT_NAME*/
	@Column(name ="DEPT_NAME",nullable=true,length=64)
	private java.lang.String deptName;
	
	/**APPLY_DATE*/
	@Column(name ="APPLY_DATE",nullable=true)
	private java.util.Date applyDate;
	
	/**PROPOSERID*/
	@Column(name ="PROPOSER_ID",nullable=true,length=32)
	private java.lang.String proposerId;
	
	/**PROPOSERNAME*/
	@Column(name ="PROPOSER_NAME",nullable=true,length=64)
	private java.lang.String proposerName;
	
	/**PHONE*/
	@Column(name ="PHONE",nullable=true,length=32)
	private java.lang.String phone;
	
	/**NEWS_TITLE*/
	@Column(name ="NEWS_TITLE",nullable=true,length=128)
	private java.lang.String newsTitle;
	
	/**PUBLISH_TO*/
	@Column(name ="PUBLISH_TO",nullable=true,length=64)
	private String publishTo;
	
	/**INNER_PUBLISH_TO*/
	@Column(name ="INNER_PUBLISH_TO",nullable=true,length=64)
	private String innerPublishTo;
	
	/**SUBMIT_TO*/
	@Column(name ="SUBMIT_TO",nullable=true,length=64)
	private String submitTo;
	
	/**EDIT_TYPE*/
	@Column(name ="EDIT_TYPE",nullable=true,precision=10)
	private int editType;
	
	/**REC_STATUS*/
	@Column(name ="REC_STATUS",nullable=true,precision=10)
	private int recStatus;
	
	@Column(name ="proc_id",nullable=true,length=32)
	private String procId;
	
	@Column(name = "need_con_sign",nullable = true,length = 1)
	@Type(type = "true_false")
	private boolean needConSign;
	
	@Column(name ="signer_ids",nullable=true,length=256)
	private String signerIds;
	
	@Column(name ="signer_names",nullable=true,length=128)
	private String signerNames;
	
	
	/**
	 *方法: 取得REC_ID
	 *@return: java.lang.String  REC_ID
	 */
	public java.lang.String getRecId(){
		return this.recId;
	}

	/**
	 *方法: 设置REC_ID
	 *@param: java.lang.String  REC_ID
	 */
	public void setRecId(java.lang.String recId){
		this.recId = recId;
	}
	/**
	 *方法: 取得DEPT_ID
	 *@return: java.lang.String  DEPT_ID
	 */
	public java.lang.String getDeptId(){
		return this.deptId;
	}

	/**
	 *方法: 设置DEPT_ID
	 *@param: java.lang.String  DEPT_ID
	 */
	public void setDeptId(java.lang.String deptId){
		this.deptId = deptId;
	}
	/**
	 *方法: 取得DEPT_NAME
	 *@return: java.lang.String  DEPT_NAME
	 */
	public java.lang.String getDeptName(){
		return this.deptName;
	}

	/**
	 *方法: 设置DEPT_NAME
	 *@param: java.lang.String  DEPT_NAME
	 */
	public void setDeptName(java.lang.String deptName){
		this.deptName = deptName;
	}
	/**
	 *方法: 取得APPLY_DATE
	 *@return: java.util.Date  APPLY_DATE
	 */
	public java.util.Date getApplyDate(){
		return this.applyDate;
	}

	/**
	 *方法: 设置APPLY_DATE
	 *@param: java.util.Date  APPLY_DATE
	 */
	public void setApplyDate(java.util.Date applyDate){
		this.applyDate = applyDate;
	}
	/**
	 *方法: 取得PROPOSERID
	 *@return: java.lang.String  PROPOSERID
	 */
	public java.lang.String getProposerId(){
		return this.proposerId;
	}

	/**
	 *方法: 设置PROPOSERID
	 *@param: java.lang.String  PROPOSERID
	 */
	public void setProposerId(java.lang.String proposerid){
		this.proposerId = proposerid;
	}
	/**
	 *方法: 取得PROPOSERNAME
	 *@return: java.lang.String  PROPOSERNAME
	 */
	public java.lang.String getProposerName(){
		return this.proposerName;
	}

	/**
	 *方法: 设置PROPOSERNAME
	 *@param: java.lang.String  PROPOSERNAME
	 */
	public void setProposerName(java.lang.String proposername){
		this.proposerName = proposername;
	}
	/**
	 *方法: 取得PHONE
	 *@return: java.lang.String  PHONE
	 */
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置PHONE
	 *@param: java.lang.String  PHONE
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得NEWS_TITLE
	 *@return: java.lang.String  NEWS_TITLE
	 */
	public java.lang.String getNewsTitle(){
		return this.newsTitle;
	}

	/**
	 *方法: 设置NEWS_TITLE
	 *@param: java.lang.String  NEWS_TITLE
	 */
	public void setNewsTitle(java.lang.String newsTitle){
		this.newsTitle = newsTitle;
	}
	/**
	 *方法: 取得PUBLISH_TO
	 *@return: java.lang.Integer  PUBLISH_TO
	 */
	public String getPublishTo(){
		return this.publishTo;
	}

	/**
	 *方法: 设置PUBLISH_TO
	 *@param: java.lang.Integer  PUBLISH_TO
	 */
	public void setPublishTo(String publishTo){
		this.publishTo = publishTo;
	}
	/**
	 *方法: 取得INNER_PUBLISH_TO
	 *@return: java.lang.Integer  INNER_PUBLISH_TO
	 */
	public String getInnerPublishTo(){
		return this.innerPublishTo;
	}

	/**
	 *方法: 设置INNER_PUBLISH_TO
	 *@param: java.lang.Integer  INNER_PUBLISH_TO
	 */
	public void setInnerPublishTo(String innerPublishTo){
		this.innerPublishTo = innerPublishTo;
	}
	/**
	 *方法: 取得SUBMIT_TO
	 *@return: java.lang.Integer  SUBMIT_TO
	 */
	public String getSubmitTo(){
		return this.submitTo;
	}

	/**
	 *方法: 设置SUBMIT_TO
	 *@param: java.lang.Integer  SUBMIT_TO
	 */
	public void setSubmitTo(String submitTo){
		this.submitTo = submitTo;
	}
	/**
	 *方法: 取得EDIT_TYPE
	 *@return: java.lang.Integer  EDIT_TYPE
	 */
	public int getEditType(){
		return this.editType;
	}

	/**
	 *方法: 设置EDIT_TYPE
	 *@param: java.lang.Integer  EDIT_TYPE
	 */
	public void setEditType(int editType){
		this.editType = editType;
	}
	/**
	 *方法: 取得REC_STATUS
	 *@return: java.lang.Integer  REC_STATUS
	 */
	public int getRecStatus(){
		return this.recStatus;
	}

	/**
	 *方法: 设置REC_STATUS
	 *@param: java.lang.Integer  REC_STATUS
	 */
	public void setRecStatus(int recStatus){
		this.recStatus = recStatus;
	}
	
	public String getProcId(){
		return this.procId;
	}
	public void setProcId(String procId){
		this.procId = procId;
	}
	
	public boolean getNeedConSign() {
        return this.needConSign;
    }
    public void setNeedConSign(boolean needConSign) {
        this.needConSign = needConSign;
    }
    
    public java.lang.String getSignerIds(){
		return this.signerIds;
	}
	public void setSignerIds(java.lang.String signerIds){
		this.signerIds = signerIds;
	}
	
	public java.lang.String getSignerNames(){
		return this.signerNames;
	}
	public void setSignerNames(java.lang.String signerNames){
		this.signerNames = signerNames;
	}
	
	public boolean equals(Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof NewsExamRec)) return false;
		else {
			NewsExamRec objInst = (NewsExamRec) obj;
			if (null == this.getRecId() || null == objInst.getRecId()) return false;
			else return (this.getRecId().equals(objInst.getRecId()));
		}
	}

	@Transient
	private int hashCode = Integer.MIN_VALUE;
	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getRecId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getRecId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
}
