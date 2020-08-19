package com.supporter.prj.cneec.tpc.derivative_contract_change.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.base.BaseDerivativeContractChangeTerms;

/**   
 * @Title: Entity
 * @Description: 收款条件
 *
 */
@Entity
@Table(name = "TPC_DERIVATIVE_CHGNGE_T", schema = "")
public class DerivativeContractChangeTerms extends BaseDerivativeContractChangeTerms {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public DerivativeContractChangeTerms() {
		super();
	}

	/**
	 * 构造函数.
	 * @param termsId
	 */
	public DerivativeContractChangeTerms(String termsId) {
		super(termsId);
	}

	private boolean add = false;// 是否新增

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

}
