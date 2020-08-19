package com.supporter.prj.cneec.tpc.purchase_demand.entity;

// default package

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.purchase_demand.entity.base.BasePurchaseDemand;
import com.supporter.prj.cneec.tpc.purchase_demand.util.PurchaseDemandConstant;
import com.supporter.prj.cneec.tpc.register_project.util.RegisterProjectConstant;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.util.CommonUtil;

/**
 * @Title: PurchaseDemand
 * @Description: 客户采购需求单实体类
 * @author: yanweichao
 * @date: 2017-9-6
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_PURCHASE_DEMAND", schema = "")
public class PurchaseDemand extends BasePurchaseDemand implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCPURDEMAND";

	// Constructors

	/** default constructor */
	public PurchaseDemand() {
		super();
	}

	/** full constructor */
	public PurchaseDemand(String demandId) {
		super(demandId);
	}

	private boolean add;// 是否新增
	private String keyword;// 搜索关键字
	private String delRecordIds;// 删除的客户ID
	private List<PurchaseDemandCustom> customList;// 客户集合

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
	public String getDelRecordIds() {
		return delRecordIds;
	}

	public void setDelRecordIds(String delRecordIds) {
		this.delRecordIds = delRecordIds;
	}

	@Transient
	public List<PurchaseDemandCustom> getCustomList() {
		return customList;
	}

	public void setCustomList(List<PurchaseDemandCustom> customList) {
		this.customList = customList;
	}

	public static final int DRAFT = 0; // 草稿
	public static final int PROCESSING = 10; // 进行中
	public static final int COMPLETED = 20;// 完成

	/**
	 * 获取状态码表.
	 * @return
	 */
	public static Map<Integer, String> getSwfStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		//map.put(PROCESSING, "进行中");
		map.put(COMPLETED, "完成");
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

	@Transient
	public String getProjectClassification() {
		String projectClassificationId = CommonUtil.trim(this.getProjectClassificationId());
		if (projectClassificationId.length() > 0) {
			return RegisterProjectConstant.getProjectClassificationMap().get(projectClassificationId);
		}
		return "";
	}

	// 单一类型
	@Transient
	public String getSingleCategory() {
		return TpcConstant.getSingleCategoryMap().get(this.getSingleCategoryCode());
	}

	// 评审类别
	@Transient
	public String getReviewClassification() {
		String classificationId = CommonUtil.trim(this.getProjectClassificationId());
		String deptName = CommonUtil.trim(this.getDeptName());
		double usdAmount = this.getUsdAmount();
		if (deptName.length() > 0) {
			if (classificationId.equals(RegisterProjectConstant.SELF_SUPPORT)) {// 自营贸易
				if (usdAmount >= 10000000) {// 自营不小于1000万美元会审（不走流程）
					return PurchaseDemandConstant.REVIEW_SELF_SUPPORT_SUPER;
				} else if (usdAmount >= 3000000) {// 自营不小于300万美元评审（事业部后公司）
					return PurchaseDemandConstant.REVIEW_SELF_SUPPORT_DA;
				} else if (!deptName.contains("第三事业部") && usdAmount >= 1000000) {// 非第三事业部自营不小于100万美元评审（事业部）
					return PurchaseDemandConstant.REVIEW_SELF_SUPPORT_XIAO_YES;
				} else if (deptName.contains("第三事业部") && usdAmount >= 500000) {// 第三事业部自营不小于50万美元评审（事业部）
					return PurchaseDemandConstant.REVIEW_SELF_SUPPORT_XIAO_YES;
				}
				return PurchaseDemandConstant.REVIEW_SELF_SUPPORT_XIAO_NO;// 其他不用评审
			} else if (classificationId.equals(RegisterProjectConstant.AGENT)) {// 代理贸易
				if (usdAmount >= 5000000) {// 代理不小于500万美元都按小额评审
					return PurchaseDemandConstant.REVIEW_AGENT_YES;
				}
				return PurchaseDemandConstant.REVIEW_AGENT_NO;// 其他不用评审
			}
		}
		return "";
	}

	// 是否对外报价评审
	@Transient
	public boolean isExternalReview() {
		String reviewClass = this.getReviewClassification();
		if (reviewClass == PurchaseDemandConstant.REVIEW_AGENT_NO 
				|| reviewClass == PurchaseDemandConstant.REVIEW_SELF_SUPPORT_XIAO_NO) {
			return false;
		}
		return true;
	}
		
}
