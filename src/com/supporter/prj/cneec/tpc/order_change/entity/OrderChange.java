package com.supporter.prj.cneec.tpc.order_change.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.order_change.entity.base.BaseOrderChange;

@Entity
@Table(name = "TPC_ORDER_CHANGE", schema = "")
public class OrderChange extends BaseOrderChange implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "ORDCHANGE";
	public static final String DOMAIN_OBJECT_ID = "sellChange";
	public static final int DB_YEAR = 0;

	
	private String prjDeptId; // 项目所属部门

	// Constructors

	/** default constructor */
	public OrderChange() {
		super();
	}

	/** minimal constructor */
	public OrderChange(String changeId) {
		super(changeId);
	}

	private boolean add;// 是否新增
	private String keyword;// 搜索关键字
	private boolean isNew;
	private String enName;// 搜索关键字

	private boolean changeAr = false;

	@Transient
	public String getEnName() {
		return "销售合同";
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

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

	
	@Transient
	public boolean getChangeAr() {
		if (this.getChangeAmount() >= 1000000 && this.getChangeRate() >= 0.2) {
			changeAr = true;
		}
		return changeAr;
	}

	public static final int DRAFT = 0; // 草稿
	public static final int PROCESSING = 10; // 审核中
	public static final int COMPLETED = 20;// 审批完成
	public static final int REJECTED = 30;// 驳回
	public static final int SEALOK = 40;// 用印审批完成
	public static final int CONTRACTOK = 50;// 合同审批完成

	/**
	 * 获取状态码表.
	 * 
	 * @return
	 */
	public static Map<Integer, String> getSwfStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(PROCESSING, "变更单审核中");
		map.put(COMPLETED, "变更单审批完成");
		map.put(REJECTED, "驳回");
		map.put(SEALOK, "用印审批完成");
		map.put(CONTRACTOK, "合同变更审批完成");
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

	// @Transient
	// public String getHasProtocolDesc() {
	// return this.isHasProtocol() ? "是" : "否";
	// }

	/**
	 * 选择类型. 1-合同变更 2-合同数据调整
	 */
	public static final class chType {
		public static final int CONCH = 1; // 合同变更
		public static final int CONADJ = 2; // 合同数据调整

		/**
		 * 获取状态码表.
		 * 
		 * @return Map<Integer, String>
		 */
		public static Map<Integer, String> getMap() {
			Map<Integer, String> map = new LinkedHashMap<Integer, String>();
			map.put(CONCH, "合同变更");
			map.put(CONADJ, "合同数据调整");
			return map;
		}

		private chType() {

		}
	}

	/** 声明流程用到的参数 **/

	private String procId;// 流程ID

	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	@Transient
	public String getModuleId() {
		return MODULE_ID;
	}

	@Transient
	public int getDbYear() {
		return DB_YEAR;
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
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getCompanyNo() {
		return getDeptId();
	}
	
	@Transient
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}
}
