package com.supporter.prj.cneec.tpc.contract_change.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.contract_change.entity.base.BaseContractChangeGoodsChild;

/**
 * @Title: Entity
 * @Description: 合同变更货物及服务明细.
 * @author Yanweichao
 * @date 2017-11-08
 * @version V1.0
 */
@Entity
@Table(name = "TPC_CONTRACT_CHANGE_GOODS_CH", schema = "")
public class ContractChangeGoodsChild extends BaseContractChangeGoodsChild {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractChangeGoodsChild() {
		super();
	}

	/**
	 * 构造函数.
	 * 
	 * @param goodsId
	 */
	public ContractChangeGoodsChild(String childId) {
		super(childId);
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
