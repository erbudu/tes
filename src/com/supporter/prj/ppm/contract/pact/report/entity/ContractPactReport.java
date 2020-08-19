package com.supporter.prj.ppm.contract.pact.report.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.contract.pact.report.entity.base.BaseContractPactReport;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "PPM_CONTRACT_PACT_REPORT", schema = "")
public class ContractPactReport extends BaseContractPactReport {

	private static final long serialVersionUID = 1L;

	public static final String MODULE_ID = "CONPACTREP"; // 应用模块的id
	public static final String DOMAIN_OBJECT_ID = "conPactRep";
	public static final String BUSI_TYPE = "reportFile";// 附件注册编号
	public static final int DB_YEAR = 0;

	/**
	 * 无参构造
	 */
	public ContractPactReport() {
	}

	/**
	 * 构造函数
	 */
	public ContractPactReport(String reportId) {
		super(reportId);
	}

	/**
	 * 全参构造
	 */
	public ContractPactReport(String reportId, String reportNo, String reportName, String prjId, String prjNo, String prjNameZh, String prjNameEn,
			String contractPactName, String contractPactTypeId, String contractPactType, Date startDate, Date endDate, String contactId,
			String contactName, String contactTel, String contactMob, String contactFax, int status, String creator, String creatorId,
			Date creationDate, String modifier, String modifierId, Date modificationDate, String submitter, String submitterId, Date submissionDate,
			String procId) {
		super(reportId, reportNo, reportName, prjId, prjNo, prjNameZh, prjNameEn, contractPactName, contractPactTypeId, contractPactType, startDate,
				endDate, contactId, contactName, contactTel, contactMob, contactFax, status, creator, creatorId, creationDate, modifier, modifierId,
				modificationDate, submitter, submitterId, submissionDate, procId);
	}

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
		/** 审批完成(生效) */
		public static final int COMPLETE = 2;
		/** 失效 */
		public static final int INVALID = 3;

		// 状态
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "草稿");
			ct.insertItem(EXAM, "审批中");
			ct.insertItem(COMPLETE, "审批完成");
			ct.insertItem(INVALID, "失效");
			return ct;
		}
	}

	// 资料清单
	List<ContractPactReportBfd> bfds;

	@Transient
	public List<ContractPactReportBfd> getBfds() {
		return bfds;
	}

	public void setBfds(List<ContractPactReportBfd> bfds) {
		this.bfds = bfds;
	}

}
