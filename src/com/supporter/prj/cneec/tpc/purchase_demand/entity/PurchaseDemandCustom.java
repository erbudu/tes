package com.supporter.prj.cneec.tpc.purchase_demand.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.purchase_demand.entity.base.BasePurchaseDemandCustom;

/**
 * @Title: PurchaseDemandCustom
 * @Description: 客户采购需求单客户实体类
 * @author: yanweichao
 * @date: 2017-09-09
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_PURCHASE_DEMAND_CUSTOM", schema = "")
public class PurchaseDemandCustom extends BasePurchaseDemandCustom implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Constructors

	/** default constructor */
	public PurchaseDemandCustom() {
		super();
	}

	/** minimal constructor */
	public PurchaseDemandCustom(String recordId) {
		super(recordId);
	}

	private boolean add = false;// 是否新增
	private String delDetailIds;// 删除的客户从表ID
	private List<PurchaseDemandDetail> detailList;// 客户从表集合

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	@Transient
	public String getDelDetailIds() {
		return delDetailIds;
	}

	public void setDelDetailIds(String delDetailIds) {
		this.delDetailIds = delDetailIds;
	}

	@Transient
	public List<PurchaseDemandDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PurchaseDemandDetail> detailList) {
		this.detailList = detailList;
	}

}
