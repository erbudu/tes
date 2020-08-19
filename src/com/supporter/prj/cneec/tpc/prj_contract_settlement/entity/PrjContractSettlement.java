package com.supporter.prj.cneec.tpc.prj_contract_settlement.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.e2b.util.E2bUtil;
import com.supporter.prj.cneec.tpc.benefit_budget.constant.BenefitBudgetItemConstant;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.base.BasePrjContractSettlement;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractTable;
import com.supporter.prj.cneec.tpc.util.IModuleEntity;
import com.supporter.util.CodeTable;
import com.supporter.util.CommonUtil;

/**
 * @Title: PrjContractSettlement
 * @Description: 
 * @author: yanweichao
 * @date: 2018-11-16
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_PRJ_CONTRACT_SETTLEMENT", schema = "")
public class PrjContractSettlement extends BasePrjContractSettlement implements IModuleEntity {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCCONSET";
	public static final String DOMAIN_OBJECT_ID = "PrjConSett";
	public static final String BUSI_TYPE_ProveApply = "ProveApply";// 付款申请报告
	public static final String BUSI_TYPE_ProveOwner = "ProveOwner";// 业主及代表审批的文件
	public static final String BUSI_TYPE_ProvePrj = "ProvePrj";// 项目相关文件
	public static final String BUSI_TYPE_ProveSpot = "ProveSpot";// 总包方现场审批文件
	public static final String BUSI_TYPE = "settlementFile";// 上传附件

	public static int NO_MONTH_REPORT_SUBMIT = 1;// 无月报可以提交
	public static int MONTH_REPORT_SUBMIT = 0;// 必须提交月报
	public static int isInner = 1; // 国内

	public static final int UNPAID = 0; // 待支付.
	public static final int CANCEL_PAID = 8;// 取消支付
	public static final int PARTLY_PAID = 10; // 部分支付
	public static final int COMPLETED = 11; // 支付完毕

	@Transient
	private List<PrjContractSettlementRec> materialList;// 明细表
	@Transient
	private String contractNo;// 合同编号
	@Transient
	private String vendorType;// 供方类型,多出属性
	@Transient
	private String stageType;// 阶段类型,多出属性
	@Transient
	private String procTitle;// 流程标题
	@Transient
	private int itemNameCheck;// 判断是否出现咨询费、佣金或代理费或其他特殊项目需要领导批准,多出属性

	// 预算
	public static final String PURCHASE_PAYMENT = BenefitBudgetItemConstant.SUMMARY_PURCHASE_PAYMENT;

	@Transient
	private String prjDeptId; //项目所属部门
	
	@Transient
	public String getBudgetId() {
		return PURCHASE_PAYMENT;
	}

	@Transient
	public String getBudgetName() {
		return "货款（服务款）";
	}

	@Transient
	private double actAmount;// 合同总金额

	public double getActAmount() {
		return actAmount;
	}

	public void setActAmount(double actAmount) {
		this.actAmount = actAmount;
	}

	@Transient
	private PrjContractTable contract;// 采购合同

	public PrjContractTable getContract() {
		return this.contract;
	}

	public void setContract(PrjContractTable contract) {
		this.contract = contract;
	}

	/**
	 * 付款状态常量类.
	 * 
	 * @author zhaohe
	 */
	public static final class SettlementStatus {
		public static final int DRAFT = 0;// 未提交
		public static final int PROCESSING = 1;// 审核中
		public static final int COMPLETE = 2;// 已付款

		/**
		 * 构造方法.
		 */
		private SettlementStatus() {
		}

		/**
		 * 获取付款状态码表.
		 */
		public static Map<String, String> getCodeTable() {
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("", "全部");
			map.put(DRAFT + "", "草稿");
			map.put(PROCESSING + "", "审核中");
			map.put(COMPLETE + "", "审批完成");
			return map;
		}
	}

	// 获得是否合同供方.
	public String getIsQualifiedDesc() {
		String ls_Qualified = "合格供应商";
		String ls_NoQualifiede = "不合格供应商";

		if (getIsQualified() != null && getIsQualified().trim().equals("1")) {
			return ls_Qualified;
		} else {
			return ls_NoQualifiede;
		}
	}

	/**
	 * 付款方式常量类.
	 * 
	 * @author zhaohe
	 */
	public static final class SettlementPayMothod {
		public static final String CASH = "现金";// 现金
		public static final String CHEQUE = "支票";// 支票
		public static final String TELEGRAPHIC_MONEY_ORDER = "电汇";// 电汇
		public static final String SIGHT_DRAFT = "即期汇票";// 即期汇票
		public static final String FUTURE_SIGHT_DRAFT = "远期汇票";// 远期汇票

		/**
		 * 构造方法.
		 */
		private SettlementPayMothod() {
		}

		/**
		 * 获取付款方式码表.
		 */
		public static CodeTable getCodeTable() {
			CodeTable lcdtbl_Return = new CodeTable();
			lcdtbl_Return.insertItem(0, CASH);
			lcdtbl_Return.insertItem(1, CHEQUE);
			lcdtbl_Return.insertItem(3, TELEGRAPHIC_MONEY_ORDER);
			lcdtbl_Return.insertItem(4, SIGHT_DRAFT);
			lcdtbl_Return.insertItem(5, FUTURE_SIGHT_DRAFT);
			return lcdtbl_Return;
		}
	}

	/**
	 * 封装支付状态.
	 * 
	 * @author hsh
	 * 
	 */
	public static class PaymentStatus {
		public static final int UNPAID = 0; // 待支付.
		public static final int CANCEL_PAID = 8;// 取消支付
		public static final int PARTLY_PAID = 10; // 部分支付
		public static final int COMPLETED = 11; // 支付完毕

		/**
		 * 构造方法.
		 * 
		 */
		private PaymentStatus() {
		}

		public static CodeTable getCodeTable() {
			CodeTable lcdtbl_Return = new CodeTable();
			lcdtbl_Return.insertItem(UNPAID, "待支付");
			lcdtbl_Return.insertItem(CANCEL_PAID, "取消支付");
			lcdtbl_Return.insertItem(PARTLY_PAID, "部分支付");
			lcdtbl_Return.insertItem(COMPLETED, "支付完毕");
			return lcdtbl_Return;
		}
	}

	public static class ControlStatus {
		public static final String EFFECTIV = "EFFECTIV";
		public static final String FAILURE = "FAILURE";
		public static final String EFFECTIV_DESC = "有效";
		public static final String FAILURE_DESC = "失效";

		public static CodeTable getStatusCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem("", "", 0);
			ct.insertItem(EFFECTIV, EFFECTIV_DESC, 1);
			ct.insertItem(FAILURE, FAILURE_DESC, 2);
			return ct;
		}

		/**
		 * 获取付款状态码表.
		 */
		public static Map<String, String> getCodeTable() {
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put(EFFECTIV_DESC, EFFECTIV_DESC);
			map.put(FAILURE_DESC, FAILURE_DESC);
			return map;
		}
	}

	public static final int REFUND = 0; // 未退款
	public static final int REFUND_LOCK = 10; // 退款锁定中
	public static final int REFUNDING = 20; // 退款中
	public static final int REFUNDED_PARTIAL = 30;// 部分退款完成
	public static final int REFUNDED = 40;// 全部退款完成

	/**
	 * 获取退款状态码表.
	 * 
	 * @return
	 */
	public static Map<Integer, String> getRefundStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(REFUND, "未退款");
		map.put(REFUND_LOCK, "退款锁定中");
		map.put(REFUNDING, "退款中");
		map.put(REFUNDED_PARTIAL, "部分退款完成");
		map.put(REFUNDED, "全部退款完成");
		return map;
	}

	// 退款状态
	@Transient
	public String getRefundStatusDesc() {
		return getRefundStatusMap().get(this.getRefundStatus());
	}

	// 资金用途
	@Transient
	public String getCapticalPurposeCodeDesc() {
		return E2bUtil.getCapticalpurposeCodeTable().get(this.getCapticalPurposeCode());
	}
		
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public List<PrjContractSettlementRec> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<PrjContractSettlementRec> materialList) {
		this.materialList = materialList;
	}

	public String getVendorType() {
		return vendorType;
	}

	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}

	public String getStageType() {
		return stageType;
	}

	public void setStageType(String stageType) {
		this.stageType = stageType;
	}

	public String getProcTitle() {
		procTitle = CommonUtil.trim(getPrjName()) + "  " + CommonUtil.trim(getContractNo()) + CommonUtil.trimToLength(getContractName(), 23) + "  （" + getPayerDeptName() + "）";
		return procTitle;
	}

	public int getDbYear() {
		return 0;
	}

	public String getDomainObjectId() {
		return DOMAIN_OBJECT_ID;
	}

	public String getEntityName() {
		return getClass().getName();
	}

	public String getModuleId() {
		return MODULE_ID;
	}

	public String getModuleBusiType() {
		return "";
	}

	public int getItemNameCheck() {
		return itemNameCheck;
	}

	public void setItemNameCheck(int itemNameCheck) {
		this.itemNameCheck = itemNameCheck;
	}

	@Transient
	public int getSwfStatus() {
		return this.getSettlementStatus();
	}

	@Transient
	public String getSwfStatusDesc() {
		return SettlementStatus.getCodeTable().get(this.getSwfStatus() + "");
	}

	@Transient
	public String getEntityId() {
		return this.getSettlementId();
	}

	@Transient
	public String getCompanyNo() {
		return CommonUtil.trim(this.getPayerDeptId());
	}
	
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}

}
