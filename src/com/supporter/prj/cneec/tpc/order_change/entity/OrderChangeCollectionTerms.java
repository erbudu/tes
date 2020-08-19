package com.supporter.prj.cneec.tpc.order_change.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.order_change.entity.base.BaseOrderChangeCollectionTerms;

/**
 * @Title: Entity
 * @Description: TPC_ORDER_CHANGE_COLLECT_TERMS.
 * @author Yanweichao
 * @date 2017-11-03
 * @version V1.0
 */
@Entity
@Table(name = "TPC_ORDER_CHANGE_COLLECT_TERMS", schema = "")
public class OrderChangeCollectionTerms extends BaseOrderChangeCollectionTerms {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public OrderChangeCollectionTerms() {
		super();
	}

	/**
	 * 构造函数.
	 * 
	 * @param termsId
	 */
	public OrderChangeCollectionTerms(String termsId) {
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
