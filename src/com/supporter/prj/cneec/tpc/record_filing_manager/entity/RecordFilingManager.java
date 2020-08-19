package com.supporter.prj.cneec.tpc.record_filing_manager.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.derivative_contract.entity.DerivativeContract;
import com.supporter.prj.cneec.tpc.derivative_contract.service.DerivativeContractService;
import com.supporter.prj.cneec.tpc.record_filing_manager.entity.base.BaseRecordFilingManager;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.util.CodeTable;
import com.supporter.util.CommonUtil;

/**
 * 备案管理
 */
@Entity
@Table(name = "TPC_RECORD_FILING_MANAGER", schema = "SUPP_APP")
public class RecordFilingManager extends BaseRecordFilingManager implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCRECFILMGR";// 合同备案
	public static final String BUSI_TYPE = "TPC_RECORD_FILING_MANAGER";// 合同草稿等
	public static final String BUSI_TYPE_CONTRACT = "TPC_FILING_CONTRACT_SCAN";// 合同扫描件，需要拷贝到信息上线的
	public static final String DOMAIN_OBJECT_ID = "RecordFilingMgr";
	
	
	private String prjDeptId; //项目所属部门

	// Constructors

	/** default constructor */
	public RecordFilingManager() {
	}

	/** minimal constructor */
	public RecordFilingManager(String recordFilingId) {
		super(recordFilingId);
	}
	
	//审批状态
	public static final int DRAFT = 0;// 草稿
	public static final int PROCESSING = 1;// 审核中
	public static final int COMPLETED = 2;// 完成
	public static final String DRAFT_DESC = "草稿";
	public static final String PROCESSING_DESC = "审核中";
	public static final String COMPLETED_DESC = "备案完成";
	
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
	//是否需要盖章
	public static final String IS_STAMP = "1";//是
	public static final String IS_NOT_STAMP = "0";//否
	public static final String IS_STAMP_DESC = "是";
	public static final String IS_NOT_STAMP_DESC = "否";
	
	public static Map<String, String> getIsStampMap(){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(IS_STAMP, IS_STAMP_DESC);
		map.put(IS_NOT_STAMP, IS_NOT_STAMP_DESC);
		return map;
	}

	// 是否盖章用印
	@Transient
	public String getIsStamp() {
		return this.getIsUseSeal();
	}

	//获取盖章状态
	@Transient
	public String getIsStampDesc() {
		if (this.getIsStamp() != null) {
			return getIsStampMap().get(this.getIsStamp());
		}
		return "";
	}

	//获取盖章状态
	@Transient
	public boolean isStamp() {
		if (CommonUtil.trim(this.getIsStamp()).equals(IS_STAMP)) {
			return true;
		}
		return false;
	}

	//是否用印
	public static final String IS_USE_SEAL = "1";//已用印
	public static final String IS_NOT_USE_SEAL = "0";//未用印
	public static final String IS_USE_SEAL_DESC = "已用印";
	public static final String IS_NOT_USE_SEAL_DESC = "未用印";
	
	public static CodeTable getIsUseSealCodeTbale(){
		CodeTable ct = new CodeTable();
		ct.insertItem(IS_USE_SEAL, IS_USE_SEAL_DESC);
		ct.insertItem(IS_NOT_USE_SEAL, IS_NOT_USE_SEAL_DESC);
		return ct;
	}
	
	public static Map<String, String> getIsUseSealMap(){
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(IS_USE_SEAL, IS_USE_SEAL_DESC);
		map.put(IS_NOT_USE_SEAL, IS_NOT_USE_SEAL_DESC);
		return map;
	}
	
	//获取用印状态
	@Transient
	public String getIsUseSealDesc() {
		if (this.getIsUseSeal() != null) {
			return getIsUseSealCodeTbale().getDisplay(this.getIsUseSeal());
		}
		return "";
	}
	
	// 搜索关键字
	private String keyword;
	private String relyBy;// 模块依赖（过滤数据）
	
	@Transient
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Column(name = "RELY_BY", nullable = true, length = 32)
	public String getRelyBy() {
		return relyBy;
	}

	public void setRelyBy(String relyBy) {
		this.relyBy = relyBy;
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

	@Transient
	public String getPaymentType() {// 衍生合同类型
		if (CommonUtil.trim(this.getRelyBy()).equals(TpcConstant.RELY_BY_DERIVATIVE_CONTRACT)) {
			DerivativeContractService service = SpringContextHolder.getBean(DerivativeContractService.class);
			DerivativeContract contract = service.get(CommonUtil.trim(this.getPreformId()));
			if (contract != null) {
				return contract.getPaymentType();
			}
		}
		return null;
	}

	/** 声明流程用到的参数  **/
	@Transient
	public String getProcTitle() {
		String procTitle = CommonUtil.trim(this.getProjectName()) + "的";
		if (CommonUtil.trim(this.getRelyBy()).equals(TpcConstant.RELY_BY_PROTOCOL_REVIEW)) {
			procTitle += "框架协议" + CommonUtil.trim(this.getBusinessNo());
		} else if (CommonUtil.trim(this.getRelyBy()).equals(TpcConstant.RELY_BY_CONTRACT_SIGN_REVIEW)) {
			procTitle += "合同" + CommonUtil.trim(this.getBusinessNo());
		} else if (CommonUtil.trim(this.getRelyBy()).equals(TpcConstant.RELY_BY_DERIVATIVE_CONTRACT)) {
			DerivativeContractService service = SpringContextHolder.getBean(DerivativeContractService.class);
			DerivativeContract contract = service.get(CommonUtil.trim(this.getPreformId()));
			String paymentTypeDesc = "";
			if (contract != null) {
				paymentTypeDesc = "（" + CommonUtil.trim(contract.getPaymentTypeDesc()) + "）";
			}
			procTitle += paymentTypeDesc + "衍生合同";
		}
		procTitle += "（" + this.getDeptName() + "）";
		return procTitle;
	}

	private String procId;

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
		return this.getRecordFilingId();
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
