package com.supporter.prj.cneec.tpc.prj_contract_table.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.prj_contract_table.entity.base.BasePrjContractCollectionTerms;

/**
 * @Title: Entity
 * @Description: TPC_PRJ_CONTRACT_COLLEC_TERMS.
 * @author Yanweichao
 * @date 2018-03-15
 * @version V1.0
 */
@Entity
@Table(name = "TPC_PRJ_CONTRACT_COLLEC_TERMS", schema = "")
public class PrjContractCollectionTerms extends BasePrjContractCollectionTerms implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public PrjContractCollectionTerms() {
		super();
	}

	/**
	 * 构造函数.
	 * @param termsId
	 */
	public PrjContractCollectionTerms(String termsId) {
		super(termsId);
	}

	/**
	 * 收/付款条款可收/付金额(应收/付款金额-在途收/付款金额-实际收/付款金额)
	 * @return
	 */
	@Transient
	public double getReceiveabledAmount() {
		return this.getReceiveAmount() - this.getOnwayAmount() - this.getRealReceiveAmount();
	}

	/**
	 * 可以进行收/付款业务(应收/付款金额大于在途收/付款金额+实际收/付款金额)
	 * @return
	 */
	@Transient
	public boolean canReceive() {
		return this.getReceiveabledAmount() > 0;
	}

	/**
	 * 收/付款完成(应收/付款金额等于实际收/付款金额)
	 * @return
	 */
	@Transient
	public boolean isReceiveOk() {
		return this.getReceiveAmount() == this.getRealReceiveAmount();
	}

}
