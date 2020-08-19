package com.supporter.prj.cneec.tpc.external_quotation_review.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.cneec.tpc.external_quotation_review.entity.base.BaseExternalQuotationReview;
import com.supporter.prj.cneec.tpc.external_quotation_review_dept.entity.ExternalQuotationReviewDept;
import com.supporter.prj.cneec.tpc.external_quotation_review_dept.service.ExternalQuotationReviewDeptService;
import com.supporter.prj.cneec.tpc.purchase_demand.util.PurchaseDemandConstant;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.util.CommonUtil;

/**
 * @Title: ExternalQuotationReview
 * @Description: 对外报价评审实体类
 * @author: yanweichao
 * @date: 2017-09-25
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_EXTERNAL_QUOTATION_REVIEW", schema = "")
public class ExternalQuotationReview extends BaseExternalQuotationReview implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCEXTQUOTRV";
	public static final String BUSI_TYPE = "TPCEXTERNALQUOTATION";
	public static final String DOMAIN_OBJECT_ID = "ExteQuotaReview";
	
	
	private String prjDeptId; //项目所属部门

	// Constructors

	/** default constructor */
	public ExternalQuotationReview() {
		super();
	}

	/** minimal constructor */
	public ExternalQuotationReview(String externalId) {
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
	
	/**
	 * 返回部门对外报价评审流程ID
	 * @return
	 */
	@Transient
	public String getDeptReviewProcId() {
		String deptReviewProcId = "";
		if (StringUtils.isNotBlank(this.getDemandId())) {
			ExternalQuotationReviewDeptService externalQuotationReviewDeptService = (ExternalQuotationReviewDeptService) SpringContextHolder.getBean(ExternalQuotationReviewDeptService.class);
			//根据采购需求单ID获取对外报价评审单
			ExternalQuotationReviewDept deptReview = externalQuotationReviewDeptService.getExternalQuotationReviewDeptByDemandId(this.getDemandId());
			if (deptReview != null) {
				deptReviewProcId = deptReview.getProcId();
			}
		}
		return deptReviewProcId;
	}


	// 是否会审
	@Transient
	public boolean isMeetReview() {
		return CommonUtil.trim(super.getReviewClassification()).equals(PurchaseDemandConstant.REVIEW_SELF_SUPPORT_SUPER);
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
