package com.supporter.prj.ppm.contract.pact.review.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.contract.pact.review.entity.base.BaseContractPactReview;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "PPM_CONTRACT_PACT_REV", schema = "")
public class ContractPactReview extends BaseContractPactReview {

	private static final long serialVersionUID = 1L;

	public static final String MODULE_ID = "CONPACTREV"; // 应用模块的id
	public static final String BUSI_TYPE = "reviewFile"; // 附件注册
	public static final String DOMAIN_OBJECT_ID = "conPactRev"; // 业务对象id
	public static final int DB_YEAR = 0;

	/**
	 * 无参构造
	 */
	public ContractPactReview() {
		super();
	}

	/**
	 * 全参构造
	 */
	public ContractPactReview(String reviewId, String reviewNo, String reportId, String reportNo, String reportName, String prjId, String prjNo,
			String prjName, String reviewType, String contractPactType, int status, String creator, String creatorId, Date creationDate,
			String modifier, String modifierId, Date modificationDate, String submitter, String submitterId, Date submissionDate, String procId) {
		super(reviewId, reviewNo, reportId, reportNo, reportName, prjId, prjNo, prjName, reviewType, contractPactType, status, creator, creatorId,
				creationDate, modifier, modifierId, modificationDate, submitter, submitterId, submissionDate, procId);
	}

	/**
	 * 构造函数
	 */
	public ContractPactReview(String reviewId) {
		super(reviewId);
	}

	// 区分新建/编辑
	private boolean isNew;

	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	@Transient
	public String getModuleId() {
		return MODULE_ID;
	}

	@Transient
	public String getBusiType() {
		return BUSI_TYPE;
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
		return "";
	}

	// 状态
	public static final class StatusCodeTable {
		/** 草稿 */
		public static final int DRAFT = 0;
		/** 审批中 */
		public static final int EXAM = 1;
		/** 审批完成 */
		public static final int COMPLETE = 2;

		// 状态
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "草稿");
			ct.insertItem(EXAM, "审批中");
			ct.insertItem(COMPLETE, "审批完成");
			return ct;
		}
	}

	// 资料清单
	List<ContractPactReviewBfd> bfds;

	@Transient
	public List<ContractPactReviewBfd> getBfds() {
		return bfds;
	}

	public void setBfds(List<ContractPactReviewBfd> bfds) {
		this.bfds = bfds;
	}

}
