package com.supporter.prj.cneec.tpc.derivative_contract_online.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.base.BaseDerivativeContractCollectionTerms;

/**   
 * @Title: Entity
 * @Description: 收款条件
 * @author Yanweichao
 * @date 2017-11-06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_DERIVATIVE_CONTRACT_COLL_T", schema = "")
public class DerivativeContractCollectionTerms extends BaseDerivativeContractCollectionTerms {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public DerivativeContractCollectionTerms() {
		super();
	}

	/**
	 * 构造函数.
	 * @param termsId
	 */
	public DerivativeContractCollectionTerms(String termsId) {
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
