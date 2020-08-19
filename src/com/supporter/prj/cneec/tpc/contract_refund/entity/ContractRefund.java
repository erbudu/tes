package com.supporter.prj.cneec.tpc.contract_refund.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.contract_refund.entity.base.BaseContractRefund;
import com.supporter.prj.cneec.tpc.contract_refund.util.ContractRefundConstant;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractRefund
 * @Description: 销售合同退款实体类
 * @author: yanweichao
 * @date: 2017-11-24
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_CONTRACT_REFUND", schema = "")
public class ContractRefund extends BaseContractRefund implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCCONREFUND";
	
	private String prjDeptId; //项目所属部门

	// Constructors

	/** default constructor */
	public ContractRefund() {
		super();
	}

	/** minimal constructor */
	public ContractRefund(String refundId) {
		super(refundId);
	}

	private boolean add = false;// 是否新增
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

	// 境内外付款
	@Transient
	public String getIsAbroadDesc() {
		return ContractRefundConstant.getIsAbroadMap().get(String.valueOf(this.getIsAbroad()));
	}

	/** 声明流程用到的参数  **/

	private String procId;// 流程ID
	private String procTitle;// 流程标题
	private String prjManagerId;
	private String prjManager;
	private String bankTellerIds;
	private String bankTellerNames;

	@Column(name = "PRJ_MANAGER_ID", length = 32)
	public String getPrjManagerId() {
		return this.prjManagerId;
	}

	public void setPrjManagerId(String prjManagerId) {
		this.prjManagerId = prjManagerId;
	}

	@Column(name = "PRJ_MANAGER", length = 32)
	public String getPrjManager() {
		return this.prjManager;
	}

	public void setPrjManager(String prjManager) {
		this.prjManager = prjManager;
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
		procTitle = CommonUtil.trim(this.getProjectName()) + "的" + CommonUtil.trim(this.getContractName()) + "（" + this.getDeptName() + "）";
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
		return this.getClass().getSimpleName();
	}

	@Transient
	public String getCompanyNo() {
		return CommonUtil.trim(this.getDeptId());
	}

	@Transient
	public String getEntityId() {
		return this.getRefundId();
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getEntityName() {
		return this.getClass().getName();
	}
	
	private List<ContractRefundDetail> detailList;
	
	private String delDetailIds;
    
	@Transient
	public List<ContractRefundDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ContractRefundDetail> detailList) {
		this.detailList = detailList;
	}
	@Transient
	public String getDelDetailIds() {
		return delDetailIds;
	}

	public void setDelDetailIds(String delDetailIds) {
		this.delDetailIds = delDetailIds;
	}
	
	@Transient
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}
}
