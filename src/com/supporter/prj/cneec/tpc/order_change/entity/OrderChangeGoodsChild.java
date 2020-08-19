package com.supporter.prj.cneec.tpc.order_change.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.order_change.entity.base.BaseOrderChangeGoodsChild;

/**
 * @Title: Entity
 * @Description: TPC_ORDER_CHANGE_GOODS.
 */
@Entity
@Table(name = "TPC_ORDER_CHANGE_GOODS_CHILD", schema = "")
public class OrderChangeGoodsChild extends BaseOrderChangeGoodsChild {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public OrderChangeGoodsChild() {
		super();
	}

	/**
	 * 构造函数.
	 * 
	 * @param goodsId
	 */
	public OrderChangeGoodsChild(String goodsId) {
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
