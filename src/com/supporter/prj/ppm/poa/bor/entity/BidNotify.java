package com.supporter.prj.ppm.poa.bor.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.poa.bor.entity.base.BaseBidNotify;

@Entity
@Table(name = "ppm_bor_notify",schema="")
public class BidNotify extends BaseBidNotify {


private  String statusDesc;
@Transient
public String getStatusDesc() {
	return statusDesc;
}

public void setStatusDesc(String statusDesc) {
	this.statusDesc = statusDesc;
} 
private String NotifyPerson;
@Transient
public String getNotifyPerson() {
	return NotifyPerson;
}

public void setNotifyPerson(String notifyPerson) {
	NotifyPerson = notifyPerson;
}
}
