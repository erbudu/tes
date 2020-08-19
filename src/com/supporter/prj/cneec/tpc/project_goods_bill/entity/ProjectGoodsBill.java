package com.supporter.prj.cneec.tpc.project_goods_bill.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.project_goods_bill.entity.base.BaseProjectGoodsBill;

/**
 * @Title: ProjectGoodsBill
 * @Description: 项目货品清单实体类
 * @author: yanweichao
 * @date: 2017-11-08
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_PROJECT_GOODS_BILL", schema = "")
public class ProjectGoodsBill extends BaseProjectGoodsBill implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCPRJGODSBI";
	public static final String BUSI_TYPE = "TPCPRJGODSBI_DECLARATION_FORM";// 附件参数，最长不超过50位

	// Constructors

	/** default constructor */
	public ProjectGoodsBill() {
		super();
	}

	/** minimal constructor */
	public ProjectGoodsBill(String billId) {
		super(billId);
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

	public static final int EFFECT = 0; // 有效
	public static final int INVALID = 10; // 无效

	/**
	 * 获取状态码表.
	 * @return
	 */
	public static Map<Integer, String> getBillStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(EFFECT, "有效");
		map.put(INVALID, "无效");
		return map;
	}

	// 状态
	@Transient
	public String getBillStatusDesc() {
		if (this.getBillStatus() != null) {
			return getBillStatusMap().get(this.getBillStatus());
		}
		return "";
	}

	public static final int GOODS_SOURCE_ORDER_ONLINE = 10; // 销售合同上线
	public static final int GOODS_SOURCE_ORDER_CHANGE = 20; // 销售合同变更
	public static final int GOODS_SOURCE_CONTRACT_ONLINE = 30; // 采购合同上线
	public static final int GOODS_SOURCE_CONTRACT_CHANGE = 40; // 采购合同变更
	public static final int GOODS_SOURCE_DERIVATIVE_CONTRACT_ONLINE = 50; // 衍生合同上线
	/**
	 * 获取货品来源.
	 * @return
	 */
	public static Map<Integer, String> getGoodsSourceMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(GOODS_SOURCE_ORDER_ONLINE, "销售合同上线");
		map.put(GOODS_SOURCE_ORDER_CHANGE, "销售合同变更");
		map.put(GOODS_SOURCE_CONTRACT_ONLINE, "采购合同上线");
		map.put(GOODS_SOURCE_CONTRACT_CHANGE, "采购合同变更");
		map.put(GOODS_SOURCE_DERIVATIVE_CONTRACT_ONLINE, "衍生合同上线");
		return map;
	}

	// 状态
	@Transient
	public String getGoodsSourceDesc() {
		if (this.getGoodsSource() != 0) {
			return getGoodsSourceMap().get(this.getGoodsSource());
		}
		return "";
	}

	// 是否存在退货
	@Transient
	public String getExistReturnDesc() {
		return this.isExistReturn() ? "是" : "否";
	}

	// 是否存在换/补情况
	@Transient
	public String getExistChangeDesc() {
		return this.isExistChange() ? "是" : "否";
	}

	// 是否存在维修情况
	@Transient
	public String getExistMaintenanceDesc() {
		return this.isExistMaintenance() ? "是" : "否";
	}

}
