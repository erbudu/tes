package com.supporter.prj.cneec.tpc.tariff_vat_payment.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.e2b.util.E2bUtil;
import com.supporter.prj.cneec.tpc.benefit_budget.constant.BenefitBudgetItemConstant;
import com.supporter.prj.cneec.tpc.tariff_vat_payment.entity.base.BaseTariffVatPayment;
import com.supporter.util.CommonUtil;

/**
 * @Title: TariffVatPayment
 * @Description: 进口关税和增值税实体类
 * @author: yanweichao
 * @date: 2017-12-20
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_TARIFF_VAT_PAYMENT", schema = "")
public class TariffVatPayment extends BaseTariffVatPayment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCTARVATPAY";
	public static final String DOMAIN_OBJECT_ID = "TariffVatPay";
	
	
	private String prjDeptId; //项目所属部门

	// Constructors

	/** default constructor */
	public TariffVatPayment() {
		super();
	}

	/** minimal constructor */
	public TariffVatPayment(String paymentId) {
		super(paymentId);
	}

	private boolean add;// 是否新增
	private String keyword;// 搜索关键字

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	@Transient
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public static final int DRAFT = 0; // 草稿
	public static final int PROCESSING = 10; // 审核中
	public static final int COMPLETED = 20;// 审批完成

	/**
	 * 获取状态码表.
	 * @return
	 */
	public static Map<Integer, String> getSwfStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(PROCESSING, "审核中");
		map.put(COMPLETED, "审批完成");
		return map;
	}

	// 状态
	@Transient
	public String getSwfStatusDesc() {
		if (this.getSwfStatus() != null) {
			return getSwfStatusMap().get(this.getSwfStatus());
		}
		return "";
	}
	/** 付款类型 **/
	public static final String IMPORT_TARIFF = BenefitBudgetItemConstant.SUMMARY_IMPORT_TARIFF; // 费用合计-国内进口关税
	public static final String VALUE_ADDED_TAX = BenefitBudgetItemConstant.SUMMARY_VALUE_ADDED_TAX; // 费用合计-国内进口增值税


	public static Map<String, String> getPaymentTypeMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(IMPORT_TARIFF, "进口关税");
		map.put(VALUE_ADDED_TAX, "进口增值税");
		return map;
	}

	@Transient
	public String getBudgetId() {
		return super.getPaymentType();
	}

	// 付款类型
	@Transient
	public String getPaymentTypeDesc() {
		return getPaymentTypeMap().get(this.getPaymentType());
	}
	
	// 资金用途
	@Transient
	public String getCapticalPurposeCodeDesc() {
		return E2bUtil.getCapticalpurposeCodeTable().get(this.getCapticalPurposeCode());
	}

	/** 声明流程用到的参数  **/

	private String procId;// 流程ID
	private String procTitle;// 流程标题
	private String prjManagerId;
	private String prjManager;
	private String bankTellerIds;
	private String bankTellerNames;

	@Column(name = "PRJ_MANAGER", length = 32)
	public String getPrjManager() {
		return this.prjManager;
	}

	public void setPrjManager(String prjManager) {
		this.prjManager = prjManager;
	}

	@Column(name = "PRJ_MANAGER_ID", length = 32)
	public String getPrjManagerId() {
		return this.prjManagerId;
	}

	public void setPrjManagerId(String prjManagerId) {
		this.prjManagerId = prjManagerId;
	}

	@Column(name = "BANK_TELLER_IDS", nullable = true, length = 512)
	public String getBankTellerIds() {
		return this.bankTellerIds;
	}

	public void setBankTellerIds(String bankTellerIds) {
		this.bankTellerIds = bankTellerIds;
	}

	@Column(name = "BANK_TELLER_NAMES", nullable = true, length = 512)
	public String getBankTellerNames() {
		return this.bankTellerNames;
	}

	public void setBankTellerNames(String bankTellerNames) {
		this.bankTellerNames = bankTellerNames;
	}

	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	@Transient
	public String getProcTitle() {
		procTitle = this.getProjectName() + "（" + this.getDeptName() + "）";
		return procTitle;
	}

	@Transient
	public int getDbYear() {
		return 0;
	}

	@Transient
	public String getModuleId() {
		return MODULE_ID;
	}

	@Transient
	public String getDomainObjectId() {
		return DOMAIN_OBJECT_ID;
	}

	@Transient
	public String getCompanyNo() {
		return CommonUtil.trim(this.getDeptId());
	}

	@Transient
	public String getEntityId() {
		return this.getPaymentId();
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getEntityName() {
		return this.getClass().getName();
	}
	
	@Transient
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}

}
