package com.supporter.prj.cneec.tpc.purchase_demand_sheet.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.purchase_demand_sheet.entity.base.BasePurchaseDemandSheet;
import com.supporter.prj.cneec.tpc.util.TpcConstant;

/**
 * @Title: PurchaseDemandSheet
 * @Description: 客户采购需求选择实体类
 * @author: yanweichao
 * @date: 2018-04-10
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_PURCHASE_DEMAND_SHEET", schema = "")
public class PurchaseDemandSheet extends BasePurchaseDemandSheet implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCPURCSHEET";// 应用编号，最长12位

	// Constructors

	/** default constructor */
	public PurchaseDemandSheet() {
		super();
	}

	/** minimal constructor */
	public PurchaseDemandSheet(String sheetId) {
		super(sheetId);
	}

	private String keyword;// 搜索关键字
	private boolean defaultSelect;// 默认可选择

	@Transient
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Transient
	public boolean getDefaultSelect() {
		return defaultSelect;
	}

	public void setDefaultSelect(boolean defaultSelect) {
		this.defaultSelect = defaultSelect;
	}

	public static final int NO_REVIEW = 0;
	public static final int REVIEWING = 10;
	public static final int REVIEWED = 20;

	public static Map<Integer, String> getReviewStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(NO_REVIEW, "未评审");
		map.put(REVIEWING, "评审中");
		map.put(REVIEWED, "已评审");
		return map;
	}

	// 状态
	@Transient
	public String getReviewStatusDesc() {
		if (this.getReviewStatus() != null) {
			return getReviewStatusMap().get(this.getReviewStatus());
		}
		return "";
	}

	// 单一类型
	@Transient
	public String getSingleCategory() {
		return TpcConstant.getSingleCategoryMap().get(this.getSingleCategoryCode());
	}

}
