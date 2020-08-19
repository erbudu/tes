package com.supporter.prj.pm.payment_onsite.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.pm.payment_onsite.entity.base.BasePaymentOnsite;
import com.supporter.prj.pm.public_proc.entity.PublicProc;
import com.supporter.util.CodeTable;

/**
 * 付款申请
 * @author Administrator
 *
 */
@Entity
@Table(name = "pm_payment_onsite", catalog = "")
public class PaymentOnsite extends BasePaymentOnsite {
	
	private static final long serialVersionUID = 1L;
	// 顶级TOP_MODULE_ID
	public static final String TOP_MODULE_ID = "0";	
	public static final String APP_NAME = "PaymentOn"; //应用编号
	public static final String DOMAIN_OBJECT_ID = "PaymentOnsite"; //业务对象编号
	
	/**
	 * 回盘审批结果
	 */
	@Transient
	private int oaExamResult = -1; //默认为未审批 [未审批/通过/不通过]
	@Transient
	private java.lang.String oaExamOpinion;
	
	public PaymentOnsite() {
		super();
	}
	
	public PaymentOnsite(String id,String prjId,String prjName, Integer address, String contractId,
			String contractNo,String contractName, String contractTypeId, String contractType,
			Integer paymentType, String paymentClauseId,String paymentClause, String paymentNo,
			Integer paymentNature, Integer paymentMethod, Integer purpose,
			String proposer, Date applyDate, String currency,
			String budgetId,String budgetNos,String budgetName, double paymentAmount, double exchangeRate,
			double rmbAmount, double engineeringAmount, double installAmount,
			double borrowAmount, double estimateAmount, String accountId,String accountNo,
			String receiverBank,String receiverAccount,String applyReason,
			Integer status, Integer statusScan, String createdById, String createdBy,
			String createdDeptId, String createdDept, Date createdDate,
			String modifiedById, String modifiedBy, Date modifiedDate,String procId) {
		super( id,prjId,prjName,address,contractId,contractNo,contractName,contractTypeId,contractType,
				paymentType, paymentClauseId,paymentClause,paymentNo,paymentNature, paymentMethod, purpose,
				proposer,applyDate, currency,budgetId,budgetNos,budgetName,paymentAmount,exchangeRate,
				rmbAmount,engineeringAmount,installAmount,borrowAmount,estimateAmount,accountId,accountNo,
				receiverBank,receiverAccount,applyReason,status, statusScan, createdById, createdBy,
				createdDeptId, createdDept, createdDate,modifiedById,modifiedBy, modifiedDate,procId);
	}
	
	// 状态
	public static final class StatusCodeTable {
		public static final int DRAFT = 0;
		public static final int EXAM = 1;
		public static final int WAIT_COMPANY_EXAM = 3; // 总部审批中
		public static final int COMPANY_EXAM_FAILED = 4; // 总部驳回
		public static final int COMPLETE = 2;
		public static final int THREE = 3;
		
		//状态
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "草稿");
			ct.insertItem(EXAM, "审批中");
			ct.insertItem(WAIT_COMPANY_EXAM, "总部审批中");
			ct.insertItem(COMPANY_EXAM_FAILED, "总部驳回");
			ct.insertItem(COMPLETE, "国内审批完成");
			return ct;
		}
		
		//支付状态
		public static CodeTable getCodeTableActual() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "未支付");
			ct.insertItem(EXAM, "已支付");
			return ct;
		}
		
		//付款方式
		public static CodeTable getPaymentMethodCode() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "现金");
			ct.insertItem(EXAM, "电汇");
			ct.insertItem(COMPLETE, "支票");
			return ct;
		}
		
		//付款性质
		public static CodeTable getPaymentNatureCode() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "工程项下");
			ct.insertItem(EXAM, "现场管理费");
			return ct;
		}
		
		//款项用途--工程项下
		public static CodeTable getPurposeCode() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "合同项下款项");
			ct.insertItem(EXAM, "无合同款项");
			ct.insertItem(COMPLETE, "代付款(未签署代采合同)");
			ct.insertItem(THREE, "其它公司借款");
			return ct;
		}
		
		//款项用途--现场管理费
		public static CodeTable getPurposeCodeOne() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "无合同付款");
			ct.insertItem(EXAM, "个人借款");
			ct.insertItem(COMPLETE, "津贴及工资");
			ct.insertItem(THREE, "合同项下款项");
			return ct;
		}
		
		//支付类型
		public static CodeTable getPaymentTypeCode() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "境内");
			ct.insertItem(EXAM, "境外");
			return ct;
		}
		
		//采购地点
		public static CodeTable getAddressCode() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "总部采购");
			ct.insertItem(EXAM, "现场采购");
			return ct;
		}
	}
	
	// 采购地点
	@Transient
	public String getAddressDesc() {
		if (getAddress() != null) {
			return StatusCodeTable.getAddressCode().getDisplay(getAddress());
		} else {
			return null;
		}
	}

	// 状态
	@Transient
	public String getStatusDesc() {
		if (getStatus() != null) {
			return StatusCodeTable.getCodeTable().getDisplay(getStatus());
		} else {
			return null;
		}
	}
	
	// 支付状态
	@Transient
	public String getStatusActualDesc() {
		if (getStatusActual() != null) {
			return StatusCodeTable.getCodeTableActual().getDisplay(getStatusActual());
		} else {
			return null;
		}
	}
	
	// 付款方式
	@Transient
	public String getPaymentMethodDesc() {
		if (getPaymentMethod() != null) {
			return StatusCodeTable.getPaymentMethodCode().getDisplay(getPaymentMethod());
		} else {
			return null;
		}
	}
	
	// 付款方式2
	@Transient
	public String getPaymentMethodTwoDesc() {
		if (getPaymentMethodTwo() != null) {
			return StatusCodeTable.getPaymentMethodCode().getDisplay(getPaymentMethodTwo());
		} else {
			return null;
		}
	}
	
	// 付款性质
	@Transient
	public String getPaymentNatureDesc() {
		if (getPaymentNature() != null) {
			return StatusCodeTable.getPaymentNatureCode().getDisplay(getPaymentNature());
		} else {
			return null;
		}
	}
	
	// 款项用途
	@Transient
	public String getPurposeDesc() {
		if(this.getPaymentNature() != null) {
			if(this.getPaymentNature()==0) {
				//工程项下
				if (getPurpose() != null) {
					return StatusCodeTable.getPurposeCode().getDisplay(getPurpose());
				} else {
					return null;
				}
			}else {
				//现场管理费
				if (getPurpose() != null) {
					return StatusCodeTable.getPurposeCodeOne().getDisplay(getPurpose());
				} else {
					return null;
				}
			}
		}else {
			return null;
		}
	}
	
	// 支付类型
	@Transient
	public String getPaymentTypeDesc() {
		if (getPaymentType() != null) {
			return StatusCodeTable.getPaymentTypeCode().getDisplay(getPaymentType());
		} else {
			return null;
		}
	}

	private boolean isNew;

	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	//用于时间段查询
	private Date startDate;

	@Transient
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	private Date endDate;

	@Transient
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 
	 * 日志操作类型
	 * 
	 */
	
	public enum LogOper{
		PAYMENTONSITE_ADD("新建付款申请"), PAYMENTONSITE_EDIT("编辑付款申请"), PAYMENTONSITE_DEL("删除付款申请");
		private String operName;
		LogOper(String operName){
			this.operName = operName;
		}
		public String getOperName() {
			return operName;
		}
		public void setOperName(String operName) {
			this.operName = operName;
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
		return this.getId();
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
	
	private String curreyStr;
	@Transient
	public String getCurreyStr() {
		return curreyStr;
	}

	public void setCurreyStr(String curreyStr) {
		this.curreyStr = curreyStr;
	}
	
	private String amountStr;
	@Transient
	public String getAmountStr() {
		return amountStr;
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}
	
}
