package com.supporter.prj.linkworks.oa.seal_manage.engrave.entity.base;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 印章销毁数据表,字段与数据库字段一一对应.
 * @author z
 * @date 2019-12-12 17:52:31
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseSealEngrave  implements Serializable {
	private static final long serialVersionUID = 1L;
	//主键ID.
	@Id
	//@GeneratedValue(generator = "uuid")
    //@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "APPLY_ID",nullable = false,length = 32)
	private java.lang.String applyId;
	//印章名称.
	@Column(name = "SEAL_NAME",nullable = true,length = 256)
	private java.lang.String sealName;
	//印章数量.
	@Column(name = "SEAL_COUNT",nullable = true,precision = 10)
	private Integer sealCount;
	//保管人ID.
	@Column(name = "SAFEKEEPING_PERSON_ID",nullable = true,length = 32)
	private java.lang.String safekeepingPersonId;
	//保管人名称.
	@Column(name = "SAFEKEEPING_PERSON_NAME",nullable = true,length = 64)
	private java.lang.String safekeepingPersonName;
	//刻制用途.
	@Column(name = "ENGRAVE_PURPOSE",nullable = true,length = 512)
	private java.lang.String engravePurpose;
	//使用地点.
	@Column(name = "LOCATION_OF_USE",nullable = true,length = 256)
	private java.lang.String locationOfUse;
	//申请时间.
	@Column(name = "APPLY_DATE",nullable = true,length = 32)
	private java.lang.String applyDate;
	//印章类型（0：部门印章，1：项目印章）.
	@Column(name = "SEAL_TYPE",nullable = true,precision = 10)
	private Integer sealType;
	//是否国外机构（0：否，1：是）
	@Column(name = "IS_FOREIGN",nullable = true,precision = 10)
	private Integer isForeign;
	//项目名称.
	@Column(name = "PRJ_NAME",nullable = true,length = 256)
	private java.lang.String prjName;
	//项目经理ID.
	@Column(name = "PRJ_MANAGER_ID",nullable = true,length = 32)
	private java.lang.String prjManagerId;
	//项目经理名称.
	@Column(name = "PRJ_MANAGER_NAME",nullable = true,length = 64)
	private java.lang.String prjManagerName;
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
	//审批状态（0：草稿，1：审批中，2：审批完成）.
	@Column(name = "IS_PRESIDENT",nullable = true,precision = 10)
	private Integer isPresident ;
	@Column(name = "CREATE_BY_TELE", nullable = true, length = 32)
	private String createByTele;
	@Column(name = "SEAL_BY_TELE", nullable = true, length = 32)
	private String sealByTele;
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
	 *方法: 取得保管人ID.
	 *@return: java.lang.String  保管人ID
	 */
	public java.lang.String getSafekeepingPersonId(){
		return this.safekeepingPersonId;
	}

	/**
	 *方法: 设置保管人ID.
	 *@param: java.lang.String  保管人ID
	 */
	public void setSafekeepingPersonId(java.lang.String safekeepingPersonId){
		this.safekeepingPersonId = safekeepingPersonId;
	}
	/**
	 *方法: 取得保管人名称.
	 *@return: java.lang.String  保管人名称
	 */
	public java.lang.String getSafekeepingPersonName(){
		return this.safekeepingPersonName;
	}

	/**
	 *方法: 设置保管人名称.
	 *@param: java.lang.String  保管人名称
	 */
	public void setSafekeepingPersonName(java.lang.String safekeepingPersonName){
		this.safekeepingPersonName = safekeepingPersonName;
	}
	/**
	 *方法: 取得刻制用途.
	 *@return: java.lang.String  刻制用途
	 */
	public java.lang.String getEngravePurpose(){
		return this.engravePurpose;
	}

	/**
	 *方法: 设置刻制用途.
	 *@param: java.lang.String  刻制用途
	 */
	public void setEngravePurpose(java.lang.String engravePurpose){
		this.engravePurpose = engravePurpose;
	}
	/**
	 *方法: 取得使用地点.
	 *@return: java.lang.String  使用地点
	 */
	public java.lang.String getLocationOfUse(){
		return this.locationOfUse;
	}

	/**
	 *方法: 设置使用地点.
	 *@param: java.lang.String  使用地点
	 */
	public void setLocationOfUse(java.lang.String locationOfUse){
		this.locationOfUse = locationOfUse;
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
	 *方法: 取得印章类型（0：部门印章，1：项目印章）.
	 *@return: int  印章类型（0：部门印章，1：项目印章）
	 */
	public Integer getSealType(){
		return this.sealType;
	}

	/**
	 *方法: 设置印章类型（0：部门印章，1：项目印章）.
	 *@param: int  印章类型（0：部门印章，1：项目印章）
	 */
	public void setSealType(Integer sealType){
		this.sealType = sealType;
	}
	
	public Integer getIsForeign() {
		return isForeign;
	}

	public void setIsForeign(Integer isForeign) {
		this.isForeign = isForeign;
	}

	/**
	 *方法: 取得项目名称.
	 *@return: java.lang.String  项目名称
	 */
	public java.lang.String getPrjName(){
		return this.prjName;
	}

	/**
	 *方法: 设置项目名称.
	 *@param: java.lang.String  项目名称
	 */
	public void setPrjName(java.lang.String prjName){
		this.prjName = prjName;
	}
	/**
	 *方法: 取得项目经理ID.
	 *@return: java.lang.String  项目经理ID
	 */
	public java.lang.String getPrjManagerId(){
		return this.prjManagerId;
	}

	/**
	 *方法: 设置项目经理ID.
	 *@param: java.lang.String  项目经理ID
	 */
	public void setPrjManagerId(java.lang.String prjManagerId){
		this.prjManagerId = prjManagerId;
	}
	/**
	 *方法: 取得项目经理名称.
	 *@return: java.lang.String  项目经理名称
	 */
	public java.lang.String getPrjManagerName(){
		return this.prjManagerName;
	}

	/**
	 *方法: 设置项目经理名称.
	 *@param: java.lang.String  项目经理名称
	 */
	public void setPrjManagerName(java.lang.String prjManagerName){
		this.prjManagerName = prjManagerName;
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
	/**
	 *方法: 取得DEPT_ID.
	 *@return: java.lang.String  DEPT_ID
	 */
	public java.lang.String getDeptId(){
		return this.deptId;
	}

	/**
	 *方法: 设置DEPT_ID.
	 *@param: java.lang.String  DEPT_ID
	 */
	public void setDeptId(java.lang.String deptId){
		this.deptId = deptId;
	}
	/**
	 *方法: 取得DEPT_NAME.
	 *@return: java.lang.String  DEPT_NAME
	 */
	public java.lang.String getDeptName(){
		return this.deptName;
	}

	/**
	 *方法: 设置DEPT_NAME.
	 *@param: java.lang.String  DEPT_NAME
	 */
	public void setDeptName(java.lang.String deptName){
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
	
	public Integer getIsPresident() {
		return this.isPresident;
	}

	public void setIsPresident(Integer isPresident) {
		this.isPresident = isPresident;
	}



	public String getCreateByTele() {
		return createByTele;
	}

	public void setCreateByTele(String createByTele) {
		this.createByTele = createByTele;
	}

	public String getSealByTele() {
		return sealByTele;
	}

	public void setSealByTele(String sealByTele) {
		this.sealByTele = sealByTele;
	}

	/**
	 * 无参构造函数.
	 */
	public BaseSealEngrave(){
	
	}
	
	/**
	 * 构造函数.
	 * @param applyId
	 */
	public BaseSealEngrave(String applyId){
		this.applyId = applyId;
	} 
}
