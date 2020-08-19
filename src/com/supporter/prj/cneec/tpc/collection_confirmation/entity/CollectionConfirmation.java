package com.supporter.prj.cneec.tpc.collection_confirmation.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.collection_confirmation.entity.base.BaseCollectionConfirmation;
import com.supporter.util.CommonUtil;

/**
 * @Title: CollectionConfirmation
 * @Description: 收款确认实体类
 * @author: yanweichao
 * @date: 2017-11-17
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_COLLECTION_CONFIRMATION", schema = "")
public class CollectionConfirmation extends BaseCollectionConfirmation implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCCOLLCONFI";
	public static final String DOMAIN_OBJECT_ID = "CollectionConfi";// 业务对象编号，最长15位

	
	private String prjDeptId; //项目所属部门
	
	// Constructors

	/** default constructor */
	public CollectionConfirmation() {
		super();
	}

	/** minimal constructor */
	public CollectionConfirmation(String collectionId) {
		super(collectionId);
	}

	private boolean add;// 是否新增
	private String keyword;// 搜索关键字
	private List<CollectionDetail> detailList;
	private String delDetailIds;

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
	public List<CollectionDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<CollectionDetail> detailList) {
		this.detailList = detailList;
	}

	@Transient
	public String getDelDetailIds() {
		return delDetailIds;
	}

	public void setDelDetailIds(String delDetailIds) {
		this.delDetailIds = delDetailIds;
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

	public static final int REFUND = 0; // 未退款
	public static final int REFUND_LOCK = 10; // 退款锁定中
	public static final int REFUNDING = 20; // 退款中
	public static final int REFUNDED_PARTIAL = 30;// 部分退款完成
	public static final int REFUNDED = 40;// 全部退款完成

	/**
	 * 获取退款状态码表.
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

	@Transient
	public String getIsOtherPayDesc() {
		return this.getIsOtherPay() ? "是" : "否";
	}

	/** 声明流程用到的参数  **/

	private String procId;// 流程ID
	private String procTitle;// 流程标题

	@Column(name = "PROC_ID", length = 32)
	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	@Transient
	public String getProcTitle() {
		procTitle = CommonUtil.trim(this.getProjectName()) + CommonUtil.trim(this.getBusinessPreposeRecord()) + "（" + this.getDeptName() + "）";
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
		return this.getCollectionId();
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
