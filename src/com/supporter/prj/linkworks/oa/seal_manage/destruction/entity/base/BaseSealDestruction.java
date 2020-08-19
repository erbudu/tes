package com.supporter.prj.linkworks.oa.seal_manage.destruction.entity.base;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**   
 * @Title: Entity
 * @Description: 印章销毁数据表,字段与数据库字段一一对应.
 * @author z
 * @date 2019-12-12 16:59:14
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseSealDestruction  implements Serializable {
	private static final long serialVersionUID = 1L;
	//主键ID.
	@Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "APPLY_ID",nullable = false,length = 32)
	private java.lang.String applyId;
	//印章名称.
	@Column(name = "SEAL_NAME",nullable = true,length = 256)
	private java.lang.String sealName;
	//印章数量.
	@Column(name = "SEAL_COUNT",nullable = true,precision = 10)
	private Integer sealCount;
	//申请时间.
	@Column(name = "APPLY_DATE",nullable = true,length = 32)
	private java.lang.String applyDate;
	//印章来源（0：部门刻制，1公司统一刻制）.
	@Column(name = "SEAL_SOURCE",nullable = true,precision = 10)
	private Integer sealSource;
	//销毁原因.
	@Column(name = "DESTRUCTION_REASON",nullable = true,length = 512)
	private java.lang.String destructionReason;
	//归档备注.
	@Column(name = "REMARKS",nullable = true,length = 512)
	private java.lang.String remarks;
	//DEPT_ID.
	@Column(name = "DEPT_ID",nullable = true,length = 32)
	private java.lang.String deptId;
	//DEPT_NAME.
	@Column(name = "DEPT_NAME",nullable = true,length = 128)
	private java.lang.String deptName;
	//创建人ID.
	@Column(name = "CREATED_BY_ID",nullable = true,length = 32)
	private java.lang.String createdById;
	//创建人名称.
	@Column(name = "CREATED_BY",nullable = true,length = 64)
	private java.lang.String createdBy;
	//创建时间.
	@Column(name = "CREATED_DATE",nullable = true,length = 32)
	private java.lang.String createdDate;
	//修改人ID.
	@Column(name = "MODIFIED_BY_ID",nullable = true,length = 32)
	private java.lang.String modifiedById;
	//修改人名称.
	@Column(name = "MODIFIED_BY",nullable = true,length = 64)
	private java.lang.String modifiedBy;
	//修改时间.
	@Column(name = "MODIFIED_DATE",nullable = true,length = 32)
	private java.lang.String modifiedDate;
	//审批状态（0：草稿，1：审批中，2：审批完成）.
	@Column(name = "STATUS",nullable = true,precision = 10)
	private Integer status;
	//流程ID.
	@Column(name = "PROC_ID",nullable = true,length = 32)
	private java.lang.String procId;
	
	/**
	 *方法: 取得主键ID.
	 *@return: java.lang.String  主键ID
	 */
	public java.lang.String getApplyId(){
		return this.applyId;
	}

	/**
	 *方法: 设置主键ID.
	 *@param: java.lang.String  主键ID
	 */
	public void setApplyId(java.lang.String applyId){
		this.applyId = applyId;
	}
	/**
	 *方法: 取得印章名称.
	 *@return: java.lang.String  印章名称
	 */
	public java.lang.String getSealName(){
		return this.sealName;
	}

	/**
	 *方法: 设置印章名称.
	 *@param: java.lang.String  印章名称
	 */
	public void setSealName(java.lang.String sealName){
		this.sealName = sealName;
	}
	/**
	 *方法: 取得印章数量.
	 *@return: int  印章数量
	 */
	public Integer getSealCount(){
		return this.sealCount;
	}

	/**
	 *方法: 设置印章数量.
	 *@param: int  印章数量
	 */
	public void setSealCount(Integer sealCount){
		this.sealCount = sealCount;
	}
	/**
	 *方法: 取得申请时间.
	 *@return: java.lang.String  申请时间
	 */
	public java.lang.String getApplyDate(){
		return this.applyDate;
	}

	/**
	 *方法: 设置申请时间.
	 *@param: java.lang.String  申请时间
	 */
	public void setApplyDate(java.lang.String applyDate){
		this.applyDate = applyDate;
	}
	/**
	 *方法: 取得印章来源（0：部门刻制，1公司统一刻制）.
	 *@return: int  印章来源（0：部门刻制，1公司统一刻制）
	 */
	public Integer getSealSource(){
		return this.sealSource;
	}

	/**
	 *方法: 设置印章来源（0：部门刻制，1公司统一刻制）.
	 *@param: int  印章来源（0：部门刻制，1公司统一刻制）
	 */
	public void setSealSource(Integer sealSource){
		this.sealSource = sealSource;
	}
	/**
	 *方法: 取得销毁原因.
	 *@return: java.lang.String  销毁原因
	 */
	public java.lang.String getDestructionReason(){
		return this.destructionReason;
	}

	/**
	 *方法: 设置销毁原因.
	 *@param: java.lang.String  销毁原因
	 */
	public void setDestructionReason(java.lang.String destructionReason){
		this.destructionReason = destructionReason;
	}
	/**
	 *方法: 取得归档备注.
	 *@return: java.lang.String  归档备注
	 */
	public java.lang.String getRemarks(){
		return this.remarks;
	}

	/**
	 *方法: 设置归档备注.
	 *@param: java.lang.String  归档备注
	 */
	public void setRemarks(java.lang.String remarks){
		this.remarks = remarks;
	}
	public java.lang.String getDeptId() {
		return deptId;
	}

	public void setDeptId(java.lang.String deptId) {
		this.deptId = deptId;
	}

	public java.lang.String getDeptName() {
		return deptName;
	}

	public void setDeptName(java.lang.String deptName) {
		this.deptName = deptName;
	}

	/**
	 *方法: 取得创建人ID.
	 *@return: java.lang.String  创建人ID
	 */
	public java.lang.String getCreatedById(){
		return this.createdById;
	}

	/**
	 *方法: 设置创建人ID.
	 *@param: java.lang.String  创建人ID
	 */
	public void setCreatedById(java.lang.String createdById){
		this.createdById = createdById;
	}
	/**
	 *方法: 取得创建人名称.
	 *@return: java.lang.String  创建人名称
	 */
	public java.lang.String getCreatedBy(){
		return this.createdBy;
	}

	/**
	 *方法: 设置创建人名称.
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreatedBy(java.lang.String createdBy){
		this.createdBy = createdBy;
	}
	/**
	 *方法: 取得创建时间.
	 *@return: java.lang.String  创建时间
	 */
	public java.lang.String getCreatedDate(){
		return this.createdDate;
	}

	/**
	 *方法: 设置创建时间.
	 *@param: java.lang.String  创建时间
	 */
	public void setCreatedDate(java.lang.String createdDate){
		this.createdDate = createdDate;
	}
	/**
	 *方法: 取得修改人ID.
	 *@return: java.lang.String  修改人ID
	 */
	public java.lang.String getModifiedById(){
		return this.modifiedById;
	}

	/**
	 *方法: 设置修改人ID.
	 *@param: java.lang.String  修改人ID
	 */
	public void setModifiedById(java.lang.String modifiedById){
		this.modifiedById = modifiedById;
	}
	/**
	 *方法: 取得修改人名称.
	 *@return: java.lang.String  修改人名称
	 */
	public java.lang.String getModifiedBy(){
		return this.modifiedBy;
	}

	/**
	 *方法: 设置修改人名称.
	 *@param: java.lang.String  修改人名称
	 */
	public void setModifiedBy(java.lang.String modifiedBy){
		this.modifiedBy = modifiedBy;
	}
	/**
	 *方法: 取得修改时间.
	 *@return: java.lang.String  修改时间
	 */
	public java.lang.String getModifiedDate(){
		return this.modifiedDate;
	}

	/**
	 *方法: 设置修改时间.
	 *@param: java.lang.String  修改时间
	 */
	public void setModifiedDate(java.lang.String modifiedDate){
		this.modifiedDate = modifiedDate;
	}
	/**
	 *方法: 取得审批状态（0：草稿，1：审批中，2：审批完成）.
	 *@return: int  审批状态（0：草稿，1：审批中，2：审批完成）
	 */
	public Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置审批状态（0：草稿，1：审批中，2：审批完成）.
	 *@param: int  审批状态（0：草稿，1：审批中，2：审批完成）
	 */
	public void setStatus(Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得流程ID.
	 *@return: java.lang.String  流程ID
	 */
	public java.lang.String getProcId(){
		return this.procId;
	}

	/**
	 *方法: 设置流程ID.
	 *@param: java.lang.String  流程ID
	 */
	public void setProcId(java.lang.String procId){
		this.procId = procId;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BaseSealDestruction(){
	
	}
	
	/**
	 * 构造函数.
	 * @param applyId
	 */
	public BaseSealDestruction(String applyId){
		this.applyId = applyId;
	} 
}
