package com.supporter.prj.cneec.tpc.credit_letter_apply.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.credit_letter_apply.entity.base.BaseCreditLetterApply;
import com.supporter.util.CodeTable;
import com.supporter.util.CommonUtil;

/**
 * 信用证开证申请
 */
@Entity
@Table(name = "TPC_CREDIT_LETTER_APPLY", schema = "SUPP_APP")
public class CreditLetterApply extends BaseCreditLetterApply implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCCRELETAPP";
	public static final String DOMAIN_OBJECT_ID = "CreditLetterApp";//app业务对象的编号
	public static final String BUSI_TYPE = "TPC_CREDIT_LETTER_APPLY";
	
	
	private String prjDeptId; //项目所属部门

	// Constructors

	/** default constructor */
	public CreditLetterApply() {
	}

	/** minimal constructor */
	public CreditLetterApply(String creditLetterId) {
		super(creditLetterId);
	}
	
	//审批状态
	public static final int DRAFT = 0;// 草稿
	public static final int PROCESSING = 1;// 审核中
	public static final int COMPLETED = 2;// 完成
	public static final String DRAFT_DESC = "草稿";
	public static final String PROCESSING_DESC = "审核中";
	public static final String COMPLETED_DESC = "审批完成";
	
	public static CodeTable getSwfStatusCodeTable(){
		CodeTable ct = new CodeTable();
		ct.insertItem(DRAFT, DRAFT_DESC);
		ct.insertItem(PROCESSING, PROCESSING_DESC);
		ct.insertItem(COMPLETED, COMPLETED_DESC);
		return ct;
	}
	
	public static Map<Integer, String> getSwfStatusMap(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, DRAFT_DESC);
		map.put(PROCESSING, PROCESSING_DESC);
		map.put(COMPLETED, COMPLETED_DESC);
		return map;
	}
	
	//获取审批状态
	@Transient
	public String getSwfStatusDesc(){
		if (this.getSwfStatus() != null){
			return getSwfStatusCodeTable().getDisplay(this.getSwfStatus());
		}
		return "";
	}
	
	//经营性质
	public static final String SELE_SUPPORT = "SELE_SUPPORT";
	public static final String AGENT = "AGENT";
	public static final String SELE_SUPPORT_DESC = "自营";
	public static final String AGENT_DESC = "代理";
	
	public static CodeTable getBusinessTypeCodeTable(){
		CodeTable ct = new CodeTable();
		ct.insertItem(SELE_SUPPORT, SELE_SUPPORT_DESC);
		ct.insertItem(AGENT, AGENT_DESC);
		return ct;
	}
	
	public static Map<String, String> getBusinessTypeMap(){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(SELE_SUPPORT, SELE_SUPPORT_DESC);
		map.put(AGENT, AGENT_DESC);
		return map;
	}
	
	//付款期限
	public static final String NOW = "NOW";
	public static final String FUTURRE = "FUTURRE";
	public static final String NOW_DESC = "即期";
	public static final String FUTURRE_DESC = "远期";
	
	public static CodeTable getSettlementTermCodeTable(){
		CodeTable ct = new CodeTable();
		ct.insertItem(NOW, NOW_DESC);
		ct.insertItem(FUTURRE, FUTURRE_DESC);
		return ct;
	}
	
	public static Map<String, String> getSettlementTermMap(){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(NOW, NOW_DESC);
		map.put(FUTURRE, FUTURRE_DESC);
		return map;
	}

	public static final int SETTLEMENT = 0;
	public static final int SETTLEMENTING = 10;
	public static final int SETTLEMENTED = 20;

	public static Map<Integer, String> getSettlementMap(){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(SETTLEMENT, "待付款");
		map.put(SETTLEMENTING, "付款中");
		map.put(SETTLEMENTED, "付款完成");
		return map;
	}

	// 搜索关键字
	private String keyword;
	
	@Transient
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	//新建属性
	private boolean isNew;
	
	@Transient
	public boolean getIsNew(){
		return this.isNew;
	}
	
	public void setIsNew(boolean isNew){
		this.isNew = isNew;
	}

	/** 声明流程用到的参数  **/

	private String procId;// 流程ID

	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
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
		return this.getCreditLetterId();
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getEntityName() {
		return this.getClass().getName();
	}

	/**
	 * 待办审批标题
	 * @return
	 */
	@Transient
	public String getProcTitle() {
		String title = CommonUtil.trim(this.getProjectName());
		title += "（" + CommonUtil.trim(this.getContractNo()) + "）";
		title += "  采购合同：";
		title += CommonUtil.trimToLength(this.getContractName(), 23);
		title += "（" + CommonUtil.trim(this.getDeptName()) + "）";
		return title;
	}
	
	@Transient
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}

}
