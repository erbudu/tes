package com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.base.BaseContractSignDeptReview;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignReviewUtil;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractSignDeptReview
 * @Description: 合同签约评审实体类
 * @author: yanweichao
 * @date: 2018-03-21
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_CONTRACT_SIGN_DEPT_REVIEW", schema = "")
public class ContractSignDeptReview extends BaseContractSignDeptReview implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCCONDEPTRV";
	public static final String BUSI_TYPE_ORDER = "TPCCONDEPTRV_ORDER";
	public static final String BUSI_TYPE_CONTRACT = "TPCCONDEPTRV_CONTRACT";
	public static final String DOMAIN_OBJECT_ID = "ConSignDeptRev";
	
	
	private String prjDeptId; //项目所属部门

	// Constructors

	/** default constructor */
	public ContractSignDeptReview() {
		super();
	}

	/** minimal constructor */
	public ContractSignDeptReview(String signId) {
		super(signId);
	}

	private boolean add;// 是否新增
	private String keyword;// 搜索关键字
	private List<ContractSignDeptOpinion> signOpinions;// 评审意见

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
	public List<ContractSignDeptOpinion> getSignOpinions() {
		return signOpinions;
	}

	public void setSignOpinions(List<ContractSignDeptOpinion> signOpinions) {
		this.signOpinions = signOpinions;
	}

	public static final int DRAFT = 0; // 草稿
	public static final int PROCESSING = 10; // 审核中
	public static final int CONFIRM = 15; // 经办人确认
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
			map.put(CONFIRM, "经办人确认");
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

	public static final int STEP_NOTIFY = 0; // 发送会审通知
	public static final int STEP_DOCUMENT = 10; // 上传会审资料
	public static final int STEP_BENEFIT = 20;// 提交效益测算表审批
	public static final int STEP_COMPLETION = 30;// 确认完成
	
	/**
	 * 获取步骤码表.
	 * @return
	 */
	public static Map<Integer, String> getStepStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(STEP_NOTIFY, "发送会审通知");
		map.put(STEP_DOCUMENT, "上传会审资料");
		map.put(STEP_BENEFIT, "提交效益测算表审批");
		map.put(STEP_COMPLETION, "确认完成");
		return map;
	}
	
	// 步骤
	@Transient
	public String getStepStatusDesc() {
		if (this.getSwfStatus() != null) {
			return getStepStatusMap().get(this.getStepStatus());
		}
		return "";
	}

	// 评审类型
	@Transient
	public String getReviewTypeDesc() {
		if (this.getReviewType() != 0) {
			return ContractSignReviewUtil.getReviewTypeMap().get(this.getReviewType());
		}
		return "";
	}

	// 评审结论
	@Transient
	public String getReviewConclusionDesc() {
		if (!CommonUtil.isNullOrEmpty(this.getReviewConclusion())) {
			return TpcConstant.getReviewConclusionMap().get(this.getReviewConclusion());
		}
		return "";
	}

	// 评审类别
	@Transient
	public String getReviewClassification() {
		String classificationId = CommonUtil.trim(this.getProjectClassificationId());// 项目类型
		double usdAmount = this.getReviewAmount();// 折合美元金额
		return ContractSignReviewUtil.getReviewClassification(classificationId, usdAmount);
	}

	// 评审类别
	@Transient
	public String getReviewClassDesc() {
		return ContractSignReviewUtil.getReviewClassMap().get(this.getReviewClassification());
	}
	
	// 是否公司评审
	@Transient
	public boolean isCompanyReview() {
		String reviewClass = this.getReviewClassification();
		// 大额评审，超大额评审需要公司评审
		if (reviewClass.equals(ContractSignReviewUtil.REVIEW_SELF_SUPPORT_DA)
				|| reviewClass.equals(ContractSignReviewUtil.REVIEW_SELF_SUPPORT_SUPER)) {
			return true;
		}
		return false;
	}
	
	// 是否会审
	@Transient
	public boolean isMeetReview() {
		String reviewClass = this.getReviewClassification();
		// 代理会审，小额会审，超大额会审
		if (reviewClass.equals(ContractSignReviewUtil.REVIEW_AGENT_MEET)
				|| reviewClass.equals(ContractSignReviewUtil.REVIEW_SELF_SUPPORT_XIAO_MEET)
				|| reviewClass.equals(ContractSignReviewUtil.REVIEW_SELF_SUPPORT_SUPER)) {
			return true;
		}
		return false;
	}

	/** 声明流程用到的参数  **/

	@Transient
	public String getProcTitle() {
		String procTitle = "";
		procTitle = CommonUtil.trim(this.getProjectName()) + "（" + this.getReviewNo() + "）" + this.getReviewContentDesc() + "（" + this.getDeptName() + "）";
		return procTitle;
	}

	private String procId;// 流程ID

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
		return this.getSignId();
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getEntityName() {
		return this.getClass().getName();
	}

	/**
	 * 省略的内容概要（避免待办标题过长）
	 * @return
	 */
	@Transient
	public String getReviewContentDesc() {
		return CommonUtil.stringTrimToLength(this.getReviewContent(), 20);
	}
	
	@Transient
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}

}
