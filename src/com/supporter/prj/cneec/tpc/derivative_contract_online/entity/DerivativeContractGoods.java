package com.supporter.prj.cneec.tpc.derivative_contract_online.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.base.BaseDerivativeContractGoods;

/**   
 * @Title: Entity
 * @Description: 货物及服务明细
 * @author Yanweichao
 * @date 2017-11-06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_DERIVATIVE_CONTRACT_GOODS", schema = "")
public class DerivativeContractGoods extends BaseDerivativeContractGoods {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public DerivativeContractGoods(){
		super();
	}

	/**
	 * 构造函数.
	 * @param goodsId
	 */
	public DerivativeContractGoods(String goodsId) {
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
