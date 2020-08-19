package com.supporter.prj.cneec.tpc.supplier_refund.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.supplier_refund.entity.SupplierRefundDetail;
import com.supporter.prj.cneec.tpc.supplier_refund.entity.base.BaseSupplierRefund;
import com.supporter.util.CommonUtil;

/**
 * @Title: SupplierRefund
 * @Description: 供应商退款实体类
 * @author: yanweichao
 * @date: 2017-11-22
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_SUPPLIER_REFUND", schema = "")
public class SupplierRefund extends BaseSupplierRefund implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCSUPREFUND";
	
	
	private String prjDeptId; //项目所属部门

	// Constructors

	/** default constructor */
	public SupplierRefund() {
		super();
	}

	/** minimal constructor */
	public SupplierRefund(String refundId) {
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

	/** 声明流程用到的参数  **/

	private String procId;// 流程ID
	private String procTitle;// 流程标题
	private String prjManagerId;
	private String prjManager;

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
	private List<SupplierRefundDetail> detailList;
	
	private String delDetailIds;
    
	@Transient
	public List<SupplierRefundDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<SupplierRefundDetail> detailList) {
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
