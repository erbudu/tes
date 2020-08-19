package com.supporter.prj.ppm.poa.bor.entity;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.poa.bor.entity.base.BaseBidInf;
import com.supporter.prj.ppm.poa.bor.entity.base.BaseBidOpenResult;
@Entity
@Table(name = "ppm_bor_bid_INF",schema="")
public class BidInf extends BaseBidInf {
private static final long serialVersionUID = 1L;
private boolean isNew=false;
@Transient
public boolean getIsNew() {
	return isNew;
}

public void setNew(boolean isNew) {
	this.isNew = isNew;
}
private List<BidCurrency> currencyList;
@Transient
public List<BidCurrency> getCurrencyList() {
	return currencyList;
}

public void setCurrencyList(List<BidCurrency> currencyList) {
	this.currencyList = currencyList;
}
private String currencyIds;
@Transient
public String getCurrencyIds() {
	return currencyIds;
}

public void setCurrencyIds(String currencyIds) {
	this.currencyIds = currencyIds;
}
private String amount;
@Transient
public String getAmount() {
	return amount;
}

public void setAmount(String amount) {
	this.amount = amount;
}


}
