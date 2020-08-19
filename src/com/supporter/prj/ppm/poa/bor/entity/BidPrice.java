package com.supporter.prj.ppm.poa.bor.entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.poa.bor.entity.base.BaseBidOpenResult;
import com.supporter.prj.ppm.poa.bor.entity.base.BaseBidPrice;
@Entity
@Table(name = "ppm_bor_bid_price",schema="")
public class BidPrice extends BaseBidPrice {
private static final long serialVersionUID = 1L;
private boolean isNew=true;
@Transient
public boolean getIsNew() {
	return isNew;
}

public void setNew(boolean isNew) {
	this.isNew = isNew;
}
private String currencyNames;
@Transient
public String getCurrencyNames() {
	return currencyNames;
}

public void setCurrencyNames(String currencyNames) {
	this.currencyNames = currencyNames;
}



}
