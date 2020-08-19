package com.supporter.prj.ppm.contract.pact.beian.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.contract.pact.beian.entity.base.BaseContractPactBeian;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "PPM_CONTRACT_PACT_RECORD", schema = "")
public class ContractPactBeian extends BaseContractPactBeian implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "CONPACTBEIAN"; // 应用模块的id
	public static final String DOMAIN_OBJECT_ID = "conPactBeian";
	public static final String BUSI_TYPE = "beianFile";// 附件注册编号
	public static final int DB_YEAR = 0;

	/**
	 * 无参构造函数.
	 */
	public ContractPactBeian() {
		super();
	}

	/**
	 * 构造函数.
	 * @param recordId
	 */
	public ContractPactBeian(String recordId) {
		super(recordId);
	}

	/**
	 * 全参构造
	 */
	public ContractPactBeian(String recordId, String recordNo, String contractPactName, String contractPactType, String prjId, String prjName,
			String prjNo, String reportId, String reportNo, String reportName, Date recordDate, String contactId, String contactName,
			String contactTel, String contactMob, String contactFax, String creator, String creatorId, Date creationDate, String modifier,
			String modifierId, Date modificationDate, String submitter, String submitterId, Date submissionDate, int status, String procId) {
		super(recordId, recordNo, contractPactName, contractPactType, prjId, prjName, prjNo, reportId, reportNo, reportName, recordDate, contactId,
				contactName, contactTel, contactMob, contactFax, creator, creatorId, creationDate, modifier, modifierId, modificationDate, submitter,
				submitterId, submissionDate, status, procId);
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
	public int getDbYear() {
		return DB_YEAR;
	}

	@Transient
	public String getBusiType() {
		return BUSI_TYPE;
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
	List<ContractPactBeianBfd> bfds;

	@Transient
	public List<ContractPactBeianBfd> getBfds() {
		return bfds;
	}

	public void setBfds(List<ContractPactBeianBfd> bfds) {
		this.bfds = bfds;
	}

}
