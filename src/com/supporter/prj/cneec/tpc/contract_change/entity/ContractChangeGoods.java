package com.supporter.prj.cneec.tpc.contract_change.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.contract_change.entity.base.BaseContractChangeGoods;

/**
 * @Title: Entity
 * @Description: 合同变更货物及服务明细.
 * @author Yanweichao
 * @date 2017-11-08
 * @version V1.0
 */
@Entity
@Table(name = "TPC_CONTRACT_CHANGE_GOODS", schema = "")
public class ContractChangeGoods extends BaseContractChangeGoods {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public ContractChangeGoods() {
		super();
	}

	/**
	 * 构造函数.
	 * 
	 * @param goodsId
	 */
	public ContractChangeGoods(String goodsId) {
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
	
	private List<ContractChangeGoodsChild> gooodsChild;
	@Transient
	public List<ContractChangeGoodsChild> getGooodsChild() {
		return gooodsChild;
	}

	public void setGooodsChild(List<ContractChangeGoodsChild> gooodsChild) {
		this.gooodsChild = gooodsChild;
	}
	
	private String delTermsIds;
	@Transient
	public String getDelTermsIds() {
		return this.delTermsIds;
	}

	public void setDelTermsIds(String delTermsIds) {
		this.delTermsIds = delTermsIds;
	}
	
}
