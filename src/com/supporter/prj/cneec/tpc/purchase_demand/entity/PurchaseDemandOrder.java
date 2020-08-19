package com.supporter.prj.cneec.tpc.purchase_demand.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.purchase_demand.entity.base.BasePurchaseDemandDetail;

/**
 * @Title: PurchaseDemandOrder
 * @Description: 客户采购需求订单
 * @author: yanweichao
 * @date: 2017-10-10
 * @version: V1.0
 */
@Entity
public class PurchaseDemandOrder extends BasePurchaseDemandDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Constructors

	/** default constructor */
	public PurchaseDemandOrder() {
		super();
	}

	/** minimal constructor */
	public PurchaseDemandOrder(String recordId) {
		super(recordId);
	}

	private int level = 1;// 级别
	private String parent = "null";// 上级ID
	private boolean isLeaf = true;// 是否叶节点
	private boolean expanded = false;// 是否展开

	@Transient
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Transient
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Transient
	public boolean getIsLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Transient
	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

}
