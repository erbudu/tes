package com.supporter.prj.pm.reserve_fund.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.pm.reserve_fund.entity.base.BaseStockCash;

@Entity
@Table(name = "pm_stock_cash", catalog = "")
public class StockCash extends BaseStockCash{	
	public StockCash() {
		super();
	}
	private double withdrawAmount;
	private double totalAmount;
	private String accountNoId;
	private String accountNo;
	@Transient
	public double getWithdrawAmount() {
		return withdrawAmount;
	}
	public void setWithdrawAmount(double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	@Transient
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	@Transient
	public String getAccountNoId() {
		return accountNoId;
	}
	public void setAccountNoId(String accountNoId) {
		this.accountNoId = accountNoId;
	}
	@Transient
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
}
