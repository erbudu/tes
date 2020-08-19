package com.supporter.prj.cneec.tpc.credit_letter_settlement.entity;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.supporter.prj.cneec.tpc.credit_letter_apply.entity.CreditLetterApply;
import com.supporter.prj.cneec.tpc.credit_letter_apply.service.CreditLetterApplyService;
import com.supporter.prj.cneec.tpc.credit_letter_settlement.entity.base.BaseCreditLetterSettlement;
import com.supporter.prj.cneec.tpc.util.IModuleEntity;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.util.CodeTable;
import com.supporter.util.CommonUtil;


/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-11-22 10:25:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_CREDIT_LETTER_SETTLEMENT", schema = "")
public class CreditLetterSettlement extends BaseCreditLetterSettlement implements IModuleEntity {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCCLS";
	public static final String DOMAIN_OBJECT_ID = "CreditLetterS";

	public static final int DRAFT = 0;//草稿
	public static final int PROCESSING = 1;//审批进行中
	public static final int COMPLETE = 2;//审批完成

	
	public static final int UNPAID = 0; //待支付.
    public static final int CANCEL_PAID = 8;//取消支付
    public static final int PARTLY_PAID = 10; //部分支付
    public static final int COMPLETED = 11; //支付完毕
    
    public static final String EFFECTIV = "EFFECTIV";
	public static final String FAILURE = "FAILURE";
	public static final String EFFECTIV_DESC="有效";
	public static final String FAILURE_DESC = "失效";
	 

	private String projectName;
	private String contractNo;
	private CreditLetterApply creditLetterApply;
	//以下为流程中用到
	private String prjManagerId;
	private String prjManager;
	private String deptId;
	private String prjId;
	private String procTitle;
	
	private List<CreditLetterSettlementR> creditLetterSettlementRList;
	
	private String delIds;
	
	private String lsCurrencyType;
	private int recordCount;
	private double sAll;
	private double sActAll;
	
	
	private String prjDeptId; //项目所属部门
	 
	 
	public static Map<String, String>  getControlStatusCodeTable(){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("", "");
		map.put(EFFECTIV, EFFECTIV_DESC);
		map.put(FAILURE, FAILURE_DESC);
		return map;
	}
	
	public static Map<Integer, String> getSettlementStatusCodeTable(){
    	Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    	map.put(-1, "全部");
    	map.put(DRAFT, "草稿");
    	map.put(PROCESSING, "审批进行中");
    	map.put(COMPLETE, "审批完成");
		return map;
	}
	
	public static Map<Integer, String> getPaymentStatusCodeTable(){
    	Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    	map.put(-1, "请选择");
    	map.put(UNPAID,"待支付");
    	map.put(CANCEL_PAID,"取消支付");
    	map.put(PARTLY_PAID,"部分支付");
    	map.put(COMPLETED,"支付完毕");
		return map;
	}
	
	@Transient
	public String getSettlementStatusDesc() {
		return CreditLetterSettlementStatus.getValueByKey(this.getSettlementStatus());
	}
	@Transient
	public String getProjectName() {
		return projectName;
	}
	@Transient
	public String getContractNo() {
		return contractNo;
	}

	/**
	 * 无参构造函数
	 */
	public CreditLetterSettlement() {
		super();
	}

	public enum AuthManageType {
		normal(IModule.AuthManageType.NORMAL, "普通角色&权限项方式"), byAdmin(
				IModule.AuthManageType.BY_ADMIN, "管理员权限"), none(
				IModule.AuthManageType.NONE, "不控制");
		private int key;
		private String value;

		AuthManageType(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public static int getKeyByValue(String value) {
			AuthManageType[] types = AuthManageType.values();
			for (AuthManageType type : types) {
				if (StringUtils.equals(value, type.getValue())) {
					return type.getKey();
				}
			}
			return 0;
		}

		public static String getValueByKey(int key) {
			AuthManageType[] types = AuthManageType.values();
			for (AuthManageType type : types) {
				if (key == type.getKey()) {
					return type.getValue();
				}
			}
			return null;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	/**
	 * 
	 * 日志操作类型
	 * @author gongjiwen
	 * @createDate 2017年3月7日
	 * @since TODO: 来源版本
	 * @modifier gongjiwen
	 * @modifiedDate 2017年3月7日
	 */
	public enum LogOper {
		MODULE_ADD("新建应用"), MODULE_EDIT("编辑应用"), MODULE_DEL("删除应用"), MODULE_CHANGE_DISPLAY_ORDER(
				"调整应用显示顺序");
		private String operName;

		LogOper(String operName) {
			this.operName = operName;
		}

		public String getOperName() {
			return operName;
		}

		public void setOperName(String operName) {
			this.operName = operName;
		}

	}

	/**
	 * 构造函数
	 * @param contractId
	 */
	public CreditLetterSettlement(String reportId) {
		super(reportId);
		System.out.println("this.getControlStatusCode()==="+this.getControlStatusCode());
		if ("".equals(this.getControlStatusCode())) {
			this.setControlStatus(ControlStatus.EFFECTIV_DESC);
			this.setControlStatusCode(ControlStatus.EFFECTIV);
		}
	}

	public boolean equals(Object other) {
		if (!(other instanceof CreditLetterSettlement)) {
			return false;
		}
		CreditLetterSettlement castOther = (CreditLetterSettlement) other;
		return StringUtils.equals(this.getCreditLetterSettlementId(), castOther.getCreditLetterSettlementId());
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getCreditLetterSettlementId()).toHashCode();
	}
	@Transient
	public List<CreditLetterSettlementR> getCreditLetterSettlementRList() {
		return creditLetterSettlementRList;
	}

	public void setCreditLetterSettlementRList(List<CreditLetterSettlementR> creditLetterSettlementRList) {
		this.creditLetterSettlementRList = creditLetterSettlementRList;
	}
	public enum CreditLetterSettlementStatus {
		//zero(0, ""), 
		DRAFT(0, "草稿"), PROCESSING(1, "审批进行中"), COMPLETE(2, "审批完成");
		private int key;
		private String value;

		CreditLetterSettlementStatus(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public static int getKeyByValue(String value) {
			CreditLetterSettlementStatus[] types = CreditLetterSettlementStatus.values();
			for (CreditLetterSettlementStatus type : types) {
				if (StringUtils.equals(value, type.getValue())) {
					return type.getKey();
				}
			}
			return 1;
		}

		public static String getValueByKey(int key) {
			CreditLetterSettlementStatus[] types = CreditLetterSettlementStatus.values();
			for (CreditLetterSettlementStatus type : types) {
				if (key == type.getKey()) {
					return type.getValue();
				}
			}
			return null;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
	public enum PaymentStatus {
		//zero(0, ""), 
		UNPAID(0, "待支付"), CANCEL_PAID(8, "取消支付"), PARTLY_PAID(10, "部分支付"),COMPLETED(11,"支付完毕");
		private int key;
		private String value;

		PaymentStatus(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public static int getKeyByValue(String value) {
			PaymentStatus[] types = PaymentStatus.values();
			for (PaymentStatus type : types) {
				if (StringUtils.equals(value, type.getValue())) {
					return type.getKey();
				}
			}
			return 1;
		}

		public static String getValueByKey(int key) {
			PaymentStatus[] types = PaymentStatus.values();
			for (PaymentStatus type : types) {
				if (key == type.getKey()) {
					return type.getValue();
				}
			}
			return null;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
	
	public static class ControlStatus{
		 public static final String EFFECTIV = "EFFECTIV";
		 public static final String FAILURE = "FAILURE";
		 public static final String EFFECTIV_DESC="有效";
		 public static final String FAILURE_DESC = "失效";
		 
		 public static CodeTable getStatusCodeTable(){
			 CodeTable ct = new CodeTable();
			 ct.insertItem("", "", 0);
			 ct.insertItem(EFFECTIV, EFFECTIV_DESC, 1);
			 ct.insertItem(FAILURE, FAILURE_DESC, 2);
			 return ct;
		 }
	 }


//  @Override
//	@Transient
//	public String getEntityName() {
//		return "oa_report";
//	}

//	//详情页下载文件
//	@Transient
//	private String files;
//	@Transient
//	public String getFiles() {
//		return files;
//	}
//
//	public void setFiles(String files) {
//		this.files = files;
//	}
	
	/**
	 * 合同控制状态是否有效.
	 */
	@Transient
	public boolean getIsEffectiv(){
		return ControlStatus.EFFECTIV.equals(CommonUtil.trim(this.getControlStatusCode()));
	}
	/**
	 * 当前实例是否完成了审批.
	 */
	@Transient
	public boolean getIsComplete(){
		return this.getSettlementStatus() == CreditLetterSettlement.CreditLetterSettlementStatus.COMPLETE.key;
	}
	/**
     * 是否待支付.
     */
	@Transient
    public boolean getIsUnpaid(){
    	return getPaymentStatus() == CreditLetterSettlement.PaymentStatus.UNPAID.key;
    }
    
    /**
     * 是否取消支付.
     */
    @Transient
    public boolean getIsCancelPaid(){
    	return this.getPaymentStatus() == CreditLetterSettlement.PaymentStatus.CANCEL_PAID.key;
    }
    
    /**
     * 是否部分支付.
     */
    @Transient
    public boolean getIsPartlyPaid(){
    	return getPaymentStatus() == CreditLetterSettlement.PaymentStatus.PARTLY_PAID.key;
    }
    
    /**
     * 支付完毕.
     */
    @Transient
    public boolean getIsCompletedPaid(){
    	return getPaymentStatus() == CreditLetterSettlement.PaymentStatus.COMPLETED.key;
    }

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	@Transient
	public CreditLetterApply getCreditLetterApply() {
		return creditLetterApply;
	}

	public void setCreditLetterApply(CreditLetterApply creditLetterApply) {
		this.creditLetterApply = creditLetterApply;
	}
	
	@Transient
	public int getBudgetYear(){
		Date ld_Year = new Date();					
		String ls_Year = CommonUtil.formatDate(ld_Year,"yyyy");
		return Integer.parseInt(ls_Year);
	}
	@Transient
	public int getBudgetMonth(){
		Date ld_Year = new Date();					
		String ls_Month = CommonUtil.formatDate(ld_Year,"MM");
		return Integer.parseInt(ls_Month);
	}
	
	@Column(name = "PRJ_MANAGER_ID", length = 32)
	public String getPrjManagerId() {
		return prjManagerId;
	}

	public void setPrjManagerId(String prjManagerId) {
		this.prjManagerId = prjManagerId;
	}

	@Column(name = "PRJ_MANAGER", length = 32)
	public String getPrjManager() {
		return prjManager;
	}
	
	public void setPrjManager(String prjManager) {
		this.prjManager = prjManager;
	}
	@Column(name = "DEPT_ID", length = 32)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Transient
	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	@Transient
	public String getProcTitle() {
		if (CommonUtil.trim(this.getProjectName()).length() == 0 && CommonUtil.trim(this.getCreditLetterId()).length() > 0) {
			CreditLetterApplyService service = SpringContextHolder.getBean(CreditLetterApplyService.class);
			CreditLetterApply apply = service.get(CommonUtil.trim(this.getCreditLetterId()));
			procTitle = CommonUtil.trim(apply.getProjectName())
					+ "  采购合同："
					+ "（" + CommonUtil.trim(apply.getContractNo()) + "）"
					+ CommonUtil.trimToLength(apply.getContractName(), 23)
					+ "（" + CommonUtil.trim(this.getPayerDeptName()) + "）";
		}
		return procTitle;
	}

	@Transient
	public int getSwfStatus() {
		return this.getSettlementStatus();
	}

	@Transient
	public String getSwfStatusDesc() {
		return this.getSettlementStatusDesc();
	}

	@Transient
	public int getDbYear() {
		return 0;
	}

	@Transient
	public String getEntityId() {
		return this.getCreditLetterSettlementId();
	}

	@Transient
	public String getDomainObjectId() {
		return DOMAIN_OBJECT_ID;
	}

	@Transient
	public String getEntityName() {
		return getClass().getName();
	}

	@Transient
	public String getModuleId() {
		return MODULE_ID;
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getCompanyNo() {
		return this.getDeptId();
	}

	@Transient
	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
	@Transient
	public String getLsCurrencyType() {
		return lsCurrencyType;
	}

	public void setLsCurrencyType(String lsCurrencyType) {
		this.lsCurrencyType = lsCurrencyType;
	}
	@Transient
	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	@Transient
	public double getSAll() {
		return sAll;
	}

	public void setSAll(double all) {
		sAll = all;
	}
	@Transient
	public double getSActAll() {
		return sActAll;
	}

	public void setSActAll(double actAll) {
		sActAll = actAll;
	}
	
	@Transient
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}

}
