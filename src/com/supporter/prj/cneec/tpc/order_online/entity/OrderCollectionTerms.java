package com.supporter.prj.cneec.tpc.order_online.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.order_online.entity.base.BaseOrderCollectionTerms;

/**   
 * @Title: Entity
 * @Description: 收款条件
 * @author Yanweichao
 * @date 2017-10-30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_ORDER_COLLECTION_TERMS", schema = "")
public class OrderCollectionTerms extends BaseOrderCollectionTerms {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public OrderCollectionTerms() {
		super();
	}

	/**
	 * 构造函数.
	 * @param termsId
	 */
	public OrderCollectionTerms(String termsId) {
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
