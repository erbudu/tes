package com.supporter.prj.cneec.tpc.order_change.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.order_change.entity.base.BaseOrderChangeGoods;

/**
 * @Title: Entity
 * @Description: TPC_ORDER_CHANGE_GOODS.
 */
@Entity
@Table(name = "TPC_ORDER_CHANGE_GOODS", schema = "")
public class OrderChangeGoods extends BaseOrderChangeGoods {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public OrderChangeGoods() {
		super();
	}

	/**
	 * 构造函数.
	 * 
	 * @param goodsId
	 */
	public OrderChangeGoods(String goodsId) {
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
	private List<OrderChangeGoodsChild> gooodsChild;
	@Transient
	public List<OrderChangeGoodsChild> getGooodsChild() {
		return gooodsChild;
	}

	public void setGooodsChild(List<OrderChangeGoodsChild> gooodsChild) {
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
