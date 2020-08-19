package com.supporter.prj.pm.fund_appropriation.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.pm.fund_appropriation.entity.FundAppropriation.StatusCodeTable;
import com.supporter.prj.pm.fund_appropriation.entity.base.BaseFundBudgetExpend;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "pm_fund_budget_expend", schema = "")
public class FundBudgetExpend extends BaseFundBudgetExpend{
	public FundBudgetExpend() {
		super();
	}
	/**
	 * 有无合同
	 * @return CodeTable
	 */
	public static final class StatusCodeTable {
		public static final int DRAFT = 0;
		public static final int EXAM = 1;
		
		/**
		 * 获取码表
		 * @return CodeTable
		 */
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "无");
			ct.insertItem(EXAM, "有");
			return ct;
		}

	}
	
	private String isContractDesc;
	/**
	 * 状态
	 * @return String
	 */
	@Transient
	public String getIsContractDesc() {
		if (getIsContract() != null) {
			return StatusCodeTable.getCodeTable().getDisplay(getIsContract());
		} else {
			return null;
		}				
	}

	public void setIsContractDesc(String isContractDesc) {
		this.isContractDesc = isContractDesc;
	}
}
