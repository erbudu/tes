package com.supporter.prj.cneec.tpc.benefit_budget.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.benefit_budget.entity.base.BaseBenefitContract;

/**
 * @Title: BenefitContract
 * @Description: 贸易项目效益预算-项目合同表.
 * @author: yanweichao
 * @date: 2018-05-29
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_BENEFIT_CONTRACT", schema = "")
public class BenefitContract extends BaseBenefitContract implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Constructors

	/** default constructor */
	public BenefitContract() {
		super();
	}

	/** minimal constructor */
	public BenefitContract(String processId) {
		super(processId);
	}

	private List<BenefitContractCurrency> currencyList;// 币别集合

	@Transient
	public List<BenefitContractCurrency> getCurrencyList() {
		return this.currencyList;
	}

	public void setCurrencyList(List<BenefitContractCurrency> currencyList) {
		this.currencyList = currencyList;
	}

}
