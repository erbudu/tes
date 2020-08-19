package com.supporter.prj.cneec.tpc.tpc_stamp_tax_item.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.tpc_stamp_tax_item.entity.base.BaseStampTaxItem;

/**
 * @Title: Entity
 * @Description: 贸易印花税税目表.
 * @author: LEGO
 * @date: 2020-02-03
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_STAMP_TAX_ITEM", schema = "")
public class StampTaxItem extends BaseStampTaxItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public StampTaxItem() {
		super();
	}

	/**
	 * 构造函数.
	 * @param itemId
	 */
	public StampTaxItem(String itemId) {
		super(itemId);
	}
	
	private boolean add;// 是否新增
	private String keyword;// 搜索关键字

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	@Transient
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
