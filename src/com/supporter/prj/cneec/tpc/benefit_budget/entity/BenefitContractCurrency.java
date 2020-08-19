package com.supporter.prj.cneec.tpc.benefit_budget.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.benefit_budget.entity.base.BaseBenefitContractCurrency;
import com.supporter.util.CommonUtil;

/**
 * @Title: BenefitContractCurrency
 * @Description: 贸易项目效益测算币别表.
 * @author: yanweichao
 * @date: 2018-06-01
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_BENEFIT_CONTRACT_CURRENCY", schema = "")
public class BenefitContractCurrency extends BaseBenefitContractCurrency implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Constructors

	/** default constructor */
	public BenefitContractCurrency() {
		super();
	}

	/** minimal constructor */
	public BenefitContractCurrency(String recordId) {
		super(recordId);
	}

	@Transient
	public String getCurrencyDisplay() {
		return CommonUtil.trim(this.getCurrency()) + "[" + CommonUtil.format(this.getRate(), "#,###.0000") + "]";
	}

}
