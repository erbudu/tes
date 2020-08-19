package com.supporter.prj.pm.contract_balance.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

import com.supporter.prj.pm.contract_balance.entity.base.BaseContractBalanceConst;
import com.supporter.prj.pm.procure_contract.entity.ProcureContract;
import com.supporter.prj.pm.public_proc.entity.PublicProc;

/**   
 * @Title: 合同结算-施工合同
 * @Description: PM_CONTRACT_BALANCE_CONST.
 * @author Administrator
 * @date 2018-07-04 18:07:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "PM_CONTRACT_BALANCE_CONST", schema = "")
public class ContractBalanceConst extends BaseContractBalanceConst {

	private static final long serialVersionUID = 1L;
	// 顶级TOP_MODULE_ID
	public static final String TOP_MODULE_ID = "0";	
	public static final String APP_NAME = "CONTRBALANC";//应用编号
	public static final String DOMAIN_OBJECT_ID = "BalanceConst";//业务对象编号
	/**
	 *搜索关键词.
	 */
	@Transient
	private String keyword;
	
	@Transient
	private boolean isNew = false; //是否是新增尚未保存记录
	
	@Transient
	private List < ContractBalanceConstSite > sites; //涉及工程部位
	@Transient
	private String delSitesIds; //删除的工程部位IDS
	
	/**
	 * 回盘审批结果
	 */
	@Transient
	private int oaExamResult = -1; //默认为未审批 [未审批/通过/不通过]
	@Transient
	private java.lang.String oaExamOpinion;
	
	//用于显示选取的签证单
	@Transient
	private String visaIds;
	@Transient
	private String visaNo;
	@Transient
	private String inquiryVisaIds;
	@Transient
	private String inquiryVisaNo;
	
	public String getVisaIds() {
		return visaIds;
	}

	public void setVisaIds(String visaIds) {
		this.visaIds = visaIds;
	}

	public String getVisaNo() {
		return visaNo;
	}

	public void setVisaNo(String visaNo) {
		this.visaNo = visaNo;
	}
	
	public String getInquiryVisaIds() {
		return inquiryVisaIds;
	}

	public void setInquiryVisaIds(String inquiryVisaIds) {
		this.inquiryVisaIds = inquiryVisaIds;
	}

	public String getInquiryVisaNo() {
		return inquiryVisaNo;
	}

	public void setInquiryVisaNo(String inquiryVisaNo) {
		this.inquiryVisaNo = inquiryVisaNo;
	}

	//--------------------------------------构造函数定义-----------------------------------------
	/**
	 * 无参构造函数.
	 */
	public ContractBalanceConst() {
		super();
	}
	
	/**
	 * 构造函数.
	 * @param balanceId
	 */
	public ContractBalanceConst(String balanceId) {
		super(balanceId);
	} 
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ContractBalanceConst)) {
			return false;
		}
		ContractBalanceConst castOther = (ContractBalanceConst) other;
		return StringUtils.equals(this.getBalanceId(), castOther.getBalanceId());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getBalanceId()).toHashCode();
	}
	
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	public List < ContractBalanceConstSite > getSites() {
		return this.sites;
	}
	public void setSites(List < ContractBalanceConstSite > sites) {
		this.sites = sites;
	}
	
	public String getDelSitesIds() {
		return delSitesIds;
	}
	public void setDelSitesIds(String delSitesIds) {
		this.delSitesIds = delSitesIds;
	}
	
	// 状态
	@Transient
	public String getStatusDesc() {
		return Status.getMap().get(this.getStatus());
	}
	
	//合同类型=工程类型
	@Transient
	public String getContractTypeDesc() {
		return ProcureContract.ContractType.getMap().get(this.getContractType());
	}
	
	/**
	 * 是否最终结算
	 * @return String
	 */
	@Transient
	public String getIsFinalBalanceDesc() {
		if (this.getIsFinalBalance()) {
			return "是";
		} else {
			return "否";
		}
	}
	
	/**
	 * 状态.
	 */
	public static final class Status {
		public static final int DRAFT = 0; // 草稿
		public static final int EXAM = 1; // 审批中
		public static final int WAIT_COMPANY_EXAM = 3; // 总部审批中
		public static final int COMPLETE  = 2; // 审批完成
		
		
		/**
		 * 获取状态码表.
		 * @return Map<Integer, String>
		 */
		public static Map<Integer, String> getMap() {
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(DRAFT, "草稿");
			map.put(EXAM, "审批中");
			map.put(WAIT_COMPANY_EXAM, "总部审批中");
			map.put(COMPLETE, "审批完成");
			return map;
		}
		
		private Status() {

		}
	}
	
	@Transient
	public int getDbYear() {
		return 0;
	}
	@Transient
	public String getDomainObjectId() {	
		return DOMAIN_OBJECT_ID;
	}
	//获取主键ID，用于公共流程页面的传参
	@Transient
	public String getEntityId() {
		return this.getBalanceId();
	}
	@Transient
	public String getEntityName() {
		return this.getClass().getName();
	}
	@Transient
	public String getModuleId() {
		return APP_NAME;
	}
	@Transient
	public String getModuleBusiType() {
		return "";
	}
	@Transient
	public String getCompanyNo() {
		return "";
	}
	
	//公共流程相关publicProc、getExamOne、getExamTwo、getExamThree、getExamFour
	@Transient
	private PublicProc publicProc;
	public void setPublicProc(PublicProc publicProc) {
		this.publicProc = publicProc;
	}

	@Transient
	public String getExamOne() {
		if (publicProc != null) {
		     return publicProc.getExamOne();
		} else {
		     return "";
		}
	}
	@Transient
	public String getExamTwo() {
		if (publicProc != null) {
		     return publicProc.getExamTwo();
		} else {
		     return "";
		}
	}
	
	@Transient
	public String getExamThree() {
		if (publicProc != null) {
		     return publicProc.getExamThree();
		} else {
		     return "";
		}
	}
	@Transient
	public String getExamFour() {
		if (publicProc != null) {
		     return publicProc.getExamFour();
		} else {
		     return "";
		}
	}
	/**
	 * 包含当期的累计扣减额（历史累计扣减+议定临时扣减）
	 * @return double
	 */
	@Transient
	public double getCumulativeDeductionsCur() {
		return this.getCumulativeDeductions() + this.getAgreedInterim();
	}
	public void setCumulativeDeductionsCur(double d) {
	}
	
	public int getOaExamResult() {
		return this.oaExamResult;
	}
	public void setOaExamResult(int oaExamResult) {
		this.oaExamResult = oaExamResult;
	}
	public java.lang.String getOaExamOpinion() {
		return this.oaExamOpinion;
	}
	public void setOaExamOpinion(java.lang.String oaExamOpinion) {
		this.oaExamOpinion = oaExamOpinion;
	}
	
}
