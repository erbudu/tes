package com.supporter.prj.pm.reserve_fund.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.pm.reserve_fund.entity.base.BaseStockCashRec;

@Entity
@Table(name = "pm_stock_cash_rec", catalog = "")
public class StockCashRec extends BaseStockCashRec{
	public StockCashRec() {
		super();
	}
}
