package com.supporter.prj.cneec.tpc.contract_online.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.contract_online.entity.base.BaseContractGoods;

/**   
 * @Title: Entity
 * @Description: 货物及服务明细
 * @author Yanweichao
 * @date 2017-11-06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_CONTRACT_GOODS", schema = "")
public class ContractGoods extends BaseContractGoods {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractGoods(){
		super();
	}

	/**
	 * 构造函数.
	 * @param goodsId
	 */
	public ContractGoods(String goodsId) {
		super(goodsId);
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
