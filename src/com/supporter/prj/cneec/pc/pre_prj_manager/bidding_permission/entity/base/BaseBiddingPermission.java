package com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.entity.base;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**   
 * @Title: Entity
 * @Description: 投标许可,字段与数据库字段一一对应.
 * @author kangh_000
 * @date 2018-12-11 09:56:39
 * @version V1.0   
 *
 */
 @MappedSuperclass
public  class BaseBiddingPermission  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *ID.
	 */
	@Id
//	@GeneratedValue(generator = "uuid")
//  	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "BIDDING_PERMISSION_ID",nullable = false,length = 32)
	private String biddingPermissionId;
	/**
	 *投标许可编号.
	 */
	@Column(name = "BIDDING_PERMISSION_NO",nullable = true,length = 90)
	private String biddingPermissionNo;
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
	@Column(name = "PRJ_NAME_ZH",nullable = true,length = 90)
	private String prjNameZh;
	/**
	 *项目名称（外文）.
	 */
	@Column(name = "PRJ_NAME_FOREIGN",nullable = true,length = 90)
	private String prjNameForeign;
	/**
	 *业主名称.
	 */
	@Column(name = "OWNER_NAME_ZH",nullable = true,length = 90)
	private String ownerNameZh;
	/**
	 *业主名称（外文）.
	 */
	@Column(name = "OWNER_NAME_FOREIGN",nullable = true,length = 90)
	private String ownerNameForeign;
	/**
	 *项目所在国/区域.
	 */
	@Column(name = "PRJ_REGION",nullable = true,length = 90)
	private String prjRegion;
	/**
	 *工程所在地.
	 */
	@Column(name = "PRJ_LOCATION",nullable = true,length = 90)
	private String prjLocation;
	/**
	 *项目资金来源.
	 */
	@Column(name = "PRJ_FUNDS_RESOURCE",nullable = true,length = 256)
	private String prjFundsResource;
	/**
	 *项目总投资(美元).
	 */
	@Column(name = "PRJ_TOTAL_INVESTMENT",nullable = true,precision = 10)
	private int prjTotalInvestment;
	/**
	 *保函各类.
	 */
	@Column(name = "GUARANTEE_CATEGORY",nullable = true,length = 90)
	private String guaranteeCategory;
	/**
	 *保函金额(美元).
	 */
	@Column(name = "GUARANTEE_AMOUNT",nullable = true,precision = 10)
	private int guaranteeAmount;
	/**
	 *保函比率.
	 */
	@Column(name = "GUARANTEE_RATIO",nullable = true,precision = 10)
	private double guaranteeRatio;
	/**
	 *保函受益人.
	 */
	@Column(name = "GUARANTEE_BENEFICIARY",nullable = true,length = 90)
	private String guaranteeBeneficiary;
	/**
	 *拟申请保函银行.
	 */
	@Column(name = "GUARANTEE_PROPOSED_BANK",nullable = true,length = 90)
	private String guaranteeProposedBank;
	/**
	 *办理时间要求.
	 */
	@Column(name = "PROCESSING_TIME_REQUIREMENT",nullable = true,length = 256)
	private String processingTimeRequirement;
	/**
	 *其它相关要求.
	 */
	@Column(name = "OTHER_REQUIREMENT",nullable = true,length = 512)
	private String otherRequirement;
	/**
	 *事业部经办人ID.
	 */
	@Column(name = "APPLIER_ID",nullable = true,length = 32)
	private String applierId;
	/**
	 *事业部经办人名称.
	 */
	@Column(name = "APPLIER_NAME",nullable = true,length = 90)
	private String applierName;
	/**
	 *事业部经办人联系电话.
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
	 *备案日期.
	 */
	@Column(name = "FILING_DATE",nullable = true)
	private java.util.Date filingDate;
	/**
	 *申请日期.
	 */
	@Column(name = "APPLY_DATE",nullable = true)
	private java.util.Date applyDate;
	/**
	 *投标许可状态.
	 */
	@Column(name = "BIDDING_PERMISSION_STATUS",nullable = true,precision = 10)
	private int biddingPermissionStatus;
	
	/**
	 *方法: 取得ID.
	 *@return: java.lang.String  ID
	 */
	public String getBiddingPermissionId(){
		return this.biddingPermissionId;
	}

	/**
	 *方法: 设置ID.
	 *@param: java.lang.String  ID
	 */
	public void setBiddingPermissionId(String biddingPermissionId){
		this.biddingPermissionId = biddingPermissionId;
	}
	/**
	 *方法: 取得投标许可编号.
	 *@return: java.lang.String  投标许可编号
	 */
	public String getBiddingPermissionNo(){
		return this.biddingPermissionNo;
	}

	/**
	 *方法: 设置投标许可编号.
	 *@param: java.lang.String  投标许可编号
	 */
	public void setBiddingPermissionNo(String biddingPermissionNo){
		this.biddingPermissionNo = biddingPermissionNo;
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
	public String getPrjNameZh(){
		return this.prjNameZh;
	}

	/**
	 *方法: 设置项目名称.
	 *@param: java.lang.String  项目名称
	 */
	public void setPrjNameZh(String prjNameZh){
		this.prjNameZh = prjNameZh;
	}
	/**
	 *方法: 取得项目名称（外文）.
	 *@return: java.lang.String  项目名称（外文）
	 */
	public String getPrjNameForeign(){
		return this.prjNameForeign;
	}

	/**
	 *方法: 设置项目名称（外文）.
	 *@param: java.lang.String  项目名称（外文）
	 */
	public void setPrjNameForeign(String prjNameForeign){
		this.prjNameForeign = prjNameForeign;
	}
	/**
	 *方法: 取得业主名称.
	 *@return: java.lang.String  业主名称
	 */
	public String getOwnerNameZh(){
		return this.ownerNameZh;
	}

	/**
	 *方法: 设置业主名称.
	 *@param: java.lang.String  业主名称
	 */
	public void setOwnerNameZh(String ownerNameZh){
		this.ownerNameZh = ownerNameZh;
	}
	/**
	 *方法: 取得业主名称（外文）.
	 *@return: java.lang.String  业主名称（外文）
	 */
	public String getOwnerNameForeign(){
		return this.ownerNameForeign;
	}

	/**
	 *方法: 设置业主名称（外文）.
	 *@param: java.lang.String  业主名称（外文）
	 */
	public void setOwnerNameForeign(String ownerNameForeign){
		this.ownerNameForeign = ownerNameForeign;
	}
	/**
	 *方法: 取得项目所在国/区域.
	 *@return: java.lang.String  项目所在国/区域
	 */
	public String getPrjRegion(){
		return this.prjRegion;
	}

	/**
	 *方法: 设置项目所在国/区域.
	 *@param: java.lang.String  项目所在国/区域
	 */
	public void setPrjRegion(String prjRegion){
		this.prjRegion = prjRegion;
	}
	/**
	 *方法: 取得工程所在地.
	 *@return: java.lang.String  工程所在地
	 */
	public String getPrjLocation(){
		return this.prjLocation;
	}

	/**
	 *方法: 设置工程所在地.
	 *@param: java.lang.String  工程所在地
	 */
	public void setPrjLocation(String prjLocation){
		this.prjLocation = prjLocation;
	}
	/**
	 *方法: 取得项目资金来源.
	 *@return: java.lang.String  项目资金来源
	 */
	public String getPrjFundsResource(){
		return this.prjFundsResource;
	}

	/**
	 *方法: 设置项目资金来源.
	 *@param: java.lang.String  项目资金来源
	 */
	public void setPrjFundsResource(String prjFundsResource){
		this.prjFundsResource = prjFundsResource;
	}
	/**
	 *方法: 取得项目总投资(美元).
	 *@return: int  项目总投资(美元)
	 */
	public int getPrjTotalInvestment(){
		return this.prjTotalInvestment;
	}

	/**
	 *方法: 设置项目总投资(美元).
	 *@param: int  项目总投资(美元)
	 */
	public void setPrjTotalInvestment(int prjTotalInvestment){
		this.prjTotalInvestment = prjTotalInvestment;
	}
	/**
	 *方法: 取得保函各类.
	 *@return: java.lang.String  保函各类
	 */
	public String getGuaranteeCategory(){
		return this.guaranteeCategory;
	}

	/**
	 *方法: 设置保函各类.
	 *@param: java.lang.String  保函各类
	 */
	public void setGuaranteeCategory(String guaranteeCategory){
		this.guaranteeCategory = guaranteeCategory;
	}
	/**
	 *方法: 取得保函金额(美元).
	 *@return: int  保函金额(美元)
	 */
	public int getGuaranteeAmount(){
		return this.guaranteeAmount;
	}

	/**
	 *方法: 设置保函金额(美元).
	 *@param: int  保函金额(美元)
	 */
	public void setGuaranteeAmount(int guaranteeAmount){
		this.guaranteeAmount = guaranteeAmount;
	}
	/**
	 *方法: 取得保函比率.
	 *@return: int  保函比率
	 */
	public double getGuaranteeRatio(){
		return this.guaranteeRatio;
	}

	/**
	 *方法: 设置保函比率.
	 *@param: int  保函比率
	 */
	public void setGuaranteeRatio(double guaranteeRatio){
		this.guaranteeRatio = guaranteeRatio;
	}
	/**
	 *方法: 取得保函受益人.
	 *@return: java.lang.String  保函受益人
	 */
	public String getGuaranteeBeneficiary(){
		return this.guaranteeBeneficiary;
	}

	/**
	 *方法: 设置保函受益人.
	 *@param: java.lang.String  保函受益人
	 */
	public void setGuaranteeBeneficiary(String guaranteeBeneficiary){
		this.guaranteeBeneficiary = guaranteeBeneficiary;
	}
	/**
	 *方法: 取得拟申请保函银行.
	 *@return: java.lang.String  拟申请保函银行
	 */
	public String getGuaranteeProposedBank(){
		return this.guaranteeProposedBank;
	}

	/**
	 *方法: 设置拟申请保函银行.
	 *@param: java.lang.String  拟申请保函银行
	 */
	public void setGuaranteeProposedBank(String guaranteeProposedBank){
		this.guaranteeProposedBank = guaranteeProposedBank;
	}
	/**
	 *方法: 取得办理时间要求.
	 *@return: java.lang.String  办理时间要求
	 */
	public String getProcessingTimeRequirement(){
		return this.processingTimeRequirement;
	}

	/**
	 *方法: 设置办理时间要求.
	 *@param: java.lang.String  办理时间要求
	 */
	public void setProcessingTimeRequirement(String processingTimeRequirement){
		this.processingTimeRequirement = processingTimeRequirement;
	}
	/**
	 *方法: 取得其它相关要求.
	 *@return: java.lang.String  其它相关要求
	 */
	public String getOtherRequirement(){
		return this.otherRequirement;
	}

	/**
	 *方法: 设置其它相关要求.
	 *@param: java.lang.String  其它相关要求
	 */
	public void setOtherRequirement(String otherRequirement){
		this.otherRequirement = otherRequirement;
	}
	/**
	 *方法: 取得事业部经办人ID.
	 *@return: java.lang.String  事业部经办人ID
	 */
	public String getApplierId(){
		return this.applierId;
	}

	/**
	 *方法: 设置事业部经办人ID.
	 *@param: java.lang.String  事业部经办人ID
	 */
	public void setApplierId(String applierId){
		this.applierId = applierId;
	}
	/**
	 *方法: 取得事业部经办人名称.
	 *@return: java.lang.String  事业部经办人名称
	 */
	public String getApplierName(){
		return this.applierName;
	}

	/**
	 *方法: 设置事业部经办人名称.
	 *@param: java.lang.String  事业部经办人名称
	 */
	public void setApplierName(String applierName){
		this.applierName = applierName;
	}
	/**
	 *方法: 取得事业部经办人联系电话.
	 *@return: java.lang.String  事业部经办人联系电话
	 */
	public String getApplierTel(){
		return this.applierTel;
	}

	/**
	 *方法: 设置事业部经办人联系电话.
	 *@param: java.lang.String  事业部经办人联系电话
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
	 *方法: 取得备案日期.
	 *@return: java.util.Date  备案日期
	 */
	public java.util.Date getFilingDate(){
		return this.filingDate;
	}

	/**
	 *方法: 设置备案日期.
	 *@param: java.util.Date  备案日期
	 */
	public void setFilingDate(java.util.Date filingDate){
		this.filingDate = filingDate;
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
	 *方法: 取得投标许可状态.
	 *@return: int  投标许可状态
	 */
	public int getBiddingPermissionStatus(){
		return this.biddingPermissionStatus;
	}

	/**
	 *方法: 设置投标许可状态.
	 *@param: int  投标许可状态
	 */
	public void setBiddingPermissionStatus(int biddingPermissionStatus){
		this.biddingPermissionStatus = biddingPermissionStatus;
	}
	
	/**
	 * 无参构造函数.
	 */
	public BaseBiddingPermission(){
	
	}
	
	/**
	 * 构造函数.
	 * @param biddingPermissionId
	 */
	public BaseBiddingPermission(String biddingPermissionId){
		this.biddingPermissionId = biddingPermissionId;
	} 
}
