package com.supporter.prj.ppm.contract.pact.review_ver.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.contract.pact.review_ver.entity.base.BaseContractPactRevVer;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "PPM_CONTRACT_PACT_REV_VER", schema = "")
public class ContractPactRevVer extends BaseContractPactRevVer implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "CONPACTREVER"; // 应用模块的id
	public static final String BUSI_TYPE = "reviewVerFile"; // 附件注册
	public static final String DOMAIN_OBJECT_ID = "conPactRevVer"; // 业务对象id
	public static final int DB_YEAR = 0;

	/**
	 * 无参构造函数.
	 */
	public ContractPactRevVer() {
		super();
	}

	/**
	 * 构造函数.
	 * @param revVerId
	 */
	public ContractPactRevVer(String revVerId) {
		super(revVerId);
	}

	/**
	 * 全参构造
	 */
	public ContractPactRevVer(String revVerId, String revVerNo, String revId, String revNo, String reportId, String reportNo, String reportName,
			String verificatContent, String revVerificatContent, String contractPactType, int status, String prjId, String prjName, String prjNo,
			String creator, String creatorId, Date creationDate, String modifier, String modifierId, Date modificationDate, String submitter,
			String submitterId, Date submissionDate, String procId) {
		super(revVerId, revVerNo, revId, revNo, reportId, reportNo, reportName, verificatContent, revVerificatContent, contractPactType, status,
				prjId, prjName, prjNo, creator, creatorId, creationDate, modifier, modifierId, modificationDate, submitter, submitterId,
				submissionDate, procId);
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
	List<ContractPactRevVerBf> bfds;

	@Transient
	public List<ContractPactRevVerBf> getBfds() {
		return bfds;
	}

	public void setBfds(List<ContractPactRevVerBf> bfds) {
		this.bfds = bfds;
	}

}
