package com.supporter.prj.ppm.poa.bor.entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.poa.bor.entity.base.BaseBidOpenResult;
import com.supporter.prj.ppm.poa.bor.entity.base.BaseCurrency;
@Entity
@Table(name = "ppm_currency",schema="")
public class BidCurrency extends BaseCurrency {
private static final long serialVersionUID = 1L;
private boolean isNew=true;
@Transient
public boolean getIsNew() {
	return isNew;
}

public void setNew(boolean isNew) {
	this.isNew = isNew;
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
