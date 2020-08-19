package com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.entity.base;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**   
 * @Title: Entity
 * @Description: 开发合作,字段与数据库字段一一对应.
 * @author kangh_000
 * @date 2018-12-14 17:09:40
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseDevelopAgreement  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *ID.
	 */
	@Id
//	@GeneratedValue(generator = "uuid")
//  	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "DEVELOP_AGREEMENT_ID",nullable = false,length = 32)
	private String developAgreementId;
	/**
	 *编号.
	 */
	@Column(name = "AGREEMENT_NO",nullable = true,length = 90)
	private String agreementNo;
	/**
	 *项目id.
	 */
	@Column(name = "PRJ_ID",nullable = true,length = 32)
	private String prjId;
	/**
	 *项目编号.
	 */
	@Column(name = "PRJ_NO",nullable = true,length = 60)
	private String prjNo;
	/**
	 *项目名称.
	 */
	@Column(name = "PRJ_NAME",nullable = true,length = 90)
	private String prjName;
	/**
	 *项目管理级别.
	 */
	@Column(name = "PRJ_MANAGE_LEVEL",nullable = true,length = 60)
	private String prjManageLevel;
	/**
	 *协议类型ID.
	 */
	@Column(name = "AGREEMENT_TYPE_ID",nullable = true,length = 32)
	private String agreementTypeId;
	/**
	 *协议类型.
	 */
	@Column(name = "AGREEMENT_TYPE_NAME",nullable = true,length = 90)
	private String agreementTypeName;
	/**
	 *协议名称.
	 */
	@Column(name = "AGREEMENT_NAME",nullable = true,length = 512)
	private String agreementName;
	/**
	 *协议对方.
	 */
	@Column(name = "AGREEMENT_OTHER_SIDE",nullable = true,length = 512)
	private String agreementOtherSide;
	/**
	 *签署地.
	 */
	@Column(name = "SIGNING_PLACE",nullable = true,length = 512)
	private String signingPlace;
	/**
	 *签署日期.
	 */
	@Column(name = "SIGNING_DATE",nullable = true)
	private java.util.Date signingDate;
	/**
	 *有效期限(月).
	 */
	@Column(name = "VALIDITY_PERIOD",nullable = true,precision = 10)
	private int validityPeriod;
	/**
	 *备注.
	 */
	@Column(name = "REMARK",nullable = true)
	private String remark;
	/**
	 *经办人ID.
	 */
	@Column(name = "APPLIER_ID",nullable = true,length = 32)
	private String applierId;
	/**
	 *经办人名称.
	 */
	@Column(name = "APPLIER_NAME",nullable = true,length = 90)
	private String applierName;
	/**
	 *经办人联系电话.
	 */
	@Column(name = "APPLIER_TEL",nullable = true,length = 32)
	private String applierTel;
	/**
	 *事业部ID.
	 */
	@Column(name = "DEPT_ID",nullable = true,length = 32)
	private String deptId;
	/**
	 *事业部名称.
	 */
	@Column(name = "DEPT_NAME",nullable = true,length = 90)
	private String deptName;
	/**
	 *流程ID.
	 */
	@Column(name = "PROC_ID",nullable = true,length = 32)
	private String procId;
	/**
	 *申请日期.
	 */
	@Column(name = "APPLY_DATE",nullable = true)
	private java.util.Date applyDate;
	/**
	 *状态.
	 */
	@Column(name = "AGREEMENT_STATUS",nullable = true,precision = 10)
	private int agreementStatus;
	
	/**
	 *方法: 取得ID.
	 *@return: java.lang.String  ID
	 */
	public String getDevelopAgreementId(){
		return this.developAgreementId;
	}

	/**
	 *方法: 设置ID.
	 *@param: java.lang.String  ID
	 */
	public void setDevelopAgreementId(String developAgreementId){
		this.developAgreementId = developAgreementId;
	}
	/**
	 *方法: 取得编号.
	 *@return: java.lang.String  编号
	 */
	public String getAgreementNo(){
		return this.agreementNo;
	}

	/**
	 *方法: 设置编号.
	 *@param: java.lang.String  编号
	 */
	public void setAgreementNo(String agreementNo){
		this.agreementNo = agreementNo;
	}
	/**
	 *方法: 取得项目id.
	 *@return: java.lang.String  项目id
	 */
	public String getPrjId(){
		return this.prjId;
	}

	/**
	 *方法: 设置项目id.
	 *@param: java.lang.String  项目id
	 */
	public void setPrjId(String prjId){
		this.prjId = prjId;
	}
	/**
	 *方法: 取得项目编号.
	 *@return: java.lang.String  项目编号
	 */
	public String getPrjNo(){
		return this.prjNo;
	}

	/**
	 *方法: 设置项目编号.
	 *@param: java.lang.String  项目编号
	 */
	public void setPrjNo(String prjNo){
		this.prjNo = prjNo;
	}
	/**
	 *方法: 取得项目名称.
	 *@return: java.lang.String  项目名称
	 */
	public String getPrjName(){
		return this.prjName;
	}

	/**
	 *方法: 设置项目名称.
	 *@param: java.lang.String  项目名称
	 */
	public void setPrjName(String prjName){
		this.prjName = prjName;
	}
	/**
	 *方法: 取得项目管理级别.
	 *@return: java.lang.String  项目管理级别
	 */
	public String getPrjManageLevel(){
		return this.prjManageLevel;
	}

	/**
	 *方法: 设置项目管理级别.
	 *@param: java.lang.String  项目管理级别
	 */
	public void setPrjManageLevel(String prjManageLevel){
		this.prjManageLevel = prjManageLevel;
	}
	/**
	 *方法: 取得协议类型ID.
	 *@return: java.lang.String  协议类型ID
	 */
	public String getAgreementTypeId(){
		return this.agreementTypeId;
	}

	/**
	 *方法: 设置协议类型ID.
	 *@param: java.lang.String  协议类型ID
	 */
	public void setAgreementTypeId(String agreementTypeId){
		this.agreementTypeId = agreementTypeId;
	}
	/**
	 *方法: 取得协议类型.
	 *@return: java.lang.String  协议类型
	 */
	public String getAgreementTypeName(){
		return this.agreementTypeName;
	}

	/**
	 *方法: 设置协议类型.
	 *@param: java.lang.String  协议类型
	 */
	public void setAgreementTypeName(String agreementTypeName){
		this.agreementTypeName = agreementTypeName;
	}
	/**
	 *方法: 取得协议名称.
	 *@return: java.lang.String  协议名称
	 */
	public String getAgreementName(){
		return this.agreementName;
	}

	/**
	 *方法: 设置协议名称.
	 *@param: java.lang.String  协议名称
	 */
	public void setAgreementName(String agreementName){
		this.agreementName = agreementName;
	}
	/**
	 *方法: 取得协议对方.
	 *@return: java.lang.String  协议对方
	 */
	public String getAgreementOtherSide(){
		return this.agreementOtherSide;
	}

	/**
	 *方法: 设置协议对方.
	 *@param: java.lang.String  协议对方
	 */
	public void setAgreementOtherSide(String agreementOtherSide){
		this.agreementOtherSide = agreementOtherSide;
	}
	/**
	 *方法: 取得签署地.
	 *@return: java.lang.String  签署地
	 */
	public String getSigningPlace(){
		return this.signingPlace;
	}

	/**
	 *方法: 设置签署地.
	 *@param: java.lang.String  签署地
	 */
	public void setSigningPlace(String signingPlace){
		this.signingPlace = signingPlace;
	}
	/**
	 *方法: 取得签署日期.
	 *@return: java.util.Date  签署日期
	 */
	public java.util.Date getSigningDate(){
		return this.signingDate;
	}

	/**
	 *方法: 设置签署日期.
	 *@param: java.util.Date  签署日期
	 */
	public void setSigningDate(java.util.Date signingDate){
		this.signingDate = signingDate;
	}
	/**
	 *方法: 取得有效期限(月).
	 *@return: int  有效期限(月)
	 */
	public int getValidityPeriod(){
		return this.validityPeriod;
	}

	/**
	 *方法: 设置有效期限(月).
	 *@param: int  有效期限(月)
	 */
	public void setValidityPeriod(int validityPeriod){
		this.validityPeriod = validityPeriod;
	}
	/**
	 *方法: 取得备注.
	 *@return: byte[]  备注
	 */
	public String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置备注.
	 *@param: byte[]  备注
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得经办人ID.
	 *@return: java.lang.String  经办人ID
	 */
	public String getApplierId(){
		return this.applierId;
	}

	/**
	 *方法: 设置经办人ID.
	 *@param: java.lang.String  经办人ID
	 */
	public void setApplierId(String applierId){
		this.applierId = applierId;
	}
	/**
	 *方法: 取得经办人名称.
	 *@return: java.lang.String  经办人名称
	 */
	public String getApplierName(){
		return this.applierName;
	}

	/**
	 *方法: 设置经办人名称.
	 *@param: java.lang.String  经办人名称
	 */
	public void setApplierName(String applierName){
		this.applierName = applierName;
	}
	/**
	 *方法: 取得经办人联系电话.
	 *@return: java.lang.String  经办人联系电话
	 */
	public String getApplierTel(){
		return this.applierTel;
	}

	/**
	 *方法: 设置经办人联系电话.
	 *@param: java.lang.String  经办人联系电话
	 */
	public void setApplierTel(String applierTel){
		this.applierTel = applierTel;
	}
	/**
	 *方法: 取得事业部ID.
	 *@return: java.lang.String  事业部ID
	 */
	public String getDeptId(){
		return this.deptId;
	}

	/**
	 *方法: 设置事业部ID.
	 *@param: java.lang.String  事业部ID
	 */
	public void setDeptId(String deptId){
		this.deptId = deptId;
	}
	/**
	 *方法: 取得事业部名称.
	 *@return: java.lang.String  事业部名称
	 */
	public String getDeptName(){
		return this.deptName;
	}

	/**
	 *方法: 设置事业部名称.
	 *@param: java.lang.String  事业部名称
	 */
	public void setDeptName(String deptName){
		this.deptName = deptName;
	}
	/**
	 *方法: 取得流程ID.
	 *@return: java.lang.String  流程ID
	 */
	public String getProcId(){
		return this.procId;
	}

	/**
	 *方法: 设置流程ID.
	 *@param: java.lang.String  流程ID
	 */
	public void setProcId(String procId){
		this.procId = procId;
	}
	/**
	 *方法: 取得申请日期.
	 *@return: java.util.Date  申请日期
	 */
	public java.util.Date getApplyDate(){
		return this.applyDate;
	}

	/**
	 *方法: 设置申请日期.
	 *@param: java.util.Date  申请日期
	 */
	public void setApplyDate(java.util.Date applyDate){
		this.applyDate = applyDate;
	}
	/**
	 *方法: 取得状态.
	 *@return: int  状态
	 */
	public int getAgreementStatus(){
		return this.agreementStatus;
	}

	/**
	 *方法: 设置状态.
	 *@param: int  状态
	 */
	public void setAgreementStatus(int agreementStatus){
		this.agreementStatus = agreementStatus;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BaseDevelopAgreement(){
	
	}
	
	/**
	 * 构造函数.
	 * @param developAgreementId
	 */
	public BaseDevelopAgreement(String developAgreementId){
		this.developAgreementId = developAgreementId;
	} 
}
