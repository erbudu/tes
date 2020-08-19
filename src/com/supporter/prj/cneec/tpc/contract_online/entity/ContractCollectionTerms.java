package com.supporter.prj.cneec.tpc.contract_online.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.contract_online.entity.base.BaseContractCollectionTerms;

/**   
 * @Title: Entity
 * @Description: 收款条件
 * @author Yanweichao
 * @date 2017-11-06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_CONTRACT_COLLECTION_TERMS", schema = "")
public class ContractCollectionTerms extends BaseContractCollectionTerms {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractCollectionTerms() {
		super();
	}

	/**
	 * 构造函数.
	 * @param termsId
	 */
	public ContractCollectionTerms(String termsId) {
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
