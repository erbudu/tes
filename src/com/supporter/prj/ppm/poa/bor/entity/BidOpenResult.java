package com.supporter.prj.ppm.poa.bor.entity;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


import com.supporter.prj.ppm.poa.bor.entity.base.BaseBidOpenResult;


@Entity
@Table(name = "ppm_bor",schema="")
public class BidOpenResult extends BaseBidOpenResult {
private static final long serialVersionUID = 1L;
public static final String MODULE_ID = "OPENRESULT";
public static final String ROLE_ExpatriateManagers = "BUSINESSMANAGERTOBUSINESS";
private boolean isNew = false;

@Transient
public boolean getIsNew() {
	return isNew;
}
public void setIsNew(boolean isNew) {
	this.isNew = isNew;
}
private List<BidCurrency>  bidCurrencyList;

@Transient
public List<BidCurrency> getBidCurrencyList() {
	return bidCurrencyList;
}
public void setBidCurrencyList(List<BidCurrency> bidCurrencyList) {
	this.bidCurrencyList = bidCurrencyList;
}
private List<BidInf>  bidInfoList;



@Transient
public List<BidInf> getBidInfoList() {
	return bidInfoList;
}
public void setBidInfoList(List<BidInf> bidInfoList) {
	this.bidInfoList = bidInfoList;
}












}
