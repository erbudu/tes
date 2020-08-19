package com.supporter.prj.ppm.contract.sign.filing.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.contract.sign.filing.entity.base.BaseContractFiling;
import com.supporter.prj.ppm.contract.sign.seal_bfd.entity.ContractSignSeal;
import com.supporter.util.CodeTable;

/**
 * @Title: Entity
 * @Description: PPM_CONTRACT_FILING.
 * @author: YAN
 * @date: 2019-09-17
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_FILING", schema = "")
public class ContractFiling extends BaseContractFiling implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "CONSIGNFIL"; // 应用模块的id
	public static final String DOMAIN_OBJECT_ID = "conSignFil";
	public static final int DB_YEAR = 0;

	/**
	 * 无参构造函数.
	 */
	public ContractFiling() {
		super();
	}

	/**
	 * 构造函数.
	 * @param filingId
	 */
	public ContractFiling(String filingId) {
		super(filingId);
	}

	private List<ContractFilingBfd> bfds;

	@Transient
	public List<ContractFilingBfd> getBfds() {
		return bfds;
	}

	public void setBfds(List<ContractFilingBfd> bfds) {
		this.bfds = bfds;
	}

	private ContractSignSeal contractSignSeal;
	@Transient
	public ContractSignSeal getContractSignSeal() {
		return contractSignSeal;
	}

	public void setContractSignSeal(ContractSignSeal contractSignSeal) {
		this.contractSignSeal = contractSignSeal;
	}
	private String prjNo;//项目编码
	private String prjCName;//项目中文名称
	private String prjEName;//项目英文名称
	@Transient
	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}
	@Transient
	public String getPrjCName() {
		return prjCName;
	}

	public void setPrjCName(String prjCName) {
		this.prjCName = prjCName;
	}
	@Transient
	public String getPrjEName() {
		return prjEName;
	}

	public void setPrjEName(String prjEName) {
		this.prjEName = prjEName;
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

		// 状态
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "草稿");
			ct.insertItem(EXAM, "审批中");
			ct.insertItem(COMPLETE, "审批完成");
			return ct;
		}
	}

	private List<ContractEffectCondition> conditionList;
	@Transient
	public List<ContractEffectCondition> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<ContractEffectCondition> conditionList) {
		this.conditionList = conditionList;
	}
}
