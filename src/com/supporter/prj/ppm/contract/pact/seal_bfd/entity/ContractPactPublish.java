package com.supporter.prj.ppm.contract.pact.seal_bfd.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.contract.pact.seal_bfd.entity.base.BaseContractPactPublish;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "PPM_CONTRACT_PACT_PUBLISH", schema = "")
public class ContractPactPublish extends BaseContractPactPublish implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "CONPACTPUB"; // 应用模块的id
	public static final String DOMAIN_OBJECT_ID = "conPactPub"; // 业务对象id
	public static final String BUSI_TYPE = "publishFile"; // 附件注册
	public static final int DB_YEAR = 0;

	/**
	 * 无参构造
	 */
	public ContractPactPublish() {
		super();
	}

	/**
	 * 构造函数.
	 */
	public ContractPactPublish(String publishId) {
		super(publishId);
	}

	/**
	 * 全参构造
	 */
	public ContractPactPublish(String publishId, String publishNo, String contractPactName, String contractPactType, String prjId, String prjName,
			String prjNo, String reportId, String reportNo, String reportName, Date startDate, Date endDate, String contactId, String contactName,
			String contactTel, String contactMob, String contactFax, String creator, String creatorId, Date creationDate, String modifier,
			String modifierId, Date modificationDate, String submitter, String submitterId, Date submissionDate, int status, String procId) {
		super(publishId, publishNo, contractPactName, contractPactType, prjId, prjName, prjNo, reportId, reportNo, reportName, startDate, endDate,
				contactId, contactName, contactTel, contactMob, contactFax, creator, creatorId, creationDate, modifier, modifierId, modificationDate,
				submitter, submitterId, submissionDate, status, procId);
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
	List<ContractPactPublishBfd> bfds;

	@Transient
	public List<ContractPactPublishBfd> getBfds() {
		return bfds;
	}

	public void setBfds(List<ContractPactPublishBfd> bfds) {
		this.bfds = bfds;
	}

}
