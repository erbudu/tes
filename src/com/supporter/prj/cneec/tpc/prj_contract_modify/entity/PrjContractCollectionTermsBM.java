package com.supporter.prj.cneec.tpc.prj_contract_modify.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.base.BasePrjContractCollectionTermsBM;

/**
 * @Title: Entity
 * @Description: TPC_PRJ_CONTRACT_TERMS_BM.
 * @author Yanweichao
 * @date 2018-03-15
 * @version V1.0
 */
@Entity
@Table(name = "TPC_PRJ_CONTRACT_TERMS_BM", schema = "")
public class PrjContractCollectionTermsBM extends BasePrjContractCollectionTermsBM implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public PrjContractCollectionTermsBM() {
		super();
	}

	/**
	 * 构造函数.
	 * @param bmId
	 */
	public PrjContractCollectionTermsBM(String bmId) {
		super(bmId);
	}

}
