package com.supporter.prj.ppm.contract.effect.report.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.contract.effect.report.entity.base.BaseContractEffectReport;
import com.supporter.util.CodeTable;

/**
 * @Title: Entity
 * @Description: 主合同签约报审表.
 * @author: YAN
 * @date: 2019-09-05
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_CONTRACT_Effect_REPORT", schema = "")
public class  ContractEffectReport extends BaseContractEffectReport implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "CONTEFFREP"; // 应用模块的id
	public static final String DOMAIN_OBJECT_ID = "conEffRep";
	public static final int DB_YEAR = 0;

	/**
	 * 无参构造函数.
	 */
	public ContractEffectReport() {
		super();
	}

	/**
	 * 构造函数.
	 * @param contractEffectId
	 */
	public ContractEffectReport(String contractEffectId) {
		super(contractEffectId);
	}

	//项目编码
	private String prjNo;
	private String prjNameZh;
	private String prjNameEn;
	@Transient
	public String getPrjNo() {
		return prjNo;
	}

	public void setPrjNo(String prjNo) {
		this.prjNo = prjNo;
	}
	@Transient
	public String getPrjNameZh() {
		return prjNameZh;
	}

	public void setPrjNameZh(String prjNameZh) {
		this.prjNameZh = prjNameZh;
	}
	@Transient
	public String getPrjNameEn() {
		return prjNameEn;
	}

	public void setPrjNameEn(String prjNameEn) {
		this.prjNameEn = prjNameEn;
	}

	List<ContractEffectReportBfd> bfds;
	@Transient
	public List<ContractEffectReportBfd> getBfds() {
		return bfds;
	}

	public void setBfds(List<ContractEffectReportBfd> bfds) {
		this.bfds = bfds;
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
}
