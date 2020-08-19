package com.supporter.prj.cneec.tpc.benefit_budget_change.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.base.BaseBenefitContractCh;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.util.CommonUtil;

@Entity
@Table(name = "TPC_BENEFIT_CONTRACT_CH", schema = "")
public class BenefitContractCh extends BaseBenefitContractCh implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "BENCONCHANGE";
	public static final String DOMAIN_OBJECT_ID = "BenefitConCh";
	public static final int DB_YEAR = 0;
	private boolean add;
	private List<BenefitContractCurrencyCh> currencyList;
	private String delCurrencyIds;
	private List<BenefitContractBudgetCh> benefitContractBudgetChList;
	public static final int DRAFT = 0;
	public static final int PROCESSING = 10;
	public static final int COMPLETED = 20;
	public static final int REJECTED = 30;
	
	
	public BenefitContractCh() {
	}

	public BenefitContractCh(String changeId) {
		super(changeId);
	}

	@Transient
	public boolean getAdd() {
		return this.add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	@Transient
	public List<BenefitContractCurrencyCh> getCurrencyList() {
		return this.currencyList;
	}

	public void setCurrencyList(List<BenefitContractCurrencyCh> currencyList) {
		this.currencyList = currencyList;
	}

	@Transient
	public String getDelCurrencyIds() {
		return this.delCurrencyIds;
	}

	public void setDelCurrencyIds(String delCurrencyIds) {
		this.delCurrencyIds = delCurrencyIds;
	}

	@Transient
	public List<BenefitContractBudgetCh> getBenefitBudgetChangeList() {
		return this.benefitContractBudgetChList;
	}

	public void setBenefitBudgetChangeList(List<BenefitContractBudgetCh> benefitContractBudgetChList) {
		this.benefitContractBudgetChList = benefitContractBudgetChList;
	}

	public static Map<Integer, String> getSwfStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(PROCESSING, "审核中");
		map.put(COMPLETED, "审批完成");
		map.put(REJECTED, "驳回");
		return map;
	}

	@Transient
	public String getSwfStatusDesc() {
		if (getSwfStatus() != null) {
			return ((String) getSwfStatusMap().get(getSwfStatus()));
		}
		return "";
	}

	@Transient
	public int getDbYear() {
		return 0;
	}

	@Transient
	public String getModuleId() {
		return "BENCONCHANGE";
	}

	@Transient
	public String getDomainObjectId() {
		return "BenefitConCh";
	}

	@Transient
	public String getCompanyNo() {
		return CommonUtil.trim(getDeptId());
	}

	@Transient
	public String getEntityId() {
		return getChangeId();
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getEntityName() {
		return super.getClass().getName();
	}
	
	private String projectDeptId;//项目所属部门
	/**
	 * 获取项目所属部门ID
	 * @return
	 */
	@Transient
	public String getProjectDeptId() {
		RegisterProjectService prjService = (RegisterProjectService) SpringContextHolder.getBean(RegisterProjectService.class);
		RegisterProject project = prjService.get(this.getProjectId());
		return project != null ? project.getProjectDeptId() : "";
	}
	
	public void setProjectDeptId(String projectDeptId) {
		this.projectDeptId = projectDeptId;
	}
	
	private String projectChargeId;//项目经理ID
	/**
	 * 获取业务负责人ID
	 * @return
	 */
	@Transient
	public String getProjectChargeId() {
		RegisterProjectService prjService = (RegisterProjectService) SpringContextHolder.getBean(RegisterProjectService.class);
		RegisterProject project = prjService.get(this.getProjectId());
		return project != null ? project.getProjectChargeId() : "";
	}
	
	public void setProjectChargeId(String getProjectChargeId) {
		this.projectChargeId = projectChargeId;
	}
}