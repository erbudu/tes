package com.supporter.prj.cneec.tpc.prj_settlement.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.e2b.util.E2bUtil;
import com.supporter.prj.cneec.tpc.prj_settlement.entity.base.BasePrjSettlement;
import com.supporter.prj.cneec.tpc.util.IModuleEntity;
import com.supporter.util.CommonUtil;

@Entity
@Table(name = "TPC_PRJ_SETTLEMENT", schema = "")
public class PrjSettlement extends BasePrjSettlement implements IModuleEntity {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCSETTLMENT";
	public static final String BUSI_TYPE = "settlementFiles";
	
	public static int NO_MONTH_REPORT_SUBMIT = 1;//无月报可以提交
	public static int MONTH_REPORT_SUBMIT=0;//必须提交月报
	
	@Transient
	private String prjDeptId; //项目所属部门
	
	/**
     * 费用支付状态常量类.
     * @author huangye
     */
	public static final int DRAFT = 0;//草稿
	public static final int PROCESSING = 1;//审核中
	public static final int COMPLETE = 2;//已付款

	/**
	 * 费用类型（费用支付）
	 * @author 田森
	 */
	public static final int BANK_CHARGES = 50;
	public static final String BANK_CHARGES_DESC = "银行费用";
	
	public static final int PORT_COST = 20;
	public static final String PORT_COST_DESC = "港杂费";
	
	public static final int CUSTOMS_DECLARATION_FEE = 30;
	public static final String CUSTOMS_DECLARATION_FEE_DESC = "报关费";
	
	public static final int OTHER_COST = 40;
	public static final String OTHER_COST_DESC = "其他";
	
	
	@Transient
	private List<PrjSettlementRec> materialList;
	@Transient
	private double actAmount;//合同总金额

	@Transient
	private String procTitle;//流程标题
	@Transient
	private int itemNameCheck;//判断是否出现咨询费、佣金或代理费或其他特殊项目需要领导批准
	
	/**
	 * 获取付款状态码表.
	 */
	public static Map<String, String> getCodeSettlementStatus() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(DRAFT+"","草稿");
		map.put(PROCESSING+"","审核中"); 
		map.put(COMPLETE+"","审批完成");
        return map;
    }

	@Transient
	public int getSwfStatus() {
		return this.getSettlementStatus();
	}

	@Transient
	public String getSwfStatusDesc() {
		return getCodeSettlementStatus().get(this.getSwfStatus()+"");
	}

	/**
	 * 封装支付状态.
	 */
    public static final int _UNPAID = 0; //待支付.
    public static final int _CANCEL_PAID = 8;//取消支付
    public static final int _PARTLY_PAID = 10; //部分支付
    public static final int _COMPLETED = 11; //支付完毕
    public static final String UNPAID = "待支付"; //待支付.
    public static final String CANCEL_PAID = "取消支付";//取消支付
    public static final String PARTLY_PAID = "部分支付"; //部分支付
    public static final String COMPLETED = "支付完毕"; //支付完毕

	/**
	 * 封装支付状态.
	 * @return
	 */
    public static Map<Integer, String> getCodePaymentStatus() {
    	Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(_UNPAID, UNPAID);
		map.put(_CANCEL_PAID, CANCEL_PAID);
		map.put(_PARTLY_PAID, PARTLY_PAID);
		map.put(_COMPLETED, COMPLETED);
		return map;
    }

	public static final String EFFECTIV = "EFFECTIV";
	public static final String FAILURE = "FAILURE";
	public static final String EFFECTIV_DESC = "有效";
	public static final String FAILURE_DESC = "失效";

	public static Map<String, String> getStatusCodeTable() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("", "");
		map.put(EFFECTIV, EFFECTIV_DESC);
		map.put(FAILURE, FAILURE_DESC);
		return map;
	}

	/**
     * 付款方式常量类.
     * @author huangye
     */
	public static final String _CASH = "0";//现金
	public static final String _CHEQUE = "1";//支票
	public static final String _TELEGRAPHIC_MONEY_ORDER = "3";//电汇
	public static final String _SIGHT_DRAFT = "4";//即期汇票
	public static final String _FUTURE_SIGHT_DRAFT = "5";//远期汇票
	public static final String CASH = "现金";//现金
	public static final String CHEQUE = "支票";//支票
	public static final String TELEGRAPHIC_MONEY_ORDER = "电汇";//电汇
	public static final String SIGHT_DRAFT = "即期汇票";//即期汇票
	public static final String FUTURE_SIGHT_DRAFT = "远期汇票";//远期汇票

	/**
	 * 获取付款方式码表.
	 */
	public static Map<String, String> getCodeSettlementPayMothod() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(_CASH, CASH);
		map.put(_CHEQUE, CHEQUE);
		map.put(_TELEGRAPHIC_MONEY_ORDER, TELEGRAPHIC_MONEY_ORDER);
		map.put(_SIGHT_DRAFT, SIGHT_DRAFT);
		map.put(_FUTURE_SIGHT_DRAFT, FUTURE_SIGHT_DRAFT);
		return map;
	}

	/**
     * 付款途径
     * @author huangye
     */
	public static final String _DOMESTIC = "0";//公司财务直接支付
	public static final String _EXTERNAL = "1";//现场直接支付
	public static final String DOMESTIC = "公司财务直接支付";//公司财务直接支付
	public static final String EXTERNAL = "现场直接支付";//现场直接支付

	/**
	 * 获取付款途径码表.
	 */
	public static Map<String, String> getCodeSettlementWay() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(_DOMESTIC, DOMESTIC);
		map.put(_EXTERNAL, EXTERNAL); 
        return map;
    }

	/**
	 * 获取费用类型
	 */
	public static Map<String, String> getCostType() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(BANK_CHARGES + "", BANK_CHARGES_DESC);
		map.put(PORT_COST + "", PORT_COST_DESC);
		map.put(CUSTOMS_DECLARATION_FEE + "", CUSTOMS_DECLARATION_FEE_DESC);
		map.put(OTHER_COST + "", OTHER_COST_DESC);
		return map;
	}

	// 资金用途
	@Transient
	public String getCapticalPurposeCodeDesc() {
		return E2bUtil.getCapticalpurposeCodeTable().get(this.getCapticalPurposeCode());
	}

	public List<PrjSettlementRec> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<PrjSettlementRec> materialList) {
		this.materialList = materialList;
	}

	public double getActAmount() {
		return actAmount;
	}

	public void setActAmount(double actAmount) {
		this.actAmount = actAmount;
	}
	
	public int getDbYear() {
		return 0;
	}
	
	public String getDomainObjectId(){
	    return "PrjSettlement";
	}
	public String getCompanyNo() {
		return CommonUtil.trim(this.getPayerDeptId());
	}
	
	public String getEntityId() {
		return this.getSettlementId();
	}
	public String getEntityName() {
		return getClass().getName();
	}

	public String getModuleId() {
		return MODULE_ID;
	}

	public String getModuleBusiType(){
		 return "";
	}

	public String getProcTitle() {
		procTitle = CommonUtil.trim(getPrjName())+" " +"（"+getPayerDeptName() + "）";
		return procTitle;
	}

	public int getItemNameCheck() {
		return itemNameCheck;
	}

	public void setItemNameCheck(int itemNameCheck) {
		this.itemNameCheck = itemNameCheck;
	}
	
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}
	
}
