package com.supporter.prj.cneec.tpc.external_quotation_review_dept.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.external_quotation_review_dept.entity.base.BaseExternalQuotationReviewDept;
import com.supporter.prj.cneec.tpc.purchase_demand.util.PurchaseDemandConstant;
import com.supporter.util.CommonUtil;

/**
 * @Title: ExternalQuotationReviewDept
 * @Description: 对外报价评审事业部评审/初评实体类
 * @author: yanweichao
 * @date: 2018-03-20
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_EXTERNAL_QUOTATION_RV_DEPT", schema = "")
public class ExternalQuotationReviewDept extends BaseExternalQuotationReviewDept implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCEXQURVDEP";
	public static final String BUSI_TYPE = "TPCEXTERNALQUOTATIONDEPT";
	public static final String DOMAIN_OBJECT_ID = "ExteQuotaDeptRv";
	
	
	private String prjDeptId; //项目所属部门

	// Constructors

	/** default constructor */
	public ExternalQuotationReviewDept() {
		super();
	}

	/** minimal constructor */
	public ExternalQuotationReviewDept(String externalId) {
		super(externalId);
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

	public static final int DRAFT = 0; // 草稿
	public static final int PROCESSING = 10; // 审核中
	public static final int COMPLETED = 20;// 审批完成

	/**
	 * 获取状态码表.
	 * @return
	 */
	public static Map<Integer, String> getSwfStatusMap(Boolean isMeetReview) {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		if (isMeetReview == null) {
			map.put(PROCESSING, "审核中/会审中");
			map.put(COMPLETED, "审批完成/会审完成");
		} else if (isMeetReview) {
			map.put(PROCESSING, "会审中");
			map.put(COMPLETED, "会审完成");
		} else {
			map.put(PROCESSING, "审核中");
			map.put(COMPLETED, "审批完成");
		}
		return map;
	}

	// 状态
	@Transient
	public String getSwfStatusDesc() {
		if (this.getSwfStatus() != null) {
			return getSwfStatusMap(this.isMeetReview()).get(this.getSwfStatus());
		}
		return "";
	}

	// 是否会审
	@Transient
	public boolean isMeetReview() {
		return CommonUtil.trim(super.getReviewClassification()).equals(PurchaseDemandConstant.REVIEW_SELF_SUPPORT_SUPER);
	}

	// 是否公司评审
	@Transient
	public boolean isCompanyReview() {
		String reviewClass = CommonUtil.trim(super.getReviewClassification());
		// 代理按小额事业部评审，自营小额事业部评审
		if (PurchaseDemandConstant.REVIEW_AGENT_YES.equals(reviewClass)
				|| PurchaseDemandConstant.REVIEW_SELF_SUPPORT_XIAO_YES.equals(reviewClass)) {
			return false;
		}
		return true;
	}

	/** 声明流程用到的参数  **/

	private String procId;
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
		procTitle = CommonUtil.trim(this.getProjectName()) + "的" + CommonUtil.trim(this.getQuotationTitle()) + "（" + this.getDeptName() + "）";
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
		return this.getExternalId();
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
