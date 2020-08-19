package com.supporter.prj.ppm.poa.bor.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
@MappedSuperclass
public class BaseBidNotify {
private String recordId;
private String prjId;
private String prjName;
private String prjNo;
private Date bidEndTime;
private int status;
private Date notifyTime;
public BaseBidNotify() {
	super();
}
public BaseBidNotify(String recordId, String prjId, String prjName, String prjNo, Date bidEndTime, int status,
		Date notifyTime) {
	super();
	this.recordId = recordId;
	this.prjId = prjId;
	this.prjName = prjName;
	this.prjNo = prjNo;
	this.bidEndTime = bidEndTime;
	this.status = status;
	this.notifyTime = notifyTime;
}
@Id
@GeneratedValue(generator = "assigned")
@GenericGenerator(name = "assigned", strategy = "assigned")
@Column(name = "RECORD_ID" , nullable = false, length = 32)
public String getRecordId() {
	return recordId;
}
public void setRecordId(String recordId) {
	this.recordId = recordId;
}
@Column(name = "PRJ_ID" , nullable = true, length = 32)
public String getPrjId() {
	return prjId;
}
public void setPrjId(String prjId) {
	this.prjId = prjId;
}
@Column(name = "PRJ_NAME" , nullable = true, length = 32)
public String getPrjName() {
	return prjName;
}
public void setPrjName(String prjName) {
	this.prjName = prjName;
}
@Column(name = "PRJ_NO" , nullable = true, length = 32)
public String getPrjNo() {
	return prjNo;
}
public void setPrjNo(String prjNo) {
	this.prjNo = prjNo;
}
@Column(name = "BID_END_TIME" , nullable = true, length = 32)
public Date getBidEndTime() {
	return bidEndTime;
}
public void setBidEndTime(Date bidEndTime) {
	this.bidEndTime = bidEndTime;
}
@Column(name = "STATUS" , nullable = true, length = 32)
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
@Column(name = "NOTIFY_TIME" , nullable = true, length = 32)
public Date getNotifyTime() {
	return notifyTime;
}
public void setNotifyTime(Date notifyTime) {
	this.notifyTime = notifyTime;
}
	
}
