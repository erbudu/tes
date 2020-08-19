package com.supporter.prj.pm.fund_appropriation.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.pm.fund_appropriation.entity.base.BaseFundAppropriation;
import com.supporter.prj.pm.public_proc.entity.PublicProc;
import com.supporter.util.CodeTable;

/**
 * 资金拨付
 * @author Administrator
 *
 */
@Entity
@Table(name = "pm_fund_appropriate", schema = "")
public class FundAppropriation extends BaseFundAppropriation {
	
	private static final long serialVersionUID = 1L;
	// 顶级TOP_MODULE_ID
	public static final String TOP_MODULE_ID = "0";	
	public static final String APP_NAME = "FUNDAPPROP"; //应用编号
	public static final String DOMAIN_OBJECT_ID = "fundAppropriate"; //业务对象编号
	
	/**
	 * 回盘审批结果
	 */
	@Transient
	private int oaExamResult = -1; //默认为未审批 [未审批/通过/不通过]
	@Transient
	private java.lang.String oaExamOpinion;
	
	public FundAppropriation() {
		super();
	}
	/**
	 * 状态
	 * @author Administrator
	 *
	 */
	public static final class StatusCodeTable {
		public static final int DRAFT = 0; //草稿
		public static final int WAIT_COMPANY_EXAM = 3; // 总部审批中
		public static final int COMPANY_EXAM_FAILED = 4; // 总部驳回
		public static final int EXAM = 1; //总部审批通过
		
		/**
		 * 获取码表
		 * @return CodeTable
		 */
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "草稿");
			ct.insertItem(WAIT_COMPANY_EXAM, "总部审批中");
			ct.insertItem(COMPANY_EXAM_FAILED, "总部驳回");
			ct.insertItem(EXAM, "审批完成");
			return ct;
		}
	}
	
	/**
	 * 性质
	 * @author Administrator
	 *
	 */
	public static final class FundPropertyTable {
		public static final int ONE = 0;
		public static final int TWO = 1;
		
		/**
		 * 获取码表
		 * @return CodeTable
		 */
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(ONE, "现场支付无需拨款");
			ct.insertItem(TWO, "现场支付需要拨款");
			return ct;
		}
	}
	
	private String propertyStr;
	/**
	 * 获取 propertyStr
	 * @return String
	 */
	@Transient
	public String getPropertyStr() {
		if (getFundProperty() != null) {
			return FundPropertyTable.getCodeTable().getDisplay(getFundProperty());
		} else {
			return null;
		}	
	}

	public void setPropertyStr(String propertyStr) {
		this.propertyStr = propertyStr;
	}

	private boolean isNew;

	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	private String statusDesc;
	/**
	 * 状态
	 * @return String
	 */
	@Transient
	public String getStatusDesc() {
		if (getStatus() != null) {
			return StatusCodeTable.getCodeTable().getDisplay(getStatus());
		} else {
			return null;
		}				
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
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
		return this.getFundId();
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
	private PublicProc publicProc;
	public void setPublicProc(PublicProc publicProc) {
		this.publicProc = publicProc;
	}

	/**
	 * 获取审批人1
	 * @return String
	 */
	@Transient
	public String getExamOne() {
		if (publicProc != null) {
		     return publicProc.getExamOne();
		} else {
		     return "";
		}
	}
	/**
	 * 获取审批人2
	 * @return String
	 */
	@Transient
	public String getExamTwo() {
		if (publicProc != null) {
		     return publicProc.getExamTwo();
		} else {
		     return "";
		}
	}
	/**
	 * 获取审批人3
	 * @return String
	 */
	@Transient
	public String getExamThree() {
		if (publicProc != null) {
		     return publicProc.getExamThree();
		} else {
		     return "";
		}
	}
	/**
	 * 获取审批人4
	 * @return String
	 */
	@Transient
	public String getExamFour() {
		if (publicProc != null) {
		     return publicProc.getExamFour();
		} else {
		     return "";
		}
	}
	
	private String delExpendIds;
	@Transient
	public String getDelExpendIds() {
		return delExpendIds;
	}

	public void setDelExpendIds(String delExpendIds) {
		this.delExpendIds = delExpendIds;
	}

	private String delBudgetIds;
	@Transient
	public String getDelBudgetIds() {
		return delBudgetIds;
	}

	public void setDelBudgetIds(String delBudgetIds) {
		this.delBudgetIds = delBudgetIds;
	}
	
	private List<FundBudgetExpend> expendList;
	@Transient
	public List<FundBudgetExpend> getExpendList() {
		return expendList;
	}

	public void setExpendList(List<FundBudgetExpend> expendList) {
		this.expendList = expendList;
	}

	private List<FundReceipt> budgetIncomeList;
	@Transient
	public List<FundReceipt> getBudgetIncomeList() {
		return budgetIncomeList;
	}

	public void setBudgetIncomeList(List<FundReceipt> budgetIncomeList) {
		this.budgetIncomeList = budgetIncomeList;
	}
	@Transient
	public int getOaExamResult() {
		return this.oaExamResult;
	}
	public void setOaExamResult(int oaExamResult) {
		this.oaExamResult = oaExamResult;
	}
	@Transient
	public java.lang.String getOaExamOpinion() {
		return this.oaExamOpinion;
	}
	public void setOaExamOpinion(java.lang.String oaExamOpinion) {
		this.oaExamOpinion = oaExamOpinion;
	}
}
